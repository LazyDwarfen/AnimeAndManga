package animeAndManga.dao;

import animeAndManga.models.Studio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JPAStudio extends CrudRepository<Studio,Long>{
    List<Studio> findAll();
}
