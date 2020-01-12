package services;

import javax.ejb.Stateless;
import java.util.List;


public abstract class AbstractService<Model, JPAModel> {
    public abstract void create(Model model);
    public abstract void update(Model model);
    public abstract void delete(Model model);
    public abstract List<Model> getAll();
    public abstract Model getById(Model model);
}

