package br.com.drogaria.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Pessoa;

public class ClienteDaoTeste {

	@Test	
	public void salvar() throws ParseException {

		PessoaDao pessoaDao = new PessoaDao();
		Pessoa pessoa = pessoaDao.buscar(10L);

		Cliente cliente = new Cliente();
		cliente.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse("09/06/2015"));
		cliente.setLiberado(true);
		cliente.setPessoa(pessoa);

		ClienteDao clienteDao = new ClienteDao();
		clienteDao.salvar(cliente);
	}
	
	@Ignore
	public void excluir() {
		
		ClienteDao clienteDao = new ClienteDao();
		Cliente cliente = clienteDao.buscar(10L); // acertar codigo
		
		clienteDao.excluir(cliente);
	}
	
	@Ignore
	public void alterar() {
		
		ClienteDao clienteDao = new ClienteDao();
		Cliente cliente  = clienteDao.buscar(10L); // acertar codigo
		
		cliente.setLiberado(false);
		
		clienteDao.editar(cliente);
		
		
	}
	@Ignore	
	public void listar() {
		
		ClienteDao clienteDao = new ClienteDao();
		List<Cliente> cliente = clienteDao.listar();
		
		System.out.println("Lista: " + cliente.size());
		
		for(Cliente resultado: cliente) {
			
			System.out.println("Lista de Clientes: + " + resultado.getPessoa().getNome());
		}
		
	}

}
