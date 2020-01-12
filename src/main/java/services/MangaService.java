package services;

import dao.JPAManga;
import models.Manga;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class MangaService extends AbstractService<Manga, JPAManga> {
    @EJB
    private
    JPAManga jpaManga;

    public void create(Manga Manga){
        jpaManga.create(Manga);
    }
    public void update(Manga Manga){
        jpaManga.update(Manga);
    }
    public void delete(Manga manga){
        jpaManga.delete(manga.getId());
    }
    public List<Manga> getAll(){
        return jpaManga.getAll();
    }
    public Manga getById(Manga manga){
        return jpaManga.getById(manga.getId());
    }
}
