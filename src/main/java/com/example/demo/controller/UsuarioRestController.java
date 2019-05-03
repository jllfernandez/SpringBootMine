package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelos.entity.Usuario;
import com.example.demo.modelos.services.IUsuarioService;

@RestController
@CrossOrigin(origins = { "http://192.168.4.130:4200" })
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/usuarios")
	public ResponseEntity<?> index() {

		ResponseEntity<?> result = null;

		Map<String, Object> response = new HashMap<String, Object>();
		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioService.findAll();

		} catch (DataAccessException dae) {
			response.put("mensaje", "Error en BBDD");
			response.put("error", dae.getMessage());
			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (null == result) {
			response.put("mensaje", "Si se han encontrado usuarios.");
			response.put("content", usuarios);

			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}
		return result;

	}

	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> byId(@PathVariable Long id) {
		ResponseEntity<?> result = null;
		Map<String, Object> response = new HashMap<String, Object>();

		Usuario usuario = null;
		try {
			usuario = usuarioService.findById(id);

		} catch (DataAccessException dae) {
			response.put("mensaje", "Error en BBDD");
			response.put("error", dae.getMessage());
			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		if (null == result) {
			if (null == usuario) {
				response.put("mensaje", "El usuario".concat(id.toString()).concat(" NO se ha encontrado."));
				response.put("error", "El usuario".concat(id.toString()).concat(" NO se ha encontrado."));
				result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

			}
		}

		if (null == result) {
			response.put("mensaje", "El usuario".concat(id.toString()).concat(" se ha encontrado."));
			response.put("content", usuario);

			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}
		return result;

	}

	@GetMapping("/usuarios/page/{page}")
	public Page<Usuario> pageable(@PathVariable Integer page) {
		int sizePaginas = 4;
		Pageable pageable = PageRequest.of(page, sizePaginas);
		return usuarioService.findPaginated(pageable);

	}

	@GetMapping("/usuarios/pageMal/{page}")
	public ResponseEntity<?> pageableMal(@PathVariable Integer page) {

		ResponseEntity<?> result = null;

		Map<String, Object> response = new HashMap<String, Object>();
		Page<Usuario> usuarios = null;
		try {
			int size = 4;
			Pageable pageable = PageRequest.of(page, size);
			usuarios = usuarioService.findPaginated(pageable);

		} catch (DataAccessException dae) {
			response.put("mensaje", "Error en BBDD");
			response.put("error", dae.getMessage());
			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (null == result) {
			response.put("mensaje", "Si se han encontrado usuarios.");
			response.put("content", usuarios);

			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}
		return result;

	}

	@PostMapping("/usuarios")
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {

		ResponseEntity<?> respuesta = null;

		Usuario newUsuario = null;
		Map<String, Object> response = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo " + err.getField() + "" + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			respuesta = new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (null == respuesta) {
			try {
				newUsuario = usuarioService.save(usuario);

			} catch (DataAccessException dae) {
				response.put("mensaje", "Error al insertar en BBDD");
				response.put("error", dae.getMessage());
				respuesta = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			}

		}

		if (null == respuesta) {
			response.put("mensaje", "Usuario creado con exito.");
			response.put("content", newUsuario);

			respuesta = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		return respuesta;
	}

	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

		ResponseEntity<?> respuesta = null;

		Usuario usuarioActual = usuarioService.findById(id);

		Usuario updatedUsuario = null;
		Map<String, Object> response = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo " + err.getField() + "" + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			respuesta = new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (null == respuesta) {
			if (null == usuarioActual) {
				response.put("mensaje", "El usuario".concat(id.toString()).concat(" NO se ha encontrado."));
				response.put("error", "El usuario".concat(id.toString()).concat(" NO se ha encontrado."));
				respuesta = new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

			}

		}

		if (null == respuesta) {
			try {

				usuarioActual.setLogin(usuario.getLogin());
				usuarioActual.setPass(usuario.getPass());
				usuarioActual.setRole(usuario.getRole());

				updatedUsuario = usuarioService.save(usuarioActual);

			} catch (DataAccessException dae) {
				response.put("mensaje", "Error al actualizar en BBDD");
				response.put("error", dae.getMessage());
				respuesta = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		if (null == respuesta) {
			response.put("mensaje", "Usuario actualizado con exito.");
			response.put("content", updatedUsuario);

			respuesta = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		return respuesta;
	}

	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ResponseEntity<?> result = null;
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			usuarioService.delete(id);

		} catch (DataAccessException dae) {
			response.put("mensaje", "Error al eliminar en BBDD");
			response.put("error", dae.getMessage());
			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (null == result) {
			response.put("mensaje", "El usuario ha sido eliminado.");

			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		return result;

	}

	@GetMapping("/usuariosByLog/{login}")
	public ResponseEntity<?> byId(@PathVariable String login) {
		ResponseEntity<?> result = null;

		Map<String, Object> response = new HashMap<String, Object>();
		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioService.searchBy(login);

		} catch (DataAccessException dae) {
			response.put("mensaje", "Error en BBDD");
			response.put("error", dae.getMessage());
			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (null == result) {
			response.put("mensaje", "Si se han encontrado usuarios.");
			response.put("content", usuarios);

			result = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}
		return result;

	}

}
