package com.bhcontrole.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.bhcontrole.model.Funcao;
import com.bhcontrole.service.FuncaoService;

@Component
public class RoleToFuncaoConversor implements Converter<Object, Funcao>{

    @Autowired
    private FuncaoService funcaoService;
 
    /*
     * Gets Funcao by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public Funcao convert(Object element) {
        Long id = Long.parseLong((String)element);
        Funcao funcao = funcaoService.find(id);
        System.out.println("Funcao : "+funcao);
        return funcao;
    }
     
    /*
     * Gets Funcao by type
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    /*
    public Funcao convert(Object element) {
        String type = (String)element;
        Funcao profile= funcaoService.findByType(type);
        System.out.println("Profile ... : "+profile);
        return profile;
    }
    */
}
