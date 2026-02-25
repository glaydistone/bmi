/**
 * 
 */
package br.com.tecnotran.bmi.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.proxy.HibernateProxyHelper;

/**
 * @author TONE_
 *
 */
public abstract class BaseEntity implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method should return the primary key.
	 * 
	 * @return
	 */
	public abstract Long getId();
	
	
	
	/* As a starting point, we provide a basic mean for entities
	 * to test for equality using their "id".
	 * 
	 * Please note that THIS IS NOT ALWAYS ACCEPTABLE since newly generated
	 * ids might break Set/Collection semantics. Please refer to the documentarion
	 * before doing something like this.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		if (getId() == null || ((BaseEntity) obj).getId() == null) {
			return false;
		}
		if (!getId().equals(((BaseEntity) obj).getId())) {
			return false;
		}
		if (!HibernateProxyHelper.getClassWithoutInitializingProxy(obj)
				.isAssignableFrom(this.getClass())) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : getId().hashCode();
	}



	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Object retorno = super.clone();
		return retorno;
	}



	public Boletim clone(BigDecimal ajuste) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
