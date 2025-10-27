package racingcar.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleRacingGameViewTest {
    private ConsoleRacingGameView view;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private StubInputReader stubInputReader;

    @BeforeEach
    void setUp() {
        stubInputReader = new StubInputReader();
        view = new ConsoleRacingGameView(stubInputReader);
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("프레젠터를 설정할 수 있다")
    void setPresenter() {
        SpyViewEventListener presenter = new SpyViewEventListener();

        view.setPresenter(presenter);
        view.start();

        assertThat(presenter.isCarNamesRequested()).isTrue();
    }

    @Test
    @DisplayName("start 메서드는 프레젠터의 콜백을 호출한다")
    void start_CallsPresenterCallbacks() {
        SpyViewEventListener presenter = new SpyViewEventListener();
        view.setPresenter(presenter);

        view.start();

        assertThat(presenter.isCarNamesRequested()).isTrue();
        assertThat(presenter.isTryCountRequested()).isTrue();
    }

    @Test
    @DisplayName("차량 이름 입력 프롬프트를 출력한다")
    void showCarNamesPrompt() {
        view.showCarNamesPrompt();

        String output = outputStream.toString();
        assertThat(output).contains("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
    }

    @Test
    @DisplayName("시도 횟수 입력 프롬프트를 출력한다")
    void showTryCountPrompt() {
        view.showTryCountPrompt();

        String output = outputStream.toString();
        assertThat(output).contains("시도할 횟수는 몇 회인가요?");
    }

    @Test
    @DisplayName("라운드 결과 헤더를 출력한다")
    void showRoundResultHeader() {
        view.showRoundResultHeader();

        String output = outputStream.toString();
        assertThat(output).contains("실행 결과");
    }

    @Test
    @DisplayName("차량 상태를 올바른 형식으로 출력한다")
    void showCarStatus() {
        view.showCarStatus("pobi", 3);

        String output = outputStream.toString();
        assertThat(output).contains("pobi : ---");
    }

    @Test
    @DisplayName("위치가 0인 차량 상태를 출력한다")
    void showCarStatus_WithZeroPosition() {
        view.showCarStatus("woni", 0);

        String output = outputStream.toString();
        assertThat(output).contains("woni : ");
        assertThat(output).doesNotContain("-");
    }

    @Test
    @DisplayName("라운드 구분자를 출력한다")
    void showRoundSeparator() {
        view.showRoundSeparator();

        String output = outputStream.toString();
        assertThat(output).isEqualTo(System.lineSeparator());
    }

    @Test
    @DisplayName("우승자를 출력한다")
    void showWinnersResult_SingleWinner() {
        List<String> winners = Arrays.asList("pobi");

        view.showWinnersResult(winners);

        String output = outputStream.toString();
        assertThat(output).contains("최종 우승자 : pobi");
    }

    @Test
    @DisplayName("여러 우승자를 쉼표로 구분하여 출력한다")
    void showWinnersResult_MultipleWinners() {
        List<String> winners = Arrays.asList("pobi", "woni", "jun");

        view.showWinnersResult(winners);

        String output = outputStream.toString();
        assertThat(output).contains("최종 우승자 : pobi, woni, jun");
    }

    static class SpyViewEventListener implements RacingGameView.ViewEventListener {
        private boolean carNamesRequested = false;
        private boolean tryCountRequested = false;

        @Override
        public String onCarNamesRequested() {
            carNamesRequested = true;
            return "pobi,woni";
        }

        @Override
        public int onTryCountRequested() {
            tryCountRequested = true;
            return 5;
        }

        public boolean isCarNamesRequested() {
            return carNamesRequested;
        }

        public boolean isTryCountRequested() {
            return tryCountRequested;
        }
    }

    static class StubInputReader implements InputReader {
        @Override
        public String readLine() {
            return "";
        }
    }
}
