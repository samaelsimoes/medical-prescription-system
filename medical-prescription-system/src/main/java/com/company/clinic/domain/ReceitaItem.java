package com.company.clinic.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "receita_item")
public class ReceitaItem implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Receita receita;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Medicamento medicamento;

    @Column(length = 120)
    private String observacao;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Receita getReceita() { return receita; }
    public void setReceita(Receita receita) { this.receita = receita; }
    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
