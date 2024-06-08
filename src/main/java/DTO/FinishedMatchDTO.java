package DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FinishedMatchDTO {
    private String firstPlayerName;
    private String secondPlayerName;
    private String winnerName;
}
