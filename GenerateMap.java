import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateMap {
    public Cell[][] map;
    public static int size;
    public static Random random = new Random();
    public static GenerateMap plan;
    public static double AMOUNT_DEAD_TERRAIN = 0.3;

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
        do {
            String name;
            Player owner;
            int troops = 1; // Easier to add later
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // Fill in each cell
                    boolean isActive = random.nextDouble() >= AMOUNT_DEAD_TERRAIN;

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
            }
        } while (!this.isValidMap()); // Ensures corect terrain map

        Player.startTroops(); // Each cells should not have one troop
        SetAdjecentCells();
    }
    /**Uses a flood fill algoritm to see if all active cells are connected
    /* @return wheather or not the generated map is valid*/
    public boolean isValidMap() {
        // Find the first active cell to start the flood fill.
        Cell startCell = this.findFirstActiveCell();
        if (startCell == null)
            return false; // No active cells found, invalid map.

        // Create a visited array, initialized to false.
        boolean[][] visited = new boolean[map.length][map[0].length];

        // Perform the flood fill from the start cell.
        floodFill(map, startCell.iCoordinate, startCell.jCoordinate, visited);

        // Check if all active cells were visited.
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].isActive && !visited[i][j]) {
                    return false; // Found an unvisited active cell, invalid map.
                }
            }
        }
        return true; // All active cells are connected.
    }

    public void floodFill(Cell[][] map, int i, int j, boolean[][] visited) {
        // Check bounds and if the cell is already visited or inactive.
        if (i < 0 || i >= map.length || j < 0 || j >= map[0].length ||
                visited[i][j] || !map[i][j].isActive) {
            return;
        }

        // Mark the cell as visited.
        visited[i][j] = true;

        // Call floodFill recursively in all four directions.
        floodFill(map, i + 1, j, visited); // South
        floodFill(map, i - 1, j, visited); // North
        floodFill(map, i, j + 1, visited); // East
        floodFill(map, i, j - 1, visited); // West
    }

    public Cell findFirstActiveCell() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].isActive) {
                    return map[i][j];
                }
            }
        }
        return null; // No active cells found.
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
