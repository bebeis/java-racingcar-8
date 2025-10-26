package racingcar.presenter;

import racingcar.service.RacingGame;
import racingcar.service.dto.RoundSnapShot;
import racingcar.service.dto.WinnerResponse;

import java.util.List;

public class StubRacingGame implements RacingGame {

    // 검증 대상 - 제대로 전달 받았는지
    List<String> carNames;
    int tryCount;

    final List<RoundSnapShot> snapShotsToReturn;
    final List<WinnerResponse> winnerResponsesToReturn;

    public StubRacingGame(final List<RoundSnapShot> snapShotsToReturn, final List<WinnerResponse> winnerResponsesToReturn) {
        this.snapShotsToReturn = snapShotsToReturn;
        this.winnerResponsesToReturn = winnerResponsesToReturn;
    }

    @Override
    public void setUpCarNames(final List<String> carNames) {
        this.carNames = carNames;
    }

    @Override
    public List<RoundSnapShot> playRounds(final int tryCount) {
        this.tryCount = tryCount;
        return snapShotsToReturn;
    }

    @Override
    public List<WinnerResponse> getWinners() {
        return winnerResponsesToReturn;
    }
}
