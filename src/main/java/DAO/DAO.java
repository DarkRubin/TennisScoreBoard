package DAO;

import model.Match;
import model.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class DAO {

    static {
        new Configuration().configure();
    }


    public void save(Model model) {
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {

            session.persist(model);
        }
    }

    public List<Match> findAllMatches() {
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            return session.createSelectionQuery("from Match order by id DESC", Match.class).getResultList();
        }
    }

}
