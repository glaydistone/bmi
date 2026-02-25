package br.com.tecnotran.bmi.controller.seguranca;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.tecnotran.bmi.model.seguranca.Usuario;
import br.com.tecnotran.bmi.model.seguranca.UsuarioSistema;
@Named
@RequestScoped
public class Seguranca {
	
	private Usuario usuario;
	
	

	public Usuario getUsuario() {
		Usuario usuario = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) 
				FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if (auth != null && auth.getPrincipal() != null) {
			usuario = ((UsuarioSistema) auth.getPrincipal()).getUsuario();
		}
		
		return usuario;
	}
	
	public boolean isAdministrador() {
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMINISTRADORES");
	}
}
