import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SwingBasic extends JFrame {
	
	static Trie t = new Trie();
	JLabel l;

	void setTextFieldProps(JTextField txt){
		txt.setSize(300,300);
		txt.setBounds(100, 100, 100, 100);
		txt.setFont(txt.getFont().deriveFont(Font.PLAIN, 50f));
		txt.setMinimumSize(new Dimension(300, 58));
		txt.setPreferredSize(new Dimension(300, 58));	
		txt.setMaximumSize(new Dimension(300, 58));
		txt.setBorder(new EmptyBorder(2,3,2,3));		
	}
	
	void setLabelProps(JLabel label){
		label.setSize(400,400);		
		label.setFont(label.getFont().deriveFont(Font.PLAIN, 30f));
		label.setText("sample");
		label.setMinimumSize(new Dimension(400, 58));
		label.setPreferredSize(new Dimension(400, 58));	
		label.setMaximumSize(new Dimension(400, 58));
	}
	
	void setPanelProps(JPanel panel){
		panel.setLayout(new FlowLayout());		
		panel.setSize(300, 1000);
		panel.setVisible(true);
	}

	JPanel panel2 = new JPanel();
	JLabel l2 = new JLabel();
	JTextField ac1 = new JTextField();
	JTextField ac2 = new JTextField();
	JTextField ac3 = new JTextField();
	JPanel panel = new JPanel();
	JTextField txt = new JTextField();
	
	JPanel panel3 = new JPanel();
	JLabel l3 = new JLabel();
	JTextField acn1 = new JTextField();
	JTextField acn2 = new JTextField();
	JTextField acn3 = new JTextField();
	
	void updateResults(){
		List<String> res = t.autoComplete(txt.getText());
		if(res.size() >= 1)
			ac1.setText(res.get(0));
		else
			ac1.setText("NIL");
		if(res.size() >= 2)
			ac2.setText(res.get(1));
		else
			ac2.setText("NIL");
		if(res.size() >= 3)
			ac3.setText(res.get(2));
		else
			ac3.setText("NIL");				
		res = t.autoCompleteNextWord(txt.getText());
		if(res.size() >= 1)
			acn1.setText(res.get(0));
		else
			acn1.setText("NIL");
		if(res.size() >= 2)
			acn2.setText(res.get(1));
		else
			acn2.setText("NIL");
		if(res.size() >= 3)
			acn3.setText(res.get(2));
		else
			acn3.setText("NIL");
	}
	
	public void start() {
		
		l = new JLabel();
		txt.setText("सुंदर");
		setTextFieldProps(txt);
		setLabelProps(l);
		l.setText("Enter a Hindi word :");

		setPanelProps(panel);
		panel.add(l);
		panel.add(txt);
		

		setLabelProps(l2);
		l2.setText("AutoComplete Suggestions");
		setTextFieldProps(ac1);
		setTextFieldProps(ac2);
		setTextFieldProps(ac3);
		panel2.add(l2); panel2.add(ac1); panel2.add(ac2); panel2.add(ac3);
		ac1.setBorder(new LineBorder(Color.BLACK));
		ac2.setBorder(new LineBorder(Color.BLACK));
		ac3.setBorder(new LineBorder(Color.BLACK));
		

		setLabelProps(l3);
		l3.setText("Next word suggestions");
		setTextFieldProps(acn1);
		setTextFieldProps(acn2);
		setTextFieldProps(acn3);
		panel3.add(l3); panel3.add(acn1); panel3.add(acn2); panel3.add(acn3);
		acn1.setBorder(new LineBorder(Color.BLACK));
		acn2.setBorder(new LineBorder(Color.BLACK));
		acn3.setBorder(new LineBorder(Color.BLACK));
		

		txt.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				updateResults();
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});

		setPanelProps(panel);
		setPanelProps(panel2);
		setPanelProps(panel3);
		add(panel);		
		add(panel2);
		add(panel3);
		setSize(2000, 800);
		setLayout(new FlowLayout());
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		updateResults();
	}

	public static void main(String args[]) throws IOException {
		t.readTrie("C:/users/prakd/Downloads/storiestrie_proper_5occurances.trie");
		new SwingBasic().start();
	}
}
