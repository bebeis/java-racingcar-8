package racingcar.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import racingcar.view.InputReader;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleInputHandlerTest {
    private ConsoleInputHandler inputHandler;
    private StubInputReader stubInputReader;

    @BeforeEach
    void setUp() {
        stubInputReader = new StubInputReader();
        inputHandler = new ConsoleInputHandler(stubInputReader);
    }

    @Test
    @DisplayName("차량 이름을 읽는다")
    void readCarNames() {
        stubInputReader.setInput("pobi,woni,jun");

        String result = inputHandler.readCarNames();

        assertThat(result).isEqualTo("pobi,woni,jun");
    }

    @Test
    @DisplayName("시도 횟수를 읽는다")
    void readTryCount() {
        stubInputReader.setInput("5");

        int result = inputHandler.readTryCount();

        assertThat(result).isEqualTo(5);
    }

    static class StubInputReader implements InputReader {
        private String input;

        public void setInput(String input) {
            this.input = input;
        }

        @Override
        public String readLine() {
            return input;
        }
    }
}

