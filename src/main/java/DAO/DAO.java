package DAO;

import model.FinishedMatch;
import model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.SelectionQuery;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



public class DAO {

    private static DAO instance;
    private static final SessionFactory SESSION_FACTORY = DbUtil.getSessionFactory();

    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
            try {
                DbUtil.createAndFillTables();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public void saveMatch(FinishedMatch math) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            session.persist("FinishedMatch", session.merge(math));
            session.getTransaction().commit();
        }
    }

    public List<FinishedMatch> findMatches() {
        try (Session session = SESSION_FACTORY.openSession()) {
            return session.createSelectionQuery("from FinishedMatch order by id DESC ", FinishedMatch.class).getResultList();
        }
    }

    public Optional<Player> findPlayer(String playerName) {
        try (Session session = SESSION_FACTORY.openSession()) {
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
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            Player player = new Player();
            player.setName(name);
            session.persist(player);
            session.getTransaction().commit();
            return player;
        }
    }

    public List<FinishedMatch> findPlayerMatches(Player player) {
        try (Session session = SESSION_FACTORY.openSession()) {
            SelectionQuery<FinishedMatch> query = session
                    .createSelectionQuery("from FinishedMatch where player1 = :player or player2 = :player", FinishedMatch.class);
            return query.setParameter("player", player).getResultList();
        }
    }
}


