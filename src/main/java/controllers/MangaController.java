package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Anime;
import models.Manga;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.Hibernate;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RestController
@RequestMapping("/Manga")
public class MangaController {

    private final String okStatus = "OK";
    private final String errorStatus = "error";

    private final String result = "result";

    private final String tabId = "manga_id";
    private final String tabName = "Manga";

    private String status;

    private String validGetParameter(final HttpServletRequest req, String paramName){
        String result = req.getParameter(paramName);
        if (result == null) {
            throw new NullPointerException("no parameter: " + paramName);
        }
        return result;
    }


    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
    }

    @GetMapping("/getall")
    protected void doGetAll(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        setAccessControlHeaders(resp);
        final ObjectMapper mappper = new ObjectMapper();
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            List< Manga > mangas = session.createQuery("from "+tabName, Manga.class).list();
            try {
                out.println(mappper.writeValueAsString(mangas));
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/get")
    protected void doGetId(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        setAccessControlHeaders(resp);
        final ObjectMapper mappper = new ObjectMapper();
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            long id = Long.parseLong(validGetParameter(req, "id"));
            String query = String.format("from %s where id=:%s",
                    tabName,  tabId);

            List<Manga> mangas = session
                    .createQuery(query, Manga.class)
                    .setParameter(tabId, id)
                    .list();
            transaction = session.beginTransaction();
            mangas.forEach(elem -> {
                try {
                    out.println(
                            mappper.writeValueAsString(elem)
                    );
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            transaction.commit();
        }
    }

    @GetMapping("/getXML")
    protected void doGetIdXml(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        setAccessControlHeaders(resp);
        final ObjectMapper mappper = new ObjectMapper();
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            long id = Long.parseLong(validGetParameter(req, "id"));
            String query = String.format("from %s where id=:%s",
                    tabName,  tabId);

            List<Manga> mangas = session
                    .createQuery(query, Manga.class)
                    .setParameter(tabId, id)
                    .list();
            transaction = session.beginTransaction();
            StringWriter xml = new StringWriter();
            try {
                //---------------------------------------------------------
                JAXBContext contextObj = JAXBContext.newInstance(Anime.class);

                Marshaller marshallerObj = contextObj.createMarshaller();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                marshallerObj.marshal(mangas.get(0), xml);
                //---------------------------------------------------------
                out.println("<?xml-stylesheet type='text/xsl' href='/resources/xsls/mangas.xsl'>\n"+xml.toString());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            transaction.commit();
        }
    }

    @PostMapping("/create")
    protected void doCreate(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        setAccessControlHeaders(resp);
        final ObjectMapper mappper = new ObjectMapper();
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            try {
                String name = validGetParameter(req,"name");
                String genre = validGetParameter(req,"genre");
                String author = validGetParameter(req, "author");
                String picURL = validGetParameter(req,"picURL");
                transaction = session.beginTransaction();
                session.save(new Manga(name, genre, author, picURL));
                transaction.commit();
                status = okStatus;
            } catch (NullPointerException e){
                e.printStackTrace();
                status = errorStatus;
            } finally {
                req.setAttribute(result, status);
            }
        }
    }
    @PostMapping("/update")
    protected void doUpdate(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            try{
                long id = Long.parseLong(validGetParameter(req, "id"));
                String name = validGetParameter(req, "name");
                String query = String.format("from %s where id=:%s",
                        tabName, tabId);
                List<Anime> studios = session.createQuery(query, Anime.class)
                        .setParameter(tabId, id)
                        .list();
                transaction = session.beginTransaction();
                studios.forEach(elem -> {
                    elem.setName(name);
                    session.update(elem);
                });
                transaction.commit();
            }catch (NullPointerException e){
                e.printStackTrace();
                status = errorStatus;
            } finally {
                req.setAttribute(result,status);
            }
        }
    }
    @PostMapping("/delete")
    protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        setAccessControlHeaders(resp);
        final ObjectMapper mappper = new ObjectMapper();
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            try{
                long id = Long.parseLong(validGetParameter(req, "id"));
                String query = String.format("from %s where id=:%s",
                        tabName,  tabId);

                List<Manga> animes = session
                        .createQuery(query, Manga.class)
                        .setParameter("anime_id", id)
                        .list();
                transaction = session.beginTransaction();
                animes.forEach(session::delete);
                transaction.commit();
                status = okStatus;
            } catch (NullPointerException e){
                e.printStackTrace();
                status = errorStatus;
            } finally {
                req.setAttribute(result, status);
            }
        }
    }


    /*@RequestMapping(value = "/Manga", method = RequestMethod.GET)
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        setAccessControlHeaders(resp);
        final ObjectMapper mappper = new ObjectMapper();
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            status = okStatus;
            switch (validGetParameter(req, actionParam)) {
                case "all": {
                    List< Manga > mangas = session.createQuery("from "+tabName, Manga.class).list();
                    try {
                        out.println(mappper.writeValueAsString(mangas));
                    }
                    catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "byID":{
                    long id = Long.parseLong(validGetParameter(req, "id"));
                    String query = String.format("from %s where id=:%s",
                            tabName,  tabId);

                    List<Manga> mangas = session
                            .createQuery(query, Manga.class)
                            .setParameter(tabId, id)
                            .list();
                    transaction = session.beginTransaction();
                    mangas.forEach(elem -> {
                        try {
                            out.println(
                                    mappper.writeValueAsString(elem)
                            );
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    });
                    transaction.commit();
                    status = okStatus;
                    break;
                }
            }
        }catch (NullPointerException e) {
            status=errorStatus;
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            req.setAttribute(result, status);
        }
    }

    @RequestMapping(value = "/Manga", method = RequestMethod.POST)
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            setAccessControlHeaders(resp);
            Transaction transaction;
            switch (validGetParameter(req, actionParam)) {
                case create: {
                    try {
                        String name = validGetParameter(req,"name");
                        String genre = validGetParameter(req,"genre");
                        String author = validGetParameter(req, "author");
                        String picURL = validGetParameter(req,"picURL");
                        transaction = session.beginTransaction();
                        session.save(new Manga(name, genre, author, picURL));
                        transaction.commit();
                        status = okStatus;
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        status = errorStatus;
                    } finally {
                        req.setAttribute(result, status);
                    }
                    break;
                }

                case delete: {
                    try{
                        long id = Long.parseLong(validGetParameter(req, "id"));
                        String query = String.format("from %s where id=:%s",
                                tabName,  tabId);

                        List<Manga> animes = session
                                .createQuery(query, Manga.class)
                                .setParameter("anime_id", id)
                                .list();
                        transaction = session.beginTransaction();
                        animes.forEach(session::delete);
                        transaction.commit();
                        status = okStatus;
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        status = errorStatus;
                    } finally {
                        req.setAttribute(result, status);
                    }
                    break;
                }
                case update: {

                    try{
                        long id = Long.parseLong(validGetParameter(req, "id"));
                        String name = validGetParameter(req, "name");


                        String query = String.format("from %s where id=:%s",
                                tabName, tabId);

                        List<Anime> studios = session
                                .createQuery(query, Anime.class)
                                .setParameter(tabId, id)
                                .list();
                        transaction = session.beginTransaction();
                        studios.forEach(elem -> {
                            elem.setName(name);
                            session.update(elem);
                        });
                        transaction.commit();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                        status = errorStatus;
                    } finally {
                        req.setAttribute(result,status);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

