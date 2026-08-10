/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.service;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.projeto.tcc.model.ClienteDTO;
import com.projeto.tcc.model.EncomendaDTO;
import com.projeto.tcc.model.EntregaDTO;
import com.projeto.tcc.model.OperadorLogisticoDTO;
import com.projeto.tcc.repository.EncomendaRepository;

@Service
public class EncomendaService {

    @Autowired
    private EncomendaRepository encomendaRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private OperadorLogisticoService operadorLogisticoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private EntregaService entregaService;
    
    public EncomendaDTO cadastrarEncomenda(Integer idOperadorLogistico, ClienteDTO clienteRequest) {
        OperadorLogisticoDTO operador = operadorLogisticoService.buscarOperadorPorId(idOperadorLogistico);
        ClienteDTO cliente = clienteService.cadastrarCliente(clienteRequest);

        EncomendaDTO encomenda = new EncomendaDTO();
        encomenda.setCodigoRastreioEncomenda(this.gerarCodigoRastreio());
        encomenda.setEnderecoAtualEncomenda("Centro de Distribuição de Origem");
        encomenda.setStatusEncomenda("em separação");
        encomenda.setAtribuicaoEncomenda("não atribuída");
        encomenda.setCliente(cliente);
        encomenda.setOperadorLogistico(operador);

        EncomendaDTO encomendaSalva = encomendaRepository.save(encomenda);

        usuarioService.enviarEmail(
                cliente.getEmailCliente(), "Belka PVC Encomendas - Código de Rastreio", "Sua encomenda foi cadastrada."
                + "\nCódigo de rastreio: " + encomendaSalva.getCodigoRastreioEncomenda());

        return encomendaSalva;   
    }
    
    public EncomendaDTO buscarEncomendaPorId(Integer idEncomenda){
        EncomendaDTO encomenda = encomendaRepository.findByIdEncomenda(idEncomenda);
        
        if(encomenda == null){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Encomenda não encontrada");
        }
        
        return encomenda;
    }
    
    public EncomendaDTO salvarEncomenda(EncomendaDTO encomenda) {
        return encomendaRepository.save(encomenda);
}
    
    public String gerarCodigoRastreio() {
        Random random = new Random();
        String codigo;
        boolean existeEncomenda;

        do {
            Integer numeroAleatorio = random.nextInt(100000) + 1;
            codigo = Integer.toString(numeroAleatorio);

            existeEncomenda = encomendaRepository.existsByCodigoRastreioEncomenda(codigo);

        } while (existeEncomenda);

        return codigo;
    }

    public EncomendaDTO rastrearEncomenda(String codigoRastreio) {
        EncomendaDTO encomenda = encomendaRepository.findByCodigoRastreioEncomenda(codigoRastreio);

        if (encomenda == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Código de rastreio não encontrado");
        }

        return encomenda;
    }
    
    public EncomendaDTO atualizarStatus(Integer idEntrega, String novoStatus) {
        EntregaDTO entrega = entregaService.verEntrega(idEntrega);
        EncomendaDTO encomenda = entrega.getEncomenda();
        encomenda.setStatusEncomenda(novoStatus);
        
        if (novoStatus.equalsIgnoreCase("em transporte")) {
            EncomendaDTO encomendaAtualizada = encomendaRepository.save(encomenda);
 
            usuarioService.enviarEmail(
                    encomenda.getCliente().getEmailCliente(),
                    "Belka PVC Encomendas",
                    "Sua encomenda está em transporte.");
 
            return encomendaAtualizada;
 
        } else if (novoStatus.equalsIgnoreCase("em rota de entrega")) {
            EncomendaDTO encomendaAtualizada = encomendaRepository.save(encomenda);
 
            usuarioService.enviarEmail(
                    encomenda.getCliente().getEmailCliente(),
                    "Belka PVC Encomendas - Código de confirmação",
                    "Sua encomenda está em rota de entrega. Código OTP: " + entrega.getCodigoOtpEntrega());
 
            return encomendaAtualizada;
 
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Status da encomenda inválido");
        }
    }
    
    public EncomendaDTO atualizarLocalAtual(Integer idEntrega, String novoLocal) {
        EntregaDTO entrega = entregaService.verEntrega(idEntrega);
        EncomendaDTO encomenda = entrega.getEncomenda();
        encomenda.setEnderecoAtualEncomenda(novoLocal);
        
        return encomendaRepository.save(encomenda);
    }
    
    public List<EncomendaDTO> listarEncomendasDoOperador(Integer idOperadorLogistico) {
        return encomendaRepository.findByOperadorLogistico_idUsuario(idOperadorLogistico);      
    }
    
    public List<EncomendaDTO> listarEncomendasNaoAtribuidasDoOperador(Integer idOperadorLogistico) {
        return encomendaRepository.findByOperadorLogistico_idUsuarioAndAtribuicaoEncomenda(idOperadorLogistico, "não atribuída");
    }
    
    public EncomendaDTO atualizarAtribuicaoDaEncomenda(EncomendaDTO encomenda, String novaAtribuicao){
        encomenda.setAtribuicaoEncomenda(novaAtribuicao);
        
        return encomendaRepository.save(encomenda);
    }

}