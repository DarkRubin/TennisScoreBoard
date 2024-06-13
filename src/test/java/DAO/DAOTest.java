package DAO;

import model.FinishedMatch;
import model.Player;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class DAOTest {

    private final DAO dao = DAO.getInstance();

    @Test
    void findPlayer() {
        Optional<Player> anton = dao.findPlayer("Anton");
        assert anton.isPresent();
    }

    @Test
    void saveMatch() {
        Player player1 = new Player("Kosta");
        Player player2 = new Player("Anna");
        dao.saveMatch(new FinishedMatch(player1, player2, player1));
    }
}