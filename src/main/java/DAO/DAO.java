package DAO;

import jakarta.persistence.NoResultException;
import model.FinishedMatch;
import model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class DAO {

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("SUCCESS");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR");
            throw new RuntimeException(e);
        }
    }

    private static SessionFactory buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }


    public void saveMatch(FinishedMatch math) {
        try (SessionFactory sessionFactory = buildSessionFactory();
            Session session = sessionFactory.openSession()) {

            session.persist("FinishedMatch", math);
        }
    }

    public List<FinishedMatch> findAllMatches() {
        try (SessionFactory sessionFactory = buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            return session.createSelectionQuery("from FinishedMatch order by id DESC", FinishedMatch.class).getResultList();
        }
    }

    public Player findOrSavePlayer(String playerName) {
        try (SessionFactory sessionFactory = buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query<Player> query = session.createQuery("select p from Player p where p.name = :name", Player.class);
            query.setParameter("name", playerName);
            Player player;
            try {
                player = query.getSingleResult();
            } catch (NoResultException e) {
                player = null;
            }

            if (player == null) {
                player = new Player(playerName);
                session.persist("Player", player);
            }

            session.getTransaction().commit();
            return player;
        }
    }
}
