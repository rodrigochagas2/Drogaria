package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.FuncionarioDao;
import br.com.drogaria.dao.PessoaDao;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {

	private Pessoa pessoa;
	private List<Funcionario> funcionarios;
	private Funcionario funcionario;
	private List<Pessoa> pessoas;

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@PostConstruct
	public void listar() {

		try {

			FuncionarioDao funcionarioDao = new FuncionarioDao();
			funcionarios = funcionarioDao.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Listar");

		}

	}

	public void novo() {

		try {

			pessoa = new Pessoa();
			funcionario = new Funcionario();
			PessoaDao pessoaDao = new PessoaDao();
			pessoas = pessoaDao.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}

	}

	public void salvar() {

		try {

			FuncionarioDao funcionarioDao = new FuncionarioDao();
			funcionarioDao.merge(funcionario);
			Messages.addGlobalInfo("Funcionario Salvo com Sucesso");
			funcionarios = funcionarioDao.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addFlashGlobalError("Erro ao Salvar Funcionario");
		}
	}

	public void excluir(ActionEvent evento) {

		try {

			FuncionarioDao funcionarioDao = new FuncionarioDao();
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			funcionarioDao.excluir(funcionario);
			Messages.addGlobalInfo("Funcionario Excluido com Sucesso");
			funcionarios = funcionarioDao.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Excluir");
		}

	}

	public void editar(ActionEvent evento) {

		try {
			FuncionarioDao funcionarioDao = new FuncionarioDao();
			PessoaDao pessoaDao = new PessoaDao();
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			funcionarioDao.merge(funcionario);
			pessoas = pessoaDao.listar();
			funcionarios = funcionarioDao.listar();

			Messages.addGlobalInfo("Funcionario Editado com Sucesso");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Editar  o Funcionario");
		}

	}

}
