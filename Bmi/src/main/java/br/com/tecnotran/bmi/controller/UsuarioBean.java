package br.com.tecnotran.bmi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.tecnotran.bmi.model.Boletim;
import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.seguranca.Usuario;
import br.com.tecnotran.bmi.model.seguranca.UsuarioSistema;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.repository.ClausulaFiltro;
import br.com.tecnotran.bmi.repository.TipoPropriedade;
import br.com.tecnotran.bmi.services.BaseService;
import br.com.tecnotran.bmi.util.jsf.FacesUtil;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PrincipalBean principalBean;

	@Inject
	private BaseDAO dao;

	@Inject
	private BaseService service;

	private Usuario usuario;

	private List<Usuario> usuarios;

	private Empresa filtroEmpresa;

	private String idStr;

	private String nome;
	
	public void trocarSenha() {
		try {
			
			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
					.getCurrentInstance().getExternalContext().getUserPrincipal();


			if (auth != null && auth.getPrincipal() != null) {
	    		usuario = ((UsuarioSistema) auth.getPrincipal()).getUsuario();
	
			}
			
			if (!this.getUsuario().getNovaSenha().equals(this.getUsuario().getRedigiteSenha())) {
				throw new Exception("Senhas estão diiferentes");
			}
			usuario.setSenha(usuario.getRedigiteSenha());  
			service.salvar(this.getUsuario());
			FacesUtil.addInfoMessage("Registro salvo com sucesso!");
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void salvar() {
		try {
			service.salvar(usuario);
			FacesUtil.addInfoMessage("Registro salvo com sucesso!");
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	@SuppressWarnings("deprecation")
	public void setIdStr(String idStr) {
		this.idStr = idStr;
		this.usuario = dao.findById(Usuario.class, new Long(idStr));
	}

	public String getIdStr() {
		return idStr;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setFiltroEmpresa(Empresa filtroEmpresa) {
		this.filtroEmpresa = filtroEmpresa;
	}

	public UsuarioBean() {
		usuario = new Usuario();
	}

	public Empresa getFiltroEmpresa() {
		return filtroEmpresa;
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

		if (nome != null && StringUtils.isNotBlank(nome)) {
			ClausulaFiltro cf = new ClausulaFiltro();
			cf.setAlias("obj");
			cf.setNomePropriedade("nome");
			cf.setTipoPropriedade(TipoPropriedade.STRING);
			cf.setValorPropriedade(nome);
			retorno.add(cf);
		}
		return retorno;

	}

	public void pesquisar() {
		List<ClausulaFiltro> clausulas = montaConsulta();
		usuarios = dao.findAll(Usuario.class, clausulas);
	}

	public void inicializar() {

	}

}
