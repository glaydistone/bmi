package br.com.tecnotran.bmi.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.tecnotran.bmi.model.Boletim;
import br.com.tecnotran.bmi.model.CustoPessoal;
import br.com.tecnotran.bmi.model.DespesaGeral;
import br.com.tecnotran.bmi.model.FrotaApoio;
import br.com.tecnotran.bmi.model.Material;
import br.com.tecnotran.bmi.model.ReceitaImobilizado;
import br.com.tecnotran.bmi.model.Servico;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.model.gestor.MovtoCustosOperac;
import br.com.tecnotran.bmi.model.gestor.MovtoDespGeral;
import br.com.tecnotran.bmi.model.gestor.MovtoDespTrib;
import br.com.tecnotran.bmi.model.gestor.MovtoFrota;
import br.com.tecnotran.bmi.model.gestor.MovtoFrotaApoio;
import br.com.tecnotran.bmi.model.gestor.MovtoKm;
import br.com.tecnotran.bmi.model.gestor.MovtoMateriais;
import br.com.tecnotran.bmi.model.gestor.MovtoServicoManut;
import br.com.tecnotran.bmi.util.cdi.BdAccess;

public class AccessDAO implements Serializable {

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@BdAccess
	private EntityManager manager;

	public EntityTransaction getTransaction() {
		return manager.getTransaction();
	};

	public <T> List<T> findAll(Class<T> clazz) {

		String qry = "from " + clazz.getName();
		return manager.createQuery(qry).getResultList();
	}

	public void excluiFrota(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		manager.createNativeQuery("delete from  Movto_Frota where [mes-ano] = " + dtAux + " and tipmov = "
				+ tipoMov.toString() + " and [cod-empresa] = " + codEmpresa).executeUpdate();
	}

	public void incluiFrota(Date dataReferencia,Boletim boletim) {

		Query qry = manager.createNativeQuery(
				"insert into Movto_Frota ([mes-ano], [tipMov], [Veic_efetivos],[Veic_reservas],[cod-empresa]) values "
						+ "(" + dataAccess(dataReferencia) + "," + 
						(boletim.getTipoMovimento().equals(TipoMovimento.RODOVIARIO) ? 2 : 1)
						+ "," + boletim.getFrotaEfetiva() + "," + boletim.getFrotaReserva() + ","
						+ boletim.getEmpresa().getNumero() + ")");
		qry.executeUpdate();
	}

	public List<MovtoFrota> recuperaFrotaMes(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		List<MovtoFrota> retorno = new ArrayList<MovtoFrota>();
		String qry = "Select distinct [mes-ano], [tipMov], [Veic_efetivos],[Veic_reservas],[cod-empresa] from Movto_Frota where [mes-ano] = "
				+ dtAux; 
		List<Object[]> frt = manager.createNativeQuery(qry).getResultList();
		for (int i = 0; i < frt.size(); i++) {
			Object[] obj = frt.get(i);
			MovtoFrota m = new MovtoFrota();
			retorno.add(m);
			m.setMesAno((Date) obj[0]);
			m.setTipoMov(Integer.parseInt(obj[1].toString()));
			m.setEfetivos((Integer) obj[2]);
			m.setReservas((Integer) obj[3]);
			m.setCodEmpresa(Integer.parseInt(obj[4].toString()));
		}

		return retorno;
	}

	public void excluiMovtoCustosOperac(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		manager.createNativeQuery("delete from [Movto-custos-operac] where [mes-ano] = " + dtAux
				+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.executeUpdate();
	}

	public List<MovtoCustosOperac> recuperaMovtoCustosOperac(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		List<MovtoCustosOperac> retorno = new ArrayList<MovtoCustosOperac>();
		List<Object[]> frt = manager.createNativeQuery("SELECT  distinct " + "[Movto-custos-operac].[cod-cargo], "
				+ "[Movto-custos-operac].tipmov, " + "[Movto-custos-operac].[qtde-elementos], "
				+ "[Movto-custos-operac].[val-salario], " + "[Movto-custos-operac].[Val-premios], "
				+ "[Movto-custos-operac].[val-horaex], " + "[Movto-custos-operac].[val-outros], "
				+ "[Movto-custos-operac].[val-encargos] " + "FROM [Movto-custos-operac]" + " where [mes-ano] = " + dtAux
				+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.getResultList();
		for (int i = 0; i < frt.size(); i++) {
			Object[] obj = frt.get(i);
			MovtoCustosOperac m = new MovtoCustosOperac();
			retorno.add(m);
			m.setCodCargo(Integer.parseInt(obj[0].toString()));
			m.setTipoMov(Integer.parseInt(obj[1].toString()));
			m.setQtdeElementos((Integer) obj[2]);
			m.setValSalario(new BigDecimal(obj[3] == null ? "0" : obj[3].toString()));
			m.setValPremios(new BigDecimal(obj[4] == null ? "0" : obj[4].toString()));
			m.setValHoraex(new BigDecimal(obj[5] == null ? "0" : obj[5].toString()));
			m.setValOutros(new BigDecimal(obj[6] == null ? "0" : obj[6].toString()));
			m.setValEncargos(new BigDecimal(obj[7] == null ? "0" : obj[7].toString()));
		}

		return retorno;
	}

	public void excluiMovtoDespGeral(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		manager.createNativeQuery("delete FROM [movto-desp-geral] AS m where [mes-ano] = " + dtAux
				+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.executeUpdate();
	}

	public List<MovtoDespGeral> recuperaMovtoDespGeral(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		List<MovtoDespGeral> retorno = new ArrayList<MovtoDespGeral>();
		List<Object[]> frt = manager
				.createNativeQuery("SELECT distinct " + "m.[cod-desp-geral], " + "m.tipmov, " + "m.[valor-desp-geral], "
						+ "m.especificacao " + " FROM [movto-desp-geral] AS m" + " where [mes-ano] = " + dtAux
						+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.getResultList();
		for (int i = 0; i < frt.size(); i++) {
			Object[] obj = frt.get(i);
			MovtoDespGeral m = new MovtoDespGeral();
			retorno.add(m);
			m.setCodDespesa(Integer.parseInt(obj[0].toString()));
			m.setTipoMov(Integer.parseInt(obj[1].toString()));
			m.setValor(new BigDecimal(obj[2] == null ? "0" : obj[2].toString()));
			if (obj[3] != null) {
				m.setEspecificacao(obj[3].toString());
			}
		}

		return retorno;
	}

	public List<MovtoDespTrib> recuperaReceitaImobilizado(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		List<MovtoDespTrib> retorno = new ArrayList<MovtoDespTrib>();
		List<Object[]> frt = manager
				.createNativeQuery("SELECT distinct m.[cod-despesa], m.tipoMov, m.[valor-desp], m.especificacao"
						+ " FROM [movto-desp-trib] AS m  where [mes-ano] = " + dtAux + " and [cod-empresa] = "
						+ codEmpresa.toString() + " and tipomov = " + tipoMov.toString())
				.getResultList();
		for (int i = 0; i < frt.size(); i++) {
			Object[] obj = frt.get(i);
			MovtoDespTrib m = new MovtoDespTrib();
			retorno.add(m);
			m.setCodDespesa(Integer.parseInt(obj[0].toString()));
			m.setTipoMov(Integer.parseInt(obj[1].toString()));
			m.setValor(new BigDecimal(obj[2] == null ? "0" : obj[2].toString()));
			if (obj[3] != null) {
				m.setEspecificacao(obj[3].toString());
			}
		}

		return retorno;
	}

	public void excluiReceitaImobilizado(Date data, Integer codEmpresa, Integer tipoMov) {
		manager.createNativeQuery("delete FROM [movto-desp-trib] AS m where [mes-ano] =  "+dataAccess(data)+
				" and [cod-empresa] = "+codEmpresa+" and tipoMov = "+tipoMov)
				.executeUpdate();

	}

	public void excluiMovtoFrotaApoio(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		manager.createNativeQuery("delete FROM [movto-frota-apoio] AS m where [mes-ano] = " + dtAux
				+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.executeUpdate();
	}

	public List<MovtoFrotaApoio> recuperaMovtoFrotaApoio(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		List<MovtoFrotaApoio> retorno = new ArrayList<MovtoFrotaApoio>();
		List<Object[]> frt = manager
				.createNativeQuery("SELECT  distinct " + "m.[cod-equipto], " + "m.tipmov, " + "m.[valor-equipto], "
						+ "m.[Km-mes] FROM [movto-frota-apoio] AS m" + " where [mes-ano] = " + dtAux
						+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.getResultList();
		for (int i = 0; i < frt.size(); i++) {
			Object[] obj = frt.get(i);
			MovtoFrotaApoio m = new MovtoFrotaApoio();
			retorno.add(m);
			m.setCodEquipto(Integer.parseInt(obj[0].toString()));
			m.setTipoMov(Integer.parseInt(obj[1].toString()));
			m.setValor(new BigDecimal(obj[2] == null ? "0" : obj[2].toString()));
			m.setKm(obj[3] == null ? 0 : Integer.parseInt(obj[3].toString()));
		}

		return retorno;
	}

	public void excluiMovtoMateriais(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		manager.createNativeQuery("delete FROM [movto-materiais] AS m where [mes-ano] = " + dtAux
				+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.executeUpdate();
	}

	public List<MovtoMateriais> recuperaMovtoMateriais(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		List<MovtoMateriais> retorno = new ArrayList<MovtoMateriais>();
		List<Object[]> frt = manager
				.createNativeQuery("SELECT distinct  " + "m.[cod-material], " + "m.tipmov, " + "m.[qtde-material], "
						+ "m.[preco-unit] FROM [movto-materiais] AS m" + " where [mes-ano] = " + dtAux
						+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.getResultList();
		for (int i = 0; i < frt.size(); i++) {
			Object[] obj = frt.get(i);
			MovtoMateriais m = new MovtoMateriais();
			retorno.add(m);
			m.setCodMaterial(Integer.parseInt(obj[0].toString()));
			m.setTipoMov(Integer.parseInt(obj[1].toString()));
			m.setQtdeMaterial(obj[2] == null ? 0 : Integer.parseInt(obj[2].toString()));
			m.setPreco(new BigDecimal(obj[3] == null ? "0" : obj[3].toString()));
		}

		return retorno;
	}

	public void excluiMovtoServicoManut(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		manager.createNativeQuery("delete FROM [movto-servico-manut] AS m where [mes-ano] = " + dtAux
				+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.executeUpdate();
	}

	public List<MovtoServicoManut> recuperaMovtoServicoManut(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		List<MovtoServicoManut> retorno = new ArrayList<MovtoServicoManut>();
		List<Object[]> frt = manager.createNativeQuery(
				"SELECT distinct  " + "m.[cod-servico], " + "m.tipmov, " + "m.[valor-servico], " + "m.especificacao, "
						+ "m.[qtde-serv] FROM [movto-servico-manut] AS m" + " where [mes-ano] = " + dtAux
						+ " and [cod-empresa] = " + codEmpresa.toString() + " and tipmov = " + tipoMov.toString())
				.getResultList();
		for (int i = 0; i < frt.size(); i++) {
			Object[] obj = frt.get(i);
			MovtoServicoManut m = new MovtoServicoManut();
			retorno.add(m);
			m.setCodServico(Integer.parseInt(obj[0].toString()));
			m.setTipoMov(Integer.parseInt(obj[1].toString()));
			m.setValor(new BigDecimal(obj[2] == null ? "0" : obj[2].toString()));
			if (obj[3] != null) {
				m.setEspecificacao(obj[3].toString());
			}
			m.setQtdeServ(obj[4] == null ? 0 : Integer.parseInt(obj[4].toString()));
		}

		return retorno;
	}

	public void excluiMovtoKm(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		manager.createNativeQuery("delete FROM [tab-kilometr] AS m where [mes-ano] = " + dtAux + " and [cod-empresa] = "
				+ codEmpresa.toString() + " and tipmov = " + tipoMov.toString()).executeUpdate();
	}

	public List<MovtoKm> recuperaMovtoKm(Date data, Integer codEmpresa, Integer tipoMov) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		String dtAux = "#" + (sdf.format(data)) + "#";
		List<MovtoKm> retorno = new ArrayList<MovtoKm>();
		List<Object[]> frt = manager.createNativeQuery("SELECT distinct   " + "m.cod_piso, " + "m.tipmov, " + "m.Km_rodado "
				+ "FROM [tab-kilometr] AS m" + " where [mes-ano] = " + dtAux + " and [cod-empresa] = "
				+ codEmpresa.toString() + " and tipmov = " + tipoMov.toString()).getResultList();
		for (int i = 0; i < frt.size(); i++) {
			Object[] obj = frt.get(i);
			MovtoKm m = new MovtoKm();
			retorno.add(m);
			m.setCodPiso(Integer.parseInt(obj[0].toString()));
			m.setTipoMov(Integer.parseInt(obj[1].toString()));
			m.setQuilometragem(new BigDecimal(obj[2] == null ? "0" : obj[2].toString()));
		}

		return retorno;
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
	public <T> List<T> findAll(Class<T> clazz, List<ClausulaFiltro> clausulas) {
		String queryString = "from ".concat(clazz.getCanonicalName()).concat(" obj");
		if (clausulas.size() > 0) {
			queryString = queryString.concat(" where ");
		}

		String predicado = " ";
		for (ClausulaFiltro c : clausulas) {
			if (predicado != " ") {
				predicado += " and ";
			}
			predicado = predicado + c.getNomePropriedade() + " = " + ":" + c.getNomePropriedade();
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

	/**
	 * Finds all objects of a class by the specified order.
	 * 
	 * @param clazz           the entity class.
	 * @param order           the order: ASC or DESC.
	 * @param propertiesOrder the properties on which to apply the ordering.
	 * 
	 * @return
	 */
	public <T> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder) {
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

	public void incluiMovtoDespGeral(Date dataReferencia, int empresa, int tipoMovimento, DespesaGeral dg) {
		String aux = "insert into  [movto-desp-geral] ([cod-desp-geral], tipmov, [valor-desp-geral], "
				+ "especificacao, [mes-ano] , [cod-empresa]) values (";
		aux = aux + dg.getTipoDespesa().getNumero() + ","
				+ tipoMovimento + "," + dg.getValor() + ",";
		aux = aux + 
				(dg.getDescricao() == null || dg.getDescricao() == " " ? "null" : "'"+dg.getDescricao()+"'") + "," + dataAccess(dataReferencia) + ","
				+ empresa + ")";
		manager.createNativeQuery(aux).executeUpdate();

	}

	public void incluiReceitaImobilizado(Date dataReferencia, int empresa, int tipoMovimento, ReceitaImobilizado ri) {
		String qry = "insert into [movto-desp-trib] ( [cod-empresa], [mes-ano], [cod-despesa], "
				+ "tipoMov, [valor-desp], especificacao ) values (" + empresa + "," + dataAccess(dataReferencia) + ","
				+ ri.getTipoReceitaImobilizado().getNumero() + "," + tipoMovimento + "," + ri.getValor() + ","
				+ (ri.getDescricao() == null || ri.getDescricao().isEmpty() ? "null" : "'"+ri.getDescricao()+"'") + ")";
		manager.createNativeQuery(qry).executeUpdate();
	}

	public void incluiMovtoCustosOperac(Date dataReferencia, int empresa, int tipoMovimento, CustoPessoal cp) {
		manager.createNativeQuery(
				"insert into [Movto-custos-operac] ([mes-ano], [cod-cargo], tipmov, [qtde-elementos], "
						+ "[val-salario], [Val-premios], [val-horaex], [val-outros], [val-encargos],  [cod-empresa] ) values ("
						+ dataAccess(dataReferencia) + "," + cp.getCargo().getNumero() + "," + tipoMovimento + ","
						+ cp.getElementos() + "," + cp.getSalarios() + "," + cp.getPremios() + "," + cp.getHorasExtras()
						+ "," + cp.getOutrosPagamentos() + "," + cp.getEncargosSociais() + "," + empresa + ")")
				.executeUpdate();

	}

	public void incluiMovtoFrotaApoio(Date dataReferencia, int empresa, int tipoMovimento, FrotaApoio fa) {
		manager.createNativeQuery("insert into [movto-frota-apoio] ([cod-equipto], tipmov, [valor-equipto], "
				+ "[Km-mes] , [mes-ano] , [cod-empresa]) values " + "(" + fa.getTipoVeiculoApoio().getNumero() + ","
				+ tipoMovimento + "," + fa.getValor() + "," + fa.getQuilometragem() + "," + dataAccess(dataReferencia)
				+ "," + empresa + ")").executeUpdate();

	}

	public void incluiMovtoKm(Boletim b) {
		int tipomov = b.getTipoMovimento().ordinal() + 1;
		manager.createNativeQuery(
				"insert into [tab-kilometr] ( cod_piso, tipmov, Km_rodado, [mes-ano], [cod-empresa]) values " + "(1,"
						+ tipomov + "," + b.getQuilometragem_piso1() + ","
						+ dataAccess(b.getDataReferencia()) + "," + b.getEmpresa().getNumero()  + ")")
				.executeUpdate();

		manager.createNativeQuery(
				"insert into [tab-kilometr] ( cod_piso, tipmov, Km_rodado, [mes-ano], [cod-empresa]) values " + "(2,"
						+ tipomov + "," + b.getQuilometragem_piso2() + ","
						+ dataAccess(b.getDataReferencia()) + "," + b.getEmpresa().getNumero()  + ")")
				.executeUpdate();

		manager.createNativeQuery(
				"insert into [tab-kilometr] ( cod_piso, tipmov, Km_rodado, [mes-ano], [cod-empresa]) values " + "(3,"
						+ tipomov + "," + b.getQuilometragem_piso3() + ","
						+ dataAccess(b.getDataReferencia()) + "," + b.getEmpresa().getNumero()  + ")")
				.executeUpdate();

	}

	public void incluiMovtoMateriais(Date dataReferencia, int empresa, int tipoMovimento, Material mt) {

		manager.createNativeQuery("insert into [movto-materiais]  ([cod-material], tipmov, [qtde-material], "
				+ "[preco-unit], [mes-ano]  , [cod-empresa] ) values (" + mt.getTipoMaterial().getNumero() + ","
				+ tipoMovimento + "," + mt.getQuantidade() + "," + mt.getValor() + "," + dataAccess(dataReferencia)
				+ "," + empresa + ")").executeUpdate();

	}

	public void incluiMovtoServicoManut(Date dataReferencia, int empresa, int tipoMovimento, Servico sv) {
		manager.createNativeQuery("insert into [movto-servico-manut] "
				+ "([cod-servico], tipmov, [valor-servico], especificacao, [qtde-serv],"
				+ "[mes-ano],  [cod-empresa] ) values (" + sv.getTipoServico().getNumero() + "," + tipoMovimento + ","
				+ sv.getValor() + "," + sv.getDescricao() + "," + sv.getQuantidade() + "," + dataAccess(dataReferencia)
				+ "," + empresa + ")").executeUpdate();

	}

	private String dataAccess(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
		return "#" + (sdf.format(data)) + "#";
	}
}
