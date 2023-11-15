import java.util.Scanner;

public class Text {
        public static Scanner scanner = new Scanner(System.in);
    // Where the boring stuf is
    public static void StartText() {
        System.out.println("Now that everything is setupp, lets begin the actual game");
        System.out.println("Since u are the player, you get to go first, you are white btw");
        System.out.println("Give the co-ordniates, where from attack then attack to who.");
        System.out.println("Ex: A1-A2 (Maybe not actually viable in this case)");

    }

    public static void Settings() {
        while (true) {
            System.out.println("What difficulty do you want? 1: Easy, 2: Default, 3: Hard");
            try {
                Risk.DIFFICULTY = scanner.nextInt();
                if (Risk.DIFFICULTY >= 1 && Risk.DIFFICULTY <= 3) {
                    break;
                }
                System.out.println("Close, you'll gett there...");
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Common, give the inputs propely, im tired of error handeling!!!");
                System.out.println("1, 2 or 3!");
                scanner.nextLine(); // Clear the buffer to handle the next input correctly

            }
        }

    }

    public static void changeName() {
        while (true) {
            System.out.println("What do you want your name to be: ");
            try {
                Risk.name = scanner.nextLine();
                if (Risk.name.length() > 0 && Risk.name.matches("[a-zA-Z0-9]+")) {
                    System.out.println("Okay, ur name has been set!");
                    break;
                } else {
                    System.out.println("Please enter a valid name with only normal characters and non-zero length.");
                }
            } catch (Exception e) {
                System.out.println("Ingen aning hur du hamna här: " + e);
            }
        }
    }

    public static void Welcome() { // Text that welcomes the player to the game and intro screen
        GenerateMap.clearConsole();
        System.out.println(
                " __      __   _                    _                     ___  _ ___ _  __                                         _                _   _           _____ _______  \r\n"
                        + //
                        " \\ \\    / /__| |__ ___ _ __  ___  | |_ ___   _ __ _  _  | _ \\/ / __| |/ /  __ _ __ _ _ __  ___   _ __ _ _ ___  __| |_  _ __ ___ __| | | |__ _  _  |_   _|__ /   \\ \r\n"
                        + //
                        "  \\ \\/\\/ / -_) / _/ _ \\ '  \\/ -_) |  _/ _ \\ | '  \\ || | |   /| \\__ \\ ' <  / _` / _` | '  \\/ -_) | '_ \\ '_/ _ \\/ _` | || / _/ -_) _` | | '_ \\ || |   | |  |_ \\ |) |\r\n"
                        + //
                        "   \\_/\\_/\\___|_\\__\\___/_|_|_\\___|  \\__\\___/ |_|_|_\\_, | |_|_\\|_|___/_|\\_\\ \\__, \\__,_|_|_|_\\___| | .__/_| \\___/\\__,_|\\_,_\\__\\___\\__,_| |_.__/\\_, |   |_| |___/___/ \r\n"
                        + //
                        "                                                  |__/                    |___/                 |_|                                         |__/                 ");
        int choice = -1;
        while (true) {
            try {
                System.out.println("Choose what you want to do:");
                System.out.println("1. Begin the game");
                System.out.println("2. Change your name");
                System.out.println("3. See the rules for the game");
                System.out.println("4. Settings, diffuculty and mapsize");
                System.out.println("5. Exit, see you");
                choice = scanner.nextInt();
                scanner.nextLine(); // Yeah..., clear chache somthing somthing
                if (choice == 1) {
                    break;
                } else if (choice == 2) {
                    changeName();
                } else if (choice == 3) {
                    Rules.ShowRules();
                    System.out.println("Helpfull, right");
                } else if (choice == 4) {
                    Settings();
                } else if (choice == 5) {
                    System.exit(1);
                } else {
                    System.out.println("Halvägs dit, du skrev ett tal!");
                }

            } catch (Exception e) {
                System.out.println("Common, give the inputs propely, im tired of error handeling!!!");
                System.out.println("Incorrect, give a value of: 1, 2, 3, 4 or 5");
                scanner.nextLine(); // Clear the buffer to handle the next input correctly
            }
        }
    }
}