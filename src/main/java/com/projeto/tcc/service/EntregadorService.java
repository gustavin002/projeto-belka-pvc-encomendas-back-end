/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.service;

import com.projeto.tcc.model.EncomendasDTO;
import com.projeto.tcc.model.EntregaDTO;
import com.projeto.tcc.model.EntregadorDTO;
import com.projeto.tcc.repository.EntregadorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EntregadorService extends UsuarioService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private OperadorLogisticoService operadorLogisticoService;

    @Autowired
    private EncomendaService encomendaService;

    @Autowired
    private EntregaService entregaService;

    public EntregadorDTO buscarEntregadorPorId(Integer idEntregador) {
        EntregadorDTO entregador = entregadorRepository.findByIdUsuario(idEntregador);
        
        if (entregador == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Entregador não encontrado");
        }
        
        return entregador;
    }

    public List<EntregadorDTO> listarEntregadoresDisponiveis() {
        return entregadorRepository.findByDisponibilidadeUsuario("disponível");
    }

    public EntregadorDTO cadastrarEntregador(String nome, String email, String senha) {
        
        if(senha.length() < 6 || senha.length() > 20){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Erro!!! senha deve ser maior que 6 e menor que 20 caracteres");
        }
        
        EntregadorDTO entregador = new EntregadorDTO();
        entregador.setNomeUsuario(nome);
        entregador.setEmailUsuario(email);
        entregador.setSenhaUsuario(senha);
        entregador.setDisponibilidadeUsuario("disponível");
        return entregadorRepository.save(entregador);
    }

    public List<EntregadorDTO> listarEntregadores() {
        return entregadorRepository.findAll();
    }

    public EntregadorDTO atualizarDisponibilidade(EntregadorDTO entregador, String novaDisponibilidade) {
        entregador.setDisponibilidadeUsuario(novaDisponibilidade);
        return entregadorRepository.save(entregador);
    }

    public EntregaDTO escolherEntregadorParaEncomenda(Integer idEncomenda, Integer idEntregador) {
        EncomendasDTO encomenda = encomendaService.buscarEncomendaPorId(idEncomenda);
        EntregadorDTO entregador = this.buscarEntregadorPorId(idEntregador);

        EntregaDTO entrega = new EntregaDTO();
        entrega.setEncomenda(encomenda);
        entrega.setEntregador(entregador);

        EntregaDTO entregaSalva = entregaService.salvarEntrega(entrega);

        this.atualizarDisponibilidade(entregador, "indisponível");
        
        encomendaService.atualizarAtribuicaoDaEncomenda(encomenda, "atribuída");

        operadorLogisticoService.enviarEmailEntregador(entregador, encomenda);

        return entregaSalva;
    }

}