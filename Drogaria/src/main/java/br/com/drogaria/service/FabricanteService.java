package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.drogaria.dao.FabricanteDao;
import br.com.drogaria.domain.Fabricante;

// http://127.0.0.1:8080/Drogaria/rest/fabricante
@Path("fabricante")
public class FabricanteService {
	
	
	@GET
	public String listar() {

		FabricanteDao fabricanteDao = new FabricanteDao();
		List<Fabricante> fabricantes = fabricanteDao.listar("descricao");
		Gson gson = new Gson();
		String json = gson.toJson(fabricantes);
		return json;
		
		
	}

	// http://127.0.0.1:8080/Drogaria/rest/fabricante/2
	@GET
	@Path("{codigo}")
	public String buscar(@PathParam("codigo") Long codigo) {

		FabricanteDao fabricanteDao = new FabricanteDao();
		Fabricante fabricante = new Fabricante();
		fabricante = fabricanteDao.buscar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(fabricante);

		return json;

	}

	@POST
	public String salvar(String json) {

		Gson gson = new Gson();

		Fabricante fabricante = gson.fromJson(json, Fabricante.class);
		FabricanteDao fabricanteDao = new FabricanteDao();
		fabricanteDao.merge(fabricante);

		String resultado = gson.toJson(fabricante);
		return resultado;

	}

	@PUT
	public String editar(String json) {

		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);

		FabricanteDao fabricanteDao = new FabricanteDao();
		fabricanteDao.editar(fabricante);

		String resultado = gson.toJson(fabricante);
		return resultado;
	}

	@DELETE
	@Path("{codigo}")
	public String excluir(@PathParam("codigo") Long codigo){
		FabricanteDao fabricanteDAO = new FabricanteDao();
		
		Fabricante fabricante = fabricanteDAO.buscar(codigo);
		fabricanteDAO.excluir(fabricante);
		
		Gson gson = new Gson();
		String saida = gson.toJson(fabricante);
		return saida;
	}

}
