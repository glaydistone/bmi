package br.com.tecnotran.bmi.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.tecnotran.bmi.model.Boletim;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.TipoPropriedade;

public class GraficoService  {
	
	@Inject
	private BaseDAO dao;
	
	public List<Boletim> buscaSerieCusto(Empresa empresa, TipoMovimento tipoMovimento, Date dataInicial, Date dataFinal) {
		ArrayList<Boletim> retorno = new ArrayList<Boletim>();
		Calendar data = Calendar.getInstance();
		data.setTime(dataInicial);
		
		Calendar clInicio = Calendar.getInstance();
		clInicio.setTime(dataInicial);
		
		Calendar clFim = Calendar.getInstance();
		clFim.setTime(dataFinal);
		
		Integer meses = (clFim.get(Calendar.YEAR)*12+clFim.get(Calendar.MONTH))-
				(clInicio.get(Calendar.YEAR)*12+clInicio.get(Calendar.MONTH));
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();
		
		ClausulaFiltro fe = new ClausulaFiltro();
		fe.setAlias("obj");
		fe.setNomePropriedade("empresa");
		fe.setTipoPropriedade(TipoPropriedade.NUMERO);
		fe.setValorPropriedade(empresa);
		filtros.add(fe);
		
		ClausulaFiltro fts = new ClausulaFiltro();
		fts.setAlias("obj");
		fts.setNomePropriedade("tipoMovimento");
		fts.setTipoPropriedade(TipoPropriedade.STRING);
		fts.setValorPropriedade(tipoMovimento);
		filtros.add(fts);
		
		ClausulaFiltro fdt = new ClausulaFiltro();
		fdt.setAlias("fdt");
		fdt.setNomePropriedade("dataReferencia");
		fdt.setTipoPropriedade(TipoPropriedade.DATA);
		fdt.setValorPropriedade(data);
		filtros.add(fdt);
			

		
		for (int i=0; i<=meses; i++ ) {
			
			Boletim rc = new Boletim();
			rc.setDataReferencia(data.getTime());
			rc.setEmpresa(empresa);
			rc.setTipoMovimento(tipoMovimento);
			
			fdt.setValorPropriedade(data.getTime());

			System.out.println("DAta: "+fdt.getValorPropriedade());
			
			List<Boletim> r = (ArrayList<Boletim>) dao.findAll(Boletim.class, filtros);
		
			data.add(Calendar.MONTH, 1);
			if (r==null || r.size()==0) {
				retorno.add(rc);
			}
			for (Boletim b: r) {
				retorno.add(b);
			}
		}
		return retorno;
		
	}

}
