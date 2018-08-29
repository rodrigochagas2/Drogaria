package br.com.drogaria.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.drogaria.domain.Historico;
import br.com.drogaria.util.HibernateUtil;

public class HistoricoDao extends GenericDao<Historico> {

	public Historico buscarComentario(Long id) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		

		try {
			Query query =sessao.createQuery("select historico from Historico historico where historico.produto.id = :idproduto");
			query.setParameter("idproduto", id);			
			return (Historico) query.uniqueResult();
			//HQL
			
			/*Criteria consulta = sessao.createCriteria(Historico.class);

			consulta.add(Restrictions.eq("produto.codigo", id));
			resultado = (Historico) consulta.uniqueResult();
			return resultado; */

		} catch (RuntimeException erro) {

			throw erro;

		} finally {
			sessao.close();
		}

	}

}
