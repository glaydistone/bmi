package br.com.tecnotran.bmi.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.tecnotran.bmi.model.BaseEntity;
import br.com.tecnotran.bmi.model.Boletim;
import br.com.tecnotran.bmi.model.Cargo;
import br.com.tecnotran.bmi.model.CoeficienteConsumo;
import br.com.tecnotran.bmi.model.CustoPessoal;
import br.com.tecnotran.bmi.model.DespesaGeral;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.FrotaApoio;
import br.com.tecnotran.bmi.model.Material;
import br.com.tecnotran.bmi.model.PlanilhaPadrao;
import br.com.tecnotran.bmi.model.PlanilhaPessoal;
import br.com.tecnotran.bmi.model.PrecoMaterial;
import br.com.tecnotran.bmi.model.ReceitaImobilizado;
import br.com.tecnotran.bmi.model.Servico;
import br.com.tecnotran.bmi.model.TipoCargo;
import br.com.tecnotran.bmi.model.TipoDespesa;
import br.com.tecnotran.bmi.model.TipoMaterial;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.model.TipoReceitaImobilizado;
import br.com.tecnotran.bmi.model.TipoServico;
import br.com.tecnotran.bmi.model.TipoVeiculoApoio;
import br.com.tecnotran.bmi.model.ValoresIndexados;
import br.com.tecnotran.bmi.model.seguranca.UsuarioSistema;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.Operador;
import br.com.tecnotran.bmi.repository.TipoPropriedade;
import br.com.tecnotran.bmi.services.BoletimService;
import br.com.tecnotran.bmi.services.NegocioException;
import br.com.tecnotran.bmi.util.jsf.FacesUtil;

@Named
@ViewScoped
public class BoletimBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final long PESSOAL_ADM = 1L;
	private static final long PESSOAL_MANUTENCAO = 2L;
	private static final long PESSOAL_OPERACAO = 3L;
	private static final long MOTORISTA = 25L;
	private static final long COBRADOR = 23L;
	private static final long FISCAL = 24L;
	private static final long DIRETOR = 4L;

	private static final long COMBUSTIVEL = 1L;
	private static final long LUBRIFICANTES = 2L;
	private static final long PECAS = 3L;
	private static final long RODAGEM = 4L;

	private static final long GRAXA = 2L;
	private static final long CAIXA = 3L;
	private static final long DIFERENCIAL = 4L;
	private static final long FREIO = 5L;
	private static final long MOTOR = 6L;

	private static final BigDecimal CEM = new BigDecimal(100);
	private String idStr;
	private String modo;

	private BigDecimal aleatorio;

	private UploadedFile arquivo;

	@Inject
	private PrincipalBean principalBean;

	@Inject
	private BaseDAO dao;

	@Inject
	private BoletimService service;

	private Boletim boletim;

	private Boletim boletimSistema;

	private List<Empresa> empresas;

	private List<Boletim> boletins;

	private List<CoeficienteConsumo> coeficientes = new ArrayList<CoeficienteConsumo>();

	private Material custoTotalMaterial;

	private List<Material> custoVariavel = new ArrayList<Material>();
	private List<Material> rodagem = new ArrayList<Material>();
	private List<CustoPessoal> custosPessoal = new ArrayList<CustoPessoal>();

	private Empresa filtroEmpresa;
	private TipoMovimento filtroTipoMovimento;
	private Date filtroDataReferencia;
	private TipoCargo tipoCargoSelecao;

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public void handleUpload(FileUploadEvent event) {

		UploadedFile arquivo = event.getFile();
		System.out.println("Não setou o arquivo");
		if (arquivo != null) {
			// salvarArquivo(file);
			System.out.println("Arquivo enviado: " + arquivo.getFileName());
		}
	}

	/**
	 * Retorna um número aleatório entre 0,90 e 1,10
	 * 
	 * @return
	 */
	public static BigDecimal getAleatorio() {
		BigDecimal rnd = new BigDecimal(Math.random() * 0.20);
		BigDecimal retorno = new BigDecimal("1.10");
		retorno = retorno.subtract(rnd, MathContext.DECIMAL32);
		return retorno;
	}

	public TipoCargo getTipoCargoSelecao() {
		return tipoCargoSelecao;
	}

	public void setTipoCargoSelecao(TipoCargo tipoCargoSelecao) {
		this.tipoCargoSelecao = tipoCargoSelecao;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {

		if (modo.equals("c")) {
			montaDadosParaConsistencia();
		}
	}

	@SuppressWarnings("deprecation")
	public void setIdStr(String idStr) {
		this.idStr = idStr;
		this.boletim = dao.findById(Boletim.class, new Long(idStr));
	}

	public String getIdStr() {
		return idStr;
	}

	public Boletim getBoletimSistema() {
		return boletimSistema;
	}

	public void setBoletimSistema(Boletim boletimSistema) {
		this.boletimSistema = boletimSistema;
	}

	private void montaDadosParaConsistencia() {
		filtroDataReferencia = boletim.getDataReferencia();
		filtroTipoMovimento = boletim.getTipoMovimento();
		pesquisar();

		boletimSistema = new Boletim();
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();
		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("dataReferencia");
		c2.setTipoPropriedade(TipoPropriedade.DATA);
		c2.setValorPropriedade(boletim.getDataReferencia());
		filtros.add(c2);
		List<PlanilhaPadrao> aux = dao.findAll(PlanilhaPadrao.class, filtros);
		boletim.getCombusLubrif().clear();
		boletim.getRodagem().clear();
		boletim.getValePlano().clear();
		boletim.getPecasServicos().clear();
		if (aux.size() > 0) {
			montaCustoPessoal(boletim, aux.get(0));
			montaCustoVariavel(boletim, aux.get(0));
		}

	}

	public void onEditaRegistro(RowEditEvent<BaseEntity> event) {
		salvarEconsistir();
	}

	public void onCancelaRegistro(RowEditEvent<BaseEntity> event) {

	}

	public void limparCombustivelLubrificante() {
		for (Material mat : boletim.getCombusLubrif()) {
			mat.setQuantidade(BigDecimal.ZERO);
			mat.setValor(BigDecimal.ZERO);
		}
	}

	public void limparRodagem() {
		for (Material mat : boletim.getRodagem()) {
			mat.setQuantidade(BigDecimal.ZERO);
			mat.setValor(BigDecimal.ZERO);
		}
	}

	public void limparDespesasAdm() {
		for (DespesaGeral desp : boletim.getDespesasAdm()) {
			desp.setDescricao(null);
			desp.setValor(BigDecimal.ZERO);
		}
	}

	public void limparTributos() {
		for (DespesaGeral desp : boletim.getTributos()) {
			desp.setDescricao(null);
			desp.setValor(BigDecimal.ZERO);
		}
	}

	public void limparValePlano() {
		for (DespesaGeral desp : boletim.getValePlano()) {
			desp.setDescricao(null);
			desp.setValor(BigDecimal.ZERO);
		}
	}

	public void limparImobilizado() {
		for (ReceitaImobilizado rec : boletim.getReceitasImobilizado()) {
			rec.setDescricao(null);
			rec.setValor(BigDecimal.ZERO);
		}
	}

	public void limparFrotaApoio() {
		for (FrotaApoio frt : boletim.getFrotasApoio()) {
			frt.setQuilometragem(BigDecimal.ZERO);
			frt.setValor(BigDecimal.ZERO);
		}
	}

	public void limparPessoalAdm() {
		for (CustoPessoal pes : boletim.getPessoalAdministrativo()) {
			pes.setElementos(0);
			pes.setEncargosSociais(BigDecimal.ZERO);
			pes.setHorasExtras(BigDecimal.ZERO);
			pes.setOutrosPagamentos(BigDecimal.ZERO);
			pes.setPremios(BigDecimal.ZERO);
			pes.setSalarios(BigDecimal.ZERO);
		}
	}

	public void limparPessoalMan() {
		for (CustoPessoal pes : boletim.getPessoalManutencao()) {
			pes.setElementos(0);
			pes.setEncargosSociais(BigDecimal.ZERO);
			pes.setHorasExtras(BigDecimal.ZERO);
			pes.setOutrosPagamentos(BigDecimal.ZERO);
			pes.setPremios(BigDecimal.ZERO);
			pes.setSalarios(BigDecimal.ZERO);
		}
	}

	public void limparPessoalOpera() {
		for (CustoPessoal pes : boletim.getPessoalOperacao()) {
			pes.setElementos(0);
			pes.setEncargosSociais(BigDecimal.ZERO);
			pes.setHorasExtras(BigDecimal.ZERO);
			pes.setOutrosPagamentos(BigDecimal.ZERO);
			pes.setPremios(BigDecimal.ZERO);
			pes.setSalarios(BigDecimal.ZERO);
		}
	}

	public void limparPessoalOutros() {
		for (CustoPessoal pes : boletim.getPessoalOutros()) {
			pes.setElementos(0);
			pes.setEncargosSociais(BigDecimal.ZERO);
			pes.setHorasExtras(BigDecimal.ZERO);
			pes.setOutrosPagamentos(BigDecimal.ZERO);
			pes.setPremios(BigDecimal.ZERO);
			pes.setSalarios(BigDecimal.ZERO);
		}
	}

	public void limparServicos() {
		for (Servico serv : boletim.getServicos()) {
			serv.setValor(BigDecimal.ZERO);
		}
		for (Material mat : boletim.getPecasServicos()) {
			mat.setQuantidade(BigDecimal.ZERO);
			mat.setValor(BigDecimal.ZERO);
		}

	}

	public void totalizarMaterial(String categoria) {
		custoTotalMaterial = new Material();
		for (Material cv : boletim.getMateriais()) {
			if (cv.getTipoMaterial().getCategoriaMaterial().getNumero().toString().contentEquals(categoria)) {
				custoTotalMaterial.setQuantidade(custoTotalMaterial.getQuantidade().add(cv.getQuantidade()));
				custoTotalMaterial.setValor(custoTotalMaterial.getValor().add(cv.getValor()));
			}
		}
		if (boletim.getQuilometragemTotal().compareTo(BigDecimal.ZERO) > 0) {
			custoTotalMaterial.setIndiceConsumo(custoTotalMaterial.getQuantidade()
					.divide(boletim.getQuilometragemTotal(), new MathContext(8, RoundingMode.HALF_EVEN)));
			custoTotalMaterial.setCustoMinimo(custoTotalMaterial.getValor().divide(boletim.getQuilometragemTotal(),
					new MathContext(8, RoundingMode.HALF_EVEN)));
		}
	}

	private void montaCustoVariavel(Boletim boletim, PlanilhaPadrao pp) {
		for (Material cv : boletim.getMateriais()) {
			if (cv.getTipoMaterial().getCategoriaMaterial().getNumero().equals(RODAGEM)) {
				boletim.getRodagem().add(cv);
				try {
					boletim.setCustoRodagemKm(boletim.getCustoRodagemKm().add(cv.getValor()));
				} catch (Exception e) {
					boletim.setCustoRodagemKm(BigDecimal.ZERO);
				}
			}

			if (cv.getTipoMaterial().getCategoriaMaterial().getNumero().equals(PECAS)) {
				boletim.getPecasServicos().add(cv);
			}
			if (cv.getTipoMaterial().getCategoriaMaterial().getNumero().equals(COMBUSTIVEL)) {
				boletim.getCombusLubrif().add(cv);
			}

			if (cv.getTipoMaterial().getCategoriaMaterial().getNumero().equals(LUBRIFICANTES)) {
				boletim.getCombusLubrif().add(cv);
			}

		}

		for (PrecoMaterial pm : pp.getPrecosMaterial()) {
			for (Material cv : boletim.getMateriais()) {
				if (cv.getTipoMaterial().getNumero().equals(pm.getTipoMaterial().getNumero())) {
					cv.setPrecoPlanilha(pm.getValor());
				}
			}
		}

		for (CoeficienteConsumo cf : pp.getCoeficienteConsumo()) {
			for (Material cv : boletim.getMateriais()) {
				if (cv.getTipoMaterial().getNumero().equals(cf.getTipoMaterial().getNumero())) {
					if (cf.getTipoMovimento().equals(boletim.getTipoMovimento())) {
						cv.setIndiceConsumoPlanilha(cf.getCoeficiente());
						cv.setCustoQuilometroPlanilha(
								cv.getPrecoPlanilha().multiply(cf.getCoeficiente(), MathContext.DECIMAL32));
					}
				}
			}
		}

		for (ValoresIndexados v : pp.getValoresIndexados()) {
			if (v.getTipoMovimento().equals(boletim.getTipoMovimento())) {
				boletim.setCustoRodagemKmSistema(v.getRodagem());
				boletim.setCustoPecasSistema(v.getServicosPecas());
				boletim.setTributosSistema(v.getTaxasTributos());
				boletim.setDespesasSistema(v.getDespesaAdministrativa());
			}

		}
		if (boletim.getQuilometragemTotal().compareTo(BigDecimal.ZERO) > 0) {
			for (Material cv : boletim.getCombusLubrif()) {

				cv.setCustoQuilometro(cv.getValor().divide(boletim.getQuilometragemTotal(),
						new MathContext(8, RoundingMode.HALF_EVEN)));

				cv.setIndiceConsumo(cv.getQuantidade().divide(boletim.getQuilometragemTotal(),
						new MathContext(8, RoundingMode.HALF_EVEN)));
			}
			for (Material cv : boletim.getRodagem()) {
				cv.setCustoQuilometro(cv.getValor().divide(boletim.getQuilometragemTotal(),
						new MathContext(8, RoundingMode.HALF_EVEN)));
				cv.setIndiceConsumo(cv.getQuantidade().divide(boletim.getQuilometragemTotal(),
						new MathContext(8, RoundingMode.HALF_EVEN)));
			}
			boletim.setCustoRodagemKm(boletim.getCustoRodagemKm().divide(boletim.getQuilometragemTotal(),
					new MathContext(8, RoundingMode.HALF_EVEN)));

		}

	}

	public Material getCustoTotalMaterial() {
		return custoTotalMaterial;
	}

	public void setCustoTotalMaterial(Material custoTotalMaterial) {
		this.custoTotalMaterial = custoTotalMaterial;
	}

	private void montaCustoPessoal(Boletim boletim, PlanilhaPadrao plan) {

		boletim.getPessoalAdministrativo().clear();
		boletim.getPessoalManutencao().clear();
		boletim.getPessoalOperacao().clear();
		boletim.getPessoalOutros().clear();

		custosPessoal.clear();
		CustoPessoal cp_adm = new CustoPessoal();
		cp_adm.setBoletim(boletim);
		cp_adm.setTipoCargo(new TipoCargo("Pessoal Administrativo"));
		custosPessoal.add(cp_adm);
		boletim.setCustoAdministracao(cp_adm);

		CustoPessoal cp_man = new CustoPessoal();
		cp_man.setBoletim(boletim);
		cp_man.setTipoCargo(new TipoCargo("Pessoal Manutenção"));
		custosPessoal.add(cp_man);
		boletim.setCustoManutencao(cp_man);

		CustoPessoal cp_mot = new CustoPessoal();
		cp_mot.setBoletim(boletim);
		cp_mot.setTipoCargo(new TipoCargo("Motorista"));
		custosPessoal.add(cp_mot);
		boletim.setCustoMotorista(cp_mot);

		CustoPessoal cp_cob = new CustoPessoal();
		cp_cob.setBoletim(boletim);
		cp_cob.setTipoCargo(new TipoCargo("Cobrador"));
		custosPessoal.add(cp_cob);
		boletim.setCustoCobrador(cp_cob);

		CustoPessoal cp_fis = new CustoPessoal();
		cp_fis.setBoletim(boletim);
		cp_fis.setTipoCargo(new TipoCargo("Fiscal"));
		custosPessoal.add(cp_fis);
		boletim.setCustoFiscal(cp_fis);

		CustoPessoal cp_ope = new CustoPessoal();
		cp_ope.setBoletim(boletim);
		cp_ope.setTipoCargo(new TipoCargo("Outros operação"));
		custosPessoal.add(cp_ope);
		boletim.setCustoOperacao(cp_ope);

		PlanilhaPessoal pp = null;
		for (PlanilhaPessoal aux : plan.getPlanilhaPessoal()) {
			if (aux.getTipoMovimento().equals(boletim.getTipoMovimento())) {
				pp = aux;
				break;
			}
		}

		BigDecimal planilhaVale = BigDecimal.ZERO;
		BigDecimal planilhaSaude = BigDecimal.ZERO;

		if (pp != null) {
			cp_adm.setSalarioMedioPlanilha(pp.getSalarioAdministracao());
			cp_adm.setRemuneracaoMediaPlanilha(pp.getAdicionalAdministracao().add(pp.getSalarioAdministracao()));
			cp_adm.setFatorUtilizacaoPlanilha(pp.getFuAdministracao());
			cp_adm.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));
			planilhaVale = pp.getValeAlimentacao();
			planilhaSaude = pp.getPlanoSaude();

			cp_man.setSalarioMedioPlanilha(pp.getSalarioManutencao());
			cp_man.setRemuneracaoMediaPlanilha(pp.getAdicionalManutencao().add(pp.getSalarioManutencao()));
			cp_man.setFatorUtilizacaoPlanilha(pp.getFuManutencao());
			cp_man.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));

			cp_mot.setSalarioMedioPlanilha(pp.getSalarioMotorista());
			cp_mot.setRemuneracaoMediaPlanilha(pp.getAdicionalMotorista().add(pp.getSalarioMotorista()));
			cp_mot.setFatorUtilizacaoPlanilha(pp.getFuMotorista());
			cp_mot.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));

			cp_cob.setSalarioMedioPlanilha(pp.getSalarioCobrador());
			cp_cob.setRemuneracaoMediaPlanilha(pp.getAdicionalCobrador().add(pp.getSalarioCobrador()));
			cp_cob.setFatorUtilizacaoPlanilha(pp.getFuCobrador());
			cp_cob.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));

			cp_fis.setSalarioMedioPlanilha(pp.getSalarioFiscal());
			cp_fis.setRemuneracaoMediaPlanilha(pp.getAdicionalFiscal().add(pp.getSalarioFiscal()));
			cp_fis.setFatorUtilizacaoPlanilha(pp.getFuFiscal());
			cp_fis.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));

			cp_ope.setSalarioMedioPlanilha(pp.getSalarioOutros());
			cp_ope.setRemuneracaoMediaPlanilha(pp.getAdicionalOutros().add(pp.getSalarioOutros()));
			cp_ope.setFatorUtilizacaoPlanilha(pp.getFuOutros());
			cp_ope.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));
		}

		CustoPessoal custo;

		Integer totalElementos = 0;
		BigDecimal remuneraDiretor = BigDecimal.ZERO;

		for (CustoPessoal cp : boletim.getCustosPessoal()) {
			custo = null;
			if (cp.getCargo().getTipoCargo().getNumero().equals(PESSOAL_ADM)) {
				boletim.getPessoalAdministrativo().add(cp);
				custo = cp_adm;
			}

			if (cp.getCargo().getTipoCargo().getNumero().equals(PESSOAL_MANUTENCAO)) {
				boletim.getPessoalManutencao().add(cp);
				custo = cp_man;
			}
			if (cp.getCargo().getTipoCargo().getNumero().equals(PESSOAL_OPERACAO)) {
				if (cp.getCargo().getNumero().equals(MOTORISTA)) {
					if (pp != null) {
						cp.setFatorUtilizacaoPlanilha(pp.getFuMotorista());
						cp.setSalarioMedioPlanilha(pp.getSalarioMotorista());
						cp.setRemuneracaoMediaPlanilha(pp.getAdicionalMotorista().add(pp.getSalarioMotorista()));
						cp.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));
					}
					custo = cp_mot;
					boletim.getPessoalOperacao().add(cp);
				} else {

					if (cp.getCargo().getNumero().equals(COBRADOR)) {
						custo = cp_cob;
						if (pp != null) {
							cp.setFatorUtilizacaoPlanilha(pp.getFuCobrador());
							cp.setSalarioMedioPlanilha(pp.getSalarioCobrador());
							cp.setRemuneracaoMediaPlanilha(pp.getAdicionalCobrador().add(pp.getSalarioCobrador()));
							cp.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));
						}
						boletim.getPessoalOperacao().add(cp);
					} else {

						if (cp.getCargo().getNumero().equals(FISCAL)) {
							if (pp != null) {
								cp.setFatorUtilizacaoPlanilha(pp.getFuFiscal());
								cp.setSalarioMedioPlanilha(pp.getSalarioFiscal());
								cp.setRemuneracaoMediaPlanilha(pp.getAdicionalFiscal().add(pp.getSalarioFiscal()));
								cp.setTaxaEncargosPlanilha(pp.getTaxaEncargos().divide(CEM));
							}
							custo = cp_fis;
							boletim.getPessoalOperacao().add(cp);
						} else {
							boletim.getPessoalOutros().add(cp);
							custo = cp_ope;
						}
					}
				}
			}
			custo.setElementos(custo.getElementos() + cp.getElementos());
			custo.setEncargosSociais(custo.getEncargosSociais().add(cp.getEncargosSociais()));
			custo.setHorasExtras(custo.getHorasExtras().add(cp.getHorasExtras()));
			custo.setOutrosPagamentos(custo.getOutrosPagamentos().add(cp.getOutrosPagamentos()));
			custo.setPremios(custo.getPremios().add(cp.getPremios()));
			custo.setSalarios(custo.getSalarios().add(cp.getSalarios()));
			totalElementos = totalElementos + cp.getElementos();
			if (cp.getCargo().getNumero().equals(DIRETOR)) {
				remuneraDiretor = remuneraDiretor.add(cp.getSalarios()).add(cp.getHorasExtras()).add(cp.getPremios())
						.add(cp.getOutrosPagamentos());
			}
		}
		boletim.setElementos(totalElementos);
		BigDecimal encargosAdm = cp_adm.getRemuneracaoTotal().subtract(remuneraDiretor);
		encargosAdm = encargosAdm.multiply(cp_adm.getTaxaEncargosPlanilha());
		BigDecimal encargosDiretor = remuneraDiretor.multiply(new BigDecimal("0.2"));
		BigDecimal taxaEncargosAdm = (encargosAdm.add(encargosDiretor));
		if (cp_adm.getRemuneracaoTotal().equals(BigDecimal.ZERO)) {
			cp_adm.setTaxaEncargosPlanilha(BigDecimal.ZERO);
		} else {
			try {
				cp_adm.setTaxaEncargosPlanilha(
						taxaEncargosAdm.divide(cp_adm.getRemuneracaoTotal(), new MathContext(8, RoundingMode.CEILING)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				cp_adm.setTaxaEncargosPlanilha(BigDecimal.ZERO);
			}
		}
		boletim.getValePlano().clear();
		boletim.getDespesasAdm().clear();
		boletim.getTributos().clear();
		for (DespesaGeral dg : boletim.getDespesasGerais()) {

			if (dg.getTipoDespesa().getNumero() == 1 || dg.getTipoDespesa().getNumero() == 25) {
				/*
				 * if (boletim.getElementos() > 0) { dg.setValorUnitario(
				 * dg.getValor().divide(new BigDecimal(boletim.getElementos()),
				 * RoundingMode.CEILING)); }
				 */
				boletim.getValePlano().add(dg);
				if (dg.getTipoDespesa().getNumero() == 1) {
					dg.setValorPlanilha(planilhaVale);
				} else {
					dg.setValorPlanilha(planilhaSaude);
				}
			} else {
				if (dg.getTipoDespesa().getNumero() == 23
						|| dg.getTipoDespesa().getCategoriaDespesa().getNumero() == 8) {
					boletim.getTributos().add(dg);
				} else {
					boletim.getDespesasAdm().add(dg);
				}
			}

		}

		for (CustoPessoal cp : custosPessoal) {
			cp.setRemuneracaoTotal(
					cp.getSalarios().add(cp.getHorasExtras().add(cp.getOutrosPagamentos().add(cp.getPremios()))));
			if (cp.getRemuneracaoTotal().compareTo(BigDecimal.ZERO) > 0) {
				cp.setTaxaEncargos(cp.getEncargosSociais().divide(cp.getRemuneracaoTotal(), 6, RoundingMode.HALF_EVEN));
			}
			if (boletim.getFrotaTotal() > 0) {
				cp.setFatorUtilizacao(new BigDecimal(cp.getElementos() / boletim.getFrotaTotal().doubleValue()));
			}
		}
	}

	public BoletimBean() {
		boletim = new Boletim();
		boletimSistema = new Boletim();
	}

	public List<Material> getCustoVariavel() {
		return custoVariavel;
	}

	public void setCustoVariavel(List<Material> custoVariavel) {
		this.custoVariavel = custoVariavel;
	}

	public List<CustoPessoal> getCustosPessoal() {
		return custosPessoal;
	}

	public void setCustosPessoal(List<CustoPessoal> custosPessoal) {
		this.custosPessoal = custosPessoal;
	}

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

	public void setFiltroDataReferencia(Date filtroMes) {
		this.filtroDataReferencia = filtroMes;
	}

	public List<ClausulaFiltro> montaConsulta() {
		List<ClausulaFiltro> retorno = new ArrayList<ClausulaFiltro>();

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {
			if (((UsuarioSistema) auth.getPrincipal()).getUsuario().getEmpresa() != null) {
				filtroEmpresa = ((UsuarioSistema) auth.getPrincipal()).getUsuario().getEmpresa();
			}
		}

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

	public List<Boletim> getBoletins() {
		return boletins;
	}

	public void setBoletins(List<Boletim> boletins) {
		this.boletins = boletins;
	}

	public TipoMovimento[] getTiposMovimento() {
		return TipoMovimento.values();
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public void pesquisar() {
		List<ClausulaFiltro> clausulas = montaConsulta();
		boletins = dao.findAll(Boletim.class, clausulas, "id_empresa");
	}

	private void linhaParaBoletim(String linha, Boletim bmiImport) {
		String[] campos = linha.split(";");
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();

		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("numero");
		c2.setOperador(Operador.IGUAL);
		c2.setTipoPropriedade(TipoPropriedade.NUMERO);
		c2.setValorPropriedade(Long.parseLong(campos[1]));
		filtros.add(c2);

		bmiImport.setEmpresa(dao.findAll(Empresa.class, filtros).get(0));
		try {
			bmiImport.setDataReferencia(new SimpleDateFormat("dd/MM/yy").parse(campos[2]));
		} catch (ParseException e) {
			throw new NegocioException("A data de referência está incorreta. (" + campos[2] + ")");
		}
		if (campos[3].equals("1")) {
			bmiImport.setTipoMovimento(TipoMovimento.SEMI_URBANO);
		} else {
			bmiImport.setTipoMovimento(TipoMovimento.RODOVIARIO);
		}
		try {
			bmiImport.setFrotaEfetiva(Integer.parseInt(campos[4]));
			bmiImport.setFrotaReserva(Integer.parseInt(campos[5]));
		} catch (NumberFormatException e) {
			throw new NegocioException("Arquivo inconsistente");
		}
	}

	private Boletim importarBMI(UploadedFile arquivo, Boletim bmiImport) {
		Integer conta = 1;

		try {

			if (arquivo.getFileName() == null) {
				throw new NegocioException("Selecione um arquivo para importação");
			}

			BufferedReader leitor;

			leitor = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(arquivo.getContent())));

			String linha;

			// Lê cabeçalho (descarta)
			leitor.readLine();

			while ((linha = leitor.readLine()) != null) {
				conta++;
				if (linha.trim().isEmpty())
					continue;

				String[] campos = linha.split(";");

				int tipoRegistro;
				try {
					tipoRegistro = Integer.parseInt(campos[0].trim());

					if (tipoRegistro == 1) {
						linhaParaBoletim(linha, bmiImport);
					}

					if (tipoRegistro == 2) {
						linhaParaCustoPessoal(linha, bmiImport);
					}

					if (tipoRegistro == 3) {
						linhaParaDespesaGeral(linha, bmiImport);
					}

					if (tipoRegistro == 4) {
						linhaParaReceitaImobilizado(linha, bmiImport);
					}

					if (tipoRegistro == 5) {
						linhaParaFrotaApoio(linha, bmiImport);
					}

					if (tipoRegistro == 6) {
						linhaParaMateriais(linha, bmiImport);
					}

					if (tipoRegistro == 7) {
						linhaParaServico(linha, bmiImport);
					}

					if (tipoRegistro == 8) {
						linhaParaKm(linha, bmiImport);
					}
				} catch (Exception e) {
					throw new NegocioException(
							"Inconsistência encontrada no conteúdo do arquivo. Erro na linha " + conta);
				}
			}
			List<Cargo> cargos = dao.findAll(Cargo.class);
			boolean existe;
			Cargo faltaCargo = null;
			for (Cargo cargo :cargos) {
				existe = false;
				for (CustoPessoal cp: bmiImport.getCustosPessoal()) {
					faltaCargo = cargo;
					if (cp.getCargo().equals(cargo)) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					CustoPessoal cpes = new CustoPessoal();
					bmiImport.getCustosPessoal().add(cpes);
					cpes.setBoletim(bmiImport);
					cpes.setCargo(faltaCargo);
				}
			}
			
			List<TipoDespesa> tipoDespesas = dao.findAll(TipoDespesa.class);
			TipoDespesa faltaTpd = null;
			for (TipoDespesa tpd :tipoDespesas) {
				existe = false;
				for (DespesaGeral dg: bmiImport.getDespesasGerais()) {
					faltaTpd = tpd;
					if (dg.getTipoDespesa().equals(tpd)) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					DespesaGeral dgr = new DespesaGeral();
					bmiImport.getDespesasGerais().add(dgr);
					dgr.setBoletim(bmiImport);
					dgr.setTipoDespesa(faltaTpd);
				}
			}
			
			List<TipoMaterial> materiais = dao.findAll(TipoMaterial.class);
			TipoMaterial faltaMat = null;
			for (TipoMaterial tpm :materiais) {
				existe = false;
				for (Material mt: bmiImport.getMateriais()) {
					faltaMat = tpm;
					if (mt.getTipoMaterial().equals(tpm)) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					Material mt = new Material();
					bmiImport.getMateriais().add(mt);
					mt.setBoletim(bmiImport);
					mt.setTipoMaterial(faltaMat);
					mt.setPreco(BigDecimal.ZERO);
				}
			}

		} catch (IOException e) {
			throw new NegocioException(e.getMessage());
		}

		return boletim;

	}

	private void linhaParaReceitaImobilizado(String linha, Boletim bmiImport) {
		String[] campos = linha.split(";");
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();

		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("numero");
		c2.setOperador(Operador.IGUAL);
		c2.setTipoPropriedade(TipoPropriedade.NUMERO);
		c2.setValorPropriedade(Long.parseLong(campos[3]));
		filtros.add(c2);

		ReceitaImobilizado ri = new ReceitaImobilizado();
		bmiImport.getReceitasImobilizado().add(ri);

		ri.setBoletim(bmiImport);
		ri.setTipoReceitaImobilizado(dao.findAll(TipoReceitaImobilizado.class, filtros).get(0));
		ri.setValor(new BigDecimal(campos[5]).divide(CEM));
	}

	private void linhaParaKm(String linha, Boletim bmiImport) {
		String[] campos = linha.split(";");
		if ((campos[4]).equals("1")) {
			bmiImport.setQuilometragem_piso1(new BigDecimal(campos[5]).divide(CEM));
		}

		if ((campos[4]).equals("2")) {
			bmiImport.setQuilometragem_piso2(new BigDecimal(campos[5]).divide(CEM));
		}
		if ((campos[4]).equals("3")) {
			bmiImport.setQuilometragem_piso3(new BigDecimal(campos[5]).divide(CEM));
		}

	}

	private void linhaParaServico(String linha, Boletim bmiImport) {
		String[] campos = linha.split(";");
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();

		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("numero");
		c2.setOperador(Operador.IGUAL);
		c2.setTipoPropriedade(TipoPropriedade.NUMERO);
		c2.setValorPropriedade(Long.parseLong(campos[3]));
		filtros.add(c2);

		Servico sv = new Servico();
		bmiImport.getServicos().add(sv);

		sv.setBoletim(bmiImport);
		sv.setTipoServico(dao.findAll(TipoServico.class, filtros).get(0));
		sv.setValor(new BigDecimal(campos[5]).divide(CEM));
	}

	private void linhaParaFrotaApoio(String linha, Boletim bmiImport) {
		String[] campos = linha.split(";");
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();

		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("numero");
		c2.setOperador(Operador.IGUAL);
		c2.setTipoPropriedade(TipoPropriedade.NUMERO);
		c2.setValorPropriedade(Long.parseLong(campos[3]));
		filtros.add(c2);

		FrotaApoio fa = new FrotaApoio();
		bmiImport.getFrotasApoio().add(fa);

		fa.setBoletim(bmiImport);
		fa.setTipoVeiculoApoio(dao.findAll(TipoVeiculoApoio.class, filtros).get(0));
		fa.setValor(new BigDecimal(campos[5]).divide(CEM));
		fa.setQuilometragem(new BigDecimal(campos[6]).divide(CEM));
		List<TipoVeiculoApoio> lista = dao.findAll(TipoVeiculoApoio.class);

	}

	private void linhaParaDespesaGeral(String linha, Boletim bmiImport) {
		String[] campos = linha.split(";");
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();

		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("numero");
		c2.setOperador(Operador.IGUAL);
		c2.setTipoPropriedade(TipoPropriedade.NUMERO);
		c2.setValorPropriedade(Long.parseLong(campos[3]));
		filtros.add(c2);

		DespesaGeral dg = new DespesaGeral();
		bmiImport.getDespesasGerais().add(dg);

		dg.setBoletim(bmiImport);
		dg.setTipoDespesa(dao.findAll(TipoDespesa.class, filtros).get(0));
		dg.setValor(new BigDecimal(campos[5]).divide(CEM));

	}

	private void linhaParaMateriais(String linha, Boletim bmiImport) {
		String[] campos = linha.split(";");
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();

		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("numero");
		c2.setOperador(Operador.IGUAL);
		c2.setTipoPropriedade(TipoPropriedade.NUMERO);
		c2.setValorPropriedade(Long.parseLong(campos[3]));
		filtros.add(c2);

		Material mt = new Material();
		bmiImport.getMateriais().add(mt);

		mt.setBoletim(bmiImport);
		mt.setTipoMaterial(dao.findAll(TipoMaterial.class, filtros).get(0));
		mt.setQuantidade(new BigDecimal(campos[5]).divide(CEM));
		mt.setValor(new BigDecimal(campos[6]).divide(CEM));

	}

	private void linhaParaCustoPessoal(String linha, Boletim bmiImport) {
		String[] campos = linha.split(";");
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();

		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("numero");
		c2.setOperador(Operador.IGUAL);
		c2.setTipoPropriedade(TipoPropriedade.NUMERO);
		c2.setValorPropriedade(Long.parseLong(campos[3]));
		filtros.add(c2);

		CustoPessoal cp = new CustoPessoal();
		bmiImport.getCustosPessoal().add(cp);

		cp.setBoletim(bmiImport);
		cp.setCargo(dao.findAll(Cargo.class, filtros).get(0));
		cp.setElementos(Integer.parseInt(campos[5]));
		cp.setSalarios(new BigDecimal(campos[6]).divide(CEM));
		cp.setPremios(new BigDecimal(campos[7]).divide(CEM));
		cp.setHorasExtras(new BigDecimal(campos[8]).divide(CEM));
		cp.setOutrosPagamentos(new BigDecimal(campos[9]).divide(CEM));
		cp.setEncargosSociais(new BigDecimal(campos[10]).divide(CEM));

	}

	public String importar() {
		String retorno = null;
		try {

			Boletim bmiImport = new Boletim();
			importarBMI(arquivo, bmiImport);
			bmiImport.setId(service.importar(bmiImport));
			String detalhe = "Empresa " + bmiImport.getEmpresa() + " " + bmiImport.getTipoMovimento();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", detalhe));
			
    		retorno =  "/boletim/Consistencia.xhtml?faces-redirect=true&modo=c&idStr="+bmiImport.getId();
			bmiImport = null;
		//	FacesContext.getCurrentInstance().getExternalContext().redirect(retorno);

		} catch (

		Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));

		}
		return retorno;
	}

	public void exportar() {
		try {
			// exportarXlsx();
			//exportarCsv();
			FacesUtil.addInfoMessage("Exportação realizada com sucesso!");
		} catch (

		Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public StreamedContent getArquivosExportacao() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zipOut = new ZipOutputStream(baos);
			List<Object[]> lista;
			
			// ==========================
			// Arquivo 1 - movto-custos-operac
			// ==========================
			lista = dao.recuperaBmiParaExportacao("movto-custos-operac", filtroDataReferencia);
			List<String> custos = geraListaCustosOperac(lista);


			zipOut.putNextEntry(new ZipEntry("movto-custos-operac.csv"));

			for (String l : custos) {
				zipOut.write(l.getBytes(StandardCharsets.UTF_8));
			}
			zipOut.closeEntry();
			
			
			// ==========================
			// Arquivo 2 - movto-desp-geral
			// ==========================
			lista = dao.recuperaBmiParaExportacao("movto-desp-geral", filtroDataReferencia);
			custos = geraListaDespGeral(lista);
			zipOut.putNextEntry(new ZipEntry("movto-desp-geral.csv"));

			for (String l : custos) {
				zipOut.write(l.getBytes(StandardCharsets.UTF_8));
			}
			zipOut.closeEntry();		
			
			// ==========================
			// Arquivo 3 - movto-desp-trib
			// ==========================
			lista = dao.recuperaBmiParaExportacao("movto-desp-trib", filtroDataReferencia);
			custos = geraListaDespTrib(lista);
			zipOut.putNextEntry(new ZipEntry("movto-desp-trib.csv"));

			for (String l : custos) {
				zipOut.write(l.getBytes(StandardCharsets.UTF_8));
			}
			zipOut.closeEntry();	
			
			// ==========================
			// Arquivo 4 - movto-frota
			// ==========================
			lista = dao.recuperaBmiParaExportacao("movto-frota", filtroDataReferencia);
			custos = geraListaMovtoFrota(lista);
			zipOut.putNextEntry(new ZipEntry("movto-frota.csv"));

			for (String l : custos) {
				zipOut.write(l.getBytes(StandardCharsets.UTF_8));
			}
			zipOut.closeEntry();
			
			// ==========================
			// Arquivo 5 - movto-frota-apoio
			// ==========================
			lista = dao.recuperaBmiParaExportacao("movto-frota-apoio", filtroDataReferencia);
			custos = geraListaMovtoFrotaApoio(lista);
			zipOut.putNextEntry(new ZipEntry("movto-frota-apoio.csv"));

			for (String l : custos) {
				zipOut.write(l.getBytes(StandardCharsets.UTF_8));
			}
			zipOut.closeEntry();
			
			
			// ==========================
			// Arquivo 6 - movto-materiais
			// ==========================
			lista = dao.recuperaBmiParaExportacao("movto-materiais", filtroDataReferencia);
			custos = geraListaMovtoMateriais(lista);
			zipOut.putNextEntry(new ZipEntry("movto-materiais.csv"));

			for (String l : custos) {
				zipOut.write(l.getBytes(StandardCharsets.UTF_8));
			}
			zipOut.closeEntry();
			
			// ==========================
			// Arquivo 7 - movto-servico-manut
			// ==========================
			lista = dao.recuperaBmiParaExportacao("movto-servico-manut", filtroDataReferencia);
			custos = geraListaMovtoServicoManut(lista);
			zipOut.putNextEntry(new ZipEntry("movto-servico-manut.csv"));

			for (String l : custos) {
				zipOut.write(l.getBytes(StandardCharsets.UTF_8));
			}
			zipOut.closeEntry();
			
			
			// ==========================
			// Arquivo 8 - tab-kilometr
			// ==========================
			lista = dao.recuperaBmiParaExportacao("tab-kilometr", filtroDataReferencia);
			custos = geraListaTabKilometr(lista);
			zipOut.putNextEntry(new ZipEntry("tab-kilomet.csv"));

			for (String l : custos) {
				zipOut.write(l.getBytes(StandardCharsets.UTF_8));
			}
			zipOut.closeEntry();
			
			
			zipOut.close();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

			return DefaultStreamedContent.builder().name("arquivos.zip").contentType("application/zip")
					.stream(() -> inputStream).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new NegocioException(e.getMessage());
		}
	}

	private void exportarXlsx() {
		Workbook workbook = new HSSFWorkbook();
		List<Object[]> lista;
		lista = dao.recuperaBmiParaExportacao("movto-custos-operac", filtroDataReferencia);
		geraAbaCustosOperac(lista, workbook);
	}

	private void geraAbaCustosOperac(List<Object[]> lista, Workbook workbook) {
		Sheet sheet = workbook.createSheet("movto-custos-operac");
		// Linha de cabecalho
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("cod-empresa");
		row.createCell(1).setCellValue("mes-ano");
		row.createCell(2).setCellValue("cod-cargo");
		row.createCell(3).setCellValue("tipmov");
		row.createCell(4).setCellValue("elementos");
		row.createCell(5).setCellValue("salario");
		row.createCell(6).setCellValue("premios");
		row.createCell(7).setCellValue("hora-extra");
		row.createCell(8).setCellValue("outros pagtos");
		row.createCell(9).setCellValue("encargos");

		// linhas de dados
		Integer conta = 1;
		for (Object[] obj : lista) {
			row = sheet.createRow(conta);
			conta++;
			row.createCell(0).setCellValue((Integer) obj[0]);
			row.createCell(1).setCellValue((String) obj[1]);
			row.createCell(2).setCellValue((Integer) obj[2]);
			row.createCell(3).setCellValue((Integer) obj[3]);
			row.createCell(4).setCellValue((Integer) obj[4]);
			row.createCell(5).setCellValue((Double) obj[5]);
			row.createCell(6).setCellValue((Double) obj[6]);
			row.createCell(7).setCellValue((Double) obj[7]);
			row.createCell(8).setCellValue((Double) obj[8]);
			row.createCell(9).setCellValue((Double) obj[9]);

		}
	}

	private List<String> geraListaCustosOperac(List<Object[]> lista) {

		List<String> retorno = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("###.00", new DecimalFormatSymbols(Locale.FRANCE));
		// Linha de cabecalho
		String linha;
		linha = "cod-empresa;mes-ano;cod-cargo;tipmov;elementos;salario;premios;hora-extra;outros pagtos;encargos\n";
		retorno.add(linha);
		// linhas de dados
		for (Object[] obj : lista) {
			linha = (obj[0]).toString() + ';';
			linha = linha + (obj[1]).toString() + ';';
			linha = linha + (obj[2]).toString() + ';';
			linha = linha + (obj[3]).toString() + ';';
			linha = linha + (obj[4]).toString() + ';';
			linha = linha + df.format(obj[5]) + ';';
			linha = linha + df.format(obj[6]) + ';';
			linha = linha + df.format(obj[7]) + ';';
			linha = linha + df.format(obj[8]) + ';';
			linha = linha + df.format(obj[9]) + "\n";
			retorno.add(linha);
		}
		return retorno;
	}
	
	private List<String> geraListaDespGeral(List<Object[]> lista) {

		List<String> retorno = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("###.00", new DecimalFormatSymbols(Locale.FRANCE));
		// Linha de cabecalho
		String linha;
		linha = "cod-empresa;mes-ano;cod-desp-geral;tipmov;valor-desp-geral\n";
		retorno.add(linha);
		// linhas de dados
		for (Object[] obj : lista) {
			linha = (obj[0]).toString() + ';';
			linha = linha + (obj[1]).toString() + ';';
			linha = linha + (obj[2]).toString() + ';';
			linha = linha + (obj[3]).toString() + ';';
			linha = linha + df.format(obj[4]) + "\n";
			retorno.add(linha);
		}
		return retorno;
	}
	
	
	private List<String> geraListaDespTrib(List<Object[]> lista) {

		List<String> retorno = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("###.00", new DecimalFormatSymbols(Locale.FRANCE));
		// Linha de cabecalho
		String linha;
		linha = "cod-empresa;mes-ano;cod-desp-geral;tipmov;valor-desp\n";
		retorno.add(linha);
		// linhas de dados
		for (Object[] obj : lista) {
			linha = (obj[0]).toString() + ';';
			linha = linha + (obj[1]).toString() + ';';
			linha = linha + (obj[2]).toString() + ';';
			linha = linha + (obj[3]).toString() + ';';
			linha = linha + df.format(obj[4]) + "\n";
			retorno.add(linha);
		}
		return retorno;
	}
	
	private List<String> geraListaMovtoFrota(List<Object[]> lista) {

		List<String> retorno = new ArrayList<String>();
		// Linha de cabecalho
		String linha;
		linha = "cod-empresa;mes-ano;tipmov;veic_efetivos;veic_reservas\n";
		retorno.add(linha);
		// linhas de dados
		for (Object[] obj : lista) {
			linha = (obj[0]).toString() + ';';
			linha = linha + (obj[1]).toString() + ';';
			linha = linha + (obj[2]).toString() + ';';
			linha = linha + (obj[3]).toString() + ';';
			linha = linha + (obj[4]).toString() + "\n";
			retorno.add(linha);
		}
		return retorno;
	}

	private List<String> geraListaMovtoFrotaApoio(List<Object[]> lista) {
		DecimalFormat df = new DecimalFormat("###.00", new DecimalFormatSymbols(Locale.FRANCE));
		List<String> retorno = new ArrayList<String>();
		// Linha de cabecalho
		String linha;
		linha = "cod-empresa;mes-ano;cod-equipto;tipmov;valor-equipto;Km-mes;valor-mes\n";
		retorno.add(linha);
		// linhas de dados
		for (Object[] obj : lista) {
			linha = (obj[0]).toString() + ';';
			linha = linha + (obj[1]).toString() + ';';
			linha = linha + (obj[2]).toString() + ';';
			linha = linha + (obj[3]).toString() + ';';
			linha = linha + df.format(obj[4]) + ';';
			linha = linha + df.format(obj[5]) + ';';
			linha = linha + df.format(obj[6]) +  "\n";
			retorno.add(linha);
		}
		return retorno;
	}
	
	
	private List<String> geraListaMovtoMateriais(List<Object[]> lista) {
		DecimalFormat df = new DecimalFormat("###.00", new DecimalFormatSymbols(Locale.FRANCE));
		List<String> retorno = new ArrayList<String>();
		// Linha de cabecalho
		String linha;
		linha = "cod-empresa;mes-ano;cod-material;tipmov;qtde-material;preco-unit\n";
		retorno.add(linha);
		// linhas de dados
		for (Object[] obj : lista) {
			linha = (obj[0]).toString() + ';';
			linha = linha + (obj[1]).toString() + ';';
			linha = linha + (obj[2]).toString() + ';';
			linha = linha + (obj[3]).toString() + ';';
			linha = linha + df.format(obj[4]) + ';';
			linha = linha + df.format(obj[5]) +  "\n";
			retorno.add(linha);
		}
		return retorno;
	}
	
	
	private List<String> geraListaMovtoServicoManut(List<Object[]> lista) {
		DecimalFormat df = new DecimalFormat("###.00", new DecimalFormatSymbols(Locale.FRANCE));
		List<String> retorno = new ArrayList<String>();
		// Linha de cabecalho
		String linha;
		linha = "cod-empresa;mes-ano;cod-servico;tipmov;valor-servico;especificacao;qtde-serv\n";
		retorno.add(linha);
		// linhas de dados
		for (Object[] obj : lista) {
			linha = (obj[0]).toString() + ';';
			linha = linha + (obj[1]).toString() + ';';
			linha = linha + (obj[2]).toString() + ';';
			linha = linha + (obj[3]).toString() + ';';
			linha = linha + df.format(obj[4]) + ';';
			linha = linha +  ';';
			linha = linha + (obj[6] == null ? "" : (obj[6]).toString()) +  "\n";
			retorno.add(linha);
		}
		return retorno;
	}
	
	
	
	private List<String> geraListaTabKilometr(List<Object[]> lista) {
		DecimalFormat df = new DecimalFormat("###.00", new DecimalFormatSymbols(Locale.FRANCE));
		List<String> retorno = new ArrayList<String>();
		// Linha de cabecalho
		String linha;
		linha = "cod-empresa;mes-ano;tipmov;cod-piso;km_rodado\n";
		retorno.add(linha);
		// linhas de dados
		for (Object[] obj : lista) {
			linha = (obj[0]).toString() + ';';
			linha = linha + (obj[1]).toString() + ';';
			linha = linha + (obj[2]).toString() + ';';
			linha = linha + (obj[3]).toString() + ';';
			linha = linha + df.format(obj[4]) +  "\n";
			retorno.add(linha);
		}
		return retorno;
	}
	
	public void consistirSistema() {
		montaConsulta();
		pesquisar();

		boletimSistema = new Boletim();
		PlanilhaPadrao plan = null;
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();
		ClausulaFiltro c2 = new ClausulaFiltro();
		c2.setNomePropriedade("dataReferencia");
		c2.setTipoPropriedade(TipoPropriedade.DATA);
		c2.setValorPropriedade(filtroDataReferencia);
		filtros.add(c2);
		plan = dao.findAll(PlanilhaPadrao.class, filtros).get(0);
		boletimSistema.getCombusLubrif().clear();
		boletimSistema.getRodagem().clear();
		boletimSistema.getValePlano().clear();
		for (Boletim b : boletins) {
			boletimSistema.setDataReferencia(b.getDataReferencia());
			boletimSistema.setTipoMovimento(b.getTipoMovimento());
			boletimSistema.setFrotaEfetiva(boletimSistema.getFrotaEfetiva() + b.getFrotaEfetiva());
			boletimSistema.setFrotaReserva(boletimSistema.getFrotaReserva() + b.getFrotaReserva());
			boletimSistema
					.setQuilometragem_piso1(boletimSistema.getQuilometragem_piso1().add(b.getQuilometragem_piso1()));
			boletimSistema
					.setQuilometragem_piso2(boletimSistema.getQuilometragem_piso2().add(b.getQuilometragem_piso2()));
			boletimSistema
					.setQuilometragem_piso3(boletimSistema.getQuilometragem_piso3().add(b.getQuilometragem_piso3()));
			juntaCustoPessoal(b);
			juntaDespesasGerais(b);
			juntaFrotaApoio(b);
			juntaMateriais(b);
			juntaReceitas(b);
			juntaservicos(b);
		}
		montaCustoVariavel(boletimSistema, plan);
		montaCustoPessoal(boletimSistema, plan);
	}

	private void juntaCustoPessoal(Boletim b) {
		boolean achou = false;
		for (CustoPessoal c : b.getCustosPessoal()) {
			achou = false;
			for (CustoPessoal cp : boletimSistema.getCustosPessoal()) {
				if (cp.getCargo().equals(c.getCargo())) {
					achou = true;
					cp.setElementos(cp.getElementos() + c.getElementos());
					cp.setEncargosSociais(cp.getEncargosSociais().add(c.getEncargosSociais()));
					cp.setHorasExtras(cp.getHorasExtras().add(c.getHorasExtras()));
					cp.setOutrosPagamentos(cp.getOutrosPagamentos().add(c.getOutrosPagamentos()));
					cp.setPremios(cp.getPremios().add(c.getPremios()));
					cp.setSalarios(cp.getSalarios().add(c.getSalarios()));
					break;
				}
			}
			if (!achou) {
				boletimSistema.getCustosPessoal().add(c);
				c.setBoletim(boletimSistema);
			}
		}
	}

	private void juntaDespesasGerais(Boletim b) {
		boolean achou = false;
		for (DespesaGeral bol : b.getDespesasGerais()) {
			achou = false;
			for (DespesaGeral s : boletimSistema.getDespesasGerais()) {
				if (s.getTipoDespesa().getNumero().equals(bol.getTipoDespesa().getNumero())) {
					achou = true;
					s.setValor(s.getValor().add(bol.getValor()));
					break;
				}
			}
			if (!achou) {
				boletimSistema.getDespesasGerais().add(bol);
				bol.setBoletim(boletimSistema);
			}
		}
	}

	private void juntaFrotaApoio(Boletim b) {
		boolean achou = false;
		for (FrotaApoio bol : b.getFrotasApoio()) {
			achou = false;
			for (FrotaApoio s : boletimSistema.getFrotasApoio()) {
				if (s.getTipoVeiculoApoio().getNumero().equals(bol.getTipoVeiculoApoio().getNumero())) {
					achou = true;
					s.setValor(s.getValor().add(bol.getValor()));
					s.setQuilometragem(s.getQuilometragem().add(bol.getQuilometragem()));
					break;
				}
			}
			if (!achou) {
				boletimSistema.getFrotasApoio().add(bol);
				bol.setBoletim(boletimSistema);
			}
		}
	}

	private void juntaMateriais(Boletim b) {
		boolean achou = false;
		for (Material bol : b.getMateriais()) {
			achou = false;
			for (Material s : boletimSistema.getMateriais()) {
				if (s.getTipoMaterial().getNumero().equals(bol.getTipoMaterial().getNumero())) {
					achou = true;
					s.setValor(s.getValor().add(bol.getValor()));
					s.setQuantidade(s.getQuantidade().add(bol.getQuantidade()));
					break;
				}
			}
			if (!achou) {
				boletimSistema.getMateriais().add(bol);
				bol.setBoletim(boletimSistema);
			}
		}
	}

	private void juntaReceitas(Boletim b) {
		boolean achou = false;
		for (ReceitaImobilizado bol : b.getReceitasImobilizado()) {
			achou = false;
			for (ReceitaImobilizado s : boletimSistema.getReceitasImobilizado()) {
				if (s.getTipoReceitaImobilizado().getNumero().equals(bol.getTipoReceitaImobilizado().getNumero())) {
					achou = true;
					s.setValor(s.getValor().add(bol.getValor()));
					break;
				}
			}
			if (!achou) {
				boletimSistema.getReceitasImobilizado().add(bol);
				bol.setBoletim(boletimSistema);
			}
		}
	}

	private void juntaservicos(Boletim b) {
		boolean achou = false;
		for (Servico bol : b.getServicos()) {
			achou = false;
			for (Servico s : boletimSistema.getServicos()) {
				if (s.getTipoServico().getNumero().equals(bol.getTipoServico().getNumero())) {
					achou = true;
					s.setValor(s.getValor().add(bol.getValor()));
					break;
				}
			}
			if (!achou) {
				boletimSistema.getServicos().add(bol);
				bol.setBoletim(boletimSistema);
			}
		}
	}

	public List<Empresa> getEmpresas() {
		return principalBean.getListaEmpresa();
	}

	public Boletim getBoletim() {
		return boletim;
	}

	public void setBoletim(Boletim boletim) {
		System.out.println("Vou setar o boletim");

		this.boletim = boletim;
	}

	public void inicializar() {

	}

	public void salvar() {
		try {/*
				 * for (CustoPessoal cp : boletim.getPessoalOperacao()) { if (cp.getElementos()
				 * > 0) { if ((cp.getSalarioMedio().compareTo(cp.getSalarioMedioPlanilha()) <
				 * 0)) { throw new NegocioException("O salário pago para " +
				 * cp.getCargo().getDescricao().trim() +
				 * " não pode ser menor que o salário da Convenção Coletiva."); } } }
				 */

			insereFatorAjuste();
			service.salvar(boletim);
			FacesUtil.addInfoMessage("Registro salvo com sucesso!");
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	private void insereFatorAjuste() {
		for (CustoPessoal cp : boletim.getPessoalOperacao()) {
			cp.setFatorAjuste(getAleatorio());
		}
		for (Material mt : boletim.getMateriais()) {
			mt.setFatorAjuste(getAleatorio());
		}
		for (DespesaGeral dg : boletim.getDespesasGerais()) {
			dg.setFatorAjuste(getAleatorio());
		}
	}

	public void salvarEconsistir() {
		/*
		 * for (CustoPessoal cp : boletim.getPessoalOperacao()) {
		 * 
		 * if (cp.getElementos() > 0) { if
		 * (cp.getSalarioMedio().compareTo(cp.getSalarioMedioPlanilha()) < 0) { throw
		 * new NegocioException("O salário pago para " +
		 * cp.getCargo().getDescricao().trim() +
		 * " não pode ser menor que o salário da Convenção Coletiva."); } } }
		 */
		insereFatorAjuste();
		service.salvar(boletim);
		montaDadosParaConsistencia();
		FacesUtil.addInfoMessage("Registro salvo com sucesso!");
	}

	public void bloquear() {
		boletim.setBloqueado(!boletim.isBloqueado());
		service.salvar(boletim);
		FacesUtil.addInfoMessage("Registro salvo com sucesso!");
	}

	public void clonar() {
		try {
			setBoletim(this.boletim.clone());

			/*
			 * boletim.setFrotaEfetiva(0); boletim.setFrotaReserva(0);
			 * boletim.setFrotaTotal(0);
			 */
			boletim.setQuilometragem_piso1(BigDecimal.ZERO);
			boletim.setQuilometragem_piso2(BigDecimal.ZERO);
			boletim.setQuilometragem_piso3(BigDecimal.ZERO);
			boletim.setQuilometragemTotal(BigDecimal.ZERO);
			boletim.setBloqueado(false);
			boletim.setDigitacaoConcluida(false);
			FacesUtil.addInfoMessage("Faça as alterações e salve os dados.");
		} catch (CloneNotSupportedException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

}
