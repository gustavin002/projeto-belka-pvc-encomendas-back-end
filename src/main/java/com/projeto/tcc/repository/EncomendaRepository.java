/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.repository;

import com.projeto.tcc.model.EncomendaDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncomendaRepository extends JpaRepository<EncomendaDTO, Integer>{
    
    EncomendaDTO findByIdEncomenda(Integer idEncomenda);
    EncomendaDTO findByCodigoRastreioEncomenda(String codigoRastreioEncomenda);
    boolean existsByCodigoRastreioEncomenda(String codigo);
    List<EncomendaDTO> findByCliente_idCliente(Integer idCliente);
    List<EncomendaDTO> findByOperadorLogistico_idUsuario(Integer idOperadorLogistico);
    List<EncomendaDTO> findByOperadorLogistico_idUsuarioAndAtribuicaoEncomenda(Integer idOperadorLogistico, String atribuicaoEncomenda);
    
}