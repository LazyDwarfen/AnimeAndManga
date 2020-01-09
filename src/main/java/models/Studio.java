package models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@XmlRootElement
@Entity
@Table(name = "studio")
public class Studio {

    @XmlAttribute
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio_id")
    private long id;

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
    @XmlTransient
    public long getId() {
        return id;
    }
    @XmlTransient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlTransient
    public List<Anime> getAnimes() {
        return animes;
    }
}
