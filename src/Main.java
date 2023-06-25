import java.util.Random;
import java.util.Scanner;

class SudokuGame {
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private static final int MINIMUM_CLUES = 17;

    private int[][] board;
    private Random random;

    public SudokuGame() {
        board = new int[SIZE][SIZE];
        random = new Random();
    }

    public void generatePuzzle(int difficultyLevel) {
        // Clear the board
        clearBoard();

        // Generate a complete Sudoku solution
        generateSolution();

        // Remove cells based on the desired difficulty level
        int cellsToBeRemoved = getCellsToBeRemoved(difficultyLevel);
        removeCells(cellsToBeRemoved);
    }

    private void clearBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = EMPTY_CELL;
            }
        }
    }

    private void generateSolution() {
        solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if (col == SIZE) {
            col = 0;
            row++;
            if (row == SIZE) {
                return true; // All cells filled, solution found
            }
        }

        if (board[row][col] != EMPTY_CELL) {
            return solve(row, col + 1); // Cell already filled, skip to the next one
        }

        // Try different numbers in the current cell
        for (int num = 1; num <= SIZE; num++) {
            if (isValidPlacement(row, col, num)) {
                board[row][col] = num;
                if (solve(row, col + 1)) {
                    return true; // Solution found
                }
                // Backtrack and try a different number
                board[row][col] = EMPTY_CELL;
            }
        }

        return false; // No solution found
    }

    private boolean isValidPlacement(int row, int col, int num) {
        // Check if the number already exists in the same row or column
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Check if the number already exists in the same 3x3 box
        int boxRow = row - (row % 3);
        int boxCol = col - (col % 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRow + i][boxCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private int getCellsToBeRemoved(int difficultyLevel) {
        switch (difficultyLevel) {
            case 1: return 30; // Easy
            case 2: return 40; // Medium
            case 3: return 50; // Hard
            default: return 40; // Default to medium difficulty
        }
    }

    private void removeCells(int cellsToBeRemoved) {
        int cellsRemoved = 0;
        while (cellsRemoved < cellsToBeRemoved) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != EMPTY_CELL) {
                board[row][col] = EMPTY_CELL;
                cellsRemoved++;
            }
        }
    }

    public void printBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        Scanner sc = new Scanner(System.in);
        System.out.println("Rules: In this game you have to select your difficulty level and then you have to replace the empty cells with number which is represented in 0. ");
        System.out.println("      Each row and column should have different digit and number should not be repeated.");
        System.out.println("Select your difficulty level: (IF YOU ENTER ANY OTHER OPTION THEN COMPILER AUTOMATICALLY SET ON MEDIUM LEVEL)");
        System.out.println("For Easy Enter 1");
        System.out.println("For Medium Enter 2");
        System.out.println("For Hard Enter 3 ");
        System.out.print("Enter your choice here: ");
        int dl = sc.nextInt();
        game.generatePuzzle(dl); // Generate a medium difficulty puzzle
        game.printBoard();
    }
}