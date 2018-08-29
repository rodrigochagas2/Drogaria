package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.google.gson.Gson;

import br.com.drogaria.dao.PessoaDao;
import br.com.drogaria.domain.Pessoa;

public class PessoaService {

	@GET
	public String listar() {

		PessoaDao pessoaDao = new PessoaDao();
		Gson gson = new Gson();
		List<Pessoa> pessoas = pessoaDao.listar();
		String resultado = gson.toJson(pessoas);
		return resultado;
	}

	@POST
	public void salvar(String json) {

		PessoaDao pessoaDao = new PessoaDao();
		Gson gson = new Gson();
		Pessoa pessoa = gson.fromJson(json, Pessoa.class);
		pessoaDao.salvar(pessoa);

	}

	@DELETE
	public void deletar(String json) {
		PessoaDao pessoaDao = new PessoaDao();
		Gson gson = new Gson();

		Pessoa pessoa = gson.fromJson(json, Pessoa.class);
		pessoaDao.buscar(pessoa.getCodigo());

	}

	@PUT
	public void editar(String json) {

		PessoaDao pessoaDao = new PessoaDao();
		Gson gson = new Gson();
		Pessoa pessoa = gson.fromJson(json, Pessoa.class);
		pessoaDao.editar(pessoa);

	}
}