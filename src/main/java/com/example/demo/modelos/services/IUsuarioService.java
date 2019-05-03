package com.example.demo.modelos.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.modelos.entity.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll();

	public Usuario findById(Long id);

	public Page<Usuario> findPaginated(Pageable pageable);

	public Usuario save(Usuario usuario);

	public void delete(Long id);
}
