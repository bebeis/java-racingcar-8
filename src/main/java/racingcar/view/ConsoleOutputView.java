package racingcar.view;

import racingcar.service.dto.CarStatus;
import racingcar.service.dto.RoundSnapShot;

import java.util.List;

import static racingcar.view.OutputFormatter.formatRoundSnapShots;
import static racingcar.view.OutputFormatter.formatWinners;

public class ConsoleOutputView implements OutputView {
    private static final String ROUND_RESULT_PROMPT = "실행 결과";
    private static final String WINNERS_RESULT_PROMPT = "최종 우승자 : ";

    @Override
    public void showRoundSnapShots(final List<RoundSnapShot> snapShots) {
        System.out.println(ROUND_RESULT_PROMPT);

        String formatted = formatRoundSnapShots(snapShots);
        System.out.println(formatted);
    }

    @Override
    public void showWinners(final List<CarStatus> winners) {
        System.out.println(WINNERS_RESULT_PROMPT + formatWinners(winners));
    }
}
