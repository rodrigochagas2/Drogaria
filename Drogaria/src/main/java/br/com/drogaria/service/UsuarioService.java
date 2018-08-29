package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.google.gson.Gson;

import br.com.drogaria.dao.UsuarioDao;
import br.com.drogaria.domain.Usuario;

public class UsuarioService {

	@GET
	public String listar() {
		UsuarioDao usuarioDao = new UsuarioDao();
		Gson gson = new Gson();
		List<Usuario> usuarios = usuarioDao.listar();

		String saida = gson.toJson(usuarios);

		return saida;
	}
	
	@POST
	public void salvar(String json) {
		
		UsuarioDao usuarioDao = new UsuarioDao();
		Gson gson = new Gson();
		Usuario usuario = gson.fromJson(json, Usuario.class);
		usuarioDao.salvar(usuario);
		
	}
	
	@PUT
	public void editar(String json) {
		
		UsuarioDao usuarioDao = new UsuarioDao();
		Gson gson = new Gson();
		Usuario usuario = gson.fromJson(json, Usuario.class);
		usuarioDao.editar(usuario);
		
	}
	
	@DELETE
	public void excluir(String json) {
		
		UsuarioDao usuarioDao = new UsuarioDao();
		Gson gson = new Gson();
		Usuario usuario = gson.fromJson(json, Usuario.class);
		usuarioDao.buscar(usuario.getCodigo());
		usuarioDao.excluir(usuario);
		
		
	}

}
