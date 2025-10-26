package racingcar.service;

import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;

import java.util.List;

public interface GameService {

    void setUpCarNames(List<String> carNames);

    List<RoundSnapShot> playRounds(int tryCount);

    List<CarStatus> getWinners();
}
