package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import services.AbstractService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractController<ModelService extends AbstractService, Model> extends HttpServlet {


    private final String actionParam = "action";
    private final String create = "create";
    private final String update = "update";
    private final String delete = "delete";

    private final String idParam = "id";

    private final String okStatus = "OK";
    private final String errorStatus = "error";
    private final String parsingErrorStatus = "error occured while parsing";

    private Long getId(HttpServletRequest req) {
        String idString = req.getParameter(idParam);
        if (idString == null)
            return null;
        else {
            try {
                return Long.parseLong(idString);
            } catch (Exception e) {
                return null;
            }
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
    }

    private String getAction(HttpServletRequest req) {
        return req.getParameter(actionParam);
    }

    private Model parseObject(HttpServletRequest req) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(req.getInputStream(), this.getMetaClass());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract Class<? extends Model> getMetaClass();

    protected abstract ModelService getService();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        final ObjectMapper mapper = new ObjectMapper();
        String action = req.getParameter("action");
        Object data = null;
        String status = okStatus;
        setAccessControlHeaders(resp);
        if (action != null) {
            switch (action) {
                case "getAll": {
                    data = getService().getAll();
                    break;
                }
                case "getById": {
                    Model model = parseObject(req);
                    if (model == null)
                        status = parsingErrorStatus;
                    else
                        data = getService().getById(data);
                    break;
                }
            }
        }
        if (data != null) {
            out.println("{\"status\":\"" + status + "\", \"data\":" + mapper.writeValueAsString(data) + "}");
        } else
            out.println("{\"status\":\"" + (status.equals(okStatus) ? errorStatus : status) + "\"}");
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String action = getAction(req);
        setAccessControlHeaders(resp);
        Model data = parseObject(req);
        if (data == null)
            out.println("{\"status\":\"" + parsingErrorStatus + "\"}");
        else {
            if (action != null) {
                switch (action) {
                    case create: {
                        getService().create(data);
                        break;
                    }
                    case update: {
                        getService().update(data);
                        break;
                    }
                    case delete: {
                        getService().delete(data);
                        break;
                    }
                }
                out.println("{\"status\":\"" + okStatus + "\"}");
            } else
                out.println("{\"status\":\"" + parsingErrorStatus + "\"}");
        }
    }
}
