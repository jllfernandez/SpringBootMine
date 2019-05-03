package com.example.demo.modelos.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.modelos.dao.IPaginatedUsuarioDao;
import com.example.demo.modelos.dao.IUsuarioDao;
import com.example.demo.modelos.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private IPaginatedUsuarioDao paginatedUsuarioDao;

	@Override
	@Transactional // (readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional // (readOnly = true)
	public Usuario findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional // (readOnly = true)
	public Page<Usuario> findPaginated(Pageable pageable) {
		return paginatedUsuarioDao.findAll(pageable);
	}

	// @Override
	@Transactional // (readOnly = true)
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	// @Override
	@Transactional // (readOnly = true)
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}

	public List<Usuario> searchBy(String usuariocode) {
		return (List<Usuario>) usuarioDao.searchBy(usuariocode);
	}

}
