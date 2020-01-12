package controllers;

import models.Anime;
import services.AnimeService;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AnimeController extends AbstractController<AnimeService, Anime> {
    @EJB
    private
    AnimeService service;

    @Override
    protected AnimeService getService(){return this.service;}

    @Override
    protected Class<? extends Anime> getMetaClass() {
        return Anime.class;
    }
}

