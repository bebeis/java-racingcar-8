package racingcar.view;

import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;

import java.util.List;

public interface OutputView {

    void showRoundSnapShots(List<RoundSnapShot> snapShots);

    void showWinners(List<CarStatus> winners);
}
