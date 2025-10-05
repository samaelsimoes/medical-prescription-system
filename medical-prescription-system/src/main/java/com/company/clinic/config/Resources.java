package com.company.clinic.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class Resources {
    @Produces
    @PersistenceContext(unitName = "clinicPU")
    private EntityManager em;
}
