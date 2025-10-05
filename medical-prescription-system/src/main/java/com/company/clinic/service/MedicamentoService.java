package com.company.clinic.service;

import com.company.clinic.domain.Medicamento;
import com.company.clinic.repository.MedicamentoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class MedicamentoService {
    @Inject private MedicamentoRepository repo;

    public List<Medicamento> search(String filtro, int first, int pageSize) {
        return repo.searchByNome(filtro, first, pageSize);
    }
    public long count(String filtro) { return repo.countByNome(filtro); }

    public Medicamento findById(Long id) {
        return repo.findById(Medicamento.class, id);
    }

    @Transactional public Medicamento save(Medicamento m) { return repo.save(m); }
    @Transactional public void delete(Medicamento m) { repo.remove(m); }
    public Medicamento find(Long id) { return repo.find(Medicamento.class, id); }
}
