package br.com.drogaria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.ClienteDao;
import br.com.drogaria.dao.FuncionarioDao;
import br.com.drogaria.dao.ProdutoDao;
import br.com.drogaria.dao.VendaDao;
import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Venda;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {

	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private Venda venda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	@PostConstruct
	public void novo() {
		try {
			venda = new Venda();
			venda.setValorTotal(new BigDecimal("0.00"));
			venda.setHorario(new Date());

			ProdutoDao produtoDAO = new ProdutoDao();
			produtos = produtoDAO.listar("descricao");

			itensVenda = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {

		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
				ItemVenda itemVenda = itensVenda.get(achou);
				itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));
				itemVenda.setValorParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
			}
		}

		if (achou < 0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setValorParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));

			itensVenda.add(itemVenda);
		}

		calcular();
	}

	public void remover(ActionEvent evento) {

		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {

			if (itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensVenda.remove(achou);
		}
		calcular();
	}

	public void calcular() {

		venda.setValorTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {

			ItemVenda itemVenda = itensVenda.get(posicao);// dar uma olhada
			venda.setValorTotal(venda.getValorTotal().add(itemVenda.getValorParcial()));
		}
	}

	public void finalizar() {

		try {

			venda.setHorario(new Date());
			venda.setFuncionario(null);
			venda.setCliente(null);

			FuncionarioDao funcionarioDao = new FuncionarioDao();
			funcionarios = funcionarioDao.listarOrdenado();

			ClienteDao clienteDao = new ClienteDao();
			clientes = clienteDao.listarOrdenado();
	

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addGlobalError("Erro ao listar os Funcionários");
		}

	}

	public void salvar() {

		try {

			if (venda.getValorTotal().signum() == 0) {
				Messages.addGlobalError("Informe um Item para venda");
				return;
			}
			
			VendaDao vendaDao = new VendaDao();
			vendaDao.salvar(venda, itensVenda);
			Messages.addFlashGlobalInfo("Venda Realizada com Sucesso");
			
			novo();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
			Messages.addFlashGlobalError("Erro ao Salvar a Venda");
		}
	}
}