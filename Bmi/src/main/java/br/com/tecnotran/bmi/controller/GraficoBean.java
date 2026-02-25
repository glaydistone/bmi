package br.com.tecnotran.bmi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;

import br.com.tecnotran.bmi.model.Boletim;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.services.GraficoService;

@Named
@RequestScoped
public class GraficoBean extends BaseBean {

	private LineChartModel model = new LineChartModel();;

	private List<Boletim> boletins;

	private Empresa empresa;
	private TipoMovimento tipoMovimento;
	private Date dataInicial;
	private Date dataFinal;

	@Inject
	GraficoService graficoServico;
	@Inject
	private BaseDAO dao;

	// @PostConstruct
	public void preRender() {

		// ChartSeries vazio = new ChartSeries("Vazio");

		/*
		 * vazio.set("1", 100); vazio.set("2", 200); vazio.set("3", 300);
		 */

		// this.model.addSeries(vazio);
	}

	public void exibirEvolucaoCustoKm() {

		boletins = graficoServico.buscaSerieCusto(empresa, tipoMovimento, dataInicial, dataFinal);
		// boletins = buscaSerieCusto(empresa, tipoMovimento, dataInicial, dataFinal) ;
		adicionarSerie2(boletins);
	}

	public List<Boletim> getBoletins() {
		return boletins;
	}

	public void setBoletins(List<Boletim> boletins) {
		this.boletins = boletins;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public GraficoService getGraficoServico() {
		return graficoServico;
	}

	public void setGraficoServico(GraficoService graficoServico) {
		this.graficoServico = graficoServico;
	}

	public void setModel(LineChartModel model) {
		this.model = model;
	}

	private void adicionarSerie2(List<Boletim> boletins) {
		model = new LineChartModel();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		
		ChartData data = new ChartData();

		LineChartDataSet dtsVariavel = new LineChartDataSet();
		LineChartDataSet dtsPessoal = new LineChartDataSet();
		LineChartDataSet dtsDespesa = new LineChartDataSet();
		LineChartDataSet dtsTributaria = new LineChartDataSet();
		
		dtsVariavel.setLabel("Custo variável");
		dtsPessoal.setLabel("Custo Pessoal");
		dtsDespesa.setLabel("Despesas gerais");
		dtsTributaria.setLabel("Despesas tributárias");
		
		dtsVariavel.setBorderColor("rgb(75, 192, 192)");
		dtsPessoal.setBorderColor("rgb(0, 0, 128)");
		dtsDespesa.setBorderColor("rgb(60, 179, 113)");
		dtsTributaria.setBorderColor("rgb(210, 105, 30)");
		

		List<String> meses = new ArrayList<String>();
		List<Object> valorVariavel = new ArrayList<Object>();
		List<Object> valorPessoal = new ArrayList<Object>();
		List<Object> valorDespesa = new ArrayList<Object>();
		List<Object> valorTributo = new ArrayList<Object>();
		for (Boletim b : boletins) {
			valorVariavel.add(b.getCustoVariavelKm());
			valorPessoal.add(b.getCustoPessoalKm());
			valorDespesa.add(b.getDespesaGeralKm());
			valorTributo.add(b.getDespesaTributariaKm());
			meses.add(sdf.format(b.getDataReferencia()));
		}
		dtsVariavel.setData(valorVariavel);
		dtsVariavel.setFill(false);
		
		dtsPessoal.setData(valorPessoal);
		dtsPessoal.setFill(false);
		
		dtsDespesa.setData(valorDespesa);
		dtsDespesa.setFill(false);
		
		dtsTributaria.setData(valorTributo);
		dtsTributaria.setFill(false);
		
		data.addChartDataSet(dtsVariavel);
		data.addChartDataSet(dtsPessoal);
		data.addChartDataSet(dtsDespesa);
		data.addChartDataSet(dtsTributaria);
		data.setLabels(meses);
		
		model.setData(data);
		LineChartOptions lco = new LineChartOptions();


	}

	private void adicionarSerie(List<Boletim> boletins) {

/*

		this.model.setTitle("Custos por quilômetro");
		this.model.setLegendPosition("e");
		this.model.setAnimate(true);

		this.model.getAxes().put(AxisType.X, new CategoryAxis());

		ChartSeries variavel = new ChartSeries("Variável");
		ChartSeries pessoal = new ChartSeries("Pessoal");
		ChartSeries despesa = new ChartSeries("Despesa geral");
		ChartSeries tributo = new ChartSeries("Despesa tributária");
		ChartSeries frotaApoio = new ChartSeries("Apoio");
		ChartSeries servico = new ChartSeries("Serviços");

		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

		for (Boletim b : boletins) {
			variavel.set(sdf.format(b.getDataReferencia()), b.getCustoVariavelKm());
			pessoal.set(sdf.format(b.getDataReferencia()), b.getCustoPessoalKm());
			despesa.set(sdf.format(b.getDataReferencia()), b.getDespesaGeralKm());
			tributo.set(sdf.format(b.getDataReferencia()), b.getDespesaTributariaKm());
		}

		this.model.addSeries(variavel);
		this.model.addSeries(pessoal);
		this.model.addSeries(despesa);
		this.model.addSeries(tributo);
		this.model.addSeries(frotaApoio);
		this.model.addSeries(servico);

		this.model.setShowPointLabels(true);
		*/
	}

	public LineChartModel getModel() {
		return model;
	}
}
