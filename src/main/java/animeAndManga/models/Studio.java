package animeAndManga.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "studio")
public class Studio {

    @XmlAttribute
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio_id")
    private Long id;

    @XmlElement
    @Column(name = "name")
    private String name;

    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studio")
    @JsonIgnore
    private List<Anime> animes;

    public Studio(){}

    public Studio(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Anime> getAnimes() {
        return animes;
    }
}
