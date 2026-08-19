/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.service;

import com.projeto.tcc.model.EncomendasDTO;
import com.projeto.tcc.model.EntregaDTO;
import com.projeto.tcc.model.EntregadorDTO;
import com.projeto.tcc.repository.EntregaRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EntregaService {
    
    @Autowired
    private EntregaRepository entregaRepository;
    
    @Autowired
    private EntregadorService entregadorService;
    
    @Autowired
    private EncomendaService encomendaService;
    
    @Autowired
    private UsuarioService usuarioService;

    public String gerarCodigoOtp() {
        Random random = new Random();
        Integer numero = random.nextInt(100000) + 1;
        return String.format("%06d", numero);
    }
    
    public EntregaDTO verEntrega(Integer idEntrega) {
        EntregaDTO entrega = entregaRepository.findByIdEntrega(idEntrega);
        
        if (entrega == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Entrega não encontrada");
        }
        
        return entrega;
    }
    
    public EntregaDTO salvarEntrega(EntregaDTO entrega) {
        return entregaRepository.save(entrega);
    }
    
    public List<EntregaDTO> listarEntregasDoEntregador(Integer idEntregador) {
        EntregadorDTO entregador = entregadorService.buscarEntregadorPorId(idEntregador);
        
        return entregaRepository.findByEntregador(entregador);
    }
    
    public EntregaDTO validarOTP(Integer idEntrega, String otpDigitado) {
        EntregaDTO entrega = this.verEntrega(idEntrega);

        if (!entrega.getCodigoOtpEntrega().equals(otpDigitado)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Código OTP inválido");
        }

        EncomendasDTO encomenda = entrega.getEncomenda();
        encomenda.setStatusEncomenda("entregue");
        encomendaService.salvarEncomenda(encomenda);

        LocalDateTime dataConclusao = LocalDateTime.now().withNano(0);
        entrega.setDataHoraEntrega(dataConclusao);
        EntregaDTO entregaAtualizada = entregaRepository.save(entrega);

        entregadorService.atualizarDisponibilidade(entrega.getEntregador(), "disponível");

        usuarioService.enviarEmail(encomenda.getCliente().getEmailCliente(),
            "Belka PVC Encomendas - Entrega concluída", "Sua encomenda foi entregue em: " + dataConclusao.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " as "));

        return entregaAtualizada;
    }

}