package racingcar.controller;

import racingcar.service.dto.RoundSnapShot;
import racingcar.service.dto.WinnerResponse;
import racingcar.view.OutputView;

import java.util.List;

public class SpyOutputView implements OutputView {

    // 검증 대상 - 제대로 전달받았는지
    List<RoundSnapShot> snapShots;
    List<WinnerResponse> winners;

    @Override
    public void showRoundSnapShots(final List<RoundSnapShot> snapShots) {
        this.snapShots = snapShots;
    }

    @Override
    public void showWinners(final List<WinnerResponse> winners) {
        this.winners = winners;
    }
}
