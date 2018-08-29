package br.com.drogaria.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Venda;
import br.com.drogaria.util.HibernateUtil;

public class VendaDao extends GenericDao<Venda> {
	public void salvar(Venda venda, List<ItemVenda> itensVenda) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.save(venda);

			for (int posicao = 0; posicao < itensVenda.size(); posicao++) {

				ItemVenda itemVenda = itensVenda.get(posicao);
				itemVenda.setVenda(venda);
				sessao.save(itemVenda);
				Produto produto = itemVenda.getProduto();
				int qtde = produto.getQuantidade() - itemVenda.getQuantidade();
				if (qtde >= 0) {
					produto.setQuantidade(new Short(qtde + ""));
					sessao.update(produto);
				} else {
					throw new RuntimeException("Quantidade Insuficiente em estoque");
				}
			}
			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		}

		finally {
			sessao.close();
		}
	}
}
