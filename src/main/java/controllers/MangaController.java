package controllers;

import models.Manga;
import services.MangaService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
@Stateless
public class MangaController extends AbstractController<MangaService, Manga> {
    @EJB
    private
    MangaService service;

    @Override
    protected MangaService getService(){return this.service;}

    @Override
    protected Class<? extends Manga> getMetaClass() {
        return Manga.class;
    }
}

