package animeAndManga.services;

import animeAndManga.dao.JPAManga;
import animeAndManga.models.Manga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MangaService {

    @Autowired
    private JPAManga jpaDao;
    @Transactional
    public void create(Manga Manga){
        jpaDao.save(Manga);
    }
    @Transactional
    public void update(Manga Manga){
        jpaDao.save(Manga);
    }
    @Transactional
    public void delete(long id){
        jpaDao.delete(jpaDao.findById(id).get());
    }
    public List<Manga> getAll(){
        return jpaDao.findAll();
    }
    public Manga getById(long id){
        return jpaDao.findById(id).get();
    }
}
