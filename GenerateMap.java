import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateMap {
    public Cell[][] map;
    public static int size;
    public static Random random = new Random();
    public static GenerateMap plan;

    public static void Initiator() {
        int num = 0;
        while (true) {
            System.out.println("What size do you want the map to be X by X: 5, 7 or 9?"); // Add later any size to be
                                                                                          // possible
            try {
                num = Text.scanner.nextInt();
                Text.scanner.nextLine();
                if (num == 5 || num == 7 || num == 9) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Common, give the inputs propely, im tired of error handeling!!!");
            }
        }
        clearConsole();
        plan = new GenerateMap(num);
    }

    public GenerateMap(int num) {
        size = num;
        map = new Cell[size][size];
        FillMap();
    }

    public static void clearConsole() { // Not my code, but hey, it works!
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                // Alternatively, you can execute the clear command for Unix systems
                // new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            // Handle exceptions
        }
    }

    public void FillMap() {
        // valid = false;
        String name;
        Player owner;
        int troops = 1; // Easier to add later
        // while (!valid) { // Try creating a map until one is correct
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Fill in each cell
                boolean isActive = random.nextDouble() >= 0.2;

                if (isActive) {
                    name = "Cell_" + Risk.LETTERS.charAt(j) + "_" + Risk.NUMBERS.charAt(i);
                    owner = Risk.players.get(random.nextInt(Risk.players.size()));
                    map[i][j] = new Cell(name, owner, troops, true, i, j);
                    owner.addTerritory(map[i][j]);
                    owner.deployTroops(1); // To make sure every cell has at least one trooper
                } else { // For the dead cells
                    map[i][j] = new Cell("Water", null, 0, false, i, j);
                }
            }
            // valid = CheckIfValid(map.clone()); // Make sure the created map is allowed
        }
        Player.startTroops(); // Each cells should not have one troop
        SetAdjecentCells();
    }

    public void SetAdjecentCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Add the adjacent cells to the list
                // Make sure to check for boundary conditions
                if (i > 0)
                    map[i][j].addAdjacentCell(map[i - 1][j]); // Up
                if (i < size - 1)
                    map[i][j].addAdjacentCell(map[i + 1][j]); // Down
                if (j > 0)
                    map[i][j].addAdjacentCell(map[i][j - 1]); // Left
                if (j < size - 1)
                    map[i][j].addAdjacentCell(map[i][j + 1]); // Right
            }
        }
    }

    public void displayMap() {
        String letters = Risk.LETTERS;
        String colour;
        String troops;
        String initial;
        String formated;
        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("   ");
                } else if (i == 0) {
                    System.out.print(letters.charAt(j - 1) + "   ");
                } else if (j == 0) {
                    System.out.print(i + "  ");
                } else {
                    if (map[i - 1][j - 1].getOwner() == null) {
                        System.out.print('#' + "   ");
                    } else {
                        colour = map[i - 1][j - 1].getOwner().getColour();
                        troops = String.valueOf(map[i - 1][j - 1].getTroops());
                        initial = String.valueOf(map[i - 1][j - 1].getOwner().getName().charAt(0));

                        formated = String.format("%-4s", troops + initial);
                        System.out.print(colour + formated + Player.ANSI_RESET);
                    }
                }
            }
            System.out.println();
        }

    }

    public static boolean CheckIfValid(ArrayList<Cell> copy) {
        // Do some checks if this is correct
        // Like check if there are any fully inveloped areas etc.
        return false;
    }

}
