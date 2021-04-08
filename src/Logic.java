public class Logic {
    Variable var = new Variable();
    int[][][] flagVals;
    int[][] cell;

    public void start() {
        System.out.println("Logic Started");
        cell = var.getCell();
        flagVals = new int[9][9][9];
        getPossibleSolutions();
        if (iterateSolutions(0, 0)) {
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
                    flagVals[y][x][0] = val;
                }
            }
        }
    }

    static int count = 0;

    public boolean iterateSolutions(int yPos, int xPos) {
        for (int y = yPos; y < 9; y++) {
            if(y!=yPos){
                xPos=0;
            }
            for (int x = xPos; x < 9; x++) {
                if (x == 8 && y == 8) {
                    count++;
                    System.out.println("DONE: " + count);
                    return checkSolution();
                }
                if (cell[y][x] == 0) {
                    for (int k = 1; k < flagVals[y][x][0]; k++) {
                        System.out.println(y+" "+x);
                        cell[y][x] = flagVals[y][x][k];
                        if(iterateSolutions(y, x)){
                            return true;
                        }
                    }
                    cell[y][x] = 0;
                    return false;
                }
            }
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
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print("  " + cell[i][j]);
                }
                System.out.println();
            }
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
}
