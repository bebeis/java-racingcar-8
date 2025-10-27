package racingcar.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.domain.car.Car;
import racingcar.domain.car.CarName;
import racingcar.domain.car.Cars;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CarsMemoryRepositoryTest {
    private CarsMemoryRepository repository;

    @BeforeEach
    void init() {
        repository = new CarsMemoryRepository();
    }

    @Nested
    @DisplayName("저장 테스트")
    class SaveTest {

        @Test
        @DisplayName("Cars 객체를 저장한다")
        void saveCars() {
            // given
            Cars cars = createCars("pobi", "bebe", "hehe");

            // when
            repository.save(cars);

            // then
            Cars savedCars = repository.findCars();
            assertThat(savedCars).isEqualTo(cars);
        }
    }

    @Nested
    @DisplayName("Cars 조회 테스트")
    class FindCarsTest {

        @Test
        @DisplayName("저장된 Cars 객체를 반환한다")
        void findSavedCars() {
            // given
            Cars cars = createCars("pobi", "bebe");
            repository.save(cars);

            // when
            Cars foundCars = repository.findCars();

            // then
            assertThat(foundCars).isEqualTo(cars);
        }
    }

    private Cars createCars(String... names) {
        List<Car> carList = Stream.of(names)
                .map(CarName::new)
                .map(Car::new)
                .toList();
        return new Cars(carList);
    }
}
