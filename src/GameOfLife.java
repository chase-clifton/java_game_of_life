import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class GameOfLife {
    public static void main(String [] args) {
        if (args.length != 2) {
            System.exit(0);
        }

        final int rowsLength = Integer.parseInt(args[0]);
        final int colsLength = Integer.parseInt(args[1]);

        Scanner scanner = new Scanner(System.in);

        boolean loop = true;
        int count = 0;
        Random randomno = new Random();

        boolean[][] currentGeneration = new boolean[rowsLength][colsLength];
        for (int i = 0; i < rowsLength; i++ ) {
            for (int j = 0; j < colsLength; j++) {
                currentGeneration[i][j] = randomno.nextBoolean();
            }
        }

        while (loop) {
            if (count > 0) {
                boolean[][] nextGeneration = new boolean[rowsLength][colsLength];

                for (int i = 0; i < rowsLength; i++) {
                    for (int j = 0; j < colsLength; j++) {
                        int liveNeighbours = 0;

                        // Check all 8 adjacent neighbours
                        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                                if ( i + rowOffset < 0 ||
                                    i + rowOffset > rowsLength - 1 ||
                                    j + colOffset < 0 ||
                                    j + colOffset > colsLength - 1 ||
                                    (rowOffset == 0 && colOffset == 0)
                                ) {
                                    continue;
                                }

                                if (currentGeneration[i + rowOffset][j + colOffset]) {
                                    liveNeighbours++;
                                }
                            }
                        }

                        switch (liveNeighbours) {
                            case 2: // No change
                                nextGeneration[i][j] = currentGeneration[i][j];
                                break;
                            case 3: // Dead cells are reborn or stay alive
                                nextGeneration[i][j] = true;
                                break;
                            default:
                                // fewer than 2, die or stay dead
                                // 4 of more -- die of overpopulation or stay dead
                                nextGeneration[i][j] = false;
                        }
                    }
                }

                if (Arrays.deepEquals(currentGeneration, nextGeneration)) {
                    System.out.println("Life has stalled. You ran " + count + " generation(s).\n\r");
                    System.exit(0);
                }

                currentGeneration = nextGeneration;
            }

            for (boolean[] row : currentGeneration) {
                for (boolean cell : row) {
                    System.out.print(cell ? '#' : '-');
                }
                System.out.println();
            }

            count++;

            System.out.println("\n\rType X to cancel or anything for life to continue evolving.\n\r");
            String s = scanner.nextLine();

            if (s.toLowerCase().equals("x")) {
                System.out.println("Thank you for playing. You ran " + count + " generation(s).\n\r");
                System.exit(0);
            }
        }
    }
}
