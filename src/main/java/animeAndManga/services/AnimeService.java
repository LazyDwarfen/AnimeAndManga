package animeAndManga.services;

import animeAndManga.dao.JPAAnime;
import animeAndManga.models.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnimeService {

    @Autowired
    private JPAAnime jpaDao;

    @Transactional
    public void create(Anime Anime){
        jpaDao.save(Anime);
    }
    @Transactional
    public void update(Anime Anime){
        jpaDao.save(Anime);
    }
    @Transactional
    public void delete(long id){
        jpaDao.delete(jpaDao.findById(id).get());
    }
    public List<Anime> getAll(){
        return jpaDao.findAll();
    }
    public Anime getById(long id){
        return jpaDao.findById(id).get();
    }
}
