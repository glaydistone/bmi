package br.com.tecnotran.bmi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="permissao")
public class Permissao extends BaseEntity {
	
	@Id
	@GeneratedValue
	@Column(name="id_permissao")
	private Long id;
	
	private String login;
	private String nome;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
