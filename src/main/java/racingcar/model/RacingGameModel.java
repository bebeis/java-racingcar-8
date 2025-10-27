package racingcar.model;

import java.util.List;

public interface RacingGameModel {

    void prepareCars(List<String> carNames);

    void playRound();

    List<CarStatusData> getCurrentCarStatuses();

    List<String> getWinnerNames();
}

