import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Cell {
    // Filed information about each cell in the above map
    public Player owner;
    public int troops;
    public boolean isActive;
    public String name;
    public ArrayList<Cell> adjacent;
    public int iCoordinate;
    public int jCoordinate;
    public static int count = 0;

    public Cell(String name, Player owner, int troops, boolean isActive, int i, int j) {
        this.owner = owner;
        this.name = name;
        this.troops = troops;
        this.isActive = isActive;
        iCoordinate = i;
        jCoordinate = j;
        adjacent = new ArrayList<>();
    }

    private boolean dfs(Cell current, Cell target, Set<Cell> visited) { // With the great thanks from stackoverflow!
        // Check if the current cell is the target cell
        if (current.equals(target)) {
            return true;
        }
        // Mark the current cell as visited
        visited.add(current);
        // Iterate over all adjacent cells
        for (Cell adjacentCell : current.adjacent) {
            // Check if the adjacent cell has the same owner and has not been visited
            if (adjacentCell.getOwner().equals(this.getOwner()) && !visited.contains(adjacentCell)) {
                // Recursively call dfs for the adjacent cell
                if (dfs(adjacentCell, target, visited)) {
                    return true;
                }
            }
        }
        // Return false if the target cell is not reachable from the current cell
        return false;
    }

    // Getters and Setters
    public boolean isConnected(Cell target) {
        return dfs(this, target, new HashSet<>()); // The dfs algoritm
    }

    public boolean isAdjecent(Cell cell) {
        return adjacent.contains(cell);
    }
    public void addAdjacentCell(Cell cell) {
        if (cell.isActive && this.isActive) {
            adjacent.add(cell);
        }
    }
    public ArrayList<Cell> getAdjecentCells() {
        return this.adjacent;
    }

    public void setTroops(int value) {
        this.troops = value;
    }

    public String getName() {
        return name;
    }

    public boolean getActive() {
        return isActive;
    }

    public Player getOwner() {
        return owner;
    }

    public int getTroops() {
        return troops;
    }

    public void addTroops(int newTroops) {
        this.troops = troops + newTroops;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }
}