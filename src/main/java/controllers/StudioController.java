package controllers;

import models.Studio;
import services.StudioService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
@Stateless
public class StudioController extends AbstractController<StudioService, Studio> {
    @EJB
    private
    StudioService service;

    @Override
    protected StudioService getService(){return this.service;}

    @Override
    protected Class<? extends Studio> getMetaClass() {
        return Studio.class;
    }
}

