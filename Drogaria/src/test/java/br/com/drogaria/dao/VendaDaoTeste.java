package br.com.drogaria.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.Venda;

public class VendaDaoTeste {

	@Test
	public void salvar() {

		ClienteDao clienteDao = new ClienteDao();
		Cliente cliente = clienteDao.buscar(15L);

		FuncionarioDao funcionarioDao = new FuncionarioDao();
		Funcionario funcionario = funcionarioDao.buscar(19L);

		Venda venda = new Venda();
		venda.setCliente(cliente);
		venda.setFuncionario(funcionario);
		venda.setHorario(new Date()); // acertar hora
		venda.setValorTotal(new BigDecimal("13.20"));

		VendaDao vendaDao = new VendaDao();
		vendaDao.salvar(venda);

	}
	
	@Test
	public void exluir() {
		
		VendaDao vendaDao = new VendaDao();		
		Venda venda = vendaDao.buscar(10L); // acertar codigo
		vendaDao.excluir(venda);
		
	}
	
	public void alterar() {
		
		FuncionarioDao funcionarioDao = new FuncionarioDao();
		Funcionario funcionario = funcionarioDao.buscar(12L);// acertar codigo
		funcionario.setDataAdmissao(new Date());
		
		VendaDao vendaDao = new VendaDao();		
		Venda venda = vendaDao.buscar(10L); // acertar codigo
		venda.setFuncionario(funcionario);
		
		
		vendaDao.editar(venda);
		
	}
	
	public void listar() {
		
		VendaDao vendaDao = new VendaDao();
		List<Venda> venda = vendaDao.listar();
		
		System.out.println(venda.size());
		
		for(Venda resultado: venda) {
			
			System.out.println("Vendas: " + resultado.getFuncionario().getPessoa().getNome());
			
		}
	}
}
