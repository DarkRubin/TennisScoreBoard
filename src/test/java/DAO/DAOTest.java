package DAO;

import model.Player;
import org.junit.jupiter.api.Test;

class DAOTest {

    private final DAO dao = new DAO();

    @Test
    void findOrSavePlayerTest() {
        Player anton = dao.findOrSavePlayer("Anton");
        System.out.println(anton);
    }
}