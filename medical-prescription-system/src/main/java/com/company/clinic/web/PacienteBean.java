    package com.company.clinic.web;

    import com.company.clinic.domain.Paciente;
    import com.company.clinic.service.PacienteService;
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
    import java.util.List;
    import java.util.Map;

    @Named
    @ViewScoped
    public class PacienteBean implements Serializable {
        private static final long serialVersionUID = 1L;

        @Inject
        private PacienteService service;

        private String filtro;
        private LazyDataModel<Paciente> model;
        private Paciente selecionado = new Paciente();

        @PostConstruct
        public void init() {
            model = new LazyDataModel<Paciente>() {
                private static final long serialVersionUID = 1L;

                @Override
                public List<Paciente> load(int first, int pageSize,
                                           Map<String, SortMeta> sortBy,
                                           Map<String, FilterMeta> filterBy) {
                    setRowCount((int) service.count(filtro));
                    return service.search(filtro, first, pageSize);
                }
            };
        }

        public void novo() { selecionado = new Paciente(); }

        public void editar(Paciente p) {
            selecionado = service.find(p.getId());
            if (selecionado == null) {
                selecionado = new Paciente();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Registro não encontrado."));
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
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível salvar: " + e.getMessage()));
            }
        }

        public void excluir(Paciente p) {
            try {
                service.delete(p);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Registro excluído."));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir: " + e.getMessage()));
            }
        }

        public String getFiltro() { return filtro; }
        public void setFiltro(String filtro) { this.filtro = filtro; }
        public LazyDataModel<Paciente> getModel() { return model; }
        public Paciente getSelecionado() { return selecionado; }
        public void setSelecionado(Paciente selecionado) { this.selecionado = selecionado; }
    }
