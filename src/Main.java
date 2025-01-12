import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void flushInput(Scanner cin) {
//        cin.nextLine(); // Consume the current line (which may be empty)

        // Loop through any additional empty lines in the buffer
        while (cin.hasNextLine() && cin.nextLine().equals("\n")) {
            // This consumes any empty lines
        }
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        Scanner cin = new Scanner(System.in);
        System.out.println("\t\t🐥FlappyBird🐥");
        System.out.println();
        char choice;
        System.out.println("Enter anything to start, q to quit.");
        System.out.print("->");
        choice=Character.toLowerCase(cin.next().charAt(0));
        while(choice!='q')
        {
            game.play();
            //flushInput(cin);
            System.out.println("Enter anything to start, q to quit.");
            System.out.print("->");
            choice=Character.toLowerCase(cin.next().charAt(0));
        }


    }
}