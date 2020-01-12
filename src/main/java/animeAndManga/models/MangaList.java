package animeAndManga.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MangaList {
    public List<Manga> getMangas() {
        return manga;
    }

    public void setMangas(List<Manga> Mangas) {
        this.manga = Mangas;
    }

    public MangaList(List<Manga> Mangas) {
        this.manga = Mangas;
    }
    public MangaList(){}

    @XmlElement
    private List<Manga> manga;
}
