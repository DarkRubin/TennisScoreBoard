package DAO;

import model.FinishedMatch;
import model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.SelectionQuery;

import java.util.List;
import java.util.Optional;


public class DAO {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }

    public void saveMatch(FinishedMatch math) {
        try (SessionFactory sessionFactory = buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist("FinishedMatch", session.merge(math));
            session.getTransaction().commit();
        }
    }

    public List<FinishedMatch> findMatches() {
        try (SessionFactory sessionFactory = buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            return session.createSelectionQuery("from FinishedMatch order by id DESC", FinishedMatch.class).getResultList();
        }
    }

    public Optional<Player> findPlayer(String playerName) {
        try (SessionFactory sessionFactory = buildSessionFactory();
            Session session = sessionFactory.openSession()) {

            SelectionQuery<Player> query = session.createSelectionQuery("from Player where name = :name", Player.class);
            List<Player> player = query.setParameter("name", playerName).getResultList();
            try {
                return Optional.ofNullable(player.get(0));
            } catch (IndexOutOfBoundsException e) {
                return Optional.empty();
            }
        }
    }

    public Player savePlayer(String name) {
        try (SessionFactory sessionFactory = buildSessionFactory();
            Session session = sessionFactory.openSession()) {

            Player player = new Player(name);
            session.persist(player);

            return player;
        }
    }

    public List<FinishedMatch> findPlayerMatches(Player player) {
        try (SessionFactory sessionFactory = buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            SelectionQuery<FinishedMatch> query = session.createSelectionQuery("from FinishedMatch where player1 = :player or player2 = :player", FinishedMatch.class);
            return query.setParameter("player", player).getResultList();
        }

    }
}
