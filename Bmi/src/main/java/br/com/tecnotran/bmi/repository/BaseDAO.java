package br.com.tecnotran.bmi.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.tecnotran.bmi.model.BaseEntity;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.seguranca.Usuario;
import javassist.NotFoundException;

public class BaseDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	/**
	 * Find an entity by its identifier.
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T extends BaseEntity> T findById(Class<T> clazz, Serializable id) {
		return manager.find(clazz, id);
	}

	public EntityTransaction getTransaction() {
		return manager.getTransaction();
	};

	/**
	 * Saves an entity.
	 * 
	 * @param <T>
	 * 
	 * @param entity
	 * @return newly created id for the entity.
	 */
	public Long save(BaseEntity entity) {
		manager.merge(entity);
		return entity.getId();
	}

	/**
	 * Marges objects with the same identifier within a session into a newly created
	 * object.
	 * 
	 * @param entity
	 * @return a newly created instance merged.
	 */
	public <T extends BaseEntity, PK extends Serializable> T merge(T entity) {
		return manager.merge(entity);
	}

	/**
	 * Deletes tne entity.
	 * 
	 * @param clazz
	 * @param id
	 * @throws NotFoundException if the id does not exist.
	 */
	public <T extends BaseEntity, PK extends Serializable> void delete(Class<T> clazz, PK id) {
		T entity = find(clazz, id);
		if (entity != null) {
			manager.remove(entity);
		} else {
			// throw new NotFoundException();
		}
	}

	/**
	 * Find an entity by its identifier.
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T extends BaseEntity> T find(Class<T> clazz, Serializable id) {
		return manager.find(clazz, id);
	}

	/**
	 * Finds an entity by one of its properties.
	 * 
	 * 
	 * @param clazz        the entity class.
	 * @param propertyName the property name.
	 * @param value        the value by which to find.
	 * @return
	 */
	public <T extends BaseEntity> List<T> findByProperty(Class<T> clazz, String propertyName, Object value) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(cb.equal(root.get(propertyName), value));
		return manager.createQuery(cq).getResultList();
	}

	/**
	 * Finds all objects of an entity class.
	 * 
	 * @param clazz the entity class.
	 * @return
	 */
	public <T extends BaseEntity> List<T> findAll(Class<T> clazz) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		cq.from(clazz);
		return manager.createQuery(cq).getResultList();
	}

	public Long consultaExistencia(String queryString, List<ClausulaFiltro> clausulas) {
		Query query = manager.createQuery(queryString);
		for (ClausulaFiltro c : clausulas) {
			if (c.getTipoPropriedade().equals(TipoPropriedade.DATA)) {
				query.setParameter(c.getNomePropriedade(), (Date) c.getValorPropriedade(), TemporalType.DATE);
			} else {
				query.setParameter(c.getNomePropriedade(), c.getValorPropriedade());
			}
		}
		return (Long) query.getSingleResult();
	}

	/**
	 * Finds all objects of an entity class.
	 * 
	 * @param clazz the entity class.
	 * @return
	 */
	public <T extends BaseEntity> List<T> findAll(Class<T> clazz, List<ClausulaFiltro> clausulas) {
		String queryString = "from ".concat(clazz.getCanonicalName()).concat(" obj");
		if (clausulas.size() > 0) {
			queryString = queryString.concat(" where ");
		}

		String predicado = " ";
		for (ClausulaFiltro c : clausulas) {
			if (predicado != " ") { // .isBlank()) {
				predicado += " and ";
			}
			if (c.getTipoPropriedade().equals(TipoPropriedade.STRING)) {
				if (c.getOperador().equals(Operador.CONTIDO)) {
					predicado = predicado + c.getNomePropriedade() + " like " + ":" + c.getNomePropriedade();
					c.setValorPropriedade("%" + c.getValorPropriedade() + "%");
				} else {
					predicado = predicado + c.getNomePropriedade() + c.getOperador().getDescricao() + ":"
							+ c.getNomePropriedade();
				}

			} else {
				predicado = predicado + c.getNomePropriedade() + c.getOperador().getDescricao() + ":"
						+ c.getNomePropriedade();
			}
		}
		queryString = queryString + predicado;
		Query query = manager.createQuery(queryString);
		for (ClausulaFiltro c : clausulas) {
			if (c.getTipoPropriedade().equals(TipoPropriedade.DATA)) {
				query.setParameter(c.getNomePropriedade(), (Date) c.getValorPropriedade(), TemporalType.DATE);
			} else {
				query.setParameter(c.getNomePropriedade(), c.getValorPropriedade());
			}
		}

		return query.getResultList();
	}

	public <T extends BaseEntity> List<T> findAll(Class<T> clazz, List<ClausulaFiltro> clausulas, String ordem) {
		String queryString = "from ".concat(clazz.getCanonicalName()).concat(" obj");
		if (clausulas.size() > 0) {
			queryString = queryString.concat(" where ");
		}

		String predicado = " ";
		for (ClausulaFiltro c : clausulas) {
			if (predicado != " ") { // (!predicado.isBlank()) {
				predicado += " and ";
			}
			predicado = predicado + c.getNomePropriedade() + " = " + ":" + c.getNomePropriedade();
		}
		queryString = queryString + predicado + " order by " + ordem;
		Query query = manager.createQuery(queryString);
		for (ClausulaFiltro c : clausulas) {
			if (c.getTipoPropriedade().equals(TipoPropriedade.DATA)) {
				query.setParameter(c.getNomePropriedade(), (Date) c.getValorPropriedade(), TemporalType.DATE);
			} else {
				query.setParameter(c.getNomePropriedade(), c.getValorPropriedade());
			}
		}

		return query.getResultList();
	}

	/**
	 * Finds all objects of a class by the specified order.
	 * 
	 * @param clazz           the entity class.
	 * @param order           the order: ASC or DESC.
	 * @param propertiesOrder the properties on which to apply the ordering.
	 * 
	 * @return
	 */
	public <T extends BaseEntity> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);

		List<javax.persistence.criteria.Order> orders = new ArrayList<javax.persistence.criteria.Order>();
		for (String propertyOrder : propertiesOrder) {
			if (order.isAscending()) {
				orders.add(cb.asc(root.get(propertyOrder)));
			} else {
				orders.add(cb.desc(root.get(propertyOrder)));
			}
		}
		cq.orderBy(orders);

		return manager.createQuery(cq).getResultList();
	}

	public Usuario porLogin(String login) {
		Usuario usuario = null;
		try {
			usuario = this.manager.createQuery("from Usuario where lower(login) = :login", Usuario.class)
					.setParameter("login", login).getSingleResult();
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuario;
	}

	public List<Object[]> recuperaParaExportacao(String view, Date dataReferencia) {
		String strDate = new SimpleDateFormat("dd/MM/yy").format(dataReferencia);
		String query = "SELECT * FROM `" + view + "` WHERE bloqueado and `mes-ano` = '"+strDate+"'";
		
		List<Object[]> lista = manager.createNativeQuery(query).getResultList();
		return lista;
	}

}
