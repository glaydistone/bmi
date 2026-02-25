package br.com.tecnotran.bmi.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import br.com.tecnotran.bmi.controller.ParametroConsultaBean;
import br.com.tecnotran.bmi.model.BaseEntity;
import br.com.tecnotran.bmi.model.Boletim;
import br.com.tecnotran.bmi.model.Cargo;
import br.com.tecnotran.bmi.model.CustoPessoal;
import br.com.tecnotran.bmi.model.DespesaGeral;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.FrotaApoio;
import br.com.tecnotran.bmi.model.Material;
import br.com.tecnotran.bmi.model.ReceitaImobilizado;
import br.com.tecnotran.bmi.model.Servico;
import br.com.tecnotran.bmi.model.TipoDespesa;
import br.com.tecnotran.bmi.model.TipoMaterial;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.model.TipoReceitaImobilizado;
import br.com.tecnotran.bmi.model.TipoServico;
import br.com.tecnotran.bmi.model.TipoVeiculoApoio;
import br.com.tecnotran.bmi.model.gestor.MovtoCustosOperac;
import br.com.tecnotran.bmi.model.gestor.MovtoDespGeral;
import br.com.tecnotran.bmi.model.gestor.MovtoDespTrib;
import br.com.tecnotran.bmi.model.gestor.MovtoFrota;
import br.com.tecnotran.bmi.model.gestor.MovtoFrotaApoio;
import br.com.tecnotran.bmi.model.gestor.MovtoKm;
import br.com.tecnotran.bmi.model.gestor.MovtoMateriais;
import br.com.tecnotran.bmi.model.gestor.MovtoServicoManut;
import br.com.tecnotran.bmi.repository.AccessDAO;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.TipoPropriedade;

public class BoletimService implements Serializable {
	ParametroConsultaBean parametroConsulta = new ParametroConsultaBean();

	private static final long serialVersionUID = 1L;

	@Inject
	private BaseDAO dao;

	@Inject
	private AccessDAO aDao;
	


	public void importarDadosAccess(Date dataReferencia) {
		EntityTransaction trx = dao.getTransaction();
		try {

			trx.begin();
		//	excluiBoletimMensal(dataReferencia);
			List<Boletim> boletins = montaBoletimGestor(dataReferencia);
			for (Boletim b : boletins) {
				try {
					dao.save(b);
					System.out.println(b.getEmpresa().getNumero()+"-"+ b.getEmpresa().getRazaoSocial());
				} catch (Exception e) {
					System.out.println(b.getEmpresa().getNumero()+"-"+ b.getEmpresa().getRazaoSocial()+" já cadastrado antes.");
				}
			}
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			throw new NegocioException("Dados não foram importados."+e.getLocalizedMessage());
		}

	}

	private List<Boletim> montaBoletimGestor(Date dataReferencia) {
		List<Boletim> retorno = new ArrayList<Boletim>();
		List<Empresa> empresas = dao.findAll(Empresa.class);
		List<Cargo> cargos = dao.findAll(Cargo.class);
		List<TipoDespesa> tiposDespesa = dao.findAll(TipoDespesa.class);
		List<TipoVeiculoApoio> tiposVeiculoApoio = dao.findAll(TipoVeiculoApoio.class);
		List<TipoMaterial> materiais = dao.findAll(TipoMaterial.class);
		List<TipoServico> servicos = dao.findAll(TipoServico.class);
		List<TipoReceitaImobilizado> receitas = dao.findAll(TipoReceitaImobilizado.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));


		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();
		ClausulaFiltro emp = new ClausulaFiltro();
		emp.setNomePropriedade("codEmpresa");
		emp.setTipoPropriedade(TipoPropriedade.NUMERO);
		filtros.add(emp);

		ClausulaFiltro dt = new ClausulaFiltro();
		dt.setNomePropriedade("mesAno");
		dt.setTipoPropriedade(TipoPropriedade.DATA);
		filtros.add(dt);

		ClausulaFiltro tm = new ClausulaFiltro();
		tm.setNomePropriedade("tipoMov");
		tm.setTipoPropriedade(TipoPropriedade.NUMERO);
		filtros.add(tm);

		List<MovtoFrota> frota = aDao.recuperaFrotaMes(dataReferencia);
		
		for (MovtoFrota f : frota) {

			if (sdf.format(f.getMesAno()).equals(sdf.format(dataReferencia))) {
				dt.setValorPropriedade(f.getMesAno());
				Boletim b = new Boletim();
				retorno.add(b);
				b.setDataReferencia(dataReferencia);
				if (f.getTipoMov() == 1) {
					b.setTipoMovimento(TipoMovimento.SEMI_URBANO);
				} else {
					b.setTipoMovimento(TipoMovimento.RODOVIARIO);
				}
				b.setFrotaEfetiva(f.getEfetivos());
				b.setFrotaReserva(f.getReservas());

				for (Empresa e : empresas) {
					if (e.getNumero().equals(f.getCodEmpresa().longValue())) {
						b.setEmpresa(e);
						break;
					}
				}

				emp.setValorPropriedade(f.getCodEmpresa());
				tm.setValorPropriedade(f.getTipoMov());
				List<MovtoCustosOperac> custos = aDao.recuperaMovtoCustosOperac(dataReferencia, f.getCodEmpresa(), f.getTipoMov());
				MovtoCustosOperac mco;

				for (int i=0; i< custos.size(); i++) {
					mco = (MovtoCustosOperac) custos.get(i);
					CustoPessoal cp = new CustoPessoal();
					cp.setElementos(mco.getQtdeElementos());
					
					for (Cargo c : cargos) {
						if (c.getNumero().equals(mco.getCodCargo().longValue())) {
							cp.setCargo(c);
							b.getCustosPessoal().add(cp);
							cp.setBoletim(b);
							break;
						}
					}
					cp.setEncargosSociais(mco.getValEncargos());
					cp.setHorasExtras(mco.getValHoraex());
					cp.setOutrosPagamentos(mco.getValOutros());
					cp.setPremios(mco.getValPremios());
					cp.setSalarios(mco.getValSalario());

					
				}
				
				
				List<MovtoDespGeral> desp = aDao.recuperaMovtoDespGeral(dataReferencia, f.getCodEmpresa(), f.getTipoMov());
				for (MovtoDespGeral m : desp) {
					DespesaGeral dg = new DespesaGeral();

					dg.setDescricao(m.getEspecificacao());
					dg.setTipoDespesa(null);
					dg.setValor(m.getValor());
					
					for (TipoDespesa c : tiposDespesa) {
						if (c.getNumero().equals(m.getCodDespesa().longValue())) {
							dg.setTipoDespesa(c);
							b.getDespesasGerais().add(dg);
							dg.setBoletim(b);
							break;
						}
					}
				}
				
				
				List<MovtoFrotaApoio> fa = aDao.recuperaMovtoFrotaApoio(dataReferencia, f.getCodEmpresa(), f.getTipoMov());
				for (MovtoFrotaApoio m : fa) {
					FrotaApoio fta = new FrotaApoio();

					fta.setQuilometragem(new BigDecimal(m.getKm()));
					fta.setValor(m.getValor());
					
				
					for (TipoVeiculoApoio tv : tiposVeiculoApoio) {
						if (tv.getNumero().equals(m.getCodEquipto().longValue())) {
							fta.setTipoVeiculoApoio(tv);
							b.getFrotasApoio().add(fta);
							fta.setBoletim(b);
							break;
						}
					}
				}
				
				List<MovtoMateriais> mvt = aDao.recuperaMovtoMateriais(dataReferencia, f.getCodEmpresa(), f.getTipoMov());
				for (MovtoMateriais m : mvt) {
					Material mt = new Material();

					mt.setQuantidade(new BigDecimal(m.getQtdeMaterial()));
					mt.setValor(m.getPreco());
					
				
					for (TipoMaterial tmt : materiais) {
						if (tmt.getNumero().equals(m.getCodMaterial().longValue())) {
							mt.setTipoMaterial(tmt);
							b.getMateriais().add(mt);
							mt.setBoletim(b);
							break;
						}
					}
				}
				
				List<MovtoKm> mkm = aDao.recuperaMovtoKm(dataReferencia, f.getCodEmpresa(), f.getTipoMov());
				for (MovtoKm m : mkm) {

					if (m.getCodPiso()==1) {
						b.setQuilometragem_piso1(m.getQuilometragem());
					}
					if (m.getCodPiso()==2) {
						b.setQuilometragem_piso2(m.getQuilometragem());
					}					
					if (m.getCodPiso()==3) {
						b.setQuilometragem_piso3(m.getQuilometragem());
					}				
				}
				
				List<MovtoServicoManut> msm = aDao.recuperaMovtoServicoManut(dataReferencia, f.getCodEmpresa(), f.getTipoMov());
				for (MovtoServicoManut m : msm) {
					
					Servico srv = new Servico();

				//	srv.setQuantidade(new BigDecimal(m.getQtdeServ()));
					srv.setValor(m.getValor());
					
				
					for (TipoServico s : servicos) {
						if (s.getNumero().equals(m.getCodServico().longValue())) {
							srv.setTipoServico(s);
							b.getServicos().add(srv);
							srv.setBoletim(b);
							break;
						}
					}
				}
				
				List<MovtoDespTrib> mdt = aDao.recuperaReceitaImobilizado(dataReferencia, f.getCodEmpresa(), f.getTipoMov());
				for (MovtoDespTrib m : mdt) {
					
					ReceitaImobilizado rec = new ReceitaImobilizado();


					rec.setValor(m.getValor());
					rec.setDescricao(m.getEspecificacao());

					
				
					for (TipoReceitaImobilizado t : receitas) {
						if (t.getNumero().equals(m.getCodDespesa().longValue())) {
							rec.setTipoReceitaImobilizado(t);
							b.getReceitasImobilizado().add(rec);
							rec.setBoletim(b);
							break;
						}
					}
				}

			}
		}

		return retorno;
	}

	private void excluiBoletimMensal(Date dataReferencia) {
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();
		String qry = null;
		ClausulaFiltro d = new ClausulaFiltro();
		d.setNomePropriedade("dataReferencia");
		d.setTipoPropriedade(TipoPropriedade.DATA);
		d.setValorPropriedade(dataReferencia);
		filtros.add(d);
		List<Boletim> boletins = dao.findAll(Boletim.class, filtros);

		for (Boletim b : boletins) {
			dao.delete(Boletim.class, b.getId());
		}
	}

	public Long importar(BaseEntity entidade) {
		Boletim b = (Boletim) entidade;
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();
		String qry = null;

		qry = "select count(ent.id) from Boletim ent where ent.dataReferencia = :dataReferencia and ent.empresa = :empresa and ent.tipoMovimento = :tipoMovimento";


		ClausulaFiltro d = new ClausulaFiltro();
		d.setNomePropriedade("dataReferencia");
		d.setTipoPropriedade(TipoPropriedade.DATA);
		d.setValorPropriedade(b.getDataReferencia());
		filtros.add(d);

		ClausulaFiltro emp = new ClausulaFiltro();
		emp.setNomePropriedade("empresa");
		emp.setTipoPropriedade(TipoPropriedade.NUMERO);
		emp.setValorPropriedade(b.getEmpresa());
		filtros.add(emp);

		ClausulaFiltro t = new ClausulaFiltro();
		t.setNomePropriedade("tipoMovimento");
		t.setTipoPropriedade(TipoPropriedade.STRING);
		t.setValorPropriedade(b.getTipoMovimento());
		filtros.add(t);
		Long retorno = null;

		EntityTransaction trx = dao.getTransaction();
		try {
			trx.begin();
			List<Boletim> boletins =  dao.findAll(Boletim.class, filtros);
			for (Boletim bol:boletins) {
				dao.delete(Boletim.class, bol.getId());
			}
			retorno = dao.save(entidade);
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			throw new NegocioException("Dados não foram salvos."+e.getLocalizedMessage());
		}

		return retorno;
	}
	
	public Long salvar(BaseEntity entidade) {
		Boletim b = (Boletim) entidade;
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();
		String qry = null;
		if (b.getId() == null) {
			qry = "select count(ent.id) from Boletim ent where ent.dataReferencia = :dataReferencia and ent.empresa = :empresa and ent.tipoMovimento = :tipoMovimento";
		} else {
			qry = "select count(ent.id) from Boletim ent where ent.id <> :id and ent.dataReferencia = :dataReferencia and ent.empresa = :empresa and ent.tipoMovimento = :tipoMovimento";
			ClausulaFiltro d = new ClausulaFiltro();
			d.setNomePropriedade("id");
			d.setTipoPropriedade(TipoPropriedade.NUMERO);
			d.setValorPropriedade(b.getId());
			filtros.add(d);
		}

		ClausulaFiltro d = new ClausulaFiltro();
		d.setNomePropriedade("dataReferencia");
		d.setTipoPropriedade(TipoPropriedade.DATA);
		d.setValorPropriedade(b.getDataReferencia());
		filtros.add(d);

		ClausulaFiltro emp = new ClausulaFiltro();
		emp.setNomePropriedade("empresa");
		emp.setTipoPropriedade(TipoPropriedade.NUMERO);
		emp.setValorPropriedade(b.getEmpresa());
		filtros.add(emp);

		ClausulaFiltro t = new ClausulaFiltro();
		t.setNomePropriedade("tipoMovimento");
		t.setTipoPropriedade(TipoPropriedade.STRING);
		t.setValorPropriedade(b.getTipoMovimento());
		filtros.add(t);
		Long retorno = null;
		if (dao.consultaExistencia(qry, filtros) > 0) {
			throw new NegocioException("Já existe um boletim cadastro neste mês e tipo de serviço");
		}
		EntityTransaction trx = dao.getTransaction();
		try {
			trx.begin();
			retorno = dao.save(entidade);
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			throw new NegocioException("Dados não foram salvos."+e.getLocalizedMessage());
		}

		return retorno;
	}

	public void exportarDadosAccess(Date dataReferencia) {
		String fase=null;
		List<ClausulaFiltro> filtros = new ArrayList<ClausulaFiltro>();
		ClausulaFiltro d = new ClausulaFiltro();
		d.setNomePropriedade("dataReferencia");
		d.setTipoPropriedade(TipoPropriedade.DATA);
		d.setValorPropriedade(dataReferencia);
		filtros.add(d);
		List<Boletim> boletins = dao.findAll(Boletim.class, filtros);
		EntityTransaction trx  =  aDao.getTransaction();
		trx.begin();
		try {

			for (Boletim b:boletins) {

				aDao.excluiFrota(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1);
				aDao.excluiReceitaImobilizado(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1);
				aDao.excluiMovtoCustosOperac(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1);
				aDao.excluiMovtoDespGeral(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1);
				aDao.excluiMovtoFrotaApoio(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1);
				aDao.excluiMovtoKm(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1);
				aDao.excluiMovtoMateriais(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1);
				aDao.excluiMovtoServicoManut(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1);
				
				aDao.incluiFrota(dataReferencia,b);
				
				for (CustoPessoal cp: b.getCustosPessoal()) {
					System.out.println("incluiMovtoCustosOperac"+b.getEmpresa().getNumero());
					aDao.incluiMovtoCustosOperac(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1, cp);
				}
				
				for (FrotaApoio fa: b.getFrotasApoio()) {
					System.out.println("incluiMovtoFrotaApoio"+b.getEmpresa().getNumero());
					aDao.incluiMovtoFrotaApoio(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1, fa);
				}
				
				for (ReceitaImobilizado ri: b.getReceitasImobilizado()) {
					System.out.println("incluiReceitaImobilizado"+b.getEmpresa().getNumero());
					aDao.incluiReceitaImobilizado(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1, ri);
				}		
				System.out.println("incluiMovtoKm"+b.getEmpresa().getNumero());
				aDao.incluiMovtoKm(b);
				for (Material mt: b.getMateriais()) {
					System.out.println("incluiMovtoMateriais"+b.getEmpresa().getNumero());
					aDao.incluiMovtoMateriais(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1, mt);
				}
				
				for (Servico sv: b.getServicos()) {
					System.out.println("incluiMovtoServicoManut"+b.getEmpresa().getNumero());
					aDao.incluiMovtoServicoManut(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1, sv);
				}
				
				
				for (DespesaGeral dg: b.getDespesasGerais()) {
					System.out.println("incluiMovtoDespGeral"+b.getEmpresa().getNumero());
					aDao.incluiMovtoDespGeral(dataReferencia, b.getEmpresa().getNumero().intValue(), b.getTipoMovimento().ordinal()+1, dg);
				}

				aDao.getManager().flush();
			}
	    	
			trx.commit();
		} catch (Exception e) {
			trx.rollback();
			throw new NegocioException("Dados não foram salvos."+e.getLocalizedMessage());
		}
		
	}

}
