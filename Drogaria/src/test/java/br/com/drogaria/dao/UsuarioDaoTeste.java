package br.com.drogaria.dao;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.dao.PessoaDao;
import br.com.drogaria.dao.UsuarioDao;
import br.com.drogaria.domain.Pessoa;
import br.com.drogaria.domain.Usuario;

public class UsuarioDaoTeste {

	@Test
	
	public void salvar() {

		PessoaDao pessoaDao = new PessoaDao();
		Pessoa pessoa = pessoaDao.buscar(10L);

		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenhaSemCriptografia("root");

		SimpleHash hasch = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
		usuario.setSenha(hasch.toHex());
		usuario.setTipo('G');

		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.salvar(usuario);
	}

	@Ignore
	public void excluir() {
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = usuarioDao.buscar(12L); // acertar codigo
		usuarioDao.excluir(usuario);
	}

	@Ignore
	public void alterar() {

		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = usuarioDao.buscar(10L);
		usuario.setSenha("q1w2e3r4");
		usuarioDao.editar(usuario);
	}

	@Ignore
	public void listar() {
		UsuarioDao usuarioDao = new UsuarioDao();
		List<Usuario> usuario = usuarioDao.listar();
		System.out.println(usuario.size());

		for (Usuario resultado : usuario) {

			System.out.println("Lista de Usuarios: " + resultado.getPessoa().getNome());
		}
	}

	@Test
	@Ignore
	public void autenticar() {

		String cpf = "353.406.508-55";
		String senha = "q1w2e3r4";

		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = usuarioDao.autenticar(cpf, senha);
		
		if(usuario==null) {
			System.out.println("Usuario nao encontrado");
			
		}else {
			
			System.out.println("Usuario:"+ usuario.getPessoa().getNome());			
		}
		
		

	}
}
