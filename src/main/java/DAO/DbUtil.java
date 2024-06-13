package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.io.*;

public class DbUtil {

    public static final String CREATE_TABLES_SQL = "/create_tables.sql";
    public static final String FILL_PLAYERS_TABLE_SQL = "/fill_players_table.sql";
    public static final String FILL_MATCHES_TABLE_SQL = "/fill_matches_table.sql";

    private static SessionFactory sessionFactory;

    protected static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    protected static void createAndFillTables() throws IOException {
        try (Session session = sessionFactory.openSession()) {
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
}
