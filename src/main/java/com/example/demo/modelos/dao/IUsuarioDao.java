package com.example.demo.modelos.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.modelos.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

}
