package racingcar.domain;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RacingGameTest {

    @ParameterizedTest
    @MethodSource("parameterProvider")
    void getWinnersTest(List<String> carNames, List<Integer> determinedNumbers, int gameTry, List<String> expectedWinners) {
        List<Car> cars = carNames.stream()
                .map(carName -> new Car(carName, 0))
                .collect(Collectors.toList());

        DeterminedNumberGenerator determinedNumberGenerator = new DeterminedNumberGenerator();
        RacingGame racingGame = new RacingGame(cars, gameTry, determinedNumberGenerator);
        determinedNumberGenerator.readRepository(determinedNumbers);

        racingGame.start();

        assertEquals(
                racingGame.getWinners().stream().map(Car::getCarName).collect(Collectors.toList()), expectedWinners
        );
    }

    private Stream<Arguments> parameterProvider() {
        return Stream.of(
                Arguments.of(List.of("pobi", "crong"), List.of(5, 3, 9, 0, 0, 9), 3, List.of("pobi")),
                Arguments.of(List.of("pobi", "crong"), List.of(3, 5, 9, 0, 0, 9), 3, List.of("crong")),
                Arguments.of(List.of("pobi", "crong"), List.of(4, 4, 4, 4, 4, 4), 3, List.of("pobi", "crong")),
                Arguments.of(List.of("pobi", "crong"), List.of(0, 0, 0, 0, 0, 0), 3, List.of("pobi", "crong")),
                Arguments.of(List.of("pobi", "crong", "hadi"), List.of(3, 4, 9, 6, 7, 6, 0, 1, 2), 3, List.of("crong", "hadi")),
                Arguments.of(List.of("pobi", "crong", "hadi"), List.of(7, 4, 9, 6, 7, 6, 0, 1, 2), 3, List.of("pobi", "crong", "hadi"))
        );
    }
}
