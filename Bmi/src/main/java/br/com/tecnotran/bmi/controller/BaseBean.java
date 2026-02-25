package br.com.tecnotran.bmi.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tecnotran.bmi.model.BaseEntity;
import br.com.tecnotran.bmi.model.Boletim;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.PlanilhaPessoal;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.TipoPropriedade;
import br.com.tecnotran.bmi.services.BaseService;
import br.com.tecnotran.bmi.util.jsf.FacesUtil;

@Named
@ViewScoped
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	


	private String idStr;
	
	public String getIdStr() {
		return idStr;
	}

	@Inject
	private PrincipalBean principalBean;
	
	@Inject
	private BaseDAO dao;
	
	@Inject
	private BaseService service;
	



}
