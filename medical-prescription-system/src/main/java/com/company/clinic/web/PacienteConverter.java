package com.company.clinic.web;

import com.company.clinic.domain.Paciente;
import com.company.clinic.service.PacienteService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "pacienteConverter", managed = true)
public class PacienteConverter implements Converter<Paciente> {

    @Inject
    private PacienteService service;

    @Override
    public Paciente getAsObject(FacesContext ctx, UIComponent comp, String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            Long id = Long.valueOf(value);
            return service.findById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent comp, Paciente value) {
        if (value == null || value.getId() == null) return "";
        return value.getId().toString();
    }
}
