package polynomial;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Calculator extends JFrame implements ActionListener{ 
	
	private JPanel mainPanel, fieldsPanel, buttonsPanel, line1, line2, line3;
	private JTextArea p1, p2, pr;
	private JLabel label1, label2, label3;
	private JButton add, sub, mul, div, der, intg;
	private Polynomial pol1, pol2, polR;
	
	public Calculator() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		configureFieldsPanel();
		configureButtonsPanel();
		
		
		setTitle("Polynomial Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(350,350);
		setLocationRelativeTo(null);
	}
	
	private void configureFieldsPanel() {
		fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridLayout(3,1));
		label1 = new JLabel("Polynom1");
		label1.setFont(new Font("Arial", Font.BOLD, 14));
		label2 = new JLabel("Polynom2");
		label2.setFont(new Font("Arial", Font.BOLD, 14));
		label3 = new JLabel("Result");
		label3.setFont(new Font("Arial", Font.BOLD, 14));
		p1 = new JTextArea(2, 20);
		p2 = new JTextArea(2, 20);
		pr = new JTextArea(2, 20);
		pr.setEditable(false);
		line1 = new JPanel();
		line2 = new JPanel();
		line3 = new JPanel();
		line1.setLayout(new FlowLayout());
		line1.add(label1, FlowLayout.LEFT);
		line1.add(p1);
		line2.setLayout(new FlowLayout());
		line2.add(label2, FlowLayout.LEFT);
		line2.add(p2);
		line3.setLayout(new FlowLayout());
		line3.add(label3, FlowLayout.LEFT);
		line3.add(pr);
		
		fieldsPanel.add(line1);
		fieldsPanel.add(line2);
		fieldsPanel.add(line3);
		
		mainPanel.add(fieldsPanel, BorderLayout.NORTH);
	}
	
	private void configureButtonsPanel() {
		
		buttonsPanel = new JPanel();
		
		buttonsPanel.setLayout(new GridLayout(3, 10, 3, 10));
		add = new JButton("+");
		sub = new JButton("-");
		mul = new JButton("*");
		div = new JButton("/");
		der = new JButton("differentiate");
		intg = new JButton("integrate");
		
		buttonsPanel.add(add);
		buttonsPanel.add(sub);
		buttonsPanel.add(mul);
		buttonsPanel.add(div);
		buttonsPanel.add(intg);
		buttonsPanel.add(der);
		

		add.addActionListener(this);
		sub.addActionListener(this);
		mul.addActionListener(this);
		div.addActionListener(this);
		intg.addActionListener(this);
		der.addActionListener(this);
		
		mainPanel.add(buttonsPanel);
	}
	
	
	private Polynomial createNewPolynomial(String line) {
		
		Polynomial pol = new Polynomial();
		
		String text[] = line.split("\\+|\\-"); // in order to keep the sign of the first coefficient

		int power = 0, coeff, lineIndex = 0;
		char sign = '+';
		
		for(String i:text) {
			if(i.length() == 0) {
				sign = '-'; // the first sign is '-';
			}
			else {
				String exp[] = i.split("\\^");
				int j = 0;
				
				if(exp.length == 0) { // if we don't have a power specified
					exp[0] = i;
				}
				
				while(j < exp[0].length() && Character.isDigit(exp[0].charAt(j))) { // extract the coefficient of x
					j+=1;
				}
				
				if(j == 0)
					coeff = 1;
				else
					coeff = Integer.parseInt(exp[0].substring(0, j));
				
				if(exp.length > 1) {
					if(exp[1].length() == 0)
						power = 0;
					else
						power = Integer.parseInt(exp[1]);
				}
				else {
					if(j < exp[0].length() && exp[0].charAt(j) == 'x') 
						power = 1;
					else
						power = 0;
				}
					
				if(sign == '-') {
					pol.addTerm(new Term(power, coeff*(-1)));
					
				}
				else {
					pol.addTerm(new Term(power, coeff));
				}
				
				if(lineIndex + i.length() < line.length() && line.charAt(lineIndex + i.length()) == '-') // remember the sign for the next step
					sign = '-';
				else 
					sign = '+';
				
			}
		
			lineIndex += i.length() + 1;	
		}
		
		return pol;
	}
	
	
	private String getSign(double nr) {
		//if((nr instanceof Double && (Double)nr > 0.0) || (nr instanceof Integer && (Integer) nr > 0))
		if(nr > 0)
			return "+";
		else
			return "";
	}
	
	
	private String showResult(Polynomial p) {
		
		List<Term> resTerms = p.getTerms();
		StringBuilder solution = new StringBuilder();
		int step = 0;
		
		ListIterator<Term> it = resTerms.listIterator(resTerms.size());
		
		while(it.hasPrevious()) {
			Term t = it.previous();
			//if(((t.coeff instanceof Integer) && (Integer)t.coeff!=0) || (t.coeff instanceof Double && (Double)t.coeff != 0.0)) {
			
			if(t.getCoeff() != 0) { // if the coeff != 0 display it
			
				if(step == 0) {
					if(t.getCoeff() == (int) t.getCoeff()) // if the coefficient is doesn't have any decimal part then write as an integer
						solution.append((int)t.getCoeff()+"x^"+t.getDegree());
					else
						solution.append(t.getCoeff()+"x^"+t.getDegree());
				}
				else {
					
					if(t.getCoeff() == (int) t.getCoeff()) // if the coefficient is doesn't have any decimal part then write as an integer
						solution.append(getSign(t.getCoeff())+(int)t.getCoeff()+"x^"+t.getDegree());
					else
						solution.append(getSign(t.getCoeff())+t.getCoeff()+"x^"+t.getDegree());
					}
			}
			
			step += 1;
		}
		pr.setText(solution.toString());
		return solution.toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pol1 = createNewPolynomial(p1.getText());
		pol2 = createNewPolynomial(p2.getText());
		
		if(e.getSource() == add) {
			polR = Functions.add(pol1, pol2);
			showResult(polR);
		} 
		else if(e.getSource() == sub) {
			polR = Functions.sub(pol1, pol2);
			showResult(polR);
		}
		else if(e.getSource() == mul) {
			polR = Functions.mul(pol1, pol2);
			showResult(polR);
		}
		else if(e.getSource() == div) {
			List<Polynomial> divResult = Functions.divideMaster(pol1, pol2);
			if(divResult.size() == 0) {
				JOptionPane.showMessageDialog(null, "The degree of first less than degree of second");
			}
			else {
				StringBuilder sol = new StringBuilder();
				sol.append(showResult(divResult.get(0)));
				sol.append("\n");
				sol.append(showResult(divResult.get(1)));
				pr.setText(sol.toString());
			}
		}
		else if(e.getSource() == der) {
			polR = Functions.der(pol1);
			showResult(polR);
		}
		else if(e.getSource() == intg) {
			polR = Functions.intg(pol1);
			showResult(polR);
		}
		
	}
	
}
