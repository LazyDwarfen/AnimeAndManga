package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Anime;
import models.Anime;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.Hibernate;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.Stateless;

@Controller
public class AnimeController {
    private final String actionParam = "action";
    private final String create = "create";
    private final String update = "update";
    private final String delete = "delete";

    private final String okStatus = "OK";
    private final String errorStatus = "error";

    private final String result = "result";

    private final String tabId = "anime_id";
    private final String tabName = "Anime";
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
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
    }

    @RequestMapping(value = "/Anime", method = RequestMethod.GET)
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        final ObjectMapper mappper = new ObjectMapper();
        setAccessControlHeaders(resp);
        List<Anime> data = null;
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            status = okStatus;
            switch (validGetParameter(req, actionParam)) {
                case "all": {
                    data = session.createQuery("from " + tabName, Anime.class).list();
                    break;
                }
                case "byID":{
                    long id = Long.parseLong(validGetParameter(req, "id"));
                    String query = String.format("from %s where id=:%s",
                            tabName,  tabId);

                    data = session
                            .createQuery(query, Anime.class)
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

    @RequestMapping(value = "/Anime", method = RequestMethod.POST)
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        String status = okStatus;
        final ObjectMapper mappper = new ObjectMapper();
        Anime model = null;
        try {
            model = mappper.readValue(req.getInputStream(),Anime.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;

            switch (validGetParameter(req, actionParam)) {
                case create: {
                    transaction = session.beginTransaction();
                    session.save(model);
                    transaction.commit();
                    break;
                }
                case delete: {
                    long id = model.getId();
                    String query = String.format("from %s where id=:%s",
                            tabName, tabId);

                    List<Anime> animes = session
                            .createQuery(query, Anime.class)
                            .setParameter(tabId, id)
                            .list();
                    transaction = session.beginTransaction();
                    animes.forEach(session::delete);
                    if(animes.size()<1)
                        status="this object was not found";
                    transaction.commit();
                    break;
                }
                case update: {
                    long id = model.getId();

                    String query = String.format("from %s where id=:%s",
                            tabName, tabId);

                    List<Anime> animes = session
                            .createQuery(query, Anime.class)
                            .setParameter(tabId, id)
                            .list();
                    transaction = session.beginTransaction();
                    Anime finalModel = model;
                    animes.forEach(elem -> {
                        elem.setName(finalModel.getName());
                        elem.setGenre(finalModel.getGenre());
                        elem.setOngoing(finalModel.isOngoing());
                        elem.setPicURL(finalModel.getPicURL());
                        session.update(elem);
                    });
                    if(animes.size()<1)
                        status="this object was not found";
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
            resp.getWriter().write("{\"status\":\""+status+"\"}");
        }
    }
}
