/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.controller;

import com.projeto.tcc.model.EncomendaDTO;
import com.projeto.tcc.model.UsuarioDTO;
import com.projeto.tcc.service.EncomendaService;
import com.projeto.tcc.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EncomendaController {

    @Autowired
    private EncomendaService encomendaService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/operador/encomendas")
    public List<EncomendaDTO> listarEncomendasDoOperador(@RequestHeader("Authorization") String auth) {
        String token = auth.replace("Bearer ", "");
        
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        UsuarioDTO usuario = tokenService.extrairClaim(token);

        return encomendaService.listarEncomendasDoOperador(usuario.getIdUsuario());
    }
    
    @GetMapping("/operador/encomendas/nao/atribuidas")
    public List<EncomendaDTO> listarEncomendasNaoAtribuidasDoOperador(@RequestHeader("Authorization") String auth) {
        String token = auth.replace("Bearer ", "");
        
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        UsuarioDTO usuario = tokenService.extrairClaim(token);

        return encomendaService.listarEncomendasNaoAtribuidasDoOperador(usuario.getIdUsuario());
    }

}