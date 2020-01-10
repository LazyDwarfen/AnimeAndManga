package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Anime;
import models.Manga;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.Hibernate;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MangaController {

    private final String actionParam = "action";
    private final String create = "create";
    private final String update = "update";
    private final String delete = "delete";

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


    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
    }
    @RequestMapping(value = "/Manga", method = RequestMethod.GET)
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        setAccessControlHeaders(resp);
        final ObjectMapper mappper = new ObjectMapper();
        List<Manga> data = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            status = okStatus;
            switch (validGetParameter(req, actionParam)) {
                case "all": {
                    data = session.createQuery("from "+tabName, Manga.class).list();
                    break;
                }
                case "byID":{
                    long id = Long.parseLong(validGetParameter(req, "id"));
                    String query = String.format("from %s where id=:%s",
                            tabName,  tabId);

                    data = session
                            .createQuery(query, Manga.class)
                            .setParameter(tabId, id)
                            .list();
                    transaction = session.beginTransaction();
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
            if(data != null){
                out.println("{\"status\":\""+status+"\", \"data\":"+ mappper.writeValueAsString(data) +"}");
            }
            else
                out.println("{\"status\":\""+status+"\"}");
        }
    }

    @RequestMapping(value = "/Manga", method = RequestMethod.POST)
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            setAccessControlHeaders(resp);
            Transaction transaction;
            final ObjectMapper mappper = new ObjectMapper();
            Manga model = null;
            try {
                model = mappper.readValue(req.getInputStream(),Manga.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (validGetParameter(req, actionParam)) {
                case create: {
                    try {
                        transaction = session.beginTransaction();
                        session.save(model);
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
                        long id = model.getId();
                        String query = String.format("from %s where id=:%s",
                                tabName,  tabId);

                        List<Manga> mangas = session
                                .createQuery(query, Manga.class)
                                .setParameter("manga_id", id)
                                .list();
                        transaction = session.beginTransaction();
                        mangas.forEach(session::delete);
                        transaction.commit();
                        status = okStatus;
                    } catch (NullPointerException e){
                        e.printStackTrace();
                        status = errorStatus;
                    }catch (PersistenceException e){
                        status = "Error: object is linked to other object";
                    }
                    break;
                }
                case update: {

                    try{
                        long id = model.getId();
                        String query = String.format("from %s where id=:%s",
                                tabName, tabId);

                        List<Manga> mangas = session
                                .createQuery(query, Manga.class)
                                .setParameter(tabId, id)
                                .list();
                        transaction = session.beginTransaction();
                        Manga finalModel = model;
                        mangas.forEach(elem -> {
                            elem.setName(finalModel.getName());
                            elem.setAuthor(finalModel.getAuthor());
                            elem.setGenre(finalModel.getGenre());
                            elem.setPicURL(finalModel.getPicURL());
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
        } finally {
            resp.getWriter().write("{\"status\":\""+status+"\"}");
        }
    }
}
