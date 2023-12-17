package ru.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Scanner;

public class StartUI {
    private static final String FILE_NAME = "data/gameRule.txt";
    private static final String BULL = "b ";
    private static final String COW = "k";

    private final Scanner scanner;

    public StartUI(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Вывод меню игры
     */
    private void printMenu() {
        System.out.println("Добро пожаловать в игру Быки и коровы!");
        System.out.println("1. Правила игры");
        System.out.println("2. Старт игры");
        System.out.println("3. Завершить игру");
    }

    /**
     * Метод выполняет чтение файла, в котором содержится правила игры.
     */
    private void readFile() {
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(FILE_NAME), StandardCharsets.UTF_8)) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = in.read()) != -1) {
                text.append((char) read);
            }
            System.out.println("Правила игры: " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод в котором выполняется ввод четырехзначных чисел игроками.
     */
    public void playGame() {
        System.out.println("Первый игрок введите четырехзначное число."
                + " Все четыре числа должны быть разными");
        int userFirst = checkDigital();
        System.out.println("Второй игрок введите четырехзначное число."
                + " Все четыре числа должны быть разными");
        int userSecond = checkDigital();
        countDigital(userFirst, userSecond);
    }

    /**
     * Метод выполняет проверку чисел
     * @return число
     */
    private int checkDigital() {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(this.scanner.next());
                if (number >= 1000 && number <= 9999 && isUniqueDigits(number)) {
                    break;
                }
                System.out.println("Вы ввели не четырехзначное число. Или цифры не уникальны");
            } catch (NumberFormatException e) {
                System.out.println("Введенный символ не является числом");
            }
        }
        return number;
    }

    /**
     * Метод выполняет проверку на уникальность цифры в числе
     * @param number число
     * @return boolean логику
     */
    private boolean isUniqueDigits(int number) {
        String numberString = String.valueOf(number);
        HashSet<Integer> digits = new HashSet<>();
        for (int i = 0; i < numberString.length(); i++) {
            digits.add(Integer.parseInt(numberString.substring(i, i + 1)));
        }
        return digits.size() == numberString.length();
    }

    /**
     * Метод выполняет подсчет быков и коров
     * @param a число
     * @param b число
     */
    private void countDigital(int a, int b) {
        if (a == b) {
            System.out.println("Число угадано!");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            String[] first = String.valueOf(a).split("");
            String[] second = String.valueOf(b).split("");

            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < first.length; i++) {
                for (int j = 0; j < second.length; j++) {
                    if (first[i].equals(second[j])) {
                        if (i == j) {
                            bulls++;
                        } else {
                            cows++;
                        }
                    }
                }
            }
            System.out.println(stringBuilder.append(bulls).append(BULL).append(cows).append(COW));
        }
    }

    /**
     * Метод запуска меню Старт игры
     */
    public void start() {
        Scanner input = new Scanner(System.in);
        boolean run = true;
        while (run) {
            printMenu();
            System.out.println("Введите номер меню: ");
            try {
                int key = Integer.parseInt(input.nextLine());
                switch (key) {
                    case 1 -> readFile();
                    case 2 -> playGame();
                    case 3 -> {
                        System.out.println("Завершение программы...");
                        run = false;
                    }
                    default -> System.out.println("Вы ввели неверное значение меню...");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введенный символ не является числом.");
            }
        }
    }
}

