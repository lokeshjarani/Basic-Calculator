import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicCalculator extends JFrame {
    private JTextField inputField;
    private double result = 0;
    private String operator = "=";
    private boolean calculating = true;

    public BasicCalculator() {
        setTitle("Basic Calculator");
        inputField = new JTextField("0", 12);
        inputField.setEditable(false);
        add(inputField, BorderLayout.NORTH);

        ActionListener numberListener = new NumberListener();
        ActionListener operatorListener = new OperatorListener();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "=", "C", "+"
        };

        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            if (buttonText.matches("[0-9]")) {
                button.addActionListener(numberListener);
            } else {
                button.addActionListener(operatorListener);
            }
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void calculate(double n) {
        switch (operator) {
            case "+": result += n; break;
            case "-": result -= n; break;
            case "*": result *= n; break;
            case "/": result /= n; break;
            case "=": result = n; break;
        }
        inputField.setText("" + result);
    }

    private class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();
            if (calculating) {
                inputField.setText(digit);
                calculating = false;
            } else {
                inputField.setText(inputField.getText() + digit);
            }
        }
    }

    private class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("C")) {
                result = 0;
                operator = "=";
                inputField.setText("0");
                calculating = true;
            } else {
                if (calculating) {
                    if (command.equals("-")) {
                        inputField.setText(command);
                        calculating = false;
                    } else {
                        operator = command;
                    }
                } else {
                    double x = Double.parseDouble(inputField.getText());
                    calculate(x);
                    operator = command;
                    calculating = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        new BasicCalculator();
    }

}
