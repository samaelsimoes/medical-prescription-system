package com.company.clinic.web;

import com.company.clinic.domain.Medicamento;
import com.company.clinic.service.MedicamentoService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class MedicamentoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private MedicamentoService service;

    private String filtro;
    private LazyDataModel<Medicamento> model;
    private Medicamento selecionado = new Medicamento();

    @PostConstruct
    public void init() {
        model = new LazyDataModel<Medicamento>() {
            private static final long serialVersionUID = 1L;

            @Override
            public List<Medicamento> load(int first, int pageSize,
                                          Map<String, SortMeta> sortBy,
                                          Map<String, FilterMeta> filterBy) {
                setRowCount((int) service.count(filtro));
                return service.search(filtro, first, pageSize);
            }
        };
    }

    public void novo() {
        selecionado = new Medicamento();
    }

    public void editar(Medicamento m) {
        try {
            if (m != null && m.getId() != null) {
                Medicamento db = service.find(m.getId());
                selecionado = (db != null) ? db : new Medicamento();
            } else {
                selecionado = new Medicamento();
            }
        } catch (Exception e) {
            selecionado = new Medicamento();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                            "Falha ao carregar o registro para edição: " + e.getMessage()));
        }
    }

    public void salvar() {
        try {
            boolean criando = (selecionado.getId() == null);
            selecionado = service.save(selecionado);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
                            criando ? "Cadastro realizado com sucesso!" : "Registro atualizado com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                            "Não foi possível salvar: " + e.getMessage()));
        }
    }

    public void excluir(Medicamento m) {
        try {
            service.delete(m);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Registro excluído."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
                            "Não foi possível excluir: " + e.getMessage()));
        }
    }

    public String getFiltro() { return filtro; }
    public void setFiltro(String filtro) { this.filtro = filtro; }
    public LazyDataModel<Medicamento> getModel() { return model; }
    public Medicamento getSelecionado() { return selecionado; }
    public void setSelecionado(Medicamento selecionado) { this.selecionado = selecionado; }
}
