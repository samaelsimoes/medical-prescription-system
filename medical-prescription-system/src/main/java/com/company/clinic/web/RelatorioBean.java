package com.company.clinic.web;

import com.company.clinic.service.ReceitaService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class RelatorioBean {
    @Inject private ReceitaService service;

    private List<Object[]> topMedicamentos;
    private List<Object[]> topPacientes;
    private List<Object[]> totaisPorPaciente;

    @PostConstruct
    public void load() {
        topMedicamentos = service.top2Medicamentos();
        topPacientes = service.top2Pacientes();
        totaisPorPaciente = service.totaisPorPaciente();
    }

    public List<Object[]> getTopMedicamentos() { return topMedicamentos; }
    public List<Object[]> getTopPacientes() { return topPacientes; }
    public List<Object[]> getTotaisPorPaciente() { return totaisPorPaciente; }
}
