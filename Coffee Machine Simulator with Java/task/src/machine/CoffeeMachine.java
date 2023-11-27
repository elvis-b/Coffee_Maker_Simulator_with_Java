package machine;

import java.util.Scanner;

public class CoffeeMachine {

    private enum State {
        CHOOSING_ACTION,
        CHOOSING_COFFEE,
        FILLING_WATER,
        FILLING_MILK,
        FILLING_BEANS,
        FILLING_CUPS
    }

    private State state = State.CHOOSING_ACTION;
    private final Scanner scanner = new Scanner(System.in);
    private int waterLevels = 400;
    private int milkLevels = 540;
    private int beans = 120;
    private int disposableCups = 9;
    private int money = 550;

    // espresso
    int waterForOneCupOfEspresso = 250;
    int beansForOneCupOfEspresso = 16;
    int espressoPrice = 4;

    // latte
    int waterForOneCupOfLatte = 350;
    int milkForOneCupOfLatte = 75;
    int beansForOneCupOfLatte = 20;
    int lattePrice = 7;

    // capuccino
    int waterForOneCupOfCapuccino = 200;
    int milkForOneCupOfCapuccino = 100;
    int beansForOneCupOfCapuccino = 12;
    int capuccinoPrice = 6;

    int coffeeAmount = 1;

    public CoffeeMachine() {

    }

    private void printPrompt() {
        if (state == State.CHOOSING_ACTION) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
        }
    }

    public void manageInput(String input) {
            switch (state) {
                case CHOOSING_ACTION:
                    manageActions(input);
                    break;
                case CHOOSING_COFFEE:
                    manageCoffeeChoice(input);
                    break;
                case FILLING_WATER, FILLING_MILK, FILLING_BEANS, FILLING_CUPS:
                    manageFilling(input);
                    break;
            }
            printPrompt();
    }

    private void manageActions(String action) {
        switch (action) {
            case "buy":
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                state = State.CHOOSING_COFFEE;
                break;
            case "fill":
                System.out.println("Write how many ml of water you want to add:");
                state = State.FILLING_WATER;
                break;
            case "take":
                take();
                state = State.CHOOSING_ACTION;
                break;
            case "remaining":
                remaining();
                state = State.CHOOSING_ACTION;
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
        }
    }

    private void manageCoffeeChoice(String coffeeType) {
        switch (coffeeType) {
            case "1":
                espressoCalculator();
                state = State.CHOOSING_ACTION;
                break;
            case "2":
                latteCalculator();
                state = State.CHOOSING_ACTION;
                break;
            case "3":
                capuccinoCalculator();
                state = State.CHOOSING_ACTION;
                break;
            case "back":
                state = State.CHOOSING_ACTION;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void manageFilling(String input) {
        int amount = Integer.parseInt(input);
        switch (state) {
            case FILLING_WATER:
                System.out.println("Write how many ml of milk you want to add:");
                waterLevels += amount;
                state = State.FILLING_MILK;
                break;
            case FILLING_MILK:
                System.out.println("Write how many grams of coffee beans you want to add:");
                milkLevels += amount;
                state = State.FILLING_BEANS;
                break;
            case FILLING_BEANS:
                System.out.println("Write how many disposable cups you want to add:");
                beans += amount;
                state = State.FILLING_CUPS;
                break;
            case FILLING_CUPS:
                disposableCups += amount;
                state = State.CHOOSING_ACTION;;
                break;
        }
    }

    private void remaining() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water\n", waterLevels);
        System.out.printf("%d ml of milk\n", milkLevels);
        System.out.printf("%d g of coffee beans\n", beans);
        System.out.printf("%d disposable cups\n", disposableCups);
        System.out.printf("$%d of money\n", money);
        System.out.println();
    }

    public void espressoCalculator() {
        if (waterLevels < 250) {
            System.out.println("Sorry, not enough water!");
        } else {
            double espressoCupsBasedOnWaterLevels = Math.floor((double) waterLevels / waterForOneCupOfEspresso);
            double espressoCupsBasedOnBeansLevels = Math.floor((double) beans / beansForOneCupOfEspresso);
            double minimumEspressoCups = Math.min(espressoCupsBasedOnWaterLevels, espressoCupsBasedOnBeansLevels);

            if (coffeeAmount <= minimumEspressoCups) {
                System.out.println("I have enough resources, making you a coffee!\n");
            }
            waterLevels -= waterForOneCupOfEspresso;
            beans -= beansForOneCupOfEspresso;
            disposableCups -= 1;
            money += espressoPrice;
            printPrompt();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public void latteCalculator() {
        if (waterLevels < 350) {
            System.out.println("Sorry, not enough water!");
        } else if (milkLevels < 75) {
            System.out.println("Sorry, not enough milk!");
        } else if (beans < 20) {
            System.out.println("Sorry, not enough beans");
        } else {
            double latteCupsBasedOnWaterLevels = Math.floor((double) waterLevels / waterForOneCupOfLatte);
            double latteCupsBasedOnMilkLevels = Math.floor((double) milkLevels / milkForOneCupOfLatte);
            double latteCupsBasedOnBeansLevels = Math.floor((double) beans / beansForOneCupOfLatte);

            double minimumLatteCups = Math.min(latteCupsBasedOnWaterLevels, Math.min(latteCupsBasedOnMilkLevels, latteCupsBasedOnBeansLevels));

            if (coffeeAmount <= minimumLatteCups) {
                System.out.println("I have enough resources, making you a coffee!");
            }
            waterLevels -= waterForOneCupOfLatte;
            milkLevels -= milkForOneCupOfLatte;
            beans -= beansForOneCupOfLatte;
            disposableCups -= 1;
            money += lattePrice;
        }
    }

    @SuppressWarnings("DuplicatedCode")
    public void capuccinoCalculator() {
        if (waterLevels < 200) {
            System.out.println("Sorry, not enough water!");
        } else if (milkLevels < 100) {
            System.out.println("Sorry, not enough milk!");
        } else if (beans < 12) {
            System.out.println("Sorry, not enough beans");
        } else {
            double capuccinoCupsBasedOnWaterLevels = Math.floor((double) waterLevels / waterForOneCupOfCapuccino);
            double capuccinoCupsBasedOnMilkLevels = Math.floor((double) milkLevels / milkForOneCupOfCapuccino);
            double capuccinoCupsBasedOnBeansLevels = Math.floor((double) beans / beansForOneCupOfCapuccino);

            double minimumCapuccinoCups = Math.min(capuccinoCupsBasedOnWaterLevels, Math.min(capuccinoCupsBasedOnMilkLevels, capuccinoCupsBasedOnBeansLevels));

            if (coffeeAmount <= minimumCapuccinoCups) {
                System.out.println("I have enough resources, making you a coffee!");
            }
            waterLevels -= waterForOneCupOfCapuccino;
            milkLevels -= milkForOneCupOfCapuccino;
            beans -= beansForOneCupOfCapuccino;
            disposableCups -= 1;
            money += capuccinoPrice;
            printPrompt();
        }
    }

    public void take() {
        System.out.printf("I gave you $%d\n", money);
        money = 0;
        System.out.println();
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            coffeeMachine.manageInput(scanner.nextLine());
        }
    }
}
