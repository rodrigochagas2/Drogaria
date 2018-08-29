package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Estado;

public class EstadoDaoTeste {

	@Test
	
	public void Salvar() {

		Estado estado = new Estado();
		estado.setNome("São Paulo");
		estado.setSigla("SP");
		EstadoDao estadoDao = new EstadoDao();
		estadoDao.salvar(estado);
	}

	@Test
	@Ignore
	public void listar() {

		EstadoDao estadoDao = new EstadoDao();
		List<Estado> resultado = estadoDao.listar();
		System.out.println("Total de Registros encontrados " + resultado.size());

		for (Estado estado : resultado) {

			System.out.println("Lista:" + estado.getSigla() + " " + estado.getNome());
		}
	}

	@Test
	@Ignore
	public void buscar() {

		EstadoDao estadoDao = new EstadoDao();
		Estado estado = estadoDao.buscar(2L);

		if (estado == null) {

			System.out.println("Nenhum registor encontrando");
		} else {
			System.out.println("Lista" + estado.getNome());
		}
	}

	@Test
	@Ignore
	public void excluir() {

		EstadoDao estadoDao = new EstadoDao();
		Estado estado = estadoDao.buscar(2L);

		if (estado == null) {
			System.out.println("Nenhum registor encontrando");
		} else {
			estadoDao.excluir(estado);
			System.out.println("Registro Excluido");
		}
	}
	
	@Test
	public void editar() {
		
		EstadoDao estadoDao = new EstadoDao();		
		Estado estado = estadoDao.buscar(1L);
		
		estado.setNome("Sao Paulo");
		estado.setSigla("SP");
		
		estadoDao.editar(estado);
	}
	
}
