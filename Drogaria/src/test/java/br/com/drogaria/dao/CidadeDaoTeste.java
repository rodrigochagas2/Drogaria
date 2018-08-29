package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;

public class CidadeDaoTeste {
	@Test
	@Ignore	
	public void salvar() {

		EstadoDao estadoDao = new EstadoDao();
		Estado estado = estadoDao.buscar(8L);

		Cidade cidade = new Cidade();
		cidade.setNome("Matão");
		cidade.setEstado(estado);

		CidadeDao cidadeDao = new CidadeDao();
		cidadeDao.salvar(cidade);
	}

	@Test
	@Ignore
	public void listar() {

		CidadeDao cidadeDao = new CidadeDao();
		List<Cidade> cidade = cidadeDao.listar();
		System.out.println(cidade.size());

		for (Cidade resultado : cidade) {

			System.out.println("Rssultado:" + resultado.getNome());
			System.out.println("Rssultado:" + resultado.getEstado().getNome());

		}

	}

	@Test
	@Ignore
	public void buscar() {

		CidadeDao cidadeDao = new CidadeDao();
		Cidade cidade = cidadeDao.buscar(7L);

		System.out.println("Cidade:" + cidade.getNome());
		System.out.println("Estado:" + cidade.getEstado().getNome());
	}

	@Test
	@Ignore
	public void excluir() {

		CidadeDao cidadeDao = new CidadeDao();
		Cidade cidade = cidadeDao.buscar(7L);

		cidadeDao.excluir(cidade);
	}

	@Test
	@Ignore
	public void editar() {

		CidadeDao cidadeDao = new CidadeDao();
		Cidade cidade = cidadeDao.buscar(8L);

		EstadoDao estadoDao = new EstadoDao();

		Estado estado = estadoDao.buscar(4L);

		cidade.setNome("Araraquara");
		cidade.setEstado(estado);

		cidadeDao.editar(cidade);

	}
	
	@Test
	public void buscarPorEstado() {

		CidadeDao cidadeDao = new CidadeDao();
		List<Cidade> cidade = cidadeDao.buscarPorEstado(9L);
		System.out.println(cidade.size());

		for (Cidade resultado : cidade) {

			System.out.println("Rssultado:" + resultado.getNome());
			System.out.println("Rssultado:" + resultado.getEstado().getNome());

		}

	}


}
