package com.company.clinic.repository;

import com.company.clinic.domain.Medicamento;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MedicamentoRepository extends BaseRepository {

    public List<Medicamento> searchByNome(String filtro, int first, int pageSize) {
        String where = "";
        Object[] params = new Object[]{};
        if (filtro != null && !filtro.isBlank()) {
            where = " where lower(m.nome) like ?1 ";
            params = new Object[]{"%"+filtro.toLowerCase()+"%"};
        }
        String ql = "select m from Medicamento m" + where + " order by m.nome asc";
        return listPage(Medicamento.class, first, pageSize, ql, params);
    }

    public long countByNome(String filtro) {
        String where = "";
        Object[] params = new Object[]{};
        if (filtro != null && !filtro.isBlank()) {
            where = " where lower(m.nome) like ?1 ";
            params = new Object[]{"%"+filtro.toLowerCase()+"%"};
        }
        return count("select count(m) from Medicamento m" + where, params);
    }
}
