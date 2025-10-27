package racingcar.view;

import java.util.List;

public interface RacingGameView {

    void setPresenter(ViewEventListener presenter);

    void start();

    void showCarNamesPrompt();

    void showTryCountPrompt();

    void showRoundResultHeader();

    void showCarStatus(String name, int position);

    void showRoundSeparator();

    void showWinnersResult(List<String> winnerNames);

    interface ViewEventListener {
        String onCarNamesRequested();

        int onTryCountRequested();
    }
}
