package com.company.clinic.service;

import com.company.clinic.domain.Medicamento;
import com.company.clinic.domain.Paciente;
import com.company.clinic.domain.Receita;
import com.company.clinic.domain.ReceitaItem;
import com.company.clinic.repository.ReceitaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ReceitaService {

    @Inject private ReceitaRepository repo;

    @Transactional
    public Receita criarReceita(Paciente paciente, List<Medicamento> medicamentos) {
        Receita r = new Receita();
        r.setPaciente(paciente);

        for (Medicamento m : medicamentos) {
            ReceitaItem it = new ReceitaItem();
            it.setReceita(r);
            it.setMedicamento(m);
            r.getItens().add(it);
        }
        return repo.save(r);
    }

    public List<Object[]> consultar(Long pacienteId, Long medicamentoId, int first, int pageSize) {
        return repo.consultarPorPacienteEMedicamento(pacienteId, medicamentoId, first, pageSize);
    }

    public long countConsultar(Long pacienteId, Long medicamentoId) {
        return repo.countConsultarPorPacienteEMedicamento(pacienteId, medicamentoId);
    }

    public List<String> listarNomesItens(Long receitaId) {
        return repo.nomesItensPorReceita(receitaId);
    }

    public List<Object[]> top2Medicamentos() { return repo.top2MedicamentosMaisPrescritos(); }
    public List<Object[]> top2Pacientes() { return repo.top2PacientesMaisPrescritos(); }
    public List<Object[]> totaisPorPaciente() { return repo.totalPorPaciente(); }
}
