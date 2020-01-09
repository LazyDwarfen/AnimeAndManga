package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Studio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.Hibernate;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class StudioController {
    private final String actionParam = "action";
    private final String create = "create";
    private final String update = "update";
    private final String delete = "delete";

    private final String okStatus = "OK";
    private final String errorStatus = "error";

    private final String result = "result";

    private final String tabId = "studio_id";
    private final String tabName = "Studio";
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

    @RequestMapping(value = "/Studio",method = RequestMethod.GET)
    public void doGet(HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        setAccessControlHeaders(resp);
        final ObjectMapper mappper = new ObjectMapper();
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            Transaction transaction;
            status = okStatus;
            switch (validGetParameter(req, actionParam)) {
                case "all": {
                    List<Studio> studios = session.createQuery("from "+tabName, Studio.class).list();
                    try {

                        out.println(mappper.writeValueAsString(studios));
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

                    List<Studio> studios = session
                            .createQuery(query, Studio.class)
                            .setParameter(tabId, id)
                            .list();
                    transaction = session.beginTransaction();
                    try {
                        out.println(mappper.writeValueAsString(studios));
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
    }

    @RequestMapping(value = "/Studio",method = RequestMethod.POST)
    public void doPost(HttpServletRequest req, final HttpServletResponse resp) {
        setAccessControlHeaders(resp);
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            {
                Transaction transaction;
                if (req.getParameter("action") != null) {
                    String action = req.getParameter("action");
                    switch (action) {
                        case "create": {
                            if (req.getParameter("name") != null) {
                                String name = req.getParameter("name");
                                transaction = session.beginTransaction();
                                session.save(new Studio(name));
                                transaction.commit();
                                req.setAttribute("result", "OK");
                            } else
                                req.setAttribute("result", "error");
                            break;
                        }
                        case "delete": {
                            if (req.getParameter("id") != null) {
                                long id = Long.parseLong(req.getParameter("id"));
                                List<Studio> studios = session
                                        .createQuery("from Studio where id=:studio_id", Studio.class)
                                        .setParameter("studio_id", id)
                                        .list();
                                transaction = session.beginTransaction();
                                studios.forEach(session::delete);
                                transaction.commit();
                                req.setAttribute("result", "OK");
                            } else
                                req.setAttribute("result", "error");
                            break;
                        }
                        case "update": {
                            if (req.getParameter("id") != null &&
                                    req.getParameter("name") != null) {
                                long id = Long.parseLong(req.getParameter("id"));
                                String name = req.getParameter("name");
                                List<Studio> studios = session
                                        .createQuery("from Studio where id=:studio_id", Studio.class)
                                        .setParameter("studio_id", id)
                                        .list();
                                transaction = session.beginTransaction();
                                studios.forEach(elem -> {
                                    elem.setName(name);
                                    session.update(elem);
                                });
                                transaction.commit();
                                req.setAttribute("result", "OK");
                            } else
                                req.setAttribute("result", "error");
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

