import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Graphics {
    Variable vars = new Variable();
    Logic logic = new Logic();
    Color colorCyan = new Color(0, 168, 154);

    JFrame frame = new JFrame();
    JFrame configFrame = new JFrame();
    JPanel menuTitle = new JPanel();
    JPanel menuInput = new JPanel();
    JPanel menuOption = new JPanel();
    JPanel menuConsole = new JPanel();
    JPanel gridArea = new JPanel();
    JButton[] buttonInput;
    JLabel[] buttonLabel;
    JButton[][] cellLabel;

    int[] numCount = new int[10];
    int[][] cell = new int[9][9];
    ArrayList<int[][]> solutionsList = new ArrayList();
    static int WIDTH_BORDER = 16;
    static int HEIGHT_BORDER = 39;
    static int WIDTH = 1422;
    static int HEIGHT = 800;
    int selectedNum = 0;
    int selectedState = 0;
    int selectedSolution = 0;

    public Graphics() {
        System.out.println("Windows Started");
        initializeMainWindow();
        initializeConfigWindow();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void initializeMainWindow() {
        setMainFrame();
        setMenuTitleArea();
        setMenuInputArea();
        setMenuInputButton();
        setMenuOptionArea();
        setMenuConsoleArea();
        setSolutionNavigateButtons();
        setGridArea();
        setGridCellLabel();
        updateGridLabels();
        updateNumCount();
    }

    private void initializeConfigWindow() {
        setConfigFrame();
    }

    private void setConfigFrame() {
        MigLayout frameLayout = new MigLayout("wrap 2, fill");
        configFrame = new JFrame();
        configFrame.setSize(WIDTH / 3 + WIDTH_BORDER, HEIGHT / 3 + HEIGHT_BORDER);
        configFrame.setLayout(frameLayout);
        configFrame.setLocationRelativeTo(null);
        configFrame.setVisible(false);
    }

    private void setMainFrame() {
        MigLayout frameLayout = new MigLayout(
                new LC().wrapAfter(4),
                new AC().fill(),
                new AC().fill()
        );
        frame.setSize(WIDTH + WIDTH_BORDER, HEIGHT + HEIGHT_BORDER);
        frame.setLayout(frameLayout);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private void setMenuTitleArea() {
        menuTitle.setLayout(new MigLayout("inset 10 20 10 20, fill"));
        menuTitle.setBackground(colorCyan);
        frame.add(menuTitle, "cell 0 0");
        JButton settings = new JButton();
        JLabel title = new JLabel("Sudoku Automatic Solver");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        menuTitle.add(title, "grow, align center center");
        menuTitle.add(settings, "width 30, height 30, align right center");

        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                configFrame.setVisible(true);
            }
        });
    }

    private void setMenuInputArea() {
        MigLayout inputLayout = new MigLayout(
                "inset 30 40 30 40");
        menuInput.setLayout(inputLayout);
        menuInput.setBackground(Color.gray);
        frame.add(menuInput, "cell 0 1");
    }

    private void setMenuInputButton() {
        buttonInput = new JButton[10];
        buttonLabel = new JLabel[10];
        String gap = "50";
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                int flag = 5 * i + j;

                buttonInput[flag] = new JButton();
                buttonInput[flag].setBackground(colorCyan);
                buttonInput[flag].setFocusPainted(false);
                buttonInput[flag].setFont(new Font("Serif", Font.BOLD, 32));
                if (flag != 9) buttonInput[flag].setText(String.valueOf(flag + 1));
                else buttonInput[flag].setText("0");

                buttonLabel[flag] = new JLabel();
                buttonLabel[flag].setOpaque(true);
                buttonLabel[flag].setVisible(true);
                buttonLabel[flag].setBackground(Color.white);
                buttonLabel[flag].setHorizontalAlignment(JLabel.CENTER);
                buttonLabel[flag].setFont(new Font("Serif", Font.PLAIN, 18));
                for (int k = 0; k < 10; k++) numCount[k] = 0;

                if (i == 1) gap = "0";
                menuInput.add(buttonInput[flag], "cell " + i + " " + j + ", width 50, height 70");
                menuInput.add(buttonLabel[flag], "cell " + i + " " + j + ", width 60, height 30, gapright " + gap + ", align center center");

                buttonInput[flag].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (flag != 9) selectedNum = flag + 1;
                        else selectedNum = 0;
                    }
                });
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
            state[i].setText(String.valueOf(i + 1));
            menuOption.add(state[i], "grow");
        }
        calculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vars.resetSolutions();
                logic.start();
                solutionsList = vars.getSolutions();
                selectedSolution=0;
                cell = solutionsList.get(0);
                updateGridLabels();
                updateNumCount();
            }
        });
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vars.resetCell();
                cell = vars.getCell();
                vars.resetSolutions();
                solutionsList = vars.getSolutions();
                solutionsList.add(cell);
                selectedSolution=0;
                updateGridLabels();
                updateNumCount();
            }
        });
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vars.saveState(selectedState, cell);
            }
        });
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cell = vars.loadState(selectedState);
                updateGridLabels();
                updateNumCount();
            }
        });
        for (int i = 0; i < 4; i++) {
            int index = i;
            state[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectedState = index;
                }
            });
        }
    }

    public void setMenuConsoleArea() {
        menuConsole.setLayout(new MigLayout());
        menuConsole.setBackground(Color.black);
        JLabel console = new JLabel();
        console.setSize(400, 150);
        console.setText("Console");
        menuConsole.add(console);
        frame.add(menuConsole, "cell 0 3");
    }

    public void setSolutionNavigateButtons() {
        JButton solutionLeft = new JButton();
        JButton solutionRight = new JButton();
        solutionLeft.setText("<");
        solutionRight.setText(">");
        solutionLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSolution--;
                if (selectedSolution < 0) {
                    selectedSolution++;
                }
                System.out.println(selectedSolution);
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        System.out.print(solutionsList.get(selectedSolution)[i][j]);
                    }
                    System.out.println();
                }
                System.out.println();
                cell = solutionsList.get(selectedSolution);
                updateGridLabels();
                updateNumCount();
            }
        });
        solutionRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedSolution++;
                if (selectedSolution >= vars.getSolutionCount()) {
                    selectedSolution--;
                }
                System.out.println(selectedSolution);
                cell = solutionsList.get(selectedSolution);
                updateGridLabels();
                updateNumCount();
            }
        });
        frame.add(solutionLeft, "cell 1 0 1 4, grow");
        frame.add(solutionRight, "cell 3 0 1 4, grow");
    }

    public void setGridArea() {
        vars.resetCell();
        cell = vars.getCell();
        cell = new int[][]{
                {3, 7, 4, 1, 6, 8, 2, 5, 9},
                {5, 1, 9, 4, 2, 7, 6, 8, 3},
                {2, 8, 6, 3, 9, 5, 7, 1, 4},
                {6, 9, 8, 5, 4, 1, 3, 7, 2},
                {1, 2, 3, 7, 8, 6, 9, 4, 5},
                {4, 5, 7, 9, 3, 2, 1, 6, 8},
                {9, 6, 2, 8, 7, 4, 5, 3, 1},
                {8, 3, 5, 6, 1, 9, 4, 2, 7},
                {7, 4, 1, 2, 5, 3, 8, 9, 6},
        };
        vars.setCell(cell);
        solutionsList.add(cell);
        MigLayout gridLayout = new MigLayout("wrap 9, inset 10 10 10 10");
        gridArea.setLayout(gridLayout);
        gridArea.setBackground(colorCyan);
        frame.add(gridArea, "cell 2 0 1 4");
    }

    public void setGridCellLabel() {
        int cellWidth = (int) ((WIDTH * 0.7 / 9) - 15);
        int cellHeight = (HEIGHT / 9) - 13;
        cellLabel = new JButton[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                int finalY = y;
                int finalX = x;
                cellLabel[y][x] = new JButton();
                cellLabel[y][x].setHorizontalAlignment(JLabel.CENTER);
                cellLabel[y][x].setFont(cellLabel[y][x].getFont().deriveFont(64.0f));
                cellLabel[y][x].setRolloverEnabled(false);
                cellLabel[y][x].setContentAreaFilled(false);
                cellLabel[y][x].setFocusPainted(false);
                cellLabel[y][x].setBorder(new LineBorder(Color.black));
                cellLabel[y][x].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent mouseEvent) {
                        numCount[cell[finalY][finalX]]--;
                        numCount[selectedNum]++;
                        cell[finalY][finalX] = selectedNum;
                        if (selectedNum == 0) {
                            if (vars.isInvalid(finalY, finalX)) {
                                vars.removeInvalid(finalY, finalX);
                            }
                            cellLabel[finalY][finalX].setText("");
                        } else {
                            cellLabel[finalY][finalX].setText(String.valueOf(selectedNum));
                        }
                        vars.setCell(finalY, finalX, selectedNum);
                        updateGridLabels();
                        updateNumCount();
                    }
                });
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
    }

    public void updateGridLabels() {
        logic.checkInput();
        ArrayList[] temp = vars.getInvalids();
        for (int i = 0; i < 10; i++) {
            numCount[i] = 0;
        }

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (vars.isInvalid(y, x)) {
                    cellLabel[y][x].setForeground(Color.red);
                } else {
                    cellLabel[y][x].setForeground(Color.black);
                }
                if (cell[y][x] == 0) {
                    cellLabel[y][x].setText("");
                } else {
                    int num = cell[y][x];
                    cellLabel[y][x].setText(String.valueOf(num));
                }
                numCount[cell[y][x]]++;
            }
        }
    }

    public void updateNumCount() {
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                buttonLabel[9].setText(String.valueOf(numCount[i]));
            } else {
                buttonLabel[i - 1].setText(String.valueOf(numCount[i]));
            }
        }
    }
}



