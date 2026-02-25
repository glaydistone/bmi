package br.com.tecnotran.bmi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tecnotran.bmi.model.CoeficienteConsumo;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.TipoPropriedade;
import br.com.tecnotran.bmi.services.BaseService;
import br.com.tecnotran.bmi.util.jsf.FacesUtil;

@Named("planilhaCoeficienteBean")
@ViewScoped
public class PlanilhaCoeficientesBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CoeficienteConsumo> coeficientes;

	private TipoMovimento filtroTipoMovimento;

	private String idStr;

	@Inject
	private PrincipalBean principalBean;

	@Inject
	private BaseDAO dao;

	@Inject
	private BaseService service;

	private CoeficienteConsumo coeficiente;

	public String getIdStr() {
		return idStr;
	}

	@SuppressWarnings("deprecation")
	public void setIdStr(String idStr) {
		this.idStr = idStr;
		this.coeficiente = dao.findById(CoeficienteConsumo.class, new Long(idStr));
	}



	public List<CoeficienteConsumo> getCoeficientes() {
		return coeficientes;
	}

	public void setCoeficientes(List<CoeficienteConsumo> coeficientes) {
		this.coeficientes = coeficientes;
	}

	public CoeficienteConsumo getCoeficiente() {
		return coeficiente;
	}

	public void setCoeficiente(CoeficienteConsumo coeficiente) {
		this.coeficiente = coeficiente;
	}

	public TipoMovimento getFiltroTipoMovimento() {
		return filtroTipoMovimento;
	}

	public void setFiltroTipoMovimento(TipoMovimento filtroTipoMovimento) {
		this.filtroTipoMovimento = filtroTipoMovimento;
	}


	public List<ClausulaFiltro> montaConsulta() {
		List<ClausulaFiltro> retorno = new ArrayList<ClausulaFiltro>();

		
		if (filtroTipoMovimento != null) {
			ClausulaFiltro cf = new ClausulaFiltro();
			cf.setAlias("obj");
			cf.setNomePropriedade("tipoMovimento");
			cf.setTipoPropriedade(TipoPropriedade.STRING);
			cf.setValorPropriedade(filtroTipoMovimento);
			retorno.add(cf);
		}
		return retorno;

	}

	public void pesquisar() {
		List<ClausulaFiltro> clausulas = montaConsulta();
		coeficientes = dao.findAll(CoeficienteConsumo.class, clausulas);
	}
	
	public PlanilhaCoeficientesBean() {
inicializar();
	}
	

	public void inicializar() {
		coeficiente = new CoeficienteConsumo();
	}


	public void salvar() {
		coeficiente.setId(service.salvar(coeficiente));
		FacesUtil.addInfoMessage("Registro salvo com sucesso!");
	}


}
