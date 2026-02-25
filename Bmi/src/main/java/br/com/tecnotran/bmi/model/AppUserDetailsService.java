package br.com.tecnotran.bmi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.tecnotran.bmi.model.seguranca.Grupo;
import br.com.tecnotran.bmi.model.seguranca.Usuario;
import br.com.tecnotran.bmi.model.seguranca.UsuarioSistema;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService {

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		BaseDAO dao = CDIServiceLocator.getBean(BaseDAO.class);
		Usuario usuario = dao.porLogin(login);
		
		UsuarioSistema user = null;
		
		if (usuario != null) {
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		for (Grupo grupo : usuario.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		}
		
		return authorities;
	}

}
