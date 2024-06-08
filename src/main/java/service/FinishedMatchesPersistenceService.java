package service;

import DAO.DAO;
import DTO.FinishedMatchDTO;
import model.FinishedMatch;
import model.MatchScore;
import model.Player;
import exception.PlayerNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FinishedMatchesPersistenceService {

    public static final int PAGE_SIZE = 10;
    private final DAO dao = new DAO();

    public List<FinishedMatchDTO> findPlayerMatches(String playerName) {
        Player player;
        Optional<Player> findPlayer = dao.findPlayer(new Player(playerName));
        if (findPlayer.isPresent()) {
            player = findPlayer.get();
        } else {
            throw new PlayerNotFoundException();
        }
        List<FinishedMatch> finishedMatch = (List<FinishedMatch>) player.getFinishedMatch();
        finishedMatch.addAll(player.getFinishedMatch2());
        return matchesToDTO(finishedMatch);
    }

    public List<FinishedMatchDTO> readPage(List<FinishedMatchDTO> finishedMatches, int page) {
        int number = (page - 1) * 10;
        List<FinishedMatchDTO> result = new ArrayList<>();
        for (int i = 0, matchesSize = finishedMatches.size(); i < PAGE_SIZE ; i++) {
            if (number < matchesSize) {
                result.add(finishedMatches.get(number));
                number++;
            } else return result;
        }
        return result;
    }

    public FinishedMatch finishMatch(MatchScore score) {
        return new FinishedMatch(score.getPlayer1(), score.getPlayer2(), score.getWinner());
    }

    public void saveMatch(FinishedMatch finishedMatch) {
        dao.saveMatch(finishedMatch);
    }

    public List<FinishedMatch> findFinishedMatches() {
        return dao.findMatches();
    }

    public int countPages(List<FinishedMatchDTO> finishedMatches) {
        return (finishedMatches.size() + 9 ) / 10;
    }

    public List<FinishedMatchDTO> matchesToDTO(List<FinishedMatch> matches) {
        List<FinishedMatchDTO> result = new ArrayList<>();
        for (FinishedMatch match : matches) {
            FinishedMatchDTO matchDTO = new FinishedMatchDTO();
            matchDTO.setFirstPlayerName(match.getPlayer1().getName());
            matchDTO.setSecondPlayerName(match.getPlayer2().getName());
            matchDTO.setWinnerName(match.getWinner().getName());
            result.add(matchDTO);
        }
        return result;
    }
}
