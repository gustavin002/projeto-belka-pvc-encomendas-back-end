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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_entrega")
public class EntregaDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    private Integer idEntrega;
    
    @Column(name = "data_hora_entrega")
    private LocalDateTime dataHoraEntrega;
 
    @Column(name = "codigo_otp_entrega")
    private String codigoOtpEntrega;
 
    @OneToOne
    @JoinColumn(name = "id_encomenda", nullable = false)
    private EncomendaDTO encomenda;
 
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private EntregadorDTO entregador;

    public Integer getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }

    public LocalDateTime getDataHoraEntrega() {
        return dataHoraEntrega;
    }

    public void setDataHoraEntrega(LocalDateTime dataHoraEntrega) {
        this.dataHoraEntrega = dataHoraEntrega;
    }

    public String getCodigoOtpEntrega() {
        return codigoOtpEntrega;
    }

    public void setCodigoOtpEntrega(String codigoOtpEntrega) {
        this.codigoOtpEntrega = codigoOtpEntrega;
    }

    public EncomendaDTO getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(EncomendaDTO encomenda) {
        this.encomenda = encomenda;
    }

    public EntregadorDTO getEntregador() {
        return entregador;
    }

    public void setEntregador(EntregadorDTO entregador) {
        this.entregador = entregador;
    }
    
}