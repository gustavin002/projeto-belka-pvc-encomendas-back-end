/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.controller;

import com.projeto.tcc.model.EncomendasDTO;
import com.projeto.tcc.service.EncomendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    
    @Autowired
    private EncomendaService encomendaService;

    @GetMapping("/cliente/rastreio/{codigoRastreio}")
    public EncomendasDTO rastrearEncomenda(@PathVariable String codigoRastreio) {
        return encomendaService.rastrearEncomenda(codigoRastreio);
    }
    
}