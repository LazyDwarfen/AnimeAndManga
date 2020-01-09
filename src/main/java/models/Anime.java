package models;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "anime")
public class Anime {
    @XmlAttribute
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anime_id")
    private long id;

    @XmlElement
    @Column(name = "name")
    private String name;

    @XmlElement
    @Column(name = "genre")
    private String genre;

    @XmlElement
    @Column(name = "ongoing")
    private boolean ongoing;

    @XmlElement
    @Column (name = "pic_url")
    private String picURL;

    @XmlElement
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @XmlElement
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    public Anime() {
    }
    public Anime(String name, String genre, boolean ongoing, String picURL) {
        this.name = name;
        this.genre = genre;
        this.ongoing = ongoing;
        this.picURL = picURL;
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
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    @XmlTransient
    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }
    @XmlTransient
    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }
    @XmlTransient
    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }
    @XmlTransient
    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}