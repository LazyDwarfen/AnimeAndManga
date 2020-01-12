package animeAndManga.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "anime")
public class Anime {
    @XmlAttribute
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anime_id")
    private Long id;

    @XmlElement
    @Column(name = "name")
    private String name;

    @XmlElement
    @Column(name = "genre")
    private String genre;

    @XmlElement
    @Column(name = "ongoing")
    private Boolean ongoing;

    @XmlElement
    @Column (name = "pic_url")
    private String picURL;

    @XmlTransient
    @ManyToOne(fetch = FetchType.EAGER) // previous type: LAZY
    @JoinColumn(name = "manga_id")
    @JsonIgnore
    private Manga manga;

    @XmlTransient
    @ManyToOne(fetch = FetchType.EAGER) // previous type: LAZY
    @JoinColumn(name = "studio_id")
    @JsonIgnore
    private Studio studio;

    public Anime() {
    }
    public Anime(String name, String genre, boolean ongoing, String picURL) {
        this.name = name;
        this.genre = genre;
        this.ongoing = ongoing;
        this.picURL = picURL;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}