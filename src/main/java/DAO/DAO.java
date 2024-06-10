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
import java.util.Scanner;


public class DAO {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    public static final String FILL_MATCHES_TABLE_SQL = "src/main/resources/fill_matches_table.sql";
    public static final String FILL_PLAYERS_TABLE_SQL = "src/main/resources/fill_players_table.sql";
    public static final String CREATE_TABLES_SQL = "src/main/resources/create_tables.sql";
    public static final String H2_DRIVER = "org.h2.Driver";

    static {
        try {
            Class.forName(H2_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        DAO dao = new DAO();
        createTables();
        fillTables();
        dao.findPlayer("Caroline").ifPresent(System.out::println);
    }

    private static void fillTables() throws FileNotFoundException {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();

            Scanner scanner = new Scanner(new File(FILL_PLAYERS_TABLE_SQL));
            readAndExecuteSQL(scanner, session);
            scanner = new Scanner(new File(FILL_MATCHES_TABLE_SQL));
            readAndExecuteSQL(scanner, session);
            session.getTransaction().commit();
        }
    }

    private static void readAndExecuteSQL(Scanner scanner, Session session) {
        while (scanner.hasNextLine()) {
            NativeQuery<Void> query = session.createNativeQuery(scanner.nextLine(), void.class);
            query.executeUpdate();
        }
    }

    public void saveMatch(FinishedMatch math) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            session.persist("FinishedMatch", session.merge(math));
            session.getTransaction().commit();
        }
    }

    public static void createTables() throws IOException {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();

            Scanner scanner = new Scanner(new File(CREATE_TABLES_SQL));
            readAndExecuteSQL(scanner, session);
            session.getTransaction().commit();
        }
    }

    public List<FinishedMatch> findMatches() {
        try (Session session = SESSION_FACTORY.openSession()) {
            return session.createSelectionQuery("from FinishedMatch order by id DESC", FinishedMatch.class).getResultList();
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
