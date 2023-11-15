import java.util.ArrayList;

public class AIPlayer extends Player {
    public static void addPlayers() {
        AIPlayer russia = new AIPlayer("Russia", START_TROOPS, ANSI_BLUE);
        AIPlayer china = new AIPlayer("China", START_TROOPS, ANSI_RED);
        AIPlayer iran = new AIPlayer("Iran", START_TROOPS, ANSI_GREEN);
        AIPlayer you = new AIPlayer(Risk.name, START_TROOPS, ANSI_RESET);

        Risk.players.add(you);
        Risk.players.add(russia);
        Risk.players.add(china);
        Risk.players.add(iran);
    }

    public AIPlayer(String name, int troopsToDeploy, String colour) {
        super(name, troopsToDeploy, colour); // The 'true' here sets the 'ai' flag
    }

    public void decideTroopPlacement() {
        this.troopsToDeploy = 3 + territory.size() / 2;
        Cell weak = null;
        int minTroops = Integer.MAX_VALUE;
        ; // max value didnt work
        for (Cell territory : this.territory) {
            if (territory.getTroops() < minTroops) {
                minTroops = territory.getTroops();
                weak = territory;
            }
        }
        System.out.println(this.getColour() + this.getName() + " decided to place this many troops on this cell: "
                + troopsToDeploy + weak.getName());
        weak.addTroops(troopsToDeploy); // Adds the troops to the weak cell
        this.deployTroops(troopsToDeploy); // Tells the player that no troops are available to be deployed
    }

    public void aiAttack() {
        while (true) { // To attack multiple times
            Cell[] attDeff = maxTroopDiff();
            if (attDeff[1].getTroops() == 0) {
                decideAttack(attDeff); 
            }
            else if (attDeff[0].getTroops() - attDeff[1].getTroops() > 2) {
                decideAttack(attDeff); 
            } else {
                break;
            }
            GenerateMap.plan.displayMap();
        }

    }

    public Cell[] maxTroopDiff() {
        int troopDiff = Integer.MIN_VALUE;
        Cell weakDef = null;
        Cell attack = null;
        for (Cell territory : this.territory) {
            for (Cell adjacent : territory.getAdjecentCells()) {
                if (territory.getTroops() - adjacent.getTroops() > troopDiff
                        && adjacent.getOwner() != territory.getOwner()) {
                    troopDiff = territory.getTroops() - adjacent.getTroops();
                    weakDef = adjacent;
                    attack = territory;
                }
            }
        }
        return new Cell[] { attack, weakDef };
    }

    public void decideAttack(Cell[] attDeff) {
        System.out.println(this.getColour() + this.getName() + " decided to attack " + attDeff[1].getName() + " from "
                + attDeff[0].getName());
        Risk.battleSequence(attDeff[0], attDeff[1]); // Attack from a cell, to another: attack is from. and weakDef is
                                                     // to.
    }

    public void decideTroopMovement() {
        if (this.CanEvenMove()) {
            Cell origin = null;
            Cell target = null;
            int lagrestTroopDifference = 0;
            for (Cell strongest : territory) {
                for (Cell weakest : territory) {
                    if (strongest.isConnected(weakest)
                            && strongest.getTroops() - weakest.getTroops() > lagrestTroopDifference) {
                        lagrestTroopDifference = strongest.getTroops() - weakest.getTroops();
                        origin = strongest;
                        target = weakest;
                    }
                }
            }
            if (origin != null && target != null) {
                // Move these between here
                // Move halv the difference
                int toMove = (origin.getTroops() - target.getTroops()) / 2;
                System.out.println(this.getColour() + this.getName() + " decided to move " + toMove + " troops from: "
                        + origin.getName() + " to " + target.getName());
                Risk.movment(origin, target, toMove);
            }
        }
    }
}