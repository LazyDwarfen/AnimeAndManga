package services;

import dao.JPAAnime;
import models.Anime;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class AnimeService extends AbstractService<Anime,JPAAnime>{
    @EJB
    private
    JPAAnime jpaAnime;

    public void create(Anime anime){
        jpaAnime.create(anime);
    }
    public void update(Anime anime){
        jpaAnime.update(anime);
    }
    public void delete(Anime anime){
        jpaAnime.delete(anime.getId());
    }
    public List<Anime> getAll(){
        return jpaAnime.getAll();
    }
    public Anime getById(Anime anime){
        return jpaAnime.getById(anime.getId());
    }
}
