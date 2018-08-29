package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.drogaria.dao.EstadoDao;
import br.com.drogaria.domain.Estado;

@Path("estado")
public class EstadoService {

	@POST
	public void salvar(String json) {

		EstadoDao estadoDao = new EstadoDao();
		Gson gson = new Gson();
		Estado estado = gson.fromJson(json, Estado.class);
		estadoDao.salvar(estado);
	}

	@GET
	public String listar() {

		EstadoDao estadoDao = new EstadoDao();
		Gson gson = new Gson();
		List<Estado> estados = estadoDao.listar();
		String resultado = gson.toJson(estados);
		return resultado;
	}
	
	@PUT
	public void editar(String json) {

		EstadoDao estadoDao = new EstadoDao();
		Gson gson = new Gson();
		Estado estado = gson.fromJson(json, Estado.class);
		estadoDao.editar(estado);
	}
	
	@DELETE
	public void excluir(String json) {
		
		EstadoDao estadoDao = new EstadoDao();
		Gson gson = new Gson();
		Estado estado = gson.fromJson(json, Estado.class);
		estadoDao.excluir(estado);
	}
	
	@GET
	@Path("{codigo}")
	public String buscar(@PathParam("codigo") Long codigo) {

		EstadoDao EstadoDao = new EstadoDao();
		Estado Estado = new Estado();
		Estado = EstadoDao.buscar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(Estado);

		return json;

	}
}
