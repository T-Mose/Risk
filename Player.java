import java.util.ArrayList;

public class Player {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final int START_TROOPS = 0;

    public String name;
    public ArrayList<Cell> territory;
    public int troopsToDeploy;
    public Boolean active;
    public String colour; // The code for changing colour in the terminal
    // public ArrayList<Card> cards; might want to add

    public Player(String name, int troopsToDeploy, String colour) {
        this.territory = new ArrayList<>();
        this.name = name;
        this.troopsToDeploy = troopsToDeploy;
        this.colour = colour;
        this.active = false;
        // Constructor
    }

    public boolean CanEvenMove() {
        for (Cell cells : this.getTerritory()) {
            for (Cell adjCell : cells.getAdjecentCells()) {
                if (cells.getOwner().equals(adjCell.getOwner())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void startTroops() {
        int ran;
        int start;
        int tem;
        int rem;
        for (Player player : Risk.players) {
            player.troopsToDeploy = GenerateMap.size * GenerateMap.size;
            start = player.troopsToDeploy;
            tem = start / player.territory.size();
            rem = start % player.territory.size();
            for (Cell cell : player.territory) {
                // size * size number of cells, will gett rougly that / 5 number of cells per
                // player -> total / (size^2) / 5
                ran = tem - GenerateMap.random.nextInt(3);
                if (ran < player.troopsToDeploy) {
                    cell.addTroops(ran);
                    player.deployTroops(ran);
                }
            }
            player.troopsToDeploy += rem; // To account for the integer division
            while (player.troopsToDeploy > 0) {
                for (Cell cell : player.territory) {
                    if (player.troopsToDeploy > 0) {
                        cell.addTroops(1);
                        player.deployTroops(1);
                    } 
                }
            }
        }
    }
    public void moreTroops() { // Adds more troops
        String input;
        troopsToDeploy = 3 + territory.size()/2;
        System.out.println("Where do you want to deploy your new troops, x: " + troopsToDeploy);
        GenerateMap.plan.displayMap();
        while (true) {
            input = Text.scanner.nextLine();
            if (Risk.inputToCell(input).getOwner() == null) {
                System.out.println("Dead ting");
            } else if (Risk.CheckFormating(input) && Risk.inputToCell(input).getOwner().equals(this)) {
                break;
            }
            System.out.println("Incorrect user input, choose a valid input, and one of ur owne cells");
        }
        Risk.inputToCell(input).addTroops(troopsToDeploy);
        deployTroops(troopsToDeploy);
    }
    public boolean getAI(){
        return this instanceof AIPlayer;
    }
    public String getColour() {
        return colour;
    }
    public ArrayList<Cell> getTerritory() {
        return this.territory;
    }

    public void addTerritory(Cell Cell) {
        territory.add(Cell);
    }
    public boolean isActive() {
        return this.active;
    }

    public void removeTerritory(Cell Cell) {
        territory.remove(Cell);
    }

    public String getName() {
        return this.name;
    }
    public void setActive(boolean value) {
        this.active = value;
    }

    public void deployTroops(int toDeploy) {
        troopsToDeploy = troopsToDeploy - toDeploy;
    }
}