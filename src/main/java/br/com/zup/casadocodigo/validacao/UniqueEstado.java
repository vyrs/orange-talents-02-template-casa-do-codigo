package br.com.zup.casadocodigo.validacao;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = {UniqueEstadoValidator.class})
public @interface UniqueEstado {
    String message() default "Esse estado já está cadastrado para este País";
    
    Class<?>[] groups() default { };
    
    Class<? extends Payload>[] payload() default { };
   
    String field1();
    
    String field2();
    
    Class<?> domainClass();
}
