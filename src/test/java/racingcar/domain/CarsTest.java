package racingcar.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.error.ErrorMessage;
import racingcar.strategy.SequentialMoveStrategy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarsTest {

    Car car1;
    Car car2;
    Car car3;
    Cars cars;

    @BeforeEach
    void init() {
        car1 = new Car(new CarName("bebe"), CarPosition.initialState());
        car2 = new Car(new CarName("jeje"), CarPosition.initialState());
        car3 = new Car(new CarName("hehe"), CarPosition.initialState());
        cars = new Cars(List.of(car1, car2, car3));
    }

    @Nested
    @DisplayName("생성 테스트")
    class CreateTest {

        @DisplayName("자동차 목록이 비어있으면 예외를 발생시킨다.")
        @Test
        void shouldThrowException_whenCarListIsEmpty() {

            assertThatThrownBy(() -> new Cars(List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.EMPTY_CAR_LIST.message());
        }

        @DisplayName("자동차 목록을 받아 객체를 생성할 수 있다.")
        @Test
        void canCreateCars_withCarList() {
            // then
            assertThat(cars.getCars().size()).isEqualTo(3);
            assertThat(cars.getCars()).containsExactly(car1, car2, car3);
        }
    }


    @Nested
    @DisplayName("전략을 바탕으로 모든 자동차들을 이동시킬 수 있다.")
    class MoveAllTest {

        @DisplayName("주어진 전략으로 모든 자동차를 이동시킨다.")
        @Test
        void canMoveCars_oneRound() {
            // given
            SequentialMoveStrategy strategy = new SequentialMoveStrategy(true, false, true);

            // when
            cars.moveAll(strategy);

            // then
            assertThat(cars.getCars().get(0).positionValue()).isEqualTo(1);
            assertThat(cars.getCars().get(1).positionValue()).isEqualTo(0);
            assertThat(cars.getCars().get(2).positionValue()).isEqualTo(1);
        }

        @DisplayName("여러 라운드에서 자동차가 움직일 수 있다.")
        @Test
        void canMoveCars_multipleRound() {
            // given
            SequentialMoveStrategy strategy = new SequentialMoveStrategy(
                    true, false, true,
                    true, true, false,
                    true, true, false);

            // when
            cars.moveAll(strategy);
            cars.moveAll(strategy);
            cars.moveAll(strategy);

            // then
            assertThat(cars.getCars().get(0).positionValue()).isEqualTo(3);
            assertThat(cars.getCars().get(1).positionValue()).isEqualTo(2);
            assertThat(cars.getCars().get(2).positionValue()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("승자 테스트")
    class WinnerTest {

        @DisplayName("한 명의 승자만 존재하는 경우 승자를 올바르게 찾아낸다.")
        @Test
        void canFindSingleWinner() {
            // given
            SequentialMoveStrategy strategy = new SequentialMoveStrategy(
                    true, false, false,
                    true, false, false,
                    true, true, true);

            // when
            cars.moveAll(strategy);
            cars.moveAll(strategy);
            cars.moveAll(strategy);
            List<Car> winners = cars.getWinners();

            // then
            assertThat(winners).hasSize(1);
            assertThat(winners.getFirst().nameValue()).isEqualTo("bebe");
            assertThat(winners.getFirst().positionValue()).isEqualTo(3);
        }

        @DisplayName("두 명 이상의 승자가 존재하는 경우에도 승자를 올바르게 찾는다.")
        @Test
        void canFindMultipleWinners() {
            // given
            SequentialMoveStrategy strategy = new SequentialMoveStrategy(
                    true, false, true,
                    true, true, true);

            // when
            cars.moveAll(strategy);
            cars.moveAll(strategy);
            List<Car> winners = cars.getWinners();

            // then
            assertThat(winners).hasSize(2);
            assertThat(winners).extracting(Car::nameValue)
                    .containsExactlyInAnyOrder("bebe", "hehe");
            assertThat(winners).allMatch(car -> car.positionValue() == 2);
        }

        @DisplayName("모든 자동차가 같은 위치에 있으면 모두 승자다.")
        @Test
        void allCarsAreWinners_whenSamePosition() {
            // given
            SequentialMoveStrategy strategy = new SequentialMoveStrategy(
                    false, false, false);

            // when
            cars.moveAll(strategy);
            List<Car> winners = cars.getWinners();

            // then
            assertThat(winners).hasSize(3);
            assertThat(winners).extracting(Car::nameValue)
                    .containsExactlyInAnyOrder("bebe", "jeje", "hehe");
            assertThat(winners).allMatch(car -> car.positionValue() == 0);
        }
    }
}
