package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.HistoricoDao;
import br.com.drogaria.dao.ProdutoDao;
import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Historico;
import br.com.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable {

	private Produto produto;
	private Fabricante fabricante;
	private Boolean exibePainelDados;

	private Historico historico;
	private List<Historico> historicos;

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	@PostConstruct
	public void novo() {

		produto = new Produto();
		exibePainelDados = false;
		historico = new Historico();
	}

	public void buscar() {

		try {
			ProdutoDao produtoDao = new ProdutoDao();
			HistoricoDao historicoDao = new HistoricoDao();

			Produto resultado = produtoDao.buscar(produto.getCodigo());

			if (resultado == null) {
				exibePainelDados = false;
				Messages.addGlobalWarn("Não existe produto cadastrado para o código informado");
			} else {
				exibePainelDados = true;
				produto = resultado;
				
				historico = historicoDao.buscarComentario(produto.getCodigo());
				/*

				for (Historico resultadoHistorico : historicos) {

					if (resultadoHistorico.getProduto().getCodigo() == produto.getCodigo()) {
						historico = historicoDao.buscar(resultadoHistorico.getCodigo());
						return;
					}
				}*/
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao buscar o produto");
			erro.printStackTrace();
		}
	}

	public void salvar() {

		try {

			historico.setHorario(new Date());
			historico.setProduto(produto);

			HistoricoDao historicoDao = new HistoricoDao();
			historicoDao.merge(historico);
			Messages.addGlobalInfo("Histórico Salvo");

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao Salvar Histórico");
		}

	}

}
