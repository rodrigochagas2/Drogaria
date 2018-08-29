package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Fabricante;

public class FabricanteDaoTeste {

	@Test
	@Ignore
	public void salvar() {

		Fabricante fabricante = new Fabricante();
		fabricante.setDescricao("Medley");

		FabricanteDao fabricanteDao = new FabricanteDao();
		fabricanteDao.salvar(fabricante);
	}

	@Test
	@Ignore
	public void listar() {

		FabricanteDao fabricanteDao = new FabricanteDao();
		List<Fabricante> resultado = fabricanteDao.listar();

		System.out.println("Qtde:" + resultado.size());

		for (Fabricante fabricante : resultado) {
			System.out.println("Lista de Fabricantes: " + fabricante.getDescricao());
		}
	}

	@Test
	@Ignore
	public void buscar() {

		FabricanteDao fabricanteDao = new FabricanteDao();

		Fabricante fabricante = fabricanteDao.buscar(3L);

		System.out.println(fabricante.getDescricao());

	}

	@Test
	@Ignore
	public void excluir() {

		FabricanteDao fabricanteDao = new FabricanteDao();

		Fabricante fabricante = fabricanteDao.buscar(3L);

		if (fabricante == null) {

			System.out.println("Nenhum registro encontrado");
		} else {

			fabricanteDao.excluir(fabricante);
			System.out.println("Registro:" + fabricante.getDescricao() + " Excluido com Sucesso");

		}
	}

		@Test
		@Ignore
	public void editar() {

		FabricanteDao fabricanteDao = new FabricanteDao();
		Fabricante fabricante = fabricanteDao.buscar(5L);

		if (fabricante == null) {

			System.out.println("Nenhum registro encontrado");
		} else {

			fabricante.setDescricao("Alterado");
			fabricanteDao.editar(fabricante);
		}

	}
		
		@Test		
		public void merge() {
/*
			Fabricante fabricante = new Fabricante();
			fabricante.setDescricao("Anché");
			FabricanteDao fabricanteDao = new FabricanteDao();
			fabricanteDao.merge(fabricante);
			*/
			
			FabricanteDao fabricanteDao = new FabricanteDao();
			Fabricante fabricante = fabricanteDao.buscar(6L);
			fabricante.setDescricao("Fabricante B");
			fabricanteDao.merge(fabricante);
			
		}

}
