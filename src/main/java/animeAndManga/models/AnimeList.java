package animeAndManga.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimeList {
    public List<Anime> getAnimes() {
        return anime;
    }

    public void setAnimes(List<Anime> animes) {
        this.anime = animes;
    }

    public AnimeList(List<Anime> animes) {
        this.anime = animes;
    }
    public AnimeList(){}

    @XmlElement
    private List<Anime> anime;
}
