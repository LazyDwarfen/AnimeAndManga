package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Anime;
import models.Anime;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.Hibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@RestController
@RequestMapping("/Anime")
public class AnimeController {

    private final String tabId = "anime_id";
    private final String tabName = "Anime";

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
        final ObjectMapper mappper = new ObjectMapper();
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            List<Anime> animes = session.createQuery("from " + tabName, Anime.class).list();
            try {
                out.println(mappper.writeValueAsString(animes));
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/get")
    protected void doGetId(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        final ObjectMapper mappper = new ObjectMapper();
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            long id = Long.parseLong(validGetParameter(req, "id"));
            String query = String.format("from %s where id=:%s",
                    tabName,  tabId);

            List<Anime> animes = session
                    .createQuery(query, Anime.class)
                    .setParameter(tabId, id)
                    .list();
            transaction = session.beginTransaction();
            try {
                out.println(mappper.writeValueAsString(animes));
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            transaction.commit();
        }
    }
    @GetMapping("/getXML")
    protected void doGetIdXML(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        final ObjectMapper mappper = new ObjectMapper();
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            long id = Long.parseLong(validGetParameter(req, "id"));
            String query = String.format("from %s where id=:%s",
                    tabName,  tabId);

            List<Anime> animes = session
                    .createQuery(query, Anime.class)
                    .setParameter(tabId, id)
                    .list();
            transaction = session.beginTransaction();
            StringWriter xml = new StringWriter();
            try {
                //---------------------------------------------------------
                JAXBContext contextObj = JAXBContext.newInstance(Anime.class);

                Marshaller marshallerObj = contextObj.createMarshaller();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                marshallerObj.marshal(animes.get(0), xml);
                //---------------------------------------------------------
                out.println("<?xml-stylesheet type='text/xsl' href='/resources/xsls/animes.xsl'>\n"+xml.toString());
            }
            catch (JAXBException e) {
                e.printStackTrace();
            }
            transaction.commit();
        }
    }

    @PostMapping("/delete")
    protected void delete(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            long id = Long.parseLong(validGetParameter(req, "id"));
            String query = String.format("from %s where id=:%s",
                    tabName, tabId);

            List<Anime> animes = session
                    .createQuery(query, Anime.class)
                    .setParameter(tabId, id)
                    .list();
            transaction = session.beginTransaction();
            animes.forEach(session::delete);
            transaction.commit();
        }
    }

    @PostMapping("/create")
    protected void create(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            String name = validGetParameter(req, "name");
            String genre = validGetParameter(req, "genre");
            String picURL = validGetParameter(req, "picURL");
            boolean ongoing = Boolean.parseBoolean(req.getParameter("ongoing"));

            transaction = session.beginTransaction();
            session.save(new Anime(name, genre, ongoing, picURL));
            transaction.commit();
        }
    }

    @PostMapping("/update")
    protected void update(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            long id = Long.parseLong(validGetParameter(req, "id"));
            String name = validGetParameter(req, "name");
            String genre = validGetParameter(req, "genre");
            String picURL = validGetParameter(req, "picURL");
            long AnimeId = Long.parseLong(validGetParameter(req, "Animeid"));
            boolean ongoing = Boolean.parseBoolean(req.getParameter("ongoing"));

            String query = String.format("from %s where id=:%s", tabName, tabId);

            List<Anime> studios = session
                    .createQuery(query, Anime.class)
                    .setParameter(tabId, id)
                    .list();
            transaction = session.beginTransaction();
            studios.forEach(elem -> {
                elem.setName(name);
                elem.setGenre(genre);
                elem.setOngoing(ongoing);
                elem.setPicURL(picURL);
                session.update(elem);
            });
            transaction.commit();
        }
    }



    /*@RequestMapping(value = "/Anime", method = RequestMethod.GET)
    protected void doGet1(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        final ObjectMapper mappper = new ObjectMapper();
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            status = okStatus;
            switch (validGetParameter(req, actionParam)) {
                case "all": {
                    List<Anime> animes = session.createQuery("from " + tabName, Anime.class).list();
                   try {
                       out.println(mappper.writeValueAsString(animes));
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

                    List<Anime> animes = session
                            .createQuery(query, Anime.class)
                            .setParameter(tabId, id)
                            .list();
                    transaction = session.beginTransaction();
                    try {
                        out.println(mappper.writeValueAsString(animes));
                    }
                    catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
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
    }*/

    /*@RequestMapping(value = "/Anime", method = RequestMethod.POST)
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        String status = okStatus;
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;

            switch (validGetParameter(req, actionParam)) {
                case create: {
                    String name = validGetParameter(req, "name");
                    String genre = validGetParameter(req, "genre");
                    String picURL = validGetParameter(req, "picURL");
                    boolean ongoing = Boolean.parseBoolean(req.getParameter("ongoing"));

                    transaction = session.beginTransaction();
                    session.save(new Anime(name, genre, ongoing, picURL));
                    transaction.commit();
                    break;
                }
                case delete: {
                    long id = Long.parseLong(validGetParameter(req, "id"));
                    String query = String.format("from %s where id=:%s",
                            tabName, tabId);

                    List<Anime> animes = session
                            .createQuery(query, Anime.class)
                            .setParameter(tabId, id)
                            .list();
                    transaction = session.beginTransaction();
                    animes.forEach(session::delete);
                    transaction.commit();
                    break;
                }
                case update: {
                    long id = Long.parseLong(validGetParameter(req, "id"));
                    String name = validGetParameter(req, "name");
                    String genre = validGetParameter(req, "genre");
                    String picURL = validGetParameter(req, "picURL");
                    long AnimeId = Long.parseLong(validGetParameter(req, "Animeid"));
                    boolean ongoing = Boolean.parseBoolean(req.getParameter("ongoing"));

                    String query = String.format("from %s where id=:%s",
                            tabName, tabId);

                    List<Anime> studios = session
                            .createQuery(query, Anime.class)
                            .setParameter(tabId, id)
                            .list();
                    transaction = session.beginTransaction();
                    studios.forEach(elem -> {
                        elem.setName(name);
                        elem.setGenre(genre);
                        elem.setOngoing(ongoing);
                        elem.setPicURL(picURL);
                        session.update(elem);
                    });
                    transaction.commit();
                    break;
                }
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            status = errorStatus;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            req.setAttribute(result, status);
        }
    }*/
}

