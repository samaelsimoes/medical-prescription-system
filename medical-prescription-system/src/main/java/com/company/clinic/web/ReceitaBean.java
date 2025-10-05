package com.company.clinic.web;

import com.company.clinic.domain.Medicamento;
import com.company.clinic.domain.Paciente;
import com.company.clinic.service.MedicamentoService;
import com.company.clinic.service.PacienteService;
import com.company.clinic.service.ReceitaService;
import org.primefaces.model.DualListModel;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class ReceitaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject private ReceitaService receitaService;
    @Inject private PacienteService pacienteService;
    @Inject private MedicamentoService medicamentoService;

    private Long pacienteId;

    private DualListModel<Long> medicamentosDualIds;

    private Map<Long, String> medLabelMap;

    private Long filtroPacienteId;
    private Long filtroMedicamentoId;
    private LazyDataModel<Object[]> model;

    @PostConstruct
    public void init() {
        List<Medicamento> meds = medicamentoService.search(null, 0, 1000);

        medLabelMap = meds.stream()
                .filter(m -> m.getId() != null)
                .collect(Collectors.toMap(Medicamento::getId, Medicamento::getNome));

        List<Long> sourceIds = new ArrayList<>(medLabelMap.keySet());
        Collections.sort(sourceIds);

        medicamentosDualIds = new DualListModel<>(sourceIds, new ArrayList<>());

        model = new LazyDataModel<Object[]>() {
            private static final long serialVersionUID = 1L;
            @Override
            public List<Object[]> load(int first, int pageSize,
                                       Map<String, SortMeta> sortBy,
                                       Map<String, FilterMeta> filterBy) {
                setRowCount((int) receitaService.countConsultar(filtroPacienteId, filtroMedicamentoId));
                return receitaService.consultar(filtroPacienteId, filtroMedicamentoId, first, pageSize);
            }
        };
    }

    public String nomeMedicamento(Long id) {
        if (id == null) return "";
        String nome = medLabelMap.get(id);
        return (nome != null) ? nome : ("ID " + id);
    }

    public String salvar() {
        try {
            if (pacienteId == null) {
                addMsg(FacesMessage.SEVERITY_WARN, "Atenção", "Selecione um paciente.");
                return null;
            }

            List<Long> idsSelecionados = new ArrayList<>(medicamentosDualIds.getTarget());

            if (idsSelecionados.isEmpty()) {
                addMsg(FacesMessage.SEVERITY_WARN, "Atenção", "Selecione ao menos um medicamento.");
                return null;
            }

            Paciente paciente = pacienteService.findById(pacienteId);
            if (paciente == null) {
                addMsg(FacesMessage.SEVERITY_ERROR, "Erro", "Paciente não encontrado.");
                return null;
            }

            List<Medicamento> selecionados = idsSelecionados.stream()
                    .map(medicamentoService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            receitaService.criarReceita(paciente, selecionados);

            addMsg(FacesMessage.SEVERITY_INFO, "Sucesso", "Receita salva com sucesso!");

            pacienteId = null;
            medicamentosDualIds.setTarget(new ArrayList<>());

            return "/pages/receita/list.xhtml?faces-redirect=true";
        } catch (Exception e) {
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível salvar: " + e.getMessage());
            return null;
        }
    }

    public List<Paciente> autoCompletePaciente(String q) {
        return pacienteService.search(q, 0, 10);
    }

    private void addMsg(FacesMessage.Severity sev, String sum, String det) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, sum, det));
    }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public DualListModel<Long> getMedicamentosDualIds() { return medicamentosDualIds; }
    public void setMedicamentosDualIds(DualListModel<Long> medicamentosDualIds) { this.medicamentosDualIds = medicamentosDualIds; }

    public Long getFiltroPacienteId() { return filtroPacienteId; }
    public void setFiltroPacienteId(Long filtroPacienteId) { this.filtroPacienteId = filtroPacienteId; }

    public Long getFiltroMedicamentoId() { return filtroMedicamentoId; }
    public void setFiltroMedicamentoId(Long filtroMedicamentoId) { this.filtroMedicamentoId = filtroMedicamentoId; }

    public LazyDataModel<Object[]> getModel() { return model; }
}
