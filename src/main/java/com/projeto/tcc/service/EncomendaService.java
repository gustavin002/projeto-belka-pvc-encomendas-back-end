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
import com.projeto.tcc.model.EncomendasDTO;
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

    public EncomendasDTO cadastrarEncomenda(Integer idOperadorLogistico, ClienteDTO clienteRequest) {
        OperadorLogisticoDTO operador = operadorLogisticoService.buscarOperadorPorId(idOperadorLogistico);
        ClienteDTO cliente = clienteService.cadastrarCliente(clienteRequest);

        EncomendasDTO encomenda = new EncomendasDTO();
        encomenda.setCodigoRastreioEncomenda(this.gerarCodigoRastreio());
        encomenda.setEnderecoAtualEncomenda("Centro de Distribuição de Origem");
        encomenda.setStatusEncomenda("em separação");
        encomenda.setAtribuicaoEncomenda("não atribuída");
        encomenda.setCliente(cliente);
        encomenda.setOperadorLogistico(operador);

        EncomendasDTO encomendaSalva = encomendaRepository.save(encomenda);

        usuarioService.enviarEmail(
                cliente.getEmailCliente(), "Belka PVC Encomendas - Código de Rastreio", "Sua encomenda foi cadastrada."
                + "\nCódigo de rastreio: " + encomendaSalva.getCodigoRastreioEncomenda());

        return encomendaSalva;
    }

    public EncomendasDTO buscarEncomendaPorId(Integer idEncomenda) {
        EncomendasDTO encomenda = encomendaRepository.findByIdEncomenda(idEncomenda);

        if (encomenda == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Encomenda não encontrada");
        }

        return encomenda;
    }

    public EncomendasDTO salvarEncomenda(EncomendasDTO encomenda) {
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

    public EncomendasDTO rastrearEncomenda(String codigoRastreio) {
        EncomendasDTO encomenda = encomendaRepository.findByCodigoRastreioEncomenda(codigoRastreio);

        if (encomenda == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Código de rastreio não encontrado");
        }

        return encomenda;
    }

    public EncomendasDTO atualizarStatus(Integer idEntrega, String novoStatus) {
        EntregaDTO entrega = entregaService.verEntrega(idEntrega);
        EncomendasDTO encomenda = entrega.getEncomenda();
        List<String> sequencia = List.of("em transporte", "em rota de entrega");
 
        String statusAtual = encomenda.getStatusEncomenda();

        if (sequencia.indexOf(novoStatus) != sequencia.indexOf(statusAtual) + 1) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Ordem de transição da encomenda incorreta, siga a ordem correta");
        }
 
        encomenda.setStatusEncomenda(novoStatus);
 
        if (novoStatus.equalsIgnoreCase("em transporte")) {
            EncomendasDTO encomendaAtualizada = encomendaRepository.save(encomenda);
 
            usuarioService.enviarEmail(
                    encomenda.getCliente().getEmailCliente(),
                    "Belka PVC Encomendas - Em transporte",
                    "Sua encomenda está em transporte.");
 
            return encomendaAtualizada;

        } else if (novoStatus.equalsIgnoreCase("em rota de entrega")) {
            String codigoOtp = entregaService.gerarCodigoOtp();
            entrega.setCodigoOtpEntrega(codigoOtp);
            entregaService.salvarEntrega(entrega);
            
            EncomendasDTO encomendaAtualizada = encomendaRepository.save(encomenda);

            usuarioService.enviarEmail(
                    encomenda.getCliente().getEmailCliente(),
                    "Belka PVC Encomendas - Código de confirmação",
                    "Sua encomenda está em rota de entrega.\nCódigo OTP: " + entrega.getCodigoOtpEntrega());

            return encomendaAtualizada;

        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Status da encomenda inválido");
        }

    }

    public EncomendasDTO atualizarLocalAtual(Integer idEntrega, String novoLocal) {
        EntregaDTO entrega = entregaService.verEntrega(idEntrega);
        EncomendasDTO encomenda = entrega.getEncomenda();
        encomenda.setEnderecoAtualEncomenda(novoLocal);

        return encomendaRepository.save(encomenda);
    }

    public List<EncomendasDTO> listarEncomendasDoOperador(Integer idOperadorLogistico) {
        return encomendaRepository.findByOperadorLogistico_idUsuario(idOperadorLogistico);
    }

    public List<EncomendasDTO> listarEncomendasNaoAtribuidasDoOperador(Integer idOperadorLogistico) {
        return encomendaRepository.findByOperadorLogistico_idUsuarioAndAtribuicaoEncomenda(idOperadorLogistico, "não atribuída");
    }

    public EncomendasDTO atualizarAtribuicaoDaEncomenda(EncomendasDTO encomenda, String novaAtribuicao) {
        encomenda.setAtribuicaoEncomenda(novaAtribuicao);

        return encomendaRepository.save(encomenda);
    }

}
