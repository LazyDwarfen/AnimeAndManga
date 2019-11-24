package models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "manga")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manga_id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "genre")
    private String genre;
    @Column(name = "author")
    private String author;
    @Column (name = "pic_url")
    private String picURL;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manga")
    @JsonIgnore
    private List<Anime> animes;

    public Manga() {
    }
    public Manga(String name, String genre, String author, String picURL) {
        this.name = name;
        this.genre = genre;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public List<Anime> getAnimes() {
        return animes;
    }

}
