import javax.swing.*;
import java.awt.*;

public class Graphics {
    JFrame frame;
    JButton[] buttonInput;
    JLabel[] buttonLabel;
    JLabel[][] cells;
    JButton buttonCalculate;
    static int WIDTH_BORDER = 16;
    static int HEIGHT_BORDER = 39;
    static int WIDTH = 1422;
    static int HEIGHT = 800;

    public Graphics() {
        System.out.println("Windows Started");
        initializeWindow();
        SwingUtilities.updateComponentTreeUI(frame);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void initializeWindow() {
        setMainFrame();
        initializeMenu();
        initializeGrid();
    }

    private void setMainFrame() {
        frame = new JFrame();
        frame.setSize(WIDTH + WIDTH_BORDER, HEIGHT + HEIGHT_BORDER);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initializeMenu() {
        setMenuTitleArea();
        setMenuInputArea();
        setMenuOptionArea();
    }


    private void setMenuTitleArea() {
        JPanel menuTitle = new JPanel();
        menuTitle.setBounds(0, 0, (int) (0.3 * WIDTH), (int) (0.2 * HEIGHT));
        menuTitle.setBackground(Color.gray);
        frame.add(menuTitle);
        menuTitle.updateUI();
    }

    private void setMenuInputArea() {
        JPanel menuInput = new JPanel();
        menuInput.setBounds(0, (int) (0.2 * HEIGHT), (int) (0.3 * WIDTH), (int) (0.6 * HEIGHT));
        menuInput.setBackground(Color.white);
        menuInput.setLayout(null);
        setMenuInputButton(menuInput);
        frame.add(menuInput);
    }

    private void setMenuInputButton(JPanel menuInput) {
        buttonInput = new JButton[10];
        buttonLabel = new JLabel[10];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <5 ; j++) {
                int flag = 5*i+j;
                buttonInput[flag] = new JButton();
                buttonLabel[flag] = new JLabel();
                if (flag != 9) buttonInput[flag].setText(String.valueOf(flag + 1));
                else buttonInput[flag].setText("X");
                buttonInput[flag].setBounds((int) (20 + WIDTH * i * 0.14), (int) (20 + HEIGHT * j * 0.11), 50, 60);
                buttonLabel[flag].setBounds((int) (80 + WIDTH * i * 0.14), (int) (20 + HEIGHT * j * 0.11), 100, 60);
                buttonLabel[flag].setBackground(Color.gray);
                buttonLabel[flag].setOpaque(true);
                buttonLabel[flag].setText("     " + flag);
                buttonLabel[flag].setVisible(true);
                menuInput.add(buttonInput[flag]);
                menuInput.add(buttonLabel[flag]);
            }
        }
    }

    private void setMenuOptionArea() {
        JPanel menuOptions = new JPanel();
        menuOptions.setBounds(0, (int) (0.8 * HEIGHT), (int) (0.3 * WIDTH), (int) (0.2 * HEIGHT));
        menuOptions.setBackground(Color.gray);
        frame.add(menuOptions);
        setMenuOptionButton(menuOptions);
        menuOptions.updateUI();

    }
    public void setMenuOptionButton(JPanel menuOption) {
        buttonCalculate = new JButton();
        buttonCalculate.setBounds(30,30,100,100);
        menuOption.add(buttonCalculate);
    }









    public void initializeGrid() {
        int width = (int) (WIDTH * 0.7 - 40);
        int height = HEIGHT - 40;
        int offset_w = (int) (WIDTH * 0.3 + 20);
        int offset_h = 20;
        JPanel gridArea = new JPanel();
        setGridArea(gridArea, width, height, offset_w, offset_h);
        setGridCells(gridArea, width, height);
    }
    public void setGridArea(JPanel gridArea, int width, int height, int offset_w, int offset_h) {
        gridArea.setBounds(offset_w, offset_h, width, height);
        gridArea.setBackground(new Color(0, 168, 154));
        frame.add(gridArea);
    }

    public void setGridCells(JPanel gridArea, int width, int height) {
        cells = new JLabel[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                cells[y][x] = new JLabel();
                cells[y][x].setBounds(20 + (width - 20) * x / 9, 20 + (height - 20) * y / 9, (width / 9) - 10, (height / 9) - 10);
                cells[y][x].setHorizontalAlignment(JLabel.CENTER);
                cells[y][x].setOpaque(true);
                cells[y][x].setText(" X ");
                cells[y][x].setVisible(true);
                gridArea.add(cells[y][x]);
            }
        }
    }
}
