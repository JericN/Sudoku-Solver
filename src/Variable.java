import java.util.ArrayList;

public class Variable {
    static int[][] cell;
    static int numSolutions;
    static ArrayList<Integer>[] invalidCell;
    static int invalidCount;
    static ArrayList<int[][]> solutionsList;
    static ArrayList<int[][]> savedState;

    public Variable() {
        cell = new int[9][9];
        solutionsList = new ArrayList<int[][]>();
        savedState = new ArrayList<int[][]>();
        for (int i = 0; i < 4; i++) {
            savedState.add(new int[9][9]);
        }
        numSolutions = 100;
        invalidCell = new ArrayList[2];
        invalidCell[0] = new ArrayList<Integer>();
        invalidCell[1] = new ArrayList<Integer>();
        invalidCount = 0;
    }

    public void setCell(int[][] input) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j] = input[i][j];
            }
        }
    }

    public void setCell(int y, int x, int num) {
        cell[y][x] = num;
    }

    public int[][] getCell() {
        int[][] ret = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ret[i][j] = cell[i][j];
            }
        }
        return ret;
    }

    public void resetCell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j] = 0;
            }
        }
    }


    public void addSolution(int[][] solution) {
        int[][] temp = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp[i][j] = solution[i][j];
            }
        }
        solutionsList.add(temp);
    }

    public ArrayList<int[][]> getSolutions() {
        return solutionsList;
    }

    public void resetSolutions() {
        solutionsList = new ArrayList<int[][]>();
    }

    public int getSolutionCount() {
        return solutionsList.size();
    }

    public void setTargetSolutionsCount(int val) {
        numSolutions = val;
    }

    public int getTargetSolutionsCount() {
        return numSolutions;
    }


    public void saveState(int index, int[][] grid) {
        System.out.println("SAVE: " + index);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                savedState.get(index)[i][j] = grid[i][j];
            }
        }
    }

    public int[][] loadState(int index) {
        System.out.println("LOAD: " + index);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j] = savedState.get(index)[i][j];
            }
        }
        return cell;
    }


    public void addInvalids(int y, int x) {
        invalidCell[0].add(y);
        invalidCell[1].add(x);
        invalidCount++;
    }

    public ArrayList<Integer>[] getInvalids() {
        return invalidCell;
    }

    public int getInvalidCount() {
        return invalidCount;
    }

    public boolean isInvalid(int y, int x) {
        for (int i = 0; i < invalidCount; i++) {
            if (y == invalidCell[0].get(i) && x == invalidCell[1].get(i)) {
                return true;
            }
        }
        return false;
    }

    public void removeInvalid(int y, int x) {
        for (int i = 0; i < invalidCount; i++) {
            if (y == invalidCell[0].get(i) && x == invalidCell[1].get(i)) {
                invalidCell[0].remove(i);
                invalidCell[1].remove(i);
                i--;
                invalidCount--;
            }
        }
    }

    public void resetInvalid() {
        invalidCell[0] = new ArrayList<Integer>();
        invalidCell[1] = new ArrayList<Integer>();
        invalidCount = 0;
    }
}
