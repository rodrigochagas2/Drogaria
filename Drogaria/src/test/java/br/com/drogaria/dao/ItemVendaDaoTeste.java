package br.com.drogaria.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;

public class ItemVendaDaoTeste {
	
	@Test
	public void salvar() {
		
		
		ItemVenda itemVenda = new ItemVenda();
		
		ProdutoDao produtoDao = new ProdutoDao();
		Produto produto = produtoDao.buscar(10L);
		
		itemVenda.setProduto(produto);
		itemVenda.setQuantidade(new Short("10"));
		itemVenda.setValorParcial(new BigDecimal("10.70"));
		
		
		ItemVendaDao itemVendaDao = new ItemVendaDao();	
		
	}
	
	@Test
	public void alterar() {
		
		ItemVendaDao itemVendaDao = new ItemVendaDao();
		ItemVenda itemVenda = itemVendaDao.buscar(10L);// Acertar o Id
		
		itemVenda.setQuantidade(new Short("10"));
		itemVendaDao.editar(itemVenda);
		
		
	}
	
	@Test
	public void excluir() {
		
		ItemVendaDao itemVendaDao = new ItemVendaDao();
		ItemVenda itemVenda = itemVendaDao.buscar(10L);// Acertar o Id
		itemVendaDao.excluir(itemVenda);	
		
	}
	
	@Test
	public void listar() {
		
		ItemVendaDao itemVendaDao = new ItemVendaDao();
		List<ItemVenda> itemVenda = itemVendaDao.listar();
		
		System.out.println(itemVenda.size());
		
		for(ItemVenda resultado: itemVenda) {
			
			System.out.println("Lista: " + resultado.getProduto().getDescricao());
		}
	}

}
