/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.controller;

import com.projeto.tcc.model.ClienteDTO;
import com.projeto.tcc.model.EncomendasDTO;
import com.projeto.tcc.model.EntregaDTO;
import com.projeto.tcc.model.EntregadorDTO;
import com.projeto.tcc.model.UsuarioDTO;
import com.projeto.tcc.service.EncomendaService;
import com.projeto.tcc.service.EntregadorService;
import com.projeto.tcc.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class OperadorLogisticoController {
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private EncomendaService encomendaService;
    
    @Autowired
    private EntregadorService entregadorService;

    @PostMapping("/operador/cadastrar/encomendas")
    public EncomendasDTO cadastrarEncomenda(@RequestHeader("Authorization") String auth, @RequestBody ClienteDTO clienteRequest) {
        String token = auth.replace("Bearer ", "");
        
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        UsuarioDTO usuario = tokenService.extrairClaim(token);

        return encomendaService.cadastrarEncomenda(usuario.getIdUsuario(), clienteRequest);
    }

    @GetMapping("/operador/entregadores/disponiveis")
    public List<EntregadorDTO> listarEntregadoresDisponiveis(@RequestHeader("Authorization") String auth) {
        String token = auth.replace("Bearer ", "");

        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        return entregadorService.listarEntregadoresDisponiveis();
    }

    @PostMapping("/operador/escolher/entregador")
    public EntregaDTO escolherEntregadorParaEncomenda(@RequestHeader("Authorization") String auth, @RequestParam Integer idEncomenda, @RequestParam Integer idEntregador) {
        String token = auth.replace("Bearer ", "");
        
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        return entregadorService.escolherEntregadorParaEncomenda(idEncomenda, idEntregador);
    }
    
}