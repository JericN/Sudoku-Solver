import javax.swing.*;
import java.awt.*;

public class Graphics {
    JFrame frame;
    JLayeredPane myLayer;
    JButton[] inputButtons;
    static int WIDTH_BORDER = 16;
    static int HEIGHT_BORDER = 39;
    static int WIDTH = 1422;
    static int HEIGHT = 800;

    public Graphics() {
        System.out.println("Windows Started");
        initializeWindow();
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void initializeWindow() {
        setMainFrame();
        initializeMenu();
    }

    private void setMainFrame() {
        frame = new JFrame();
        frame.setSize(WIDTH + WIDTH_BORDER, HEIGHT + HEIGHT_BORDER);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.cyan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        myLayer = new JLayeredPane();
        myLayer.setBounds(0, 0, WIDTH, HEIGHT);

    }

    private void initializeMenu() {
        menuTitleArea();
        menuInputArea();
        menuOptionArea();
    }

    private void menuTitleArea() {
        JPanel menuTitle = new JPanel();
        menuTitle.setBounds(0, 0, (int) (0.3 * WIDTH), (int) (0.2 * HEIGHT));
        menuTitle.setBackground(Color.green);
        frame.add(menuTitle);
    }

    private void menuInputArea() {
        JPanel menuInput = new JPanel();
        menuInput.setBounds(0, (int) (0.2 * HEIGHT), (int) (0.3 * WIDTH), (int) (0.6 * HEIGHT));
        menuInput.setBackground(Color.blue);
        menuInput.setLayout(null);
        frame.add(menuInput);
        inputButtons(menuInput);
    }

    private void menuOptionArea() {
        JPanel menuOptions = new JPanel();
        menuOptions.setBounds(0, (int) (0.8 * HEIGHT), (int) (0.3 * WIDTH), (int) (0.2 * HEIGHT));
        menuOptions.setBackground(Color.red);
        frame.add(menuOptions);
    }

    private void inputButtons(JPanel menuInput) {
        inputButtons = new JButton[10];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <5 ; j++) {
                int curr = 5*i+j;
                inputButtons[curr] = new JButton();
                inputButtons[curr].setBounds(20+(int)(i*WIDTH*0.14),50+(int)(j*HEIGHT*0.1),(int) (0.05 * WIDTH),(int) (0.05 * HEIGHT));
                menuInput.add(inputButtons[curr]);
            }
        }
    }
}
