import cs2030.simulator.SimState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;
import java.util.Scanner;

/**
 * Assignment 2
 * @author A0200294R
 * @version CS2030 AY19/20 Sem 1 Assignment 2
 */
class Main {
    /**
     * The entry point of the program.
     * then run a simulation based on the input data.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimState simState = SimState.init(
                                scanner.nextInt(),
                                scanner.nextInt(),
                                scanner.nextInt(),
                                scanner.nextInt(),
                                scanner.nextInt(),
                                scanner.nextDouble(),
                                scanner.nextDouble(),
                                scanner.nextDouble(),
                                scanner.nextDouble(),
                                scanner.nextDouble()
                            );
        System.out.println(simState.run());
    }
}
