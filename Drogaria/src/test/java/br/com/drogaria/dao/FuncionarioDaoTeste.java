package br.com.drogaria.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.Pessoa;

public class FuncionarioDaoTeste {
	
	@Test	
	public void salvar() {
		
		PessoaDao pessoaDao = new PessoaDao();
		Pessoa pessoa =pessoaDao.buscar(11L);		
		
		Funcionario funcionario = new Funcionario();		
		funcionario.setCarteiraTrabalho("23232");
		funcionario.setDataAdmissao(new Date());
		funcionario.setPessoa(pessoa);
		
		FuncionarioDao funcionarioDao = new FuncionarioDao();
		funcionarioDao.salvar(funcionario);
		
		System.out.println("Funcionario Salvo");
	}
	
	@Test
	@Ignore
	public void alterar() {
		
		FuncionarioDao funcionarioDao = new FuncionarioDao();
		Funcionario funcionario = funcionarioDao.buscar(17L);
		
		funcionario.setCarteiraTrabalho("6969");
		
		funcionarioDao.editar(funcionario);		
	}
	
	@Test
	@Ignore
	public void listar() {
		
		FuncionarioDao funcionarioDao = new FuncionarioDao();
		List<Funcionario> funcionarios = funcionarioDao.listar();
		
		for(Funcionario resultado: funcionarios) {
			
			System.out.println("Funcioario : " + resultado.getPessoa().getNome());
		}
	}
	
	
	@Test
	@Ignore
	public void excluir() {
		
		FuncionarioDao funcionarioDao = new FuncionarioDao();
		Funcionario funcionario = funcionarioDao.buscar(17L);
		
		System.out.println("Excluido: " + funcionario.getPessoa().getNome());
		
		funcionarioDao.excluir(funcionario);
	}

}
