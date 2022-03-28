import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class SimulationFrame {
    JFrame frame = new JFrame("Queues Management Application");
    JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    JLabel jLabel, jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7;
    JButton buttonStart;
    JPanel p = new JPanel(new BorderLayout());
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JScrollPane scrollPane;
    Border border;
    Font myFont;
    JTextArea jTextArea;

    public SimulationFrame() {
        jTextArea = new JTextArea(100, 100);
        jTextArea.setEditable(false);
        scrollPane = new JScrollPane(jTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10, 0, 400, 200);
        p2.add(scrollPane);


        myFont = new Font("Serif", Font.BOLD, 13);
        border = BorderFactory.createLineBorder(new Color(113, 24, 127), 3);

        frame.setBackground(new Color(212, 208, 255));
        p1.setBackground(new Color(212, 208, 255));
        p2.setBackground(new Color(212, 208, 255));
        p.setBackground(new Color(212, 208, 255));

        textField1 = new JTextField(5);
        textField2 = new JTextField(5);
        textField3 = new JTextField(5);
        textField4 = new JTextField(5);
        textField5 = new JTextField(5);
        textField6 = new JTextField(5);
        textField7 = new JTextField(5);

        jLabel = new JLabel("CurrentTime:");
        jLabel.setFont(myFont);
        jLabel1 = new JLabel("SimulationTime:");
        jLabel1.setFont(myFont);
        jLabel2 = new JLabel("Nb of Clients");
        jLabel2.setFont(myFont);
        jLabel3 = new JLabel("Nb of Queues");
        jLabel3.setFont(myFont);
        jLabel4 = new JLabel("MinArrivalTime");
        jLabel4.setFont(myFont);
        jLabel5 = new JLabel("MaxArrivalTime");
        jLabel5.setFont(myFont);
        jLabel6 = new JLabel("MinServiceTime");
        jLabel6.setFont(myFont);
        jLabel7 = new JLabel("MaxServiceTime");
        jLabel7.setFont(myFont);

        buttonStart = new JButton("START");

        jLabel.setBounds(200, 10, 110, 20);
        jLabel.setBorder(border);

        jLabel1.setBounds(10, 10, 99, 20);
        jLabel1.setBorder(border);
        textField1.setBounds(110, 10, 50, 20);

        jLabel2.setBounds(10, 40, 120, 20);
        textField2.setBounds(110, 40, 50, 20);

        jLabel3.setBounds(200, 40, 120, 20);
        textField3.setBounds(300, 40, 50, 20);

        jLabel4.setBounds(10, 70, 120, 20);
        textField4.setBounds(110, 70, 50, 20);

        jLabel5.setBounds(200, 70, 120, 20);
        textField5.setBounds(300, 70, 50, 20);

        jLabel6.setBounds(10, 100, 120, 20);
        textField6.setBounds(110, 100, 50, 20);

        jLabel7.setBounds(200, 100, 120, 20);
        textField7.setBounds(300, 100, 50, 20);

        buttonStart.setBounds(10, 150, 120, 20);
        buttonStart.setFont(new Font("Times New Roman", Font.BOLD, 14));

        Color btnDefaultBackground = buttonStart.getBackground();
        Color btnDefaultForeground = buttonStart.getForeground();

        buttonStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                buttonStart.setBackground(new Color(148, 1, 138));
                buttonStart.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                buttonStart.setBackground(btnDefaultBackground);
                buttonStart.setForeground(btnDefaultForeground);
            }
        });

        p1.add(jLabel);
        p1.add(jLabel1);
        p1.add(textField1);
        p1.add(jLabel2);
        p1.add(textField2);
        p1.add(jLabel3);
        p1.add(textField3);
        p1.add(jLabel4);
        p1.add(textField4);
        p1.add(jLabel5);
        p1.add(textField5);
        p1.add(jLabel6);
        p1.add(textField6);
        p1.add(jLabel7);
        p1.add(textField7);
        p1.add(buttonStart);

        p.add(p1);
        p.add(p2);
        p1.setLayout(null);
        p2.setLayout(null);
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        frame.add(p);
        frame.setSize(450, 450);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void print(String text) {
        jTextArea.append(text);

    }

    void displayCurrentTime(int currentTime) {
        String s = String.valueOf(currentTime);
        jLabel.setText("CurrentTime:" + s);
    }


}
