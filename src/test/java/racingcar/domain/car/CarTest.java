package racingcar.domain.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.stub.strategy.AlwaysMoveStrategy;
import racingcar.stub.strategy.NeverMoveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

class CarTest {
    AlwaysMoveStrategy alwaysMoveStrategy = new AlwaysMoveStrategy();
    NeverMoveStrategy neverMoveStrategy = new NeverMoveStrategy();

    @Nested
    @DisplayName("이동 규칙 테스트")
    class MoveTest {
        Car car;

        @BeforeEach
        void init() {
            car = new Car(new CarName("bebe"), CarPosition.initialState());
        }

        @DisplayName("전략이 이동 가능을 반환하면 자동차가 전진한다.")
        @Test
        void movesForward_whenStrategyCanMove() {
            // when
            car.moveDeterminedBy(alwaysMoveStrategy);

            // then
            assertThat(car.positionValue()).isEqualTo(1);
        }

        @DisplayName("전략이 이동 불가를 반환하면 자동차가 전진하지 않는다..")
        @Test
        void movesForward_whenStrategyCantMove() {
            // when
            car.moveDeterminedBy(neverMoveStrategy);

            // then
            assertThat(car.positionValue()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("위치 비교 테스트")
    class PositionComparisonTest {
        AlwaysMoveStrategy alwaysMoveStrategy = new AlwaysMoveStrategy();
        Car car1;
        Car car2;

        @BeforeEach
        void init() {
            car1 = new Car(new CarName("bebe"), CarPosition.initialState());
            car2 = new Car(new CarName("hehe"), CarPosition.initialState());
        }

        @DisplayName("같은 위치에 있는 자동차인지 식별할 수 있어야 한다.")
        @Test
        void shouldReturnTrue_whenCarsSamePosition() {
            // when
            boolean result = car1.isSamePosition(car2);

            // then
            assertThat(result).isTrue();
        }

        @DisplayName("서로 다른 위치에 있는 자동차는 동일한 위치로 식별되지 않는다")
        @Test
        void shouldReturnFalse_whenCarsDifferentPosition() {
            // given
            car1.moveDeterminedBy(alwaysMoveStrategy);

            // when
            boolean result = car1.isSamePosition(car2);

            // then
            assertThat(result).isFalse();
        }

        @DisplayName("위치에 따른 앞 뒤 비교를 할 수 있다.")
        @Test
        void canCompare_whenCarsDifferentPosition() {
            // given
            car1.moveDeterminedBy(alwaysMoveStrategy);

            // when
            int result = car1.compareTo(car2);

            // then
            assertThat(result).isGreaterThan(0);
        }
    }
}
