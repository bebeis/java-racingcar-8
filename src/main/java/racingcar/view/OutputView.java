package racingcar.view;

import racingcar.service.dto.RoundSnapShot;
import racingcar.service.dto.WinnerResponse;

import java.util.List;

public interface OutputView {

    void showRoundSnapShots(List<RoundSnapShot> snapShots);

    void showWinners(List<WinnerResponse> winners);
}
