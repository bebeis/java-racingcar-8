package racingcar.service;

import racingcar.service.dto.RoundSnapShot;
import racingcar.service.dto.WinnerResponse;

import java.util.List;

public interface RacingGame {

    void setUpCarNames(List<String> carNames);

    void setUpTryCount(int tryCount);

    List<RoundSnapShot> play();

    List<WinnerResponse> getWinners();
}
