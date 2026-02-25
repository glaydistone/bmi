package br.com.tecnotran.bmi.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tecnotran.bmi.model.PlanilhaPadrao;
import br.com.tecnotran.bmi.model.PrecoFrotaApoio;
import br.com.tecnotran.bmi.model.PrecoMaterial;
import br.com.tecnotran.bmi.model.TipoMaterial;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.model.ValoresIndexados;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.TipoPropriedade;
import br.com.tecnotran.bmi.services.BaseService;
import br.com.tecnotran.bmi.services.NegocioException;
import br.com.tecnotran.bmi.util.jsf.FacesUtil;

@Named("planilhaPrecosBean")
@ViewScoped
public class PlanilhaPrecosBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final long COMBUSTIVEL = 1L;
	private static final long LUBRIFICANTES = 2L;
	private static final long PECAS = 3L;
	private static final long RODAGEM = 4L;

	private List<PlanilhaPadrao> planilhas;

	private Date filtroDataReferencia;

	private String idStr;

	@Inject
	private PrincipalBean principalBean;

	@Inject
	private BaseDAO dao;

	@Inject
	private BaseService service;

	private PlanilhaPadrao planilha;

	public String getIdStr() {
		return idStr;
	}

	@SuppressWarnings("deprecation")
	public void setIdStr(String idStr) {
		this.idStr = idStr;
		this.planilha = dao.findById(PlanilhaPadrao.class, new Long(idStr));
	}

	public PlanilhaPadrao getPlanilha() {
		return planilha;
	}

	public void setPlanilha(PlanilhaPadrao planilha) {
		this.planilha = planilha;
	}

	public void clonar() {
		try {
			setPlanilha((PlanilhaPadrao) this.planilha.clone());
			FacesUtil.addInfoMessage("Faça as alterações e salve os dados.");
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			FacesUtil.addErrorMessage(e.getMessage());

		}
	}
	
	public TipoMovimento[] getTiposMovimento() {
		return TipoMovimento.values();
	}

	public List<PlanilhaPadrao> getPlanilhas() {
		return planilhas;
	}

	public void setPlanilhas(List<PlanilhaPadrao> planilhas) {
		this.planilhas = planilhas;
	}

	public PlanilhaPrecosBean() {
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
		return retorno;

	}

	public void pesquisar() {
		List<ClausulaFiltro> clausulas = montaConsulta();
		planilhas = dao.findAll(PlanilhaPadrao.class, clausulas, "dataReferencia" );
	}

	public void inicializar() {
		//planilha = new PlanilhaPadrao();
	}

	public void excluir() {
		service.excluir(planilha);
		FacesUtil.addInfoMessage("Registro excluido!");
		inicializar();
	}

	public void salvar() {
		try {

		    Long id = service.salvar(planilha);
		    planilha.setId(id);
			FacesUtil.addInfoMessage("Registro salvo com sucesso!");
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void atualizaValores()  {

		PlanilhaPadrao mesPassado = null;
		PlanilhaPadrao atual = this.getPlanilha();

		try {
			if (atual.getFgvPecas().equals(BigDecimal.ZERO)) {
				throw new NegocioException("O índice FGV 1416653 (Peças) deve ser maior que 0 (zero).");
			}

			if (atual.getFgvRodagem().equals(BigDecimal.ZERO)) {
				throw new NegocioException("O índice FGV 1420741 (Rodagem) deve ser maior que 0 (zero).");
			}

			if (atual.getIpca().equals(BigDecimal.ZERO)) {
				throw new NegocioException("O índice IPCA deve ser maior que 0 (zero).");
			}
			
			if (atual.getIgpm().equals(BigDecimal.ZERO)) {
				throw new NegocioException("O índice IGPM deve ser maior que 0 (zero).");
			}
			
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(planilha.getDataReferencia());
			gc.add(GregorianCalendar.MONTH, -1);
			List<PlanilhaPadrao> aux = dao.findByProperty(PlanilhaPadrao.class, "dataReferencia", gc.getTime());
			BigDecimal indiceIpca = BigDecimal.ONE;
			BigDecimal indiceFgvPecas = BigDecimal.ONE;
			BigDecimal indiceFgvRodagem = BigDecimal.ONE;
			BigDecimal indiceIgpm = BigDecimal.ONE;
			if ((aux != null) && (aux.size() > 0)) {
				mesPassado = aux.get(0);
			}

			if (mesPassado != null) {
				indiceIpca = atual.getIpca().divide(mesPassado.getIpca(), RoundingMode.CEILING);
				indiceFgvPecas = atual.getFgvPecas().divide(mesPassado.getFgvPecas(), RoundingMode.CEILING);
				indiceFgvRodagem = atual.getFgvRodagem().divide(mesPassado.getFgvRodagem(), RoundingMode.CEILING);
				indiceIgpm = atual.getIgpm().divide(mesPassado.getIgpm(), RoundingMode.CEILING);
			}

			for (PrecoMaterial prc : atual.getPrecosMaterial()) {
				if (prc.getTipoMaterial().getCategoriaMaterial().getNumero().equals(RODAGEM)) {
					prc.setValor(prc.getValor().multiply(indiceFgvRodagem));
				}
			}

			for (ValoresIndexados prc : atual.getValoresIndexados()) {
				prc.setDespesaAdministrativa(prc.getDespesaAdministrativa().multiply(indiceIgpm));
				prc.setTaxasTributos(prc.getTaxasTributos().multiply(indiceIpca));
				prc.setServicosPecas(prc.getServicosPecas().multiply(indiceFgvPecas));
			}

			for (PrecoFrotaApoio prc : atual.getPrecosFrotaApoio()) {
				prc.setCustoMaximo(prc.getCustoMaximo().multiply(indiceFgvPecas));
				prc.setCustoMinimo(prc.getCustoMinimo().multiply(indiceFgvPecas));
			}
			FacesUtil.addInfoMessage("Os valores foram atualizados.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	private void copiaMesAnterior() {
		PlanilhaPadrao mesPassado = null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(planilha.getDataReferencia());
		gc.add(GregorianCalendar.MONTH, -1);
		List<PlanilhaPadrao> aux = dao.findByProperty(PlanilhaPadrao.class, "dataReferencia", gc.getTime());
		if ((aux != null) && (aux.size() > 0)) {
			mesPassado = aux.get(0);
		}

		if (mesPassado == null) {
			for (TipoMaterial tm : principalBean.getListaTipoMaterial()) {
				PrecoMaterial pm = new PrecoMaterial(tm, BigDecimal.ZERO);
				pm.setPlanilhaPreco(planilha);
				planilha.getPrecosMaterial().add(pm);
			}
		} else {
			for (PrecoMaterial pmMp : mesPassado.getPrecosMaterial()) {
				PrecoMaterial pm = new PrecoMaterial(pmMp.getTipoMaterial(), pmMp.getValor());
				pm.setPlanilhaPreco(planilha);
				planilha.getPrecosMaterial().add(pm);
			}
		}
	}

}
