package racingcar.domain.car;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import racingcar.error.ErrorMessage;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarNameTest {

    @DisplayName("자동차 이름 길이가 최대 길이를 초과할 수 없다.")
    @Test
    void shouldThrowsException_whenLengthOverFive() {
        assertThatThrownBy(() -> new CarName("가나다라마바사"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.EXCEED_CAR_NAME_MAX_LENGTH.message());
    }

    @DisplayName("자동차 이름이 비어있을 수 없다.")
    @Test
    void shouldThrowsException_whenNameIsEmpty() {
        assertThatThrownBy(() -> new CarName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.EMPTY_CAR_NAME.message());
    }
}
