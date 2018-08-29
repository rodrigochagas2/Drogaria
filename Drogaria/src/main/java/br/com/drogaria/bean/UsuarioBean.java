package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.PessoaDao;
import br.com.drogaria.dao.UsuarioDao;
import br.com.drogaria.domain.Pessoa;
import br.com.drogaria.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private List<Usuario> usuarios;
	private Usuario usuario;
	private List<Pessoa> pessoas;
	private Pessoa pessoa;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void salvar() {

		try {

			UsuarioDao usuarioDao = new UsuarioDao();
			usuarioDao.merge(usuario);

			usuario = new Usuario();
			usuarios = usuarioDao.listar();

			PessoaDao pessoaDao = new PessoaDao();
			pessoas = pessoaDao.listar("nome");
			Messages.addGlobalInfo("Usuario salvo com Sucesso");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Salvar Usuario");
		}

	}

	public void editar(ActionEvent evento) {

		try {

			PessoaDao pessoaDao = new PessoaDao();
			UsuarioDao usuarioDao = new UsuarioDao();

			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			pessoas = pessoaDao.listar();

			usuarioDao.editar(usuario);
			usuarioDao.listar();
			Messages.addGlobalInfo("Usuario Editado com Sucesso");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Editar Usúario");
		}

	}

	@PostConstruct
	public void listar() {

		try {

			UsuarioDao usuarioDao = new UsuarioDao();
			usuarios = usuarioDao.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();

		}

	}

	public void excluir(ActionEvent evento) {

		try {
			UsuarioDao usuarioDao = new UsuarioDao();
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			usuarioDao.excluir(usuario);
			usuarios = usuarioDao.listar();
			Messages.addGlobalInfo("Usuario Excluido com Sucesso");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Excluir");
		}

	}

	public void novo() {

		usuario = new Usuario();
		PessoaDao pessoaDao = new PessoaDao();
		pessoas = pessoaDao.listar();
	}
}
