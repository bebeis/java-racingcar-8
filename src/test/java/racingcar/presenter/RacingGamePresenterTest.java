package racingcar.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.model.CarStatusData;
import racingcar.model.RacingGameModel;
import racingcar.view.RacingGameView;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RacingGamePresenterTest {

    @Nested
    @DisplayName("프레젠터 통합 테스트")
    class PresenterIntegrationTest {
        private SpyRacingGameView spyView;
        private StubInputHandler stubInputHandler;
        private SpyRacingGameModel spyModel;
        private RacingGamePresenter presenter;

        @BeforeEach
        void setUp() {
            spyView = new SpyRacingGameView();
            stubInputHandler = new StubInputHandler();
            spyModel = new SpyRacingGameModel();
            presenter = new RacingGamePresenter(spyView, spyModel, stubInputHandler);
        }

        @Test
        @DisplayName("start 메서드 실행 시 view의 start를 호출한다")
        void start_CallsViewStart() {
            stubInputHandler.setCarNamesInput("pobi,woni,jun");
            stubInputHandler.setTryCountInput(5);

            presenter.start();

            assertThat(spyView.isStartCalled()).isTrue();
        }

        @Test
        @DisplayName("차량 이름 요청 시 입력 프롬프트를 표시한다")
        void onCarNamesRequested_ShowsPrompt() {
            stubInputHandler.setCarNamesInput("pobi,woni,jun");

            presenter.onCarNamesRequested();

            assertThat(spyView.isCarNamesPromptShown()).isTrue();
        }

        @Test
        @DisplayName("차량 이름 요청 시 모델에 차량을 준비한다")
        void onCarNamesRequested_PreparesCars() {
            stubInputHandler.setCarNamesInput("pobi,woni,jun");

            presenter.onCarNamesRequested();

            assertThat(spyModel.getPreparedCarNames()).containsExactly("pobi", "woni", "jun");
        }

        @Test
        @DisplayName("시도 횟수 요청 시 입력 프롬프트를 표시한다")
        void onTryCountRequested_ShowsPrompt() {
            stubInputHandler.setTryCountInput(3);

            presenter.onTryCountRequested();

            assertThat(spyView.isTryCountPromptShown()).isTrue();
        }

        @Test
        @DisplayName("시도 횟수 요청 시 라운드 결과 헤더를 표시한다")
        void onTryCountRequested_ShowsRoundResultHeader() {
            stubInputHandler.setTryCountInput(2);

            presenter.onTryCountRequested();

            assertThat(spyView.isRoundResultHeaderShown()).isTrue();
        }

        @Test
        @DisplayName("시도 횟수 요청 시 지정된 횟수만큼 라운드를 진행한다")
        void onTryCountRequested_PlaysRoundsForSpecifiedCount() {
            stubInputHandler.setTryCountInput(5);

            presenter.onTryCountRequested();

            assertThat(spyModel.getPlayRoundCallCount()).isEqualTo(5);
        }

        @Test
        @DisplayName("시도 횟수 요청 시 각 라운드마다 차량 상태를 표시한다")
        void onTryCountRequested_ShowsCarStatusesEachRound() {
            stubInputHandler.setTryCountInput(2);
            spyModel.setCarStatuses(Arrays.asList(
                    new CarStatusData("pobi", 1),
                    new CarStatusData("woni", 2)
            ));

            presenter.onTryCountRequested();

            assertThat(spyView.getShownCarStatuses()).hasSize(4);
        }

        @Test
        @DisplayName("시도 횟수 요청 시 우승자를 표시한다")
        void onTryCountRequested_ShowsWinners() {
            stubInputHandler.setTryCountInput(3);
            spyModel.setWinnerNames(Arrays.asList("woni", "jun"));

            presenter.onTryCountRequested();

            assertThat(spyView.getShownWinnerNames()).containsExactly("woni", "jun");
        }

        @Test
        @DisplayName("시도 횟수 요청 시 라운드 구분자를 표시한다")
        void onTryCountRequested_ShowsRoundSeparators() {
            stubInputHandler.setTryCountInput(3);

            presenter.onTryCountRequested();

            assertThat(spyView.getRoundSeparatorCount()).isEqualTo(3);
        }
    }

    static class SpyRacingGameView implements RacingGameView {
        private boolean startCalled = false;
        private boolean carNamesPromptShown = false;
        private boolean tryCountPromptShown = false;
        private boolean roundResultHeaderShown = false;
        private List<CarStatusData> shownCarStatuses = new java.util.ArrayList<>();
        private int roundSeparatorCount = 0;
        private List<String> shownWinnerNames = null;
        private ViewEventListener presenter;

        @Override
        public void setPresenter(ViewEventListener presenter) {
            this.presenter = presenter;
        }

        @Override
        public void start() {
            startCalled = true;
        }

        @Override
        public void showCarNamesPrompt() {
            carNamesPromptShown = true;
        }

        @Override
        public void showTryCountPrompt() {
            tryCountPromptShown = true;
        }

        @Override
        public void showRoundResultHeader() {
            roundResultHeaderShown = true;
        }

        @Override
        public void showCarStatus(String name, int position) {
            shownCarStatuses.add(new CarStatusData(name, position));
        }

        @Override
        public void showRoundSeparator() {
            roundSeparatorCount++;
        }

        @Override
        public void showWinnersResult(List<String> winnerNames) {
            shownWinnerNames = winnerNames;
        }

        public boolean isStartCalled() {
            return startCalled;
        }

        public boolean isCarNamesPromptShown() {
            return carNamesPromptShown;
        }

        public boolean isTryCountPromptShown() {
            return tryCountPromptShown;
        }

        public boolean isRoundResultHeaderShown() {
            return roundResultHeaderShown;
        }

        public List<CarStatusData> getShownCarStatuses() {
            return shownCarStatuses;
        }

        public int getRoundSeparatorCount() {
            return roundSeparatorCount;
        }

        public List<String> getShownWinnerNames() {
            return shownWinnerNames;
        }
    }

    static class StubInputHandler implements RacingGamePresenter.InputHandler {
        private String carNamesInput;
        private int tryCountInput;

        public void setCarNamesInput(String carNamesInput) {
            this.carNamesInput = carNamesInput;
        }

        public void setTryCountInput(int tryCountInput) {
            this.tryCountInput = tryCountInput;
        }

        @Override
        public String readCarNames() {
            return carNamesInput;
        }

        @Override
        public int readTryCount() {
            return tryCountInput;
        }
    }

    static class SpyRacingGameModel implements RacingGameModel {
        private List<String> preparedCarNames;
        private int playRoundCallCount = 0;
        private List<CarStatusData> carStatuses = Arrays.asList(
                new CarStatusData("pobi", 0),
                new CarStatusData("woni", 0)
        );
        private List<String> winnerNames = Arrays.asList("pobi");

        @Override
        public void prepareCars(List<String> carNames) {
            this.preparedCarNames = carNames;
        }

        @Override
        public void playRound() {
            playRoundCallCount++;
        }

        @Override
        public List<CarStatusData> getCurrentCarStatuses() {
            return carStatuses;
        }

        @Override
        public List<String> getWinnerNames() {
            return winnerNames;
        }

        public List<String> getPreparedCarNames() {
            return preparedCarNames;
        }

        public int getPlayRoundCallCount() {
            return playRoundCallCount;
        }

        public void setCarStatuses(List<CarStatusData> carStatuses) {
            this.carStatuses = carStatuses;
        }

        public void setWinnerNames(List<String> winnerNames) {
            this.winnerNames = winnerNames;
        }
    }
}
