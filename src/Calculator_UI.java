import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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
        tx.setFont(new Font("Arial", Font.PLAIN, 26));
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
            if(exp.equals("0") || exp.equals(" ")){
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
            String result = evaluateAnswer(exp);
            tx.setText(result);
        }
    }
    public String evaluateAnswer(String s){
        String[] o = s.split("[-+*/=]");
        String[] op = new String[o.length - 1];
        for (String split: o) {
            if(split == ""){
                return "Invalid";
            }
        }
        int id = 0;
        for(int i = 0; i<s.length();i++){
            String xs = String.valueOf(s.charAt(i));
            try{
                if("*/+-".contains(xs)){
                    op[id] = xs;
                    id++;
                }
            }catch (ArrayIndexOutOfBoundsException e){
                return "Invalid";
            }

        }
        ArrayList<String> ex = new ArrayList<>();
        int n = o.length + op.length;
        int id1 = 0,id2 = 0;
        for(int i = 0; i<n;i++){
            if(i%2==0){
                ex.add(o[id1]);
                id1++;
            }else{
                ex.add(op[id2]);
                id2++;
            }
        }
        String order = "";
        for(String ord :op){
            if("*/".contains(ord)){
                order = ord + order;
            }else if("+-".contains(ord)){
                order = order + ord;
            }
        }
        for(int i = 0;i<order.length();i++){
            String ope = String.valueOf(order.charAt(i));
            int idx = ex.indexOf(ope);
            double n1 = Double.parseDouble(ex.get(idx-1));
            double n2 =  Double.parseDouble(ex.get(idx+1));
            double ans = getAns(n1,n2,ope);
            ex.remove(idx);
            ex.remove(idx);
            ex.remove(idx-1);
            ex.add(idx-1,ans+"");
        }
        return ex.get(0);
    }
    public double getAns(double x, double y,String ch){
        switch (ch){
            case "+":
                return x+y;
            case "-":
                return x-y;
            case "*":
                return x*y;
            case "/":
                return x/y;
        }
        return 0;
    }
}
