package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.drogaria.dao.ProdutoDao;
import br.com.drogaria.domain.Produto;

@Path("produto")
public class ProdutoService {

	@GET
	public String listar() {

		ProdutoDao produtoDao = new ProdutoDao();
		Gson gson = new Gson();
		List<Produto> produtos = produtoDao.listar("descricao");
		String resultado = gson.toJson(produtos);
		return resultado;
	}

	@POST
	public void salvar(String json) {

		ProdutoDao produtoDao = new ProdutoDao();
		Gson gson = new Gson();
		Produto produto = gson.fromJson(json, Produto.class);
		produtoDao.merge(produto);
	}

	@PUT
	public void editar(String json) {

		ProdutoDao produtoDao = new ProdutoDao();
		Gson gson = new Gson();
		Produto produto = gson.fromJson(json, Produto.class);
		produtoDao.editar(produto);
	}

	@DELETE
	@Path("{codigo}")
	public String excluir(@PathParam("codigo") Long codigo) {
		ProdutoDao produtoDao = new ProdutoDao();

		Produto produto= produtoDao.buscar(codigo);
		produtoDao.excluir(produto);

		Gson gson = new Gson();
		String saida = gson.toJson(produto);
		return saida;
	}

}
