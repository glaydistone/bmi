package br.com.tecnotran.bmi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tecnotran.bmi.model.Boletim;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.TipoPropriedade;
import br.com.tecnotran.bmi.services.BoletimService;

@Named
@ViewScoped
public class ParametroConsultaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Empresa filtroEmpresa;
	private TipoMovimento filtroTipoMovimento;
	private Date filtroDataReferencia;

	
	public Empresa getFiltroEmpresa() {
		return filtroEmpresa;
	}


	public void setFiltroEmpresa(Empresa filtroEmpresa) {
		this.filtroEmpresa = filtroEmpresa;
	}


	public TipoMovimento getFiltroTipoMovimento() {
		return filtroTipoMovimento;
	}


	public void setFiltroTipoMovimento(TipoMovimento filtroTipoMovimento) {
		this.filtroTipoMovimento = filtroTipoMovimento;
	}


	public Date getFiltroDataReferencia() {
		return filtroDataReferencia;
	}


	public void setFiltroDataReferencia(Date filtroDataReferencia) {
		this.filtroDataReferencia = filtroDataReferencia;
	}


	public List<ClausulaFiltro> montaConsulta() {
		List<ClausulaFiltro> retorno = new ArrayList<ClausulaFiltro>();
		
		if (filtroEmpresa != null) {
			ClausulaFiltro cf = new ClausulaFiltro();
			cf.setAlias("obj");
			cf.setNomePropriedade("empresa");
			cf.setTipoPropriedade(TipoPropriedade.NUMERO);
			cf.setValorPropriedade(filtroEmpresa);
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
		
		if (filtroDataReferencia != null) {
			ClausulaFiltro cf = new ClausulaFiltro();
			cf.setAlias("obj");
			cf.setNomePropriedade("dataReferencia");
			cf.setTipoPropriedade(TipoPropriedade.DATA);
			cf.setValorPropriedade(filtroDataReferencia);
			retorno.add(cf);
		}
		return retorno;
	}
	


}
