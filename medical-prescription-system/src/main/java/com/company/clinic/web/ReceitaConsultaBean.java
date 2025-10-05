package com.company.clinic.web;

import com.company.clinic.domain.Medicamento;
import com.company.clinic.domain.Paciente;
import com.company.clinic.service.MedicamentoService;
import com.company.clinic.service.PacienteService;
import com.company.clinic.service.ReceitaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.FilterMeta;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ReceitaConsultaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject private ReceitaService receitaService;
    @Inject private PacienteService pacienteService;
    @Inject private MedicamentoService medicamentoService;

    private Long pacienteId;
    private Long medicamentoId;

    private LazyDataModel<Object[]> model;

    private List<String> itensDaReceita;

    @PostConstruct
    public void init() {
        model = new LazyDataModel<Object[]>() {
            private static final long serialVersionUID = 1L;
            @Override
            public List<Object[]> load(int first, int pageSize,
                                       Map<String, SortMeta> sortBy,
                                       Map<String, FilterMeta> filterBy) {
                setRowCount((int) receitaService.countConsultar(pacienteId, medicamentoId));
                return receitaService.consultar(pacienteId, medicamentoId, first, pageSize);
            }
        };
    }

    public void limparFiltros() {
        pacienteId = null;
        medicamentoId = null;
    }

    public void abrirItens(Long receitaId) {
        itensDaReceita = receitaService.listarNomesItens(receitaId);
    }

    public List<Paciente> autoCompletePaciente(String q) { return pacienteService.search(q, 0, 10); }
    public List<Medicamento> autoCompleteMedicamento(String q) { return medicamentoService.search(q, 0, 10); }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public Long getMedicamentoId() { return medicamentoId; }
    public void setMedicamentoId(Long medicamentoId) { this.medicamentoId = medicamentoId; }
    public LazyDataModel<Object[]> getModel() { return model; }
    public List<String> getItensDaReceita() { return itensDaReceita; }
}
