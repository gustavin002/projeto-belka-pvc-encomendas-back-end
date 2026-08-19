/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_encomenda")
public class EncomendasDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_encomenda")
    private Integer idEncomenda;

    @Column(name = "codigo_rastreio_encomenda", nullable = false, unique = true)
    private String codigoRastreioEncomenda;

    @Column(name = "endereco_atual_encomenda", nullable = false)
    private String enderecoAtualEncomenda;

    @Column(name = "status_encomenda", nullable = false)
    private String statusEncomenda;

    @Column(name = "atribuicao_encomenda", nullable = false)
    private String atribuicaoEncomenda;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteDTO cliente;

    @ManyToOne
    @JoinColumn(name = "id_operador_logistico", nullable = false)
    private OperadorLogisticoDTO operadorLogistico;

    public Integer getIdEncomenda() {
        return idEncomenda;
    }

    public void setIdEncomenda(Integer idEncomenda) {
        this.idEncomenda = idEncomenda;
    }

    public String getCodigoRastreioEncomenda() {
        return codigoRastreioEncomenda;
    }

    public void setCodigoRastreioEncomenda(String codigoRastreioEncomenda) {
        this.codigoRastreioEncomenda = codigoRastreioEncomenda;
    }

    public String getEnderecoAtualEncomenda() {
        return enderecoAtualEncomenda;
    }

    public void setEnderecoAtualEncomenda(String enderecoAtualEncomenda) {
        this.enderecoAtualEncomenda = enderecoAtualEncomenda;
    }

    public String getStatusEncomenda() {
        return statusEncomenda;
    }

    public void setStatusEncomenda(String statusEncomenda) {
        this.statusEncomenda = statusEncomenda;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public OperadorLogisticoDTO getOperadorLogistico() {
        return operadorLogistico;
    }

    public void setOperadorLogistico(OperadorLogisticoDTO operadorLogistico) {
        this.operadorLogistico = operadorLogistico;
    }

    public String getAtribuicaoEncomenda() {
        return atribuicaoEncomenda;
    }

    public void setAtribuicaoEncomenda(String atribuicaoEncomenda) {
        this.atribuicaoEncomenda = atribuicaoEncomenda;
    }

}