package com.company.clinic.web.converter;

import com.company.clinic.domain.Medicamento;
import com.company.clinic.service.MedicamentoService;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@ApplicationScoped
@FacesConverter(value = "medicamentoConverter", managed = true)
public class MedicamentoConverter implements Converter<Medicamento> {

    @Inject
    private MedicamentoService service;

    @Override
    public Medicamento getAsObject(FacesContext ctx, UIComponent comp, String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            Long id = Long.valueOf(value);
            return service.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent comp, Medicamento value) {
        if (value == null || value.getId() == null) return "";
        return value.getId().toString();
    }
}
