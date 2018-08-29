package br.com.drogaria.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;

public class ProdutoDaoTeste {

	@Test
	@Ignore
	public void salvar() {

		FabricanteDao fabricanteDao = new FabricanteDao();
		Produto produto = new Produto();
		ProdutoDao produtoDao = new ProdutoDao();

		produto.setDescricao("coristina");

		Fabricante fabricante = fabricanteDao.buscar(6L);
		produto.setFabricante(fabricante);

		produto.setPreco(new BigDecimal("10.70"));
		produto.setQuantidade(new Short("9"));

		produtoDao.salvar(produto);

		// fazer ItemVenda
		// fazer Pessoa
	}

	@Test
	@Ignore
	public void listar() {

		ProdutoDao produtoDao = new ProdutoDao();

		List<Produto> produto = produtoDao.listar();
		System.out.println(produto.size());

		for (Produto resultado : produto) {

			System.out.println("Produto : " + resultado.getDescricao());
			System.out.println("Fabricante " + resultado.getFabricante().getDescricao());
			System.out.println("Preço " + resultado.getPreco());
			System.out.println();

		}
	}

	@Test
	@Ignore
	public void editar() {

		ProdutoDao produtoDao = new ProdutoDao();
		Produto produto = produtoDao.buscar(9L);
		
		System.out.println("Produto que vai ser alterado: " + produto.getDescricao());
		produto.setDescricao("Energil C");
		produtoDao.editar(produto);
		
		System.out.println("Produto alterado para : " + produto.getDescricao());
	}
	
	@Test
	public void excluir() {
		
		ProdutoDao produtoDao = new ProdutoDao();
		Produto produto = produtoDao.buscar(9L);

		System.out.println("Produto Excluido :" + produto.getDescricao());
		
		produtoDao.excluir(produto);
		
	}
	
	
	
}
