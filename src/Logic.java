import java.util.ArrayList;

public class Logic {
    Variable var = new Variable();
    int emptyCellCount;
    int[][][] flagVals;
    int[][] cell;
    ArrayList<ArrayList<Integer>> emptyCell;

    public void start() {
        System.out.println("Logic Started");
        flagVals = new int[9][9][10];
        cell = var.getCell();
        printGrid();
        if (!checkInput()) {
            System.out.println("Invalid Sudoku");
            return;
        }
        getPossibleSolutions();
        sortPossibleSolutions();
        if (iterateSolutions(0)) {
            System.out.println("Solution Found");
            printGrid();
            var.setCell(cell);
        } else {
            System.out.println("Invalid Sudoku");
        }
        System.out.println("Logic Stopped");
    }


    public void getPossibleSolutions() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (cell[y][x] == 0) {
                    int val = 0;
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
                        int xZone = (int) Math.ceil((float) (x + 1) / 3);
                        int yZone = (int) Math.ceil((float) (y + 1) / 3);
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
                            val++;
                            flagVals[y][x][val] = num;
                        }
                    }
                    flagVals[y][x][0] = val;
                }
            }
        }
    }

    public void sortPossibleSolutions() {
        emptyCell = new ArrayList<ArrayList<Integer>>();
        emptyCellCount = 0;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (cell[y][x] == 0) {
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

    public boolean iterateSolutions(int nPos) {
        if (nPos == emptyCellCount) {
            printGrid();
            return true;
        } else {
            int yPos = emptyCell.get(nPos).get(0);
            int xPos = emptyCell.get(nPos).get(1);
            for (int k = 0; k < emptyCell.get(nPos).get(2); k++) {
                cell[yPos][xPos] = emptyCell.get(nPos).get(k + 3);
                if (checkPossible( yPos, xPos)) {
                    if (iterateSolutions(nPos + 1)) {
                        return true;
                    }
                }
            }
            cell[yPos][xPos] = 0;
        }
        return false;
    }

    public boolean checkInput() {
        cell = var.getCell();
        var.resetInvalid();
        boolean flag = true;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                int temp = cell[y][x];
                if (temp == 0) {
                    continue;
                }
                for (int k = 0; k < 9; k++) {
                    if (x != k && temp == cell[y][k]) {
                        if(!var.isInvalid(y,k)){
                            var.addInvalids(y,k);
                        }
                    }
                    if (y != k && temp == cell[k][x]) {
                        if(!var.isInvalid(k,x)){
                            var.addInvalids(k,x);
                        }
                    }
                }
                int xZone = (int) Math.ceil((float) (x + 1) / 3);
                int yZone = (int) Math.ceil((float) (y + 1) / 3);
                for (int i = (yZone - 1) * 3; i < yZone * 3; i++) {
                    for (int j = (xZone - 1) * 3; j < xZone * 3; j++) {
                        if (i != y || j != x) {
                            if (temp == cell[i][j]) {
                                if(!var.isInvalid(i,j)){
                                    var.addInvalids(i,j);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(var.getInvalidCount()==0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkPossible(int y, int x) {
        int temp = cell[y][x];
        for (int k = 0; k < 9; k++) {
            if (x != k && temp == cell[y][k]) {
                return false;
            }
            if (y != k && temp == cell[k][x]) {
                return false;
            }
        }
        int xZone = (int) Math.ceil((float) (x + 1) / 3);
        int yZone = (int) Math.ceil((float) (y + 1) / 3);
        for (int i = (yZone - 1) * 3; i < yZone * 3; i++) {
            for (int j = (xZone - 1) * 3; j < xZone * 3; j++) {
                if (i != y || j != x) {
                    if (temp == cell[i][j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void printGrid() {
        for (int k = 0; k < 9; k++) {
            for (int j = 0; j < 9; j++) {
                System.out.print("  " + cell[k][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printFlags() {
        boolean empty = true;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                for (int k = 1; k < flagVals[y][x][0]; k++) {
                    if (flagVals[y][x][k] != 0) {
                        System.out.print(flagVals[y][x][k]);
                        empty = false;
                    }
                }
                if (!empty) {
                    System.out.println("\n");
                    empty = true;
                }
            }
        }
    }
    public void printInvalids(){
        ArrayList[] temp = var.getInvalids();
        for (int i = 0; i < var.getInvalidCount(); i++) {
            System.out.print(temp[0].get(i)+":");
            System.out.println(temp[1].get(i));
        }
    }
}
