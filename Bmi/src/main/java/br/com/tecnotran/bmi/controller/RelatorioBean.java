package br.com.tecnotran.bmi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.util.jsf.FacesUtil;
import br.com.tecnotran.bmi.util.report.ExecutorRelatorio;
import jakarta.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

@Named
@RequestScoped
public class RelatorioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dataInicio;
	private Date dataFim;
	private Empresa empresa;
	private TipoMovimento tipoMovimento;

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitirComposicaoCusto() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		  parametros.put("dataInicial", sdf.format(this.dataInicio));
		  parametros.put("dataFinal", sdf.format(this.dataFim));
		 
		if (this.empresa != null) {
			parametros.put("idEmpresa", this.empresa.getId());
			parametros.put("nomeEmpresa", this.empresa.getRazaoSocial());
		} else {
			parametros.put("nomeEmpresa", "Todas as empresas");
		}
		parametros.put("tipoMovimento", this.getTipoMovimento());
		
		Session session = manager.unwrap(Session.class);
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/composicaoCusto.jasper", this.response,
				parametros, "Composicao Custo.pdf");
		

		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
  
				facesContext.responseComplete();

		} else {
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}

	}

	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
