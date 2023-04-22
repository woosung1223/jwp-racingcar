package racingcar.view.input;

import java.util.Scanner;

public class InputView {

    private static final String CAR_NAMES_INPUT_MESSAGE = "경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).";
    private static final String TRY_COUNT_INPUT_MESSAGE = "시도할 회수는 몇회인가요?";

    private final Scanner scanner;
    private final InputValidator inputValidator = new InputValidator();

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readCarName() {
        System.out.println(CAR_NAMES_INPUT_MESSAGE);

        return scanner.nextLine();
    }

    public int readGameTry() {
        System.out.println(TRY_COUNT_INPUT_MESSAGE);

        String gameTry = scanner.nextLine();

        inputValidator.validateGameTryRange(gameTry);
        return Integer.parseInt(gameTry);
    }
}
