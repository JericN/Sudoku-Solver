import net.miginfocom.layout.LC;
import net.miginfocom.layout.AC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Graphics {
    Variable vars = new Variable();
    Logic logic = new Logic();

    Color colorCyan = new Color(0, 168, 154);
    JFrame frame;
    JPanel menuTitle = new JPanel();
    JPanel menuInput = new JPanel();
    JPanel menuOption = new JPanel();
    JPanel menuConsole = new JPanel();
    JPanel gridArea = new JPanel();

    JButton[] buttonInput;
    JLabel[] buttonLabel;
    JButton[][] cellLabel;

    int[] numbers = new int[10];
    int[][] cell = new int[9][9];
    static int WIDTH_BORDER = 16;
    static int HEIGHT_BORDER = 39;
    static int WIDTH = 1422;
    static int HEIGHT = 800;
    int selectedNum = 0;

    public Graphics() {
        System.out.println("Windows Started");
        initializeWindow();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void initializeWindow() {
        setMainFrame();
        setMenuTitleArea();
        setMenuInputArea();
        setMenuInputButton();
        setMenuOptionArea();
        setMenuConsoleArea();
        initializeGrid();
    }

    private void setMainFrame() {
        MigLayout frameLayout = new MigLayout(
                "wrap 2, fill",
                "[350!][1050!]",
                "[" + HEIGHT * 0.1 + "!][" + HEIGHT * 0.5 + "!][" + HEIGHT * 0.2 + "!][" + HEIGHT * 0.15 + "!]"
        );
        frame = new JFrame();
        frame.setSize(WIDTH + WIDTH_BORDER, HEIGHT + HEIGHT_BORDER);
        frame.setLayout(frameLayout);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private void setMenuTitleArea() {
        menuTitle.setBackground(colorCyan);
        frame.add(menuTitle, "cell 0 0, width 426, height 80");
    }

    private void setMenuInputArea() {
        MigLayout inputLayout = new MigLayout(
                "wrap 4, inset 30 40 30 40",
                "[]10[]50[]10[]",
                "[]10[]10[]10[]10[]");
        menuInput.setLayout(inputLayout);
        menuInput.setBackground(Color.gray);
        frame.add(menuInput, "cell 0 1, width 426, height 400");
    }

    private void setMenuInputButton() {
        buttonInput = new JButton[10];
        buttonLabel = new JLabel[10];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                int flag = 5 * i + j;
                buttonInput[flag] = new JButton();
                buttonLabel[flag] = new JLabel();
                if (flag != 9) {
                    buttonInput[flag].setText(String.valueOf(flag + 1));
                } else {
                    buttonInput[flag].setText("X");
                }
                buttonInput[flag].setBackground(colorCyan);
                buttonInput[flag].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (flag != 9)
                            selectedNum = flag + 1;
                        else selectedNum = 0;
                    }
                });
                buttonLabel[flag].setBackground(Color.white);
                buttonLabel[flag].setOpaque(true);
                buttonLabel[flag].setHorizontalAlignment(JLabel.CENTER);
                buttonLabel[flag].setVisible(true);
                menuInput.add(buttonInput[flag], "width 50, height 60");
                menuInput.add(buttonLabel[flag], "width 50, height 60");
                menuInput.updateUI();
            }
        }
    }

    public void setMenuOptionArea() {
        MigLayout inputLayout = new MigLayout(
                "wrap 4, inset 10 20 10 20",
                "[80]10[80]10[80]10[80]",
                "[30]10[30]10[30]10[30]"
        );
        menuOption.setLayout(inputLayout);
        menuOption.setBackground(Color.gray);
        frame.add(menuOption, "cell 0 2");
        setMenuOptionButtons();
    }

    public void setMenuOptionButtons() {
        JButton calculate = new JButton();
        JButton reset = new JButton();
        JButton save = new JButton();
        JButton load = new JButton();
        JButton[] state = new JButton[4];

        calculate.setText("Calculate");
        menuOption.add(calculate, "span 4 1, grow");
        reset.setText("Reset Grid");
        menuOption.add(reset, "span 4 1, grow");
        save.setText("Save");
        menuOption.add(save, "span 2 1, grow");
        load.setText("Load");
        menuOption.add(load, "span 2 1, grow");
        for (int i = 0; i < 4; i++) {
            state[i] = new JButton();
            state[i].setText(String.valueOf(i+1));
            menuOption.add(state[i], "grow");
        }
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logic.start();
                updateGrid();
                updateNums();
            }
        });
    }

    public void setMenuConsoleArea() {
        menuConsole.setLayout(new MigLayout());
        menuConsole.setBackground(Color.black);
        JLabel console = new JLabel();
        console.setSize(400, 150);
        console.setText("Console");
        menuConsole.add(console);
        frame.add(menuConsole, "cell 0 3, width 426, height 160");
    }

    public void initializeGrid() {
        int width = (int) (WIDTH * 0.7 - 40);
        int height = HEIGHT - 40;
        int offset_w = (int) (WIDTH * 0.3 + 20);
        int offset_h = 20;
        gridArea = new JPanel();
//        cell = new int[][]{
//                {3,7,4,1,6,8,2,5,9},
//                {5,1,9,4,2,7,6,8,3},
//                {2,8,6,3,9,5,7,1,4},
//                {6,9,8,5,4,1,3,7,2},
//                {1,2,3,7,8,6,9,4,5},
//                {4,5,7,9,3,2,1,6,8},
//                {9,6,2,8,7,4,5,3,1},
//                {8,3,5,6,1,9,4,2,7},
//                {7,4,1,2,5,3,8,9,6},
//        };
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cell[i][j] = 0;
            }
        }
        for (int i = 0; i < 10; i++) {
            numbers[i]=0;
        }
        vars.setCell(cell);
        setGridArea(width, height, offset_w, offset_h);
        setGridcellLabel(width, height);
    }

    public void setGridArea(int width, int height, int offset_w, int offset_h) {

        MigLayout gridLayout = new MigLayout("wrap 9, inset 10 40 10 40");
        gridArea.setLayout(gridLayout);
        gridArea.setSize(width, height);
        gridArea.setBackground(colorCyan);
        frame.add(gridArea, "cell 1 0 1 4, grow");
    }

    public void setGridcellLabel(int width, int height) {
        int cellWidth = (width / 9) - 10;
        int cellHeight = (height / 9) - 10;
        cellLabel = new JButton[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                cellLabel[y][x] = new JButton();
                cellLabel[y][x].setHorizontalAlignment(JLabel.CENTER);
                cellLabel[y][x].setFont(cellLabel[y][x].getFont().deriveFont(64.0f));
                cellLabel[y][x].setRolloverEnabled(false);
                cellLabel[y][x].setContentAreaFilled(false);
                cellLabel[y][x].setFocusPainted(false);
                cellLabel[y][x].setBorder(new LineBorder(Color.black));
                int finalY = y;
                int finalX = x;
                MouseListener mouseListener = new MouseAdapter() {
                    public void mousePressed(MouseEvent mouseEvent) {
                        numbers[cell[finalY][finalX]]--;
                        numbers[selectedNum]++;
                        cell[finalY][finalX] = selectedNum;
                        if (selectedNum == 0) {
                            cellLabel[finalY][finalX].setText("");
                        } else {
                            cellLabel[finalY][finalX].setText(String.valueOf(selectedNum));
                        }
                        vars.setCell(cell);
                        updateNums();
                    }
                };
                cellLabel[y][x].addMouseListener(mouseListener);
                if (x % 3 == 0 && y % 3 == 0) {
                    gridArea.add(cellLabel[y][x], "gapleft 10, gaptop 10,width " + cellWidth + "!, height " + cellHeight + "!");
                } else if (x % 3 == 0) {
                    gridArea.add(cellLabel[y][x], "gapleft 10,width " + cellWidth + "!, height " + cellHeight + "!");
                } else if (y % 3 == 0) {
                    gridArea.add(cellLabel[y][x], "gaptop 10,width " + cellWidth + "!, height " + cellHeight + "!");
                } else {
                    gridArea.add(cellLabel[y][x], "width " + cellWidth + "!, height " + cellHeight + "!");
                }
            }
        }
        updateGrid();
        updateNums();
    }

    public void updateGrid() {
        for (int i = 0; i < 10; i++) {
            numbers[i]=0;
        }
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (cell[y][x] == 0) {
                    cellLabel[y][x].setText("");
                } else {
                    int num = cell[y][x];
                    cellLabel[y][x].setText(String.valueOf(num));
                }
                numbers[cell[y][x]]++;
            }
        }
        
    }
    public void updateNums(){
        for (int i = 0; i < 10; i++) {
            if( i==0){
                buttonLabel[9].setText(String.valueOf(numbers[i]));
            }else{
                buttonLabel[i-1].setText(String.valueOf(numbers[i]));
            }

        }
    }
}

