import java.util.ArrayList;

public class Variable {
    static int[][] cell;
    static int numSolutions;
    static ArrayList<Integer>[] invalidCell;
    static int invalidCount;

    public Variable() {
        cell = new int[9][9];
        numSolutions = 1;
        invalidCell = new ArrayList[2];
        invalidCell[0] = new ArrayList<Integer>();
        invalidCell[1] = new ArrayList<Integer>();
        invalidCount = 0;
    }

    public void setCell(int[][] input) {
        cell = input;
    }

    public void setCell(int y, int x, int num) {
        cell[y][x] = num;
    }

    public int[][] getCell() {
        return cell;
    }

    public void resetCell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j] = 0;
            }
        }
    }
    public void setNumSols(int val){
        numSolutions = val;
    }
    public int getNumSols(){
        return numSolutions;
    }
    public void addInvalids(int y, int x){
        invalidCell[0].add(y);
        invalidCell[1].add(x);
        invalidCount++;
    }
    public ArrayList<Integer>[] getInvalids(){
        return invalidCell;
    }
    public int getInvalidCount(){
        return invalidCount;
    }
    public boolean isInvalid(int y, int x){
        for (int i = 0; i < invalidCount; i++) {
            if(y == invalidCell[0].get(i) && x == invalidCell[1].get(i)){
                return true;
            }
        }
        return false;
    }
    public void removeInvalid(int y, int x){
        for (int i = 0; i < invalidCount; i++) {
            if(y == invalidCell[0].get(i) && x == invalidCell[1].get(i)){
                invalidCell[0].remove(i);
                invalidCell[1].remove(i);
                i--;
                invalidCount--;
            }
        }
    }
    public void resetInvalid(){
        invalidCell[0] = new ArrayList<Integer>();
        invalidCell[1] = new ArrayList<Integer>();
        invalidCount=0;
    }
}
