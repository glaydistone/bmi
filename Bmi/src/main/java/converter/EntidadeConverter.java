package converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.tecnotran.bmi.model.Empresa;
import br.com.tecnotran.bmi.model.BaseEntity;
import br.com.tecnotran.bmi.repository.BaseDAO;
import br.com.tecnotran.bmi.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = BaseEntity.class)
public class EntidadeConverter implements Converter {

	//@Inject
	private BaseDAO dao;

	public EntidadeConverter() {
		dao = CDIServiceLocator.getBean(BaseDAO.class);
	}

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		BaseEntity retorno = null;

		if (value != null) {
			Long id = new Long(value);
			//retorno = dao.findById(Empresa.class, id);
			retorno = (BaseEntity)this.getAttributesFrom(component).get(value);

		}

		return retorno;
	}

    private void addAttribute(UIComponent component, BaseEntity o) {
        this.getAttributesFrom(component).put(o.getId().toString(), o);
    }
 
    private Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }
	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String retorno = "";

		/*
		 * if (value != null) { retorno = ((EntidadeBase) value).getId().toString(); }
		 */
		
		if (value != null && !"".equals(value)) {
            BaseEntity entity = (BaseEntity) value;
 
            if (entity.getId() != null) {
                this.addAttribute(component, entity);
 
                if (entity.getId() != null) {
                    return String.valueOf(entity.getId());
                }
                return (String) value;
            }
        }
		

		return retorno;
	}

}
