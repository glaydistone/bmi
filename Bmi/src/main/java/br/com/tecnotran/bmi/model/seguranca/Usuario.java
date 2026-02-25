package br.com.tecnotran.bmi.model.seguranca;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.tecnotran.bmi.model.BaseEntity;
import br.com.tecnotran.bmi.model.Empresa;

@Entity
@Table(name="usuario")
public class Usuario extends BaseEntity {
	
	@Id
	@GeneratedValue
	@Column(name="id_usuario")
	private Long id;
	
	@Column(nullable=false, length=20)
	private String login;
	
	@Column(nullable=false, length=80)
	private String nome;
	
	@Column(nullable=false, length=20)
	private String senha;
	
	@Transient
	private String redigiteSenha;
	
	@Transient
	private String novaSenha;
	
	public String getNovaSenha() {
		return novaSenha;
	}


	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}


	public String getRedigiteSenha() {
		return redigiteSenha;
	}


	public void setRedigiteSenha(String redigiteSenha) {
		this.redigiteSenha = redigiteSenha;
	}


	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name="id_usuario"),
			inverseJoinColumns = @JoinColumn(name = "id_grupo"))
	private List<Grupo> grupos = new ArrayList<Grupo>();
	
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = true, foreignKey = @ForeignKey(name = "FK_usuario_empresa"))
	private Empresa empresa;
	
	@Transient
	private boolean usuarioEmpresa;

	public boolean isUsuarioEmpresa() {
		return (getEmpresa() != null);
	}


	public void setUsuarioEmpresa(boolean isEmpresa) {
		this.usuarioEmpresa = isEmpresa;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	
	public List<Grupo> getGrupos() {
		return grupos;
	}



	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}



}
