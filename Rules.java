import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Rules {
    public static void ShowRules() {
     try {
            BufferedReader file = new BufferedReader(new FileReader("Rules.txt"));

            String line = file.readLine();

            while (line != null) { // Kör tills texten tar slut
                System.out.println(line);
                line = file.readLine();
            }
            file.close();
        } catch (IOException e) {
            System.out.println("ERROR!: " + e.getMessage());
            System.exit(1);
        }
    }
}