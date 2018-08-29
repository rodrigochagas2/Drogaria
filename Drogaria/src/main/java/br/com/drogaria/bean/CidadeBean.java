package br.com.drogaria.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.drogaria.dao.CidadeDao;
import br.com.drogaria.dao.EstadoDao;
import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;
import br.com.drogaria.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {

	private List<Cidade> cidades;
	private Cidade cidade;
	private List<Estado> estados;

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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public void novo() {

		try {

			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar("nome");
			cidade = new Cidade();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro");
		}

	}

	@PostConstruct
	public void listar() {

		try {

			CidadeDao cidadeDao = new CidadeDao();
			cidades = cidadeDao.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Listar Cidades");
		}
	}

	public void salvar() {
		try {

			CidadeDao cidadeDao = new CidadeDao();
			cidadeDao.merge(cidade);

			cidade = new Cidade();
			cidades = cidadeDao.listar("nome");

			EstadoDao estadoDao = new EstadoDao();
			estados = estadoDao.listar();
			Messages.addGlobalInfo("Cidade Salva");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Salvar Cidade");

		}
	}

	public void excluir(ActionEvent evento) {

		try {

			CidadeDao cidadeDao = new CidadeDao();
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionado");
			cidadeDao.excluir(cidade);
			cidades = cidadeDao.listar("nome");
			Messages.addGlobalInfo("Cidade Excluida");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Excluir");
		}

	}

	public void editar(ActionEvent evento) {

		try {

			CidadeDao cidadeDao = new CidadeDao();
			EstadoDao estadoDao = new EstadoDao();

			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
			cidadeDao.editar(cidade);

			estados = estadoDao.listar("nome");
			Messages.addGlobalInfo("Cidade Alterada com Sucesso");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Editar");
		}

	}

	public void imprimir() {

		try {

			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();
			Map<String, Object> parametros = new HashMap<>();

			String cidadeNome = (String) filtros.get("nome");
			String estadoNome = (String) filtros.get("estado.nome");
			String estadoSigla = (String) filtros.get("estado.sigla");

			if (cidadeNome != null) {
				parametros.put("CIDADE_NOME", "%" + cidadeNome + "%");
			} else {
				parametros.put("CIDADE_NOME", "%%");
			}

			if (estadoNome != null) {
				parametros.put("ESTADO_NOME", "%" + estadoNome + "%");
			} else {
				parametros.put("ESTADO_NOME", "%%");
			}

			if (estadoSigla != null) {
				parametros.put("ESTADO_SIGLA", "%" + estadoSigla + "%");
			} else {
				parametros.put("ESTADO_SIGLA", "%%");
			}

			String caminho = Faces.getRealPath("/reports/cidades.jasper");
			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);

		} catch (RuntimeException | JRException erro) {
			erro.printStackTrace();
		}

	}

}
