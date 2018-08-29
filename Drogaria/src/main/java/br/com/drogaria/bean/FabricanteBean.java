package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.com.drogaria.dao.FabricanteDao;
import br.com.drogaria.domain.Fabricante;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FabricanteBean implements Serializable {

	private List<Fabricante> fabricantes;
	private Fabricante fabricante;

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public void salvar() {

		try {
			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/fabricante");

			Gson gson = new Gson();
			String json = gson.toJson(fabricante);
			caminho.request().post(Entity.json(json));

			Messages.addGlobalInfo("Fabricante salvo");
			novo();

			json = caminho.request().get(String.class);

			Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);// primeiro a conversão para vetor
			fabricantes = Arrays.asList(vetor);

		} catch (RuntimeException erro) {
			Messages.addGlobalFatal("Erro ao salvar Fabricante");
			erro.printStackTrace();
		}
	}

	public void novo() {

		fabricante = new Fabricante();
	}

	@Test
	@PostConstruct
	public void list() {

		try {
			// FabricanteDao fabricanteDao = new FabricanteDao();
			// fabricantes = fabricanteDao.listar();

			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/fabricante");
			String json = caminho.request().get(String.class);

			Gson gson = new Gson();
			Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);

			fabricantes = Arrays.asList(vetor);

		} catch (RuntimeException erro) {
			Messages.addGlobalFatal("Erro ao listar Fabricante");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");

			Client cliente = ClientBuilder.newClient();

			WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/fabricante");
			WebTarget caminhoExcluir = caminho.path("{codigo}").resolveTemplate("codigo", fabricante.getCodigo());

			caminhoExcluir.request().delete();
			String json = caminho.request().get(String.class);

			Gson gson = new Gson();
			Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);

			fabricantes = Arrays.asList(vetor);

			Messages.addGlobalInfo("Fabricante removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o fabricante");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		try {
			FabricanteDao fabricanteDao = new FabricanteDao();
			fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
			fabricanteDao.editar(fabricante);
			fabricanteDao.listar("descricao");
			Messages.addGlobalInfo("Usuario Editado com Sucesso");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Editar Contato");
		}

	}
}
