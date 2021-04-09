import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Logic {
    Variable var = new Variable();
    static int count;
    int emptyCellCount = 0;
    ArrayList<ArrayList<Integer>> emptyCell = new ArrayList<ArrayList<Integer>>();
    int[][][] flagVals;
    int[][] cell;

    public void start() {
        System.out.println("Logic Started");
        count = 0;
        cell = var.getCell();
        flagVals = new int[9][9][9];
        getPossibleSolutions();
        sortFlags();
        if (iterateSolutions(0)) {
            System.out.println("Solution Found");
            for (int k = 0; k < 9; k++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print("  " + cell[k][j]);
                }
                System.out.println();
            }
        } else {
            System.out.println("Invalid Sudoku");
        }
        System.out.println("Logic Stopped");
        for (int i = 0; i < emptyCellCount; i++) {
            for (int j = 0; j < emptyCell.get(i).get(2) + 3; j++) {
                System.out.print(" " + emptyCell.get(i).get(j));
            }
            System.out.println();
        }
    }

    public void getPossibleSolutions() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (cell[y][x] == 0) {
                    int val = 1;
                    for (int num = 1; num < 10; num++) {
                        boolean flag = true;
                        for (int k = 0; k < 9; k++) {
                            if (num == cell[y][k]) {
                                flag = false;
                            }
                            if (num == cell[k][x]) {
                                flag = false;
                            }
                        }
                        int xZone = (int) Math.ceil((float)(x + 1) / 3);
                        int yZone = (int) Math.ceil((float)(y + 1) / 3);
                        for (int i = (yZone - 1) * 3; i < yZone * 3; i++) {
                            for (int j = (xZone - 1) * 3; j < xZone * 3; j++) {
                                if (i != y || j != x) {
                                    if (num == cell[i][j]) {
                                        flag = false;
                                    }
                                }
                            }
                        }
                        if (flag) {
                            flagVals[y][x][val] = num;
                            val++;
                        }
                    }
                    flagVals[y][x][0] = val - 1;
                }
            }
        }
    }

    public boolean iterateSolutions(int nPos) {
        if (nPos == emptyCellCount) {
            count++;
            System.out.println("DONE: " + count);
            return checkSolution();
        } else {
            int yPos = emptyCell.get(nPos).get(0);
            int xPos = emptyCell.get(nPos).get(1);
            for (int k = 0; k < emptyCell.get(nPos).get(2); k++) {
                cell[yPos][xPos] = emptyCell.get(nPos).get(k + 3);
                if (checkPossible(yPos,xPos)){
                    if (iterateSolutions(nPos + 1)) {
                        return true;
                    }
                }
            }
            cell[yPos][xPos] = 0;
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
                int xZone = (int) Math.ceil((float)(x + 1) / 3);
                int yZone = (int) Math.ceil((float)(y + 1) / 3);
                for (int i = (yZone - 1) * 3; i < yZone * 3; i++) {
                    for (int j = (xZone - 1) * 3; j < xZone * 3; j++) {
                        if (i != y || j != x) {
                            if (temp == cell[i][j]) {
                                flag = false;
                            }
                        }
                    }
                }
                if (!flag) {
                    return false;
                }
            }
        }
        if (flag) {
            System.out.println("Returned True");
            return true;
        } else {
            return false;
        }
    }
    public boolean checkPossible(int y, int x){
        boolean flag = true;
        int temp = cell[y][x];
        for (int k = 0; k < 9; k++) {
            if (x != k && temp == cell[y][k]) {
                flag = false;
            }
            if (y != k && temp == cell[k][x]) {
                flag = false;
            }
        }
        int xZone = (int) Math.ceil((float)(x + 1) / 3);
        int yZone = (int) Math.ceil((float)(y + 1) / 3);
        for (int i = (yZone - 1) * 3; i < yZone * 3; i++) {
            for (int j = (xZone - 1) * 3; j < xZone * 3; j++) {
                if (i != y || j != x) {
                    if (temp == cell[i][j]) {
                        flag = false;
                    }
                }
            }
        }
        return flag;
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
    }

    public void print() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                for (int k = 1; k < flagVals[y][x][0]; k++) {
                    if(flagVals[y][x][k]!=0){
                        System.out.print(flagVals[y][x][k]);
                    }
                }
                System.out.println("\n");
            }
        }
    }


}
