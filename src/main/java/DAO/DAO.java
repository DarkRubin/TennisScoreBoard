package DAO;

import model.FinishedMatch;
import model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.SelectionQuery;

import java.io.*;
import java.util.List;
import java.util.Optional;


public class DAO {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    public static final String CREATE_TABLES_SQL = "/create_tables.sql";
    public static final String FILL_PLAYERS_TABLE_SQL = "/fill_players_table.sql";
    public static final String FILL_MATCHES_TABLE_SQL = "/fill_matches_table.sql";

    static {
        try {
            createAndFillTables();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createAndFillTables() throws IOException {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            readFile(CREATE_TABLES_SQL, session);
            readFile(FILL_PLAYERS_TABLE_SQL, session);
            readFile(FILL_MATCHES_TABLE_SQL, session);
            session.getTransaction().commit();
        }
    }

    private static void readFile(String filePath, Session session) throws IOException {
        try (InputStream inputStream = DAO.class.getResourceAsStream(filePath)) {
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    NativeQuery<Void> query = session.createNativeQuery(line, void.class);
                    query.executeUpdate();
                }
            } else {
                throw new FileNotFoundException();
            }
        }
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
            return session.createSelectionQuery("from FinishedMatch", FinishedMatch.class).getResultList();
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
            Player player = new Player(name);
            session.persist(player);
            return player;
        }
    }

    public List<FinishedMatch> findPlayerMatches(Player player) {
        try (Session session = SESSION_FACTORY.openSession()) {
            SelectionQuery<FinishedMatch> query = session.createSelectionQuery("from FinishedMatch where player1 = :player or player2 = :player", FinishedMatch.class);
            return query.setParameter("player", player).getResultList();
        }
    }
}


