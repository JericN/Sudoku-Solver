public class Logic {
    Variable var = new Variable();
    int[][] cell;

    public void start() {
        cell = var.getCell();
        solve();
    }

    public void solve() {
        int[][] flagVals = new int[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if(cell[y][x]==0){
                    for (int i = 1; i < 10; i++) {
                        for (int k = 0; k < 9; k++) {
                            if(cell[y][k] == i){

                            }
                        }
                    }
                }
            }
        }
    }
}
