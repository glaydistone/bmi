package br.com.tecnotran.bmi.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tecnotran.bmi.model.IndicesEconomicos;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.services.BaseService;
import br.com.tecnotran.bmi.util.jsf.FacesUtil;

@Named("indicesEconomicos")
@ViewScoped
public class IndicesEconomicosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idStr;

	public String getIdStr() {
		return idStr;
	}

	@Inject
	private BaseDAO dao;

	@Inject
	private BaseService service;

	private IndicesEconomicos indice;

	private List<IndicesEconomicos> indices;

	@PostConstruct
	private void inicializar() {
		this.indice = new IndicesEconomicos();
		this.indices = dao.findAll(IndicesEconomicos.class);
	}

	public void salvar() {
		service.salvar(indice);
		FacesUtil.addInfoMessage("Registro salvo com sucesso!");
		inicializar();
	}

	public IndicesEconomicos getIndice() {
		return indice;
	}

	public void setIndice(IndicesEconomicos indice) {
		this.indice = indice;
	}

	public void excluir() {
		service.excluir(indice);
		FacesUtil.addInfoMessage("Registro excluido!");
		inicializar();
	}

	public List<IndicesEconomicos> getIndices() {
		return indices;
	}

	public void setIndices(List<IndicesEconomicos> indices) {
		this.indices = indices;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

}
