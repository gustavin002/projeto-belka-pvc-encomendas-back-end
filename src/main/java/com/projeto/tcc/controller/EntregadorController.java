/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.controller;

import com.projeto.tcc.model.EncomendasDTO;
import com.projeto.tcc.model.EntregaDTO;
import com.projeto.tcc.service.EncomendaService;
import com.projeto.tcc.service.EntregaService;
import com.projeto.tcc.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EntregadorController {
    
    @Autowired
    private EntregaService entregaService;
    
    @Autowired
    private EncomendaService encomendaService;
    
    @Autowired
    private TokenService tokenService;
    
    @GetMapping("/entregador/entrega/{idEntrega}")
    public EntregaDTO verEntrega(@RequestHeader("Authorization") String auth, @PathVariable Integer idEntrega) {
        String token = auth.replace("Bearer ", "");
        
        if(!tokenService.validarToken(token)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        return entregaService.verEntrega(idEntrega);
    }

    @PutMapping("/entregador/entrega/{idEntrega}/status")
    public EncomendasDTO atualizarStatus(@RequestHeader("Authorization") String auth, @PathVariable Integer idEntrega, @RequestParam String novoStatus) {
        String token = auth.replace("Bearer ", "");
        
        if(!tokenService.validarToken(token)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        return encomendaService.atualizarStatus(idEntrega, novoStatus);
    }

     @PutMapping("/entregador/entrega/{idEntrega}/local")
    public EncomendasDTO atualizarLocalAtual(@RequestHeader("Authorization") String auth, @PathVariable Integer idEntrega, @RequestParam String novoLocal) {
        String token = auth.replace("Bearer ", "");
        
        if(!tokenService.validarToken(token)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        return encomendaService.atualizarLocalAtual(idEntrega, novoLocal);
    }

    @PostMapping("/entregador/entrega/{idEntrega}/validar/otp")
    public EntregaDTO validarOTP(@RequestHeader("Authorization") String auth, @PathVariable Integer idEntrega, @RequestParam String otpDigitado) {
        String token = auth.replace("Bearer ", "");
        
        if(!tokenService.validarToken(token)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado");
        }
        
        return entregaService.validarOTP(idEntrega, otpDigitado);
    }

}