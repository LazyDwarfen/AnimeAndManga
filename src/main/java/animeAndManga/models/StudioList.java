package animeAndManga.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StudioList {
    public List<Studio> getStudios() {
        return studio;
    }

    public void setStudios(List<Studio> Studios) {
        this.studio = Studios;
    }

    public StudioList(List<Studio> Studios) {
        this.studio = Studios;
    }
    public StudioList(){}

    @XmlElement
    private List<Studio> studio;
}
