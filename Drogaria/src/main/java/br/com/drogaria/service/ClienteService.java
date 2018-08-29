package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.google.gson.Gson;

import br.com.drogaria.dao.ClienteDao;
import br.com.drogaria.domain.Cliente;

public class ClienteService {

	@GET
	public String listar() {

		ClienteDao clienteDao = new ClienteDao();
		Gson gson = new Gson();
		List<Cliente> clientes = clienteDao.listar();
		String resultado = gson.toJson(clientes);
		return resultado;

	}

	@POST
	public void salvar(String json) {

		ClienteDao clienteDao = new ClienteDao();
		Gson gson = new Gson();
		Cliente cliente = gson.fromJson(json, Cliente.class);
		clienteDao.merge(cliente);

	}

	@PUT
	public void editar(String json) {

		ClienteDao clienteDao = new ClienteDao();
		Gson gson = new Gson();
		Cliente cliente = gson.fromJson(json, Cliente.class);
		clienteDao.editar(cliente);

	}
	
	@DELETE
	public void excluir(String json) {
		
		ClienteDao clienteDao = new ClienteDao();
		Gson gson = new Gson();
		Cliente cliente = gson.fromJson(json, Cliente.class);
		clienteDao.buscar(cliente.getCodigo());
		clienteDao.excluir(cliente);
		
	}
	

}
