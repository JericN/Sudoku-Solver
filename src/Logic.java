import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Logic {
    Variable var = new Variable();
    int emptyCellCount = 0;
    ArrayList<ArrayList<Integer>> emptyCell = new ArrayList<ArrayList<Integer>>();
    int[][][] flagVals;
    int[][] cell;

    public void start() {
        System.out.println("Logic Started");
        cell = var.getCell();
        flagVals = new int[9][9][9];
        getPossibleSolutions();
        sortFlags();
        if (iterateSolutions(0)) {
            System.out.println("Solution Found");
        } else {
            System.out.println("Invalid Sudoku");
        }
        System.out.println("Logic Stopped");
    }

    public void getPossibleSolutions() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (cell[y][x] == 0) {
                    int val = 1;
                    for (int i = 1; i < 10; i++) {
                        boolean flag = true;
                        for (int k = 0; k < 9; k++) {
                            if (i == cell[y][k]) {
                                flag = false;
                            }
                            if (i == cell[k][x]) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            flagVals[y][x][val] = i;
                            val++;
                        }
                    }
                    flagVals[y][x][0] = val - 1;
                }
            }
        }
    }

    static int count = 0;

    public boolean iterateSolutions(int nPos) {
        for (int i = nPos; i < emptyCellCount; i++) {
            if (i == emptyCellCount-1) {
                count++;
                System.out.println("DONE: " + count);
                for (int k = 0; k < 9; k++) {
                    for (int j = 0; j < 9; j++) {
                        System.out.print("  " + cell[k][j]);
                    }
                    System.out.println();
                }
                return checkSolution();
            }
            int yPos = emptyCell.get(i).get(0);
            int xPos = emptyCell.get(i).get(1);
            for (int k = 0; k < emptyCell.get(i).get(2); k++) {
                cell[yPos][xPos] = emptyCell.get(i).get(k + 3);
                if (iterateSolutions(i + 1)) {
                    return true;
                }
            }
            cell[yPos][xPos] = 0;
            return false;
        }
        return false;
    }

    public boolean checkSolution() {
        boolean flag = true;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                flag = true;
                int temp = cell[y][x];
                for (int k = 0; k < 9; k++) {
                    if (x != k && temp == cell[y][k]) {
                        flag = false;
                    }
                    if (y != k && temp == cell[k][x]) {
                        flag = false;
                    }
                }
                if (!flag) {
                    System.out.println("Short False");
                    return false;
                }
            }
        }
        if (flag) {
            System.out.println("Returned True");
            return true;
        } else {
            System.out.println("Returned False");
            return false;
        }
    }

    public void print() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                for (int k = 1; k < flagVals[y][x][0]; k++) {
                    System.out.println(flagVals[y][x][k]);
                }
                System.out.println("\n\n");
            }
        }
    }


    public void sortFlags() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (flagVals[y][x][0] != 0) {
                    emptyCell.add(new ArrayList<Integer>());
                    emptyCell.get(emptyCellCount).add(y);
                    emptyCell.get(emptyCellCount).add(x);
                    emptyCell.get(emptyCellCount).add(flagVals[y][x][0]);
                    for (int j = 0; j < flagVals[y][x][0]; j++) {
                        emptyCell.get(emptyCellCount).add(flagVals[y][x][j + 1]);
                    }
                    emptyCellCount++;
                }
            }
        }

        for (int i = 0; i < emptyCellCount; i++) {
            ArrayList<Integer> key = (ArrayList<Integer>) emptyCell.get(i).clone();
            int j = i - 1;
            while (j >= 0 && emptyCell.get(j).get(2) > key.get(2)) {
                emptyCell.get(j + 1).clear();
                emptyCell.get(j + 1).addAll(emptyCell.get(j));
                j = j - 1;
            }
            emptyCell.get(j + 1).clear();
            emptyCell.get(j + 1).addAll(key);
        }
        for (int i = 0; i < emptyCellCount; i++) {
            for (int j = 0; j < emptyCell.get(i).get(2)+3; j++) {
                System.out.print(" "+emptyCell.get(i).get(j));
            }
            System.out.println();
        }
    }
}
