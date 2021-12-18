package crosswordgui;

import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class CrosswordPanel {

	private JFrame frame; //private because you don't want other subclass to change these variables
	private JPanel p1, p2, p3;

	private JButton checkButton;
	private JLabel labelSides;
	private GridBagConstraints greyConstraint;

	// Constructor method
	public CrosswordPanel(String title) {
		frame = new JFrame(title); //opstellen van een frame


		//frame.setLayout(new GridLayout(1,1));
		//frame.setPreferredSize(new Dimension(400,400));
		this.gridSizeCrossword();
		this.setPanelAnswer();
		this.setPanelClues();
		
		frame.add(p1);
		frame.add(p2, BorderLayout.SOUTH);
		frame.add(p3, BorderLayout.WEST);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}



	public void gridSizeCrossword() {
		//first crossword layout, sets of crossword grid
		String filepath = "input_crossword"; //enter name of input file.
		matrixGenerate matrixArray = new matrixGenerate(filepath);
		Panel panel = new Panel();
		p1 = panel.getPanelCrossword();
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;

		//Filling up horizontal as well vertical = both

		// creating the crossword array
		for(int j=0; j < 13; j++) { //for loop for the buttons implementation
			for (int i=0; i < 13; i++ ) {
				String symbol = matrixArray.getMatrix()[j][i];
				if(symbol.equals("O")) {
					//System.out.println(symbol);
					AbstractSquarePanel white = new WhiteSquarePanel(p1, symbol);	
					c.gridx = i;
					c.gridy = j;
					p1.add(white.getButton(),c);
				} else if(symbol.equals("X")) {
					AbstractSquarePanel black = new BlackSquarePanel(p1, symbol);
					c.gridx = i;
					c.gridy = j;
					p1.add(black.getButton(),c);

				} else if(symbol.equals("S")) {
					AbstractSquarePanel grey = new GreySquarePanel(p1, symbol);
					c.gridx = i;
					c.gridy = j;
					p1.add(grey.getButton(),c);
				} else {
					String symbolBlue = symbol; //save the symbol in a string and use that to generate the hint.
					//System.out.println("Symbol retrieved from matrix: " + symbolBlue);
					AbstractSquarePanel blue = new BlueSquarePanel(p1,symbolBlue);
					//PopKeyboard keyboard = new PopKeyboard(blue.getTypeSquare(), blue.getButton(), symbolBlue);
					//System.out.println(blue.getSymbol());
					//System.out.println(blue.getPossibleLetters());

					//System.out.println("Hello this is: " + blue.getPossibleLetters());
					//System.out.println("Clue letters: " + blue.getClueLetters());
					c.gridx = i;
					c.gridy = j;
					p1.add(blue.getButton(),c);
				}
			}
		}

	}

	public void setPanelAnswer() {
		Panel panelTwo = new Panel();
		p2 = panelTwo.getPanelGrey();

		labelSides = new JLabel("");
		GridBagConstraints g = new GridBagConstraints();
		AbstractSquarePanel [] answerArray = new AbstractSquarePanel [9];
		//g.fill = GridBagConstraints.BOTH;
		int counter = 0;
		for(int k = 0; k < 9; k++) {
			AbstractSquarePanel greyCheckAnswer = new GreySquarePanel(p2, "AnswerGreyButton");
			answerArray[k] = greyCheckAnswer;
			g.gridx = k;
			g.gridy = 0;
			p2.add(greyCheckAnswer.getButton(), g);
			counter++;
		}
		//System.out.println(Arrays.toString(answerArray));
		checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				for(AbstractSquarePanel button : answerArray ) {
					sb.append(button.getButtonText());
				}
				System.out.println("This is your answer: " + sb.toString());
				
				if(sb.toString().equals("SOUTHPARK")) {
					JLabel congrats = new JLabel("Congratulations! Your answer is correct!", JLabel.CENTER);
			
					JOptionPane.showMessageDialog(frame, congrats, "Correct Answer",
							JOptionPane.PLAIN_MESSAGE); //"frame" is to position your dialog box relative to frame
				} else {
					JOptionPane.showMessageDialog(frame, "Too bad, your answer was not correct.", "Incorrect Answer",
							JOptionPane.PLAIN_MESSAGE);
				}
				
			}
			
		});
		g.gridx = counter;
		g.gridy = 0;
		p2.add(checkButton, g);
		
		//control answer 
		

	}

	public void setPanelClues() {
		Panel panelThree = new Panel();
		p3 = panelThree.getPanelClues();
		p3.setLayout(new GridLayout(42,5));
		p3.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		p3.setBackground(Color.white);
		CluesList cluesContent = new CluesList();
		String [] cluesContentTwo = cluesContent.getStringClues();
		
		System.out.println(Arrays.toString(cluesContentTwo));
		GridBagConstraints s = new GridBagConstraints();
		//make JLabels for each clue
		int count = 0;
		for(String cluesOne : cluesContentTwo) {
		//for(int i = 0; i < 20; i++) {
			JLabel labelClue = new JLabel(cluesOne);
			//JLabel labelClue = new JLabel(cluesContentTwo[i]);
			s.gridx = 0;
			s.gridy = count;
			count++;
			p3.add(labelClue);
			
			
		}
		
	}

	//grey squares layout for check



	/*GridBagConstraints g = new GridBagConstraints();
	g.fill = GridBagConstraints.BOTH;

	//position the check button

	JLabel empty = new JLabel("");
	g.gridx = 0;
	g.gridy = 0;
	p2.add(empty, g);




	//int index = 2;
	for(int k = 1; k < 10; k++) {
		JButton greySquare = new JButton();
		greySquare.setPreferredSize(new Dimension(30,30));
		//button.addActionListener(listen);
		g.gridx = k;
		g.gridy = 0;
		//index++;
		p2.add(greySquare, g);
	}

	b1 = new JButton("Check");
	b1.setPreferredSize(new Dimension(200,200));
	//b1.setPreferredSize(new Dimension(10,10));
	g.gridx = 11;
	g.gridy=0;
	g.gridwidth = 2;
	p2.add(b1,g);

	JLabel emptyTwo = new JLabel("");
	g.gridx = 12;
	g.gridy = 0;
	p2.add(emptyTwo, g);
	 */


	/*
	public void initialisation() {
		//packing the window and panels
		frame.setLayout(new GridLayout(2,1));
		frame.add(p1, BorderLayout.PAGE_START);
		frame.add(p2, BorderLayout.PAGE_END);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { //stukje code dat je mag kopieren
			public void run() {
				new CrosswordPanel("Swedish Crossword"); 
			}
		});
	}
}
