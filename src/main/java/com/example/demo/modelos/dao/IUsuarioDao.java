package com.example.demo.modelos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelos.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	@Query("SELECT usuario_ from Usuario usuario_ " + " LEFT JOIN FETCH usuario_.role role_ "
			+ "WHERE usuario_.login = :login")
	public List<Usuario> searchBy(@Param("login") String login);

}
