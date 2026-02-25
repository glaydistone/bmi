package br.com.tecnotran.bmi.services;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import br.com.tecnotran.bmi.model.BaseEntity;
import br.com.tecnotran.bmi.repository.BaseDAO;

public class BaseService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BaseDAO dao;

	public Long salvar(BaseEntity entidade) {
		Long retorno = null;
		EntityTransaction trx = dao.getTransaction();
		try {
			trx.begin();
			retorno = dao.save(entidade);
			trx.commit();
			return retorno;
		} catch (Exception e) {
			trx.rollback();
			throw new NegocioException(e.getMessage());
		}
	}

	public void excluir(BaseEntity entidade) {
		if (entidade != null && entidade.getId() != null) {
			EntityTransaction trx = dao.getTransaction();
			try {
				trx.begin();
				dao.delete(entidade.getClass(), entidade.getId());
				trx.commit();
			} catch (Exception e) {
				trx.rollback();
				throw new NegocioException(e.getMessage());
			}
		}
	}

}
