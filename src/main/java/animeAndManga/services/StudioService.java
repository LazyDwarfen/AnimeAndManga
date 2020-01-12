package animeAndManga.services;

import animeAndManga.dao.JPAStudio;
import animeAndManga.models.Studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudioService {

    @Autowired
    private JPAStudio jpaDao;
    @Transactional
    public void create(Studio Studio){
        jpaDao.save(Studio);
    }
    @Transactional
    public void update(Studio Studio){
        jpaDao.save(Studio);
    }
    @Transactional
    public void delete(long id){
        jpaDao.delete(jpaDao.findById(id).get());
    }
    public List<Studio> getAll(){
        return jpaDao.findAll();
    }
    public Studio getById(long id){
        return jpaDao.findById(id).get();
    }
}
