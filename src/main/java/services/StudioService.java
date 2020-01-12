package services;

import dao.JPAStudio;
import models.Studio;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class StudioService extends AbstractService<Studio, JPAStudio> {
    @EJB
    private
    JPAStudio jpaStudio;

    public void create(Studio studio){
        jpaStudio.create(studio);
    }
    public void update(Studio studio){
        jpaStudio.update(studio);
    }
    public void delete(Studio studio){
        jpaStudio.delete(studio.getId());
    }
    public List<Studio> getAll(){
        return jpaStudio.getAll();
    }
    public Studio getById(Studio studio){
        return jpaStudio.getById(studio.getId());
    }
}
