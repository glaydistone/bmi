package br.com.tecnotran.bmi.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.PlanilhaPadrao;
import br.com.tecnotran.bmi.model.ValoresIndexados;
import br.com.tecnotran.bmi.model.PlanilhaPessoal;
import br.com.tecnotran.bmi.model.PrecoMaterial;
import br.com.tecnotran.bmi.model.TipoMaterial;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.TipoPropriedade;
import br.com.tecnotran.bmi.services.BaseService;
import br.com.tecnotran.bmi.util.jsf.FacesUtil;

@Named("pessoalBean")
@ViewScoped
public class PlanilhaPessoalBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;


	private List<PlanilhaPessoal> planilhas;

	private Date filtroDataReferencia;
	private TipoMovimento filtroTipoMovimento;

	private String idStr;

	@Inject
	private PrincipalBean principalBean;

	@Inject
	private BaseDAO dao;

	@Inject
	private BaseService service;

	private PlanilhaPessoal planilha;

	public List<PlanilhaPessoal> getPlanilhas() {
		return planilhas;
	}

	public void setPlanilhas(List<PlanilhaPessoal> planilhas) {
		this.planilhas = planilhas;
	}

	public PlanilhaPessoal getPlanilha() {
		return planilha;
	}

	public void setPlanilha(PlanilhaPessoal planilha) {
		this.planilha = planilha;
	}

	public String getIdStr() {
		return idStr;
	}

	@SuppressWarnings("deprecation")
	public void setIdStr(String idStr) {
		this.idStr = idStr;
		this.planilha = dao.findById(PlanilhaPessoal.class, new Long(idStr));
	}

	public void clonar() {
		try {
			setPlanilha( (PlanilhaPessoal) this.planilha.clone());
			FacesUtil.addInfoMessage("Faça as alterações e salve os dados.");
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			FacesUtil.addErrorMessage(e.getMessage());;
		}
	}
	public PlanilhaPessoalBean() {
		inicializar();
	}

	public Date getFiltroDataReferencia() {
		return filtroDataReferencia;
	}

	public void setFiltroDataReferencia(Date filtroMes) {
		this.filtroDataReferencia = filtroMes;
	}

	public List<ClausulaFiltro> montaConsulta() {
		List<ClausulaFiltro> retorno = new ArrayList<ClausulaFiltro>();

		if (filtroDataReferencia != null) {
			ClausulaFiltro cf = new ClausulaFiltro();
			cf.setAlias("obj");
			cf.setNomePropriedade("dataReferencia");
			cf.setTipoPropriedade(TipoPropriedade.DATA);
			cf.setValorPropriedade(filtroDataReferencia);
			retorno.add(cf);
		}
		
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
		planilhas = dao.findAll(PlanilhaPessoal.class, clausulas);
	}

	public void inicializar() {
		planilha = new PlanilhaPessoal();
	}

	public TipoMovimento[] getTiposMovimento() {
		return TipoMovimento.values();
	}

	public void excluir() {
		service.excluir(planilha);
		FacesUtil.addInfoMessage("Registro excluido!");
		inicializar();
	}

	public void salvar() {
		planilha.setId(service.salvar(planilha));
		FacesUtil.addInfoMessage("Registro salvo com sucesso!");
	}



}
