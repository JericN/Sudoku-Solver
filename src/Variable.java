public class Variable {
    int[][] cell;
    public void value(){
        cell = new int[9][9];
    }
    public void setCell(int[][] input){
        cell=input;
    }
    public int[][] getCell(){
        return cell;
    }
}
