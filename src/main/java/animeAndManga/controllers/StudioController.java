package animeAndManga.controllers;

import animeAndManga.models.Studio;
import animeAndManga.services.StudioService;
import animeAndManga.utils.ResponseDataCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Studio")
public class StudioController {

    private final StudioService service;

    @Autowired
    public StudioController(StudioService service){
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
    public ResponseEntity<ResponseDataCase> create(@RequestBody Studio Studio){
        service.create(Studio);
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
    public ResponseEntity<ResponseDataCase> update (@RequestBody Studio Studio){
        service.update(Studio);
        return new ResponseEntity<>(new ResponseDataCase(null,"OK"),HttpStatus.OK);
    }
}

