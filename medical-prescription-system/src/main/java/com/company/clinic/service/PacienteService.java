package com.company.clinic.service;

import com.company.clinic.domain.Paciente;
import com.company.clinic.repository.PacienteRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PacienteService {

    @Inject
    private PacienteRepository repo;

    public List<Paciente> search(String filtro, int first, int pageSize) {
        return repo.search(filtro, first, pageSize);
    }

    public long count(String filtro) {
        return repo.countSearch(filtro);
    }

    @Transactional
    public Paciente save(Paciente p) {
        return repo.save(p);
    }

    @Transactional
    public void delete(Paciente p) {
        repo.remove(p);
    }

    public Paciente findById(Long id) {
        return repo.findById(Paciente.class, id);
    }

    public long countSearch(String filtro) {
        return repo.countSearch(filtro);
    }

    public Paciente find(Long id) {
        return repo.find(Paciente.class, id);
    }
}
