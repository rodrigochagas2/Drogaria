package br.com.drogaria.bean;

import java.io.Serializable;
import java.sql.Connection;
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

import com.google.gson.Gson;

import br.com.drogaria.converter.UpperCaseConverter;
import br.com.drogaria.dao.EstadoDao;
import br.com.drogaria.domain.Estado;
import br.com.drogaria.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {

	private Estado estado;
	private List<Estado> estados;

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void novo() {

		estado = new Estado();
	}

	public void excluir(ActionEvent evento) {

		try {

			EstadoDao estadoDao = new EstadoDao();

			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			estadoDao.excluir(estado);

			Messages.addGlobalInfo("Estado Excluido");
			estados = estadoDao.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}

	@PostConstruct // Um Construtor que é chamado depois do construtor da classe
	public void list() {

		try {
			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar();

		} catch (RuntimeException erro) {

			Messages.addGlobalFatal(null, "Ocorreu um Erro ao Listar");
			erro.printStackTrace();
		}

	}

	public void salvar() {

		try {
			Client client = ClientBuilder.newClient();
			WebTarget caminho = client.target("http://env-4985206.jelasticlw.com.br//Drogaria/rest/estado");
			Gson gson = new Gson();
			String json = gson.toJson(estado);
			caminho.request().post(Entity.json(json));
			Messages.addGlobalInfo("Estado Salvo !!");
			
			json = caminho.request().get(String.class);
			Estado[] vetor = gson.fromJson(json, Estado[].class);
			estados = Arrays.asList(vetor);	
			novo();

		} catch (RuntimeException erro) {
			Messages.addGlobalError(null, "Ocorreu um erro ao tentar salvar o estado");
			erro.printStackTrace();
		}		
	}

	public void editar(ActionEvent evento) {

		try {
			EstadoDao estadoDao = new EstadoDao();
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
			estadoDao.editar(estado);
			Messages.addGlobalInfo("Estado Editado com Sucesso");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalInfo("Erro ao editar o estado");

		}

	}

	public void imprimir() {

		try {

			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String estNome = (String) filtros.get("nome");
			String estSigla = (String) filtros.get("sigla");
			Map<String, Object> parametros = new HashMap<>();
			UpperCaseConverter upperCaseConverter = new UpperCaseConverter();

			if (estNome == null) {
				parametros.put("ESTADO_NOME", "%%");
			} else {

				estNome = (String) upperCaseConverter.getAsObject(null, null, estNome);
				parametros.put("ESTADO_NOME", "%" + estNome + "%");
			}
			if (estSigla == null) {
				parametros.put("ESTADO_SIGLA", "%%");
			} else {
				estSigla = upperCaseConverter.getAsString(null, null, estSigla);
				parametros.put("ESTADO_SIGLA", "%" + estSigla + " %");
			}

			String caminnho = Faces.getRealPath("/reports/estados.jasper");
			Connection conexao = HibernateUtil.getConexao();
			JasperPrint relatorio = JasperFillManager.fillReport(caminnho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);

		} catch (RuntimeException | JRException erro) {
			erro.printStackTrace();
		}
	}
}
