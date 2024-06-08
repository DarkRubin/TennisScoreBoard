package DAO;

import model.FinishedMatch;
import model.Player;
import org.junit.jupiter.api.Test;

class DAOTest {

    private final DAO dao = new DAO();

    @Test
    void findPlayerOrSavePlayer() {
        Player anton = dao.findOrSavePlayer("Anton");
        System.out.println(anton);
    }

    @Test
    void saveMatch() {
        Player player1 = new Player("Kosta");
        Player player2 = new Player("Anna");
        dao.saveMatch(new FinishedMatch(player1, player2, player1));
    }
}