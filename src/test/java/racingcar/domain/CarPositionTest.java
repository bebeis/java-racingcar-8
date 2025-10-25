package racingcar.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.error.ErrorMessage;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.*;

class CarPositionTest {

    private CarPosition createPositionViaReflection(int value) {
        try {
            Constructor<CarPosition> constructor = CarPosition.class.getDeclaredConstructor(int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException("인스턴스 생성 실패", e);
        }
    }

    @Nested
    @DisplayName("위치 값 테스트")
    class CreationTest {

        @DisplayName("음의 위치를 가질 수 없다.")
        @Test
        void shouldThrowException_whenPositionIsNegative() {
            assertThatThrownBy(() -> createPositionViaReflection(-2))
                    .hasRootCauseInstanceOf(IllegalStateException.class)
                    .hasRootCauseMessage(ErrorMessage.NEGATIVE_POSITION.message());
        }

        @DisplayName("0 이상의 값을 가질 수 있다..")
        @Test
        void canCreatePosition_whenNonNegative() {
            assertThatNoException()
                    .isThrownBy(() -> createPositionViaReflection(2));
        }
    }

    @Nested
    @DisplayName("이동 시")
    class MoveTest {

        CarPosition carPosition;

        @BeforeEach
        void init() {
            carPosition = CarPosition.initialState();
        }

        @DisplayName("초기 객체 생성 시 Position은 0이어야 한다.")
        @Test
        void positionShouldBeZero_whenInit() {
            int positionValue = carPosition.getPosition();
            assertThat(positionValue).isEqualTo(0);
        }

        @DisplayName("한 번에 한 칸씩 이동할 수 있다.")
        @Test
        void moveNextOnce_incrementsOne() {
            // given
            int beforePositionValue = carPosition.getPosition();

            // when
            CarPosition afterPosition = carPosition.moveNext();

            // then
            assertThat(afterPosition.getPosition()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("비교 시")
    class CompareTest {

        @DisplayName("같은 위치에 있으면 0을 반환한다.")
        @Test
        void compareToShouldReturnZero_whenPositionsAreEqual() {
            // given
            CarPosition p1 = CarPosition.initialState();
            CarPosition p2 = CarPosition.initialState();

            // when
            int result = p1.compareTo(p2);

            // then
            assertThat(result).isZero();
        }

        @DisplayName("앞 위치가 더 작으면 음수를 반환한다.")
        @Test
        void compareToShouldReturnNegative_whenBehind() {
            // given
            CarPosition p1 = CarPosition.initialState();
            CarPosition p2 = CarPosition.initialState().moveNext();

            // when
            int result = p1.compareTo(p2);

            // then
            assertThat(result).isLessThan(0);
        }

        @DisplayName("앞 위치가 더 크면 양수를 반환한다.")
        @Test
        void compareToShouldReturnPositive_whenBehind() {
            // given
            CarPosition p1 = CarPosition.initialState().moveNext();
            CarPosition p2 = CarPosition.initialState();

            // when
            int result = p1.compareTo(p2);

            // then
            assertThat(result).isGreaterThan(0);
        }

        @DisplayName("같은 위치의 객체는 동등하다.")
        @Test
        void equalsShouldReturnTrue_whenSamePosition() {
            // given
            CarPosition p1 = CarPosition.initialState();
            CarPosition p2 = CarPosition.initialState();

            // when
            boolean result = p1.equals(p2);

            // then
            assertThat(result).isTrue();
        }

    }
}
