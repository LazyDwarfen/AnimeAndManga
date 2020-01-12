package animeAndManga.controllers;

import animeAndManga.models.Manga;
import animeAndManga.models.MangaList;
import animeAndManga.services.MangaService;
import animeAndManga.utils.ResponseDataCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/Manga")
public class MangaController {

    private final MangaService service;

    @Autowired
    public MangaController(MangaService service){
        this.service = service;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDataCase> getAll() {
        return new ResponseEntity<>(new ResponseDataCase(service.getAll(),"OK"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDataCase> getById(@PathVariable("id") long id){
        return new ResponseEntity<>(new ResponseDataCase(service.getById(id),"OK"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDataCase> create(@RequestBody Manga Manga){
        service.create(Manga);
        return new ResponseEntity<>(new ResponseDataCase(null,"OK"),HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDataCase> delete(@PathVariable("id") long id){
        service.delete(id);
        return new ResponseEntity<>(new ResponseDataCase(null,"OK"),HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDataCase> update(@RequestBody  Manga Manga){
        service.update(Manga);
        return new ResponseEntity<>(new ResponseDataCase(null,"OK"),HttpStatus.OK);
    }
}

