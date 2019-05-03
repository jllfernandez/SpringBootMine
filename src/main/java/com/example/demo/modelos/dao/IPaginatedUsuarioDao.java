package com.example.demo.modelos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.entity.Usuario;

public interface IPaginatedUsuarioDao extends JpaRepository<Usuario, Long> {

}
