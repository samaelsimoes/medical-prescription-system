package com.company.clinic.repository;

import com.company.clinic.domain.Paciente;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PacienteRepository extends BaseRepository {

    public List<Paciente> search(String filtro, int first, int pageSize) {
        String where = "";
        List<Object> params = new ArrayList<>();

        String filtroLike = null;
        String cpfDigitsLike = null;

        if (filtro != null && !filtro.isBlank()) {
            filtroLike = "%" + filtro.toLowerCase() + "%";
            String cpfDigits = filtro.replaceAll("\\D", "");
            cpfDigitsLike = cpfDigits.isEmpty() ? null : "%" + cpfDigits + "%";

            where = " where lower(p.nome) like ?1";
            params.add(filtroLike);

            if (cpfDigitsLike != null) {
                where += " or p.cpf like ?2";
                params.add(cpfDigitsLike);
            } else {
                where += " or p.cpf like ?2";
                params.add("%" + filtro + "%");
            }
        }

        String jpql = "select p from Paciente p" + where + " order by p.id desc";
        return listPage(Paciente.class, first, pageSize, jpql, params.toArray());
    }

    public long countSearch(String filtro) {
        String where = "";
        List<Object> params = new ArrayList<>();

        String filtroLike = null;
        String cpfDigitsLike = null;

        if (filtro != null && !filtro.isBlank()) {
            filtroLike = "%" + filtro.toLowerCase() + "%";
            String cpfDigits = filtro.replaceAll("\\D", "");
            cpfDigitsLike = cpfDigits.isEmpty() ? null : "%" + cpfDigits + "%";

            where = " where lower(p.nome) like ?1";
            params.add(filtroLike);

            if (cpfDigitsLike != null) {
                where += " or p.cpf like ?2";
                params.add(cpfDigitsLike);
            } else {
                where += " or p.cpf like ?2";
                params.add("%" + filtro + "%");
            }
        }

        String jpql = "select count(p) from Paciente p" + where;
        return count(jpql, params.toArray());
    }

}
