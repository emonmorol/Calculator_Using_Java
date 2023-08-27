import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

public class Calculator_UI extends JFrame implements ActionListener {
    JLabel tx;
    String exp = " ";
    Calculator_UI(){
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(300, 400);
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        tx = new JLabel("0");
        tx.setFont(new Font("Arial", Font.PLAIN, 40));
        contentPanel.add(tx);
        tx.setPreferredSize(new Dimension(tx.getPreferredSize().width, 60));
        tx.setOpaque(true);
        tx.setBackground(Color.BLACK);
        tx.setForeground(Color.white);
        tx.setHorizontalAlignment(SwingConstants.RIGHT);


        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            if("+-*/".contains(label)){
                button.setBackground(new Color(232,232,232));
            }else if("0123456789".contains(label)){
                button.setBackground(new Color(255,255,255));
            }else if("C=".contains(label)){
                button.setBackground(new Color(237,126,80));
            }
            button.setFont(new Font("Arial", Font.PLAIN, 26));
            buttonPanel.add(button);
        }

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }


    public static void main(String[] args) {
        Calculator_UI c = new Calculator_UI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if ("0123456789".contains(s)) {
            if(exp.equals("0")){
                exp = s;
            }else{
                exp += s;
            }
            tx.setText(exp);
        } else if ("+-*/".contains(s)) {
            exp += s;
            tx.setText(exp);
        } else if (s.equals("C")) {
            exp = "0";
            tx.setText(exp);
        } else if (s.equals("=")) {

//            try {
                String result = evaluateAnswer(exp);
                tx.setText(result);
//                display.setText(result);
//                expression.setLength(0);
//                expression.append(result);
//            } catch (Exception ex) {
//                display.setText("Error");
//            }
        }

        tx.setText(exp);
    }
    public String evaluateAnswer(String s){
        String post = toPostfix(s);
        return post;
    }
    public String toPostfix(String infix) {
        String post = "";
        ArrayList<Character> op = new ArrayList<>();

        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c)) {
                post+=c;
            } else if (isOperator(c)) {
                while (!op.isEmpty() && precedence(c) <= precedence(op.get(op.size() - 1))) {
                    post += op.remove(op.size() - 1);
                }
                op.add(c);
            }
        }

        while (!op.isEmpty()) {
            post+=op.remove(op.size() - 1);
        }
        return post;
    }

    public boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    public static double applyOperator(double a, double b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return Math.pow(a, b);
            default:
                return 0.0;
        }
    }
}
