/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.service;

import com.projeto.tcc.model.EncomendasDTO;
import com.projeto.tcc.model.EntregadorDTO;
import com.projeto.tcc.model.OperadorLogisticoDTO;
import com.projeto.tcc.repository.OperadorLogisticoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OperadorLogisticoService extends UsuarioService {

    @Autowired
    private OperadorLogisticoRepository operadorLogisticoRepository;

    public OperadorLogisticoDTO buscarOperadorPorId(Integer idOperadorLogistico) {
        OperadorLogisticoDTO operador = operadorLogisticoRepository.findByIdUsuario(idOperadorLogistico);
    
    if (operador == null) {
        throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Operador logístico não encontrado");
    }

        return operador; 
    }
    
    public OperadorLogisticoDTO cadastrarOperadorLogistico(String nome, String email, String senha) {
        
        if(senha.length() < 6 || senha.length() > 20){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Erro!!! senha deve ser maior que 6 e menor que 20 caracteres");
        }
        
        OperadorLogisticoDTO operador = new OperadorLogisticoDTO();
        operador.setNomeUsuario(nome);
        operador.setEmailUsuario(email);
        operador.setSenhaUsuario(senha);
        operador.setDisponibilidadeUsuario("disponível");

        return operadorLogisticoRepository.save(operador);
    }

    public List<OperadorLogisticoDTO> listarOperadoresLogisticos() {
        return operadorLogisticoRepository.findAll();
    }

    public void enviarEmailEntregador(EntregadorDTO entregador, EncomendasDTO encomenda) {
        this.enviarEmail(entregador.getEmailUsuario(),"Belka PVC Encomendas - Nova entrega atribuída", "Cliente: " +
            encomenda.getCliente().getNomeCliente()
            + "\nEndereço de entrega: " + encomenda.getCliente().getEnderecoCliente());
    }

}