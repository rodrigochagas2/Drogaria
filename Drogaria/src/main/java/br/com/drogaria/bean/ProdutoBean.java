package br.com.drogaria.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;
import com.google.gson.Gson;

import br.com.drogaria.dao.FabricanteDao;
import br.com.drogaria.dao.ProdutoDao;
import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

	private List<Produto> produtos;
	private Produto produto;
	private List<Fabricante> fabricantes;

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void novo() {

		produto = new Produto();

		Client cliente = ClientBuilder.newClient();
		WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/fabricante");
		String json = caminho.request().get(String.class);
		Gson gson = new Gson();
		Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);

		fabricantes = Arrays.asList(vetor);

	}

	public void salvar() {

		try {

			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/produto");

			Gson gson = new Gson();

			String json = gson.toJson(produto);
			caminho.request().post(Entity.json(json));
			novo();

			json = caminho.request().get(String.class);

			Produto[] vetor = gson.fromJson(json, Produto[].class);// primeiro a conversão para vetor
			produtos = Arrays.asList(vetor);
			Messages.addGlobalInfo("Produto Salvo com Sucesso");
			/*
			 * ProdutoDao produtoDao = new ProdutoDao(); FabricanteDao fabricanteDao = new
			 * FabricanteDao(); produtoDao.merge(produto); produto = new Produto(); produtos
			 * = produtoDao.listar(); fabricantes = fabricanteDao.listar();
			 */

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Salvar Produto");
		}

	}

	public void excluir(ActionEvent evento) {

		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/produto");
			WebTarget caminhoExcluir = caminho.path("{codigo}").resolveTemplate("codigo", produto.getCodigo());

			caminhoExcluir.request().delete();

			String json = caminho.request().get(String.class);

			// listar
			Gson gson = new Gson();
			Produto[] vetor = gson.fromJson(json, Produto[].class);

			produtos = Arrays.asList(vetor);
			Messages.addGlobalInfo("Produto Excluído");

			/*
			 * 
			 * ProdutoDao produtoDao = new ProdutoDao();
			 * 
			 * produto = (Produto)
			 * evento.getComponent().getAttributes().get("produtoSelecionado");
			 * produtoDao.excluir(produto); produtos = produtoDao.listar();
			 */

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao Excluir");
			erro.printStackTrace();
		}

	}

	@PostConstruct
	public void listar() {

		try {
			
			
			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://127.0.0.1:8080/Drogaria/rest/produto");
			Gson gson = new Gson();
			
			String json = caminho.request().get(String.class);
			
			Produto[] vetor =gson.fromJson(json, Produto[].class);
			
			produtos = Arrays.asList(vetor);
			

			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao Listar Produtos");
			erro.printStackTrace();

		}
	}

	public void editar(ActionEvent evento) {

		try {

			FabricanteDao fabricanteDao = new FabricanteDao();
			ProdutoDao produtoDao = new ProdutoDao();
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			produtoDao.merge(produto);
			fabricantes = fabricanteDao.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalInfo("Erro ao Editar");
		}
	}

	public void imprimir() {

		try {	
			
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");// captura o panel
			Map<String, Object> filtros = tabela.getFilters();//  recebe os filtros que tem na tabela, tipo String e o valor do object

			String proDescricao = (String) filtros.get("descricao");// faz o cast para transformar um objeto em uma String
			String fabrDescricao = (String) filtros.get("fabricante.descricao");

			String caminho = Faces.getRealPath("/reports/produtos.jasper");// caminho relatório
			Map<String, Object> parametros = new HashMap<>();// mapeia o local da memoria onde esta sendo executado o programa

			if (proDescricao == null) {
				parametros.put("PRODUTO_DESCRICAO", "%%");
			} else {
				parametros.put("PRODUTO_DESCRICAO", "%" + proDescricao + "%");
			}
			if (fabrDescricao == null) {
				parametros.put("FABRICANTE_DESCRICAO", "%%");
			} else {
				parametros.put("FABRICANTE_DESCRICAO", "%" + fabrDescricao + "%");
			}

			Connection conexao = HibernateUtil.getConexao();
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);

		} catch (RuntimeException | JRException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Ocorreu um erro ao imprimir");
		}
	}
}
