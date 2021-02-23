package br.com.zup.casadocodigo.validacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class UniqueEstadoValidator implements ConstraintValidator<UniqueEstado, Object> {

    private String attribute1;
    private String attribute2;
    private Class<?> klass;
    
    @PersistenceContext
    private EntityManager manager;
    
    @Override
    public void initialize(UniqueEstado uniqueEstado) {
    	attribute1 = uniqueEstado.field1();
    	attribute2 = uniqueEstado.field2();
        klass = uniqueEstado.domainClass();

    }
    
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Query query = manager.createQuery("select 1 from " + klass.getName() + " where " + attribute1 + " =:nome and  pais_id =:paisId");
        
		query.setParameter("nome", (String) new BeanWrapperImpl(value).getPropertyValue(attribute1));
		
		query.setParameter("paisId", (Long) new BeanWrapperImpl(value).getPropertyValue(attribute2));

        List<?> list = query.getResultList();

        return list.isEmpty();
	}

}
