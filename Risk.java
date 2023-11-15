import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class Risk {
    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "123456789";
    public static int DIFFICULTY = 2;
    public static final int MAX_DURATION = 30;
    public static String name = "T3D";
    public static ArrayList<Player> players = new ArrayList<Player>();

    public static void main(String[] args) {
        Text.Welcome();

        startGame();
    }

    public static void startGame() {
        String userAction;

        AIPlayer.addPlayers();
        GenerateMap.Initiator();
        Text.StartText();
        int i = 0;
        while (players.size() > 1 && i++ < 100) {
            Iterator<Player> iterator = players.iterator();
            while (iterator.hasNext()) {
                Player player = iterator.next();
                if (player.territory.size() == 0) { // Removes dead players
                    iterator.remove();
                } else {
                    System.out.println("Its now the following players turn: " + player.getName());
                    player.setActive(true);
                    GenerateMap.plan.displayMap();
                    if (player.getAI()) { // Remove the false once AI is ready to be implemented
                        // Attack the adjacent cell with the least amout of soliders
                        // Add troops to a random owned tile
                        System.out.println(player.getColour() + player.getName());
                        AIPlayer aiPlayer = (AIPlayer) player;
                        if (i > 1) {
                            aiPlayer.decideTroopPlacement();
                        }
                        aiPlayer.aiAttack();
                        aiPlayer.decideTroopMovement();

                    } else {
                        if (i > 1) { // Means that there has been a previous round, so troops can be added
                            player.moreTroops();
                        }
                        while (true) {
                            System.out.println("\nDo you want to attack?. Y/N");
                            GenerateMap.plan.displayMap();
                            String in;
                            while (true) {
                                in = Text.scanner.nextLine();
                                if (in.equals("Y") || in.equals("N")) {
                                    break;
                                }
                                System.out.println("Y or N");
                            }
                            if (in.equals("Y")) {
                                userAction = userInput(player, true);
                                Attack(userAction.substring(0, userAction.indexOf("-")),
                                        userAction.substring(userAction.indexOf("-") + 1)); // Just splits the user
                                                                                            // input
                            } else {
                                break;
                            }
                        }
                        MoveTroops(player); // Now time to move pieces
                    }

                }
            }
            // End of everyones turn, do cards more troops etc
            Sleep(2000);
            // GenerateMap.clearConsole();
            System.out.println("That was the " + i + "th round of turns");
        }
        GenerateMap.clearConsole();
        System.out.println("We have a winner!!! IT IS: ");
        System.out.println(players.get(0).getName());
    }

    public static Cell inputToCell(String input) {
        int i1 = LETTERS.indexOf(input.substring(0, 1));
        int j1 = Integer.parseInt(input.substring(1)) - 1;
        return GenerateMap.plan.map[j1][i1];
    }

    public static void MoveTroops(Player player) {
        if (player.CanEvenMove()) {
            System.out.println("Do you want to move troops? Y/N - wont check this so say 'Y' or 'N'");
            GenerateMap.plan.displayMap();
            String in;
            while (true) {
                in = Text.scanner.nextLine();
                if (in.equals("Y") || in.equals("N")) {
                    break;
                }
                System.out.println("Y or N");
            }
            if (in.equals("Y")) {
                String input = userInput(player, false);
                Cell from1 = inputToCell(input.substring(0, input.indexOf("-")));
                Cell to1 = inputToCell(input.substring(input.indexOf("-") + 1));

                System.out.println("How many troops do you want to move, MAX: " + from1.getTroops());
                int move = Text.scanner.nextInt();
                while (move > from1.getTroops()) {
                    System.out.println("incorrect troop allocation!");
                    move = Text.scanner.nextInt();
                }
                movment(from1, to1, move);
                Text.scanner.nextLine(); // Reset the buffer
            } else {
                System.out.println("Okay, now wait for ur next turn!");
            }
        } else {
            System.out.println("Ur position is terrible and you cant move any troops");
        }
    }

    public static void movment(Cell from, Cell to, int num) {
        to.addTroops(num);
        from.addTroops(-num);
    }

    static String userInput(Player player, boolean action) { // Takes user inputs in the form of co-ordinates and makes
                                                             // sure they are both valid and legal
        boolean legalInput = false; // If it works in the games logic
        boolean validInput = false; // If the formatting is correct
        String input;
        String from;
        String to;
        System.out.println();
        System.out.println();
        while (true) {
            if (action) {
                System.out.println("Who do " + player.getName() + " want to attack, here is the currect map: ");
            } else {
                System.out.println("Where do you want to move your troops, you can move one stack");
            }
            System.out.println("In the form of A1-A2");
            GenerateMap.plan.displayMap();
            try {
                input = Text.scanner.nextLine();
                from = input.substring(0, input.indexOf("-"));
                to = input.substring(input.indexOf("-") + 1);
                // Format and legality
                if (input.length() == 5 && CheckFormating(from) && CheckFormating(to)) {
                    validInput = true; // This will ensure that the input holds the format A1-A2 or B5-C2 for example,
                                       // checks validity
                } else {
                    System.out.println(
                            "The formatting is incorrect, choose between the upper letters then a number: A2-A3 or B2-A4");
                }
                if (legalMove(from, to, action) && validInput) {
                    return input; // Checks legality
                } else {
                    System.out.println("The input was formatted correctly, but not legal");
                    System.out.println(
                            "Read the rules, or maybe dont attack out of bound, yourself and only an adjecent (alive) tile");
                }

            } catch (Exception e) {
                System.out.println(e + " Knaaas");
            }
        }
    }

    public static boolean CheckFormating(String word) {
        String letters = LETTERS.substring(0, GenerateMap.size);
        String numbers = NUMBERS.substring(0, GenerateMap.size);

        return (letters.contains(word.substring(0, 1)) && numbers.contains(word.substring(1)));
    }

    public static void Message(Cell attacker, Cell defender) {
        System.out.println(attacker.getOwner().getName() + " is attempting to attack " + defender.getOwner().getName());
        System.out.println(attacker.getTroops() + " are facing off aginast " + defender.getTroops());
    }

    public static void Attack(String from, String to) {
        Cell attacker = inputToCell(from);
        Cell defender = inputToCell(to);

        Message(attacker, defender);

        battleSequence(attacker, defender);
    }

    public static void BattleVisuals(Cell attacker, Cell defender) {
        String att = "";
        String deff = "";
        for (int i = 0; i < attacker.getTroops(); i++) {
            att += "I";
        }
        for (int i = 0; i < defender.getTroops(); i++) {
            deff += "I";
        }
        System.out.println(attacker.getOwner().getColour() + att + " vs " + defender.getOwner().getColour() + deff
                + Player.ANSI_RESET);

    }

    public static void Sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void battleSequence(Cell attacker, Cell defender) {
        int i = 0;
        int attackerDice;
        int defenderDice;
        int dmg;
        while (i++ < MAX_DURATION && attacker.getTroops() > 0 && defender.getTroops() > 0) {
            Sleep(1000);
            System.out.println("In the " + (i) + " round the battle rages on! Its " + attacker.getTroops() + " vs "
                    + defender.getTroops());
            BattleVisuals(attacker, defender);
            attackerDice = GenerateMap.random.nextInt(6) + 1;
            defenderDice = GenerateMap.random.nextInt(6) + 1 + DIFFICULTY - 2;
            if (attackerDice > defenderDice) {
                dmg = attacker.getTroops() < attackerDice - defenderDice ? attacker.getTroops()
                        : attackerDice - defenderDice; // 1 trooper cant damage 5 enemies
                dmg = dmg * -1;
                if (defender.getTroops() > dmg * -1) {
                    defender.addTroops(dmg);
                } else {
                    defender.setTroops(0); // Should be zero
                }
            } else { // If they are equal does not matter
                dmg = defender.getTroops() < defenderDice - attackerDice ? defender.getTroops()
                        : defenderDice - attackerDice;
                dmg = dmg * -1;
                if (attacker.getTroops() > dmg * -1) {
                    attacker.addTroops(dmg);
                } else {
                    attacker.setTroops(0);
                }
            }
            System.out.println(attacker.getOwner().getName() + " rolled a: " + attackerDice + " vs the defenders: "
                    + defenderDice);
            // Battle
        }
        if (i == 30) {
            System.out.println("The battle timed out, tough luck");
        } else if (attacker.getTroops() == 0) {
            System.out.println("  ___     _ _              _ \r\n" + //
                    " | __|_ _(_) |_  _ _ _ ___| |\r\n" + //
                    " | _/ _` | | | || | '_/ -_)_|\r\n" + //
                    " |_|\\__,_|_|_|\\_,_|_| \\___(_)\r\n" + //
                    "                             ");
            System.out.println("The attack failed and all your troops are dead");
            Sleep(500);
        } else {
            System.out.println("  ___                      _ \r\n" + //
                    " / __|_  _ __ __ ___ _____| |\r\n" + //
                    " \\__ \\ || / _/ _/ -_|_-<_-<_|\r\n" + //
                    " |___/\\_,_\\__\\__\\___/__/__(_)\r\n" + //
                    "                             ");
            System.out.println(defender.getOwner().getName() + ":s troops were all slayn defending " +
                    defender.getName() + " and the territory is now occupied by " + attacker.getOwner().getName());
            Sleep(500);
            int move;
            if (attacker.getOwner().getAI()) {
                move = attacker.getTroops() / 2;
                System.out.println(move);
            } else {
                System.out.println("How many troops do you want to move, max: " + attacker.getTroops());
                move = Text.scanner.nextInt();
                while (move > attacker.getTroops()) {
                    System.out.println("incorrect troop allocation!");
                    move = Text.scanner.nextInt();
                }
                Text.scanner.nextLine(); // Reset the buffer
            }
            movment(attacker, defender, move);

            defender.getOwner().removeTerritory(defender);
            attacker.getOwner().addTerritory(defender);
            defender.setOwner(attacker.getOwner());
        }
    }

    public static boolean legalMove(String from, String to, boolean action) { // Action true = attack move otherwise its
                                                                              // end of turn move
        Cell from1 = inputToCell(from);
        Cell to1 = inputToCell(to);
        if (action) {
            if (from1.getOwner().isActive() && !from1.getOwner().equals(to1.getOwner())) {
                return from1.isAdjecent(to1);
            } else {
                System.out.println("You can only choose to attack wiht your own pieces");
                return false;
            }
        } else {
            return from1.getOwner().equals(to1.getOwner()) && from1.isConnected(to1);
        }
    }
}
