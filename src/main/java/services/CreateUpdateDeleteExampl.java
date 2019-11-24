import com.fasterxml.jackson.databind.ObjectMapper;
import models.Anime;
import models.Manga;
import models.Studio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.Hibernate;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class CreateUpdateDeleteExampl extends HttpServlet {
    ///
    ///блаблабла doGet
    ///
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        try (Session session = Hibernate.getSessionFactory().openSession()) {
            {
                final ObjectMapper mappper = new ObjectMapper();
                Transaction transaction = null;
                if (req.getParameter("action") != null) {
                    String action = req.getParameter("action");
                    switch (action) {
                        case "create": {
                            if (req.getParameter("name") != null) {
                                String name = req.getParameter("name");
                                transaction = session.beginTransaction(); // создаем транзакцию
                                session.save(new Studio(name)); //сохраняем
                                transaction.commit(); // и отправляем в БД
                                req.setAttribute("result", "OK");
                            } else
                                req.setAttribute("result", "error");
                            break;
                        }
                        case "delete": {
                            if (req.getParameter("id") != null) {
                                long id = req.getParameter("id");
                                List<Studio> studios = session
                                        .createQuery("from Studio where id=:studio_id", Studio.class) //SQL запрос к базе
                                        .setParameter("studio_id", id)
                                        .list();
                                transaction = session.beginTransaction(); // создаем транзакцию
                                studios.forEach(elem -> {
                                    session.delete(elem); //удаляем
                                });
                                transaction.commit(); // и отправляем в БД
                                req.setAttribute("result", "OK");
                            } else
                                req.setAttribute("result", "error");
                            break;
                        }
                        case "update": {
                            if (req.getParameter("id") != null &&
                                    req.getParameter("name") != null) {
                                long id = req.getParameter("id");
                                String name = req.getParameter("name");
                                List<Studio> studios = session
                                        .createQuery("from Studio where id=:studio_id", Studio.class) //SQL запрос к базе
                                        .setParameter("studio_id", id)
                                        .list();
                                transaction = session.beginTransaction(); // создаем транзакцию
                                studios.forEach(elem -> {
                                    elem.setName(name);
                                    session.update(elem);// меняем
                                });
                                transaction.commit(); // и отправляем в БД
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

