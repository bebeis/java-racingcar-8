package racingcar.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.error.ErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static racingcar.error.ErrorMessage.CANNOT_PROCEED_WHEN_FINISHED;

class RoundTest {

    @DisplayName("남은 시도 횟수는 0 이하일 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -2})
    void shouldThrowException_whenRemainingCountLessThenZero(int remainingCount) {
        assertThatThrownBy(() -> Round.from(remainingCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NON_POSITIVE_TRY_COUNT.message());
    }

    @DisplayName("남은 시도 횟수를 차감할 수 있다.")
    @Test
    void canDeductRemainingCount() {
        // given
        Round round = Round.from(5);

        // when
        round = round.next();

        assertThat(round.getValue()).isEqualTo(4);
    }

    @DisplayName("남은 시도 횟수가 0이 되었을 때, 게임의 종료를 판정할 수 있다.")
    @Test
    void shouldDetermineGameEnding_whenRemainingCountIsZero() {
        // given
        Round round = Round.from(1);
        round = round.next();

        // when
        boolean result = round.isFinished();

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("남은 시도 횟수가 0보다 클 때, 게임 유지를 판정할 수 있다..")
    @Test
    void shouldDetermineGameEnding_whenRemainingCountIsGraterThenZero() {
        // given
        Round round = Round.from(3);
        round = round.next();

        // when
        boolean result = round.isFinished();

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("남은 시도 횟수가 0일 때, 라운드를 진행할 수 없다.")
    @Test
    void shouldThrowException_whenRemainingRoundIsZeroAndProceed() {
        // given
        Round round = Round.from(1);
        round = round.next();

        // when && then
        assertThatThrownBy(round::next)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(CANNOT_PROCEED_WHEN_FINISHED.message());
    }
}
