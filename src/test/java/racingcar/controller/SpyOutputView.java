package racingcar.controller;

import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;
import racingcar.view.OutputView;

import java.util.List;

public class SpyOutputView implements OutputView {

    // 검증 대상 - 제대로 전달받았는지
    List<RoundSnapShot> snapShots;
    List<CarStatus> winners;

    @Override
    public void showRoundSnapShots(final List<RoundSnapShot> snapShots) {
        this.snapShots = snapShots;
    }

    @Override
    public void showWinners(final List<CarStatus> winners) {
        this.winners = winners;
    }
}
