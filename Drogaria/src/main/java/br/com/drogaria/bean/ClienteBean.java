package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.ClienteDao;
import br.com.drogaria.dao.PessoaDao;
import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private Cliente cliente;

	private List<Cliente> clientes;
	private List<Pessoa> pessoas;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	@PostConstruct
	public void listar() {
		try {
			ClienteDao ClienteDao = new ClienteDao();
			clientes = ClienteDao.listar("dataCadastro");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os clientes");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			cliente = new Cliente();

			PessoaDao PessoaDao = new PessoaDao();
			pessoas = PessoaDao.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo cliente");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ClienteDao ClienteDao = new ClienteDao();
			PessoaDao pessoaDao = new PessoaDao();
			ClienteDao.merge(cliente);

			cliente = new Cliente();

			clientes = ClienteDao.listar("dataCadastro");
			pessoas = pessoaDao.listar();

			Messages.addGlobalInfo("Cliente salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o cliente");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			ClienteDao clienteDao = new ClienteDao();
			clienteDao.excluir(cliente);
			clientes = clienteDao.listar("dataCadastro");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			ClienteDao clienteDao = new ClienteDao();
			PessoaDao pessoaDao = new PessoaDao();
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			clienteDao.editar(cliente);
			pessoas = pessoaDao.listar();
			
			Messages.addFlashGlobalInfo("Cliente Editado com Sucesso");
			clienteDao.editar(cliente);
			clientes = clienteDao.listar("dataCadastro");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Editar Cliente");
		}

	}

}
