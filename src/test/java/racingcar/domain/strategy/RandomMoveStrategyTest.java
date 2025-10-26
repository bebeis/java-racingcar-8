package racingcar.domain.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;

class RandomMoveStrategyTest {

    @DisplayName("랜덤 값이 경계 값 이상인 경우 움직일 수 있다고 판단한다")
    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9})
    void shouldReturnTrue_whenRandomValueAboveFour(int value) {
        assertRandomNumberInRangeTest(
                () -> {
                    RandomMoveStrategy strategy = new RandomMoveStrategy();
                    boolean result = strategy.canMove();
                    assertThat(result).isTrue();
                },
                value
        );
    }

    @DisplayName("랜덤 값이 경계 값 이하인 움직일 수 없다고 판단한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 3})
    void shouldReturnFalse_whenRandomValueBelowFour(int value) {
        assertRandomNumberInRangeTest(
                () -> {
                    RandomMoveStrategy strategy = new RandomMoveStrategy();
                    boolean result = strategy.canMove();
                    assertThat(result).isFalse();
                },
                value
        );
    }
}
