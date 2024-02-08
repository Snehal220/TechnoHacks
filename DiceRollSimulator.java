package Snehal.Project;
import java.util.Random;

public class DiceRollSimulator {
    public static void main(String[] args) {
        // Create a Random object to simulate dice rolls
        Random rand = new Random();

        // Roll the first die
        int die1 = rand.nextInt(6) + 1;

        // Roll the second die
        int die2 = rand.nextInt(6) + 1;

        // Calculate the total sum of the dice rolls
        int total = die1 + die2;

        // Display the result
        System.out.println("Dice 1: " + die1);
        System.out.println("Dice 2: " + die2);
        System.out.println("Total: " + total);
    }
}
