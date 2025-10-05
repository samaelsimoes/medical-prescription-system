package com.company.clinic.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class ReceitaRepository extends BaseRepository {

    public List<Object[]> consultarPorPacienteEMedicamento(Long pacienteId, Long medicamentoId, int first, int pageSize) {
        String where = " where 1=1 ";
        if (pacienteId != null) where += " and r.paciente.id = :pacId ";
        if (medicamentoId != null) where += " and i.medicamento.id = :medId ";

        String jpql = "select r.id, r.paciente.nome, r.criadaEm, count(i.id) " +
                "from Receita r join r.itens i " + where +
                " group by r.id, r.paciente.nome, r.criadaEm order by r.id desc";

        TypedQuery<Object[]> q = em().createQuery(jpql, Object[].class);
        if (pacienteId != null) q.setParameter("pacId", pacienteId);
        if (medicamentoId != null) q.setParameter("medId", medicamentoId);
        return q.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public long countConsultarPorPacienteEMedicamento(Long pacienteId, Long medicamentoId) {
        String where = " where 1=1 ";
        if (pacienteId != null) where += " and r.paciente.id = :pacId ";
        if (medicamentoId != null) where += " and i.medicamento.id = :medId ";

        String jpql = "select count(distinct r.id) from Receita r join r.itens i " + where;
        TypedQuery<Long> q = em().createQuery(jpql, Long.class);
        if (pacienteId != null) q.setParameter("pacId", pacienteId);
        if (medicamentoId != null) q.setParameter("medId", medicamentoId);
        return q.getSingleResult();
    }

    public List<Object[]> top2MedicamentosMaisPrescritos() {
        String jpql = "select i.medicamento.nome, count(i.id) " +
                "from ReceitaItem i group by i.medicamento.nome order by count(i.id) desc";
        return em().createQuery(jpql, Object[].class).setMaxResults(2).getResultList();
    }

    public List<Object[]> top2PacientesMaisPrescritos() {
        String jpql = "select r.paciente.nome, count(i.id) " +
                "from ReceitaItem i join i.receita r group by r.paciente.nome order by count(i.id) desc";
        return em().createQuery(jpql, Object[].class).setMaxResults(2).getResultList();
    }

    public List<Object[]> totalPorPaciente() {
        String jpql = "select r.paciente.nome, count(i.id) " +
                "from ReceitaItem i join i.receita r group by r.paciente.nome order by r.paciente.nome";
        return em().createQuery(jpql, Object[].class).getResultList();
    }
    public List<String> nomesItensPorReceita(Long receitaId) {
        String jpql = "select i.medicamento.nome " +
                "from ReceitaItem i " +
                "where i.receita.id = :id " +
                "order by i.medicamento.nome";
        return em().createQuery(jpql, String.class)
                .setParameter("id", receitaId)
                .getResultList();
    }
}
