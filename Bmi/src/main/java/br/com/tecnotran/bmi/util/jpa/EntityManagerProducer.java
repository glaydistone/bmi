package br.com.tecnotran.bmi.util.jpa;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.hibernate.Session;

import br.com.tecnotran.bmi.util.cdi.BdAccess;

@ApplicationScoped
public class EntityManagerProducer {
	
	@PersistenceUnit(unitName = "BoletimPU")
	private EntityManagerFactory factory;
	
	
	 @PersistenceUnit(unitName = "BoletimAcc")
	  
	  private EntityManagerFactory accessFactory;
	 
	
	public EntityManagerProducer() {
		factory = Persistence.createEntityManagerFactory("BoletimPU");
		accessFactory = Persistence.createEntityManagerFactory("BoletimAcc");
	}
	
	@Produces @RequestScoped @Default
	public Session createPUEntityManager() {
		return (Session) factory.createEntityManager();
	}
	
	
	@Produces @RequestScoped @BdAccess
	public EntityManager createEntityManagerAccess() {
		return accessFactory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager manager) {
		manager.close();
	}
}
