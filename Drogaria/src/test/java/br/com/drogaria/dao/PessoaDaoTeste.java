package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Pessoa;

public class PessoaDaoTeste {

	@Test

	public void salvar() {

		Pessoa pessoa = new Pessoa();
		Cidade cidade = new Cidade();
		CidadeDao cidadeDao = new CidadeDao();
		cidade = cidadeDao.buscar(9L);

		pessoa.setBairro("Vila Furini");
		pessoa.setCelular("16996034352");
		pessoa.setCep("15990312");
		pessoa.setComplemento("Fundos");
		pessoa.setCpf("35340650855");
		pessoa.setEmail("rodrigochagas2@gmail.com");
		pessoa.setNome("Beatriz");
		pessoa.setNumero(new Short("900"));
		pessoa.setRg("32434343");
		pessoa.setRua("Francisco Carlos Martins");
		pessoa.setTelefone("33824030");
		pessoa.setCidade(cidade);

		PessoaDao pessoaDao = new PessoaDao();

		pessoaDao.salvar(pessoa);
	}

	@Test
	@Ignore
	public void excluir() {

		PessoaDao pessoaDao = new PessoaDao();
		Pessoa pessoa = pessoaDao.buscar(14L);

		System.out.println("Excluido " + pessoa.getNome());
		pessoaDao.excluir(pessoa);

	}

	@Test
	@Ignore
	public void editar() {

		PessoaDao pessoaDao = new PessoaDao();
		Pessoa pessoa = pessoaDao.buscar(12L);
		pessoa.setNome("Rodrigo");

		pessoaDao.editar(pessoa);

	}

	@Test
	@Ignore
	public void listar() {

		PessoaDao pessoaDao = new PessoaDao();
		List<Pessoa> pessoa = pessoaDao.listar();
		System.out.println(pessoa.size());

		for (Pessoa resultado : pessoa) {

			System.out.println("Nome : " + resultado.getNome());

		}
	}
}
