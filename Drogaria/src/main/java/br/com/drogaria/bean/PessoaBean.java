package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.CidadeDao;
import br.com.drogaria.dao.EstadoDao;
import br.com.drogaria.dao.PessoaDao;
import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;
import br.com.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {

	private List<Pessoa> pessoas;
	private Pessoa pessoa;
	private List<Estado> estados;
	private List<Cidade> cidades;

	private Estado estado;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@PostConstruct
	public void listar() {

		try {

			PessoaDao pessoaDao = new PessoaDao();
			pessoas = pessoaDao.listar("nome");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar as pessoas");
			erro.printStackTrace();
		}
	}

	public void novo() {

		try {
			pessoa = new Pessoa();
			estado = new Estado();

			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar();

			cidades = new ArrayList<Cidade>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao Abrir");
		}

		pessoa = new Pessoa();
	}

	public void excluir(ActionEvent evento) {

		try {

			PessoaDao pessoaDao = new PessoaDao();
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			pessoaDao.excluir(pessoa);
			Messages.addGlobalInfo("Pessoa Excluida com Sucesso");
			pessoas = pessoaDao.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao Excluir");
		}

	}

	public void editar(ActionEvent evento) {
		try {
			
			
			
			
					pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
					
					estado = pessoa.getCidade().getEstado();
					
					EstadoDao estadoDAO = new EstadoDao();
					estados = estadoDAO.listar("nome");
					
					CidadeDao cidadeDAO = new CidadeDao();
					cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
					
				}catch(RuntimeException erro){
					Messages.addGlobalError("Ocorreu um erro ao tentar selecionar uma pessoa");
				
			
				}
	}
			/*
			PessoaDao pessoaDao = new PessoaDao();
			EstadoDao estadoDao = new EstadoDao();
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			
			pessoaDao.editar(pessoa);			
			pessoas = pessoaDao.listar();
			estados = estadoDao.listar();
			
				
			Messages.addGlobalInfo("Pessoa Editada com Sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao Editar");
		}

	}
			
			
			*/
			
			
			
			
			
			
		

	public void salvar() {

		try {

			PessoaDao pessoaDao = new PessoaDao();
			pessoaDao.merge(pessoa);

			pessoas = pessoaDao.listar();

			pessoa = new Pessoa();
			estado = new Estado();

			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar("nome");
			Messages.addGlobalInfo("Pessoa Salva com Sucesso");

		} catch (RuntimeException erro) {

			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Salvar Pessoa");
		}

	}

	public void popular() {

		try {

			if (estado != null) {
				CidadeDao cidadeDao = new CidadeDao();
				cidades = cidadeDao.buscarPorEstado(estado.getCodigo());

			} else {

				cidades = new ArrayList<>();
			}

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao filtrar Cidades");
		}
	}

}
