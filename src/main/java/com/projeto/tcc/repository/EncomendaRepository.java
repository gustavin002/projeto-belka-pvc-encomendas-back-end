/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc.repository;

import com.projeto.tcc.model.EncomendasDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncomendaRepository extends JpaRepository<EncomendasDTO, Integer>{
    
    EncomendasDTO findByIdEncomenda(Integer idEncomenda);
    EncomendasDTO findByCodigoRastreioEncomenda(String codigoRastreioEncomenda);
    boolean existsByCodigoRastreioEncomenda(String codigo);
    List<EncomendasDTO> findByCliente_idCliente(Integer idCliente);
    List<EncomendasDTO> findByOperadorLogistico_idUsuario(Integer idOperadorLogistico);
    List<EncomendasDTO> findByOperadorLogistico_idUsuarioAndAtribuicaoEncomenda(Integer idOperadorLogistico, String atribuicaoEncomenda);
    
}