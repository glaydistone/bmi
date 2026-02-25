package br.com.tecnotran.bmi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.tecnotran.bmi.model.Cargo;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.TipoCargo;
import br.com.tecnotran.bmi.model.TipoMaterial;
import br.com.tecnotran.bmi.model.TipoMovimento;
import br.com.tecnotran.bmi.model.TipoVeiculoApoio;
import br.com.tecnotran.bmi.model.seguranca.Grupo;
import br.com.tecnotran.bmi.model.seguranca.UsuarioSistema;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.Order;

@Named("principalBean")
@SessionScoped
public class PrincipalBean  implements Serializable{
	@Inject
	private BaseDAO dao;
	
	private List<Empresa> listaEmpresa = new ArrayList<Empresa>();
	private List<Cargo> listaCargo = new ArrayList<Cargo>();
	private List<TipoCargo> listaTipoCargo = new ArrayList<TipoCargo>();
	private List<TipoMaterial> listaTipoMaterial = new ArrayList<TipoMaterial>();
	private List<TipoVeiculoApoio> listaTipoVeiculoApoio = new ArrayList<TipoVeiculoApoio>();
	private List<Grupo> listaGrupo = new ArrayList<Grupo>();

	private Empresa filtroEmpresa;
	private TipoMovimento filtroTipoMovimento;
	private Date filtroDataReferencia;
	private Grupo grupo;
	
	private Date agora = new Date();
	
	public Grupo getGrupo() {
		return grupo;
	}


	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}


	public void setListaGrupo(List<Grupo> listaGrupo) {
		this.listaGrupo = listaGrupo;
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



	public void setFiltroDataReferencia(Date filtroDataReferencia) {
		this.filtroDataReferencia = filtroDataReferencia;
	}
	
	public Date getAgora() {
		return agora;
	}



	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
	}



	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}



	public List<Cargo> getListaCargo() {
		return listaCargo;
	}



	public void setListaCargo(List<Cargo> listaCargo) {
		this.listaCargo = listaCargo;
	}



	public List<TipoCargo> getListaTipoCargo() {
		return listaTipoCargo;
	}



	public void setListaTipoCargo(List<TipoCargo> listaTipoCargo) {
		this.listaTipoCargo = listaTipoCargo;
	}



	public List<TipoMaterial> getListaTipoMaterial() {
		return listaTipoMaterial;
	}



	public void setListaTipoMaterial(List<TipoMaterial> listaTipoMaterial) {
		this.listaTipoMaterial = listaTipoMaterial;
	}



	public List<TipoVeiculoApoio> getListaTipoVeiculoApoio() {
		return listaTipoVeiculoApoio;
	}



	public void setListaTipoVeiculoApoio(List<TipoVeiculoApoio> listaTipoVeiculoApoio) {
		this.listaTipoVeiculoApoio = listaTipoVeiculoApoio;
	}

	public TipoMovimento[] getTiposMovimento() {
		return TipoMovimento.values();
	}

	@PostConstruct
	public void init() {
		listaEmpresa = (List<Empresa>) dao.findAll(Empresa.class,Order.ASC,new String[]{"razaoSocial"});
			
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null) {  // se for uma empresa, tiras as outras empresas da lista
			Empresa e = null;     // para impedir que o usuário consulte dados de outras empresas
			if (((UsuarioSistema) auth.getPrincipal()).getUsuario().getEmpresa() != null) {
				Empresa empUsr = ((UsuarioSistema) auth.getPrincipal()).getUsuario().getEmpresa();
				for (int i= listaEmpresa.size()-1; i>= 0; i--) {
					e = (Empresa) listaEmpresa.get(i);
					if (!e.getId().equals(empUsr.getId())) {
						listaEmpresa.remove(i);
					}
				}
			}
		}
		
		listaCargo = (List<Cargo>) dao.findAll(Cargo.class,Order.ASC,new String[]{"descricao"});
		listaTipoCargo = (List<TipoCargo>) dao.findAll(TipoCargo.class,Order.ASC,new String[]{"descricao"});
		listaTipoMaterial = (List<TipoMaterial>) dao.findAll(TipoMaterial.class,Order.ASC,new String[]{"descricao"});
		listaTipoVeiculoApoio = (List<TipoVeiculoApoio>) dao.findAll(TipoVeiculoApoio.class,Order.ASC,new String[]{"descricao"});
		listaGrupo = (List<Grupo>) dao.findAll(Grupo.class,Order.ASC,new String[]{"descricao"});
	}


	public List<Grupo> getListaGrupo() {
		return listaGrupo;
	}

}
