/* Mishael Zerrudo
   A program that plays a game of hangman*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
public class HangmanGUI extends JFrame
{
	private JLabel wordLabel;
	private JLabel hintLabel;
	private JButton aButton;
	private JButton bButton;
	private JButton cButton;
	private JButton dButton;
	private JButton eButton;
	private JButton fButton;
	private JButton gButton;
	private JButton hButton;
	private JButton iButton;
	private JButton jButton;
	private JButton kButton;
	private JButton lButton;
	private JButton mButton;
	private JButton nButton;
	private JButton oButton;
	private JButton pButton;
	private JButton qButton;
	private JButton rButton;
	private JButton sButton;
	private JButton tButton;
	private JButton uButton;
	private JButton vButton;
	private JButton wButton;
	private JButton xButton;
	private JButton yButton;
	private JButton zButton;
	
	private JButton againButton;
	private JButton quitButton;
	private JLabel answerLabel;
	
	private JPanel letterPanel;
	private JPanel wordPanel;
	private DrawGallows gPanel;
	
	private String[] wordList;			//holds words player has to guess
	private char[] wordRevealed;		//determines which words have been revealed. Ex: Array could hold the value '_' 'a' '_' '_' 'm' 'a' '_'
										//which will show up as _ a _ _ m a _ for the user.
	private String word;				//the word chosen for player to guess
	private int randNum;				//a random number that determines which word the player has to guess
	private int tries;
	
	private ButtonListener buttonListens;
	private ContinueListener continueListens;
	
	private final int MAX_WORDS = 75;
	
	public HangmanGUI() throws FileNotFoundException
	{
		super("Hangman Game");
		setBounds(0, 0, 700, 500);
		tries = 0;
		
		//declare panels
		letterPanel = new JPanel(new GridLayout(6, 5, 5, 5));
		wordPanel = new JPanel(new BorderLayout());
		gPanel = new DrawGallows();
		
		//create label that holds the word and the hint
		wordLabel = new JLabel("");					//starts as an empty string because no word has been chosen yet
		hintLabel = new JLabel("");
		answerLabel = new JLabel("");				//shows the answer to player if they fail to guess the word
		answerLabel.setVisible(false);
		
		//create buttons for the letters
		aButton = new JButton("A");
		bButton = new JButton("B");
		cButton = new JButton("C");
		dButton = new JButton("D");
		eButton = new JButton("E");
		fButton = new JButton("F");
		gButton = new JButton("G");
		hButton = new JButton("H");
		iButton = new JButton("I");
		jButton = new JButton("J");
		kButton = new JButton("K");
		lButton = new JButton("L");
		mButton = new JButton("M");
		nButton = new JButton("N");
		oButton = new JButton("O");
		pButton = new JButton("P");
		qButton = new JButton("Q");
		rButton = new JButton("R");
		sButton = new JButton("S");
		tButton = new JButton("T");
		uButton = new JButton("U");
		vButton = new JButton("V");
		wButton = new JButton("W");
		xButton = new JButton("X");
		yButton = new JButton("Y");
		zButton = new JButton("Z");
		
		//creates play again and quit button
		againButton = new JButton("Play Again");
		quitButton = new JButton("Quit");
		againButton.setPreferredSize(new Dimension(100, 75));
		quitButton.setPreferredSize(new Dimension(100, 75));
		againButton.setVisible(false);				//this button only appears after the game ends
		quitButton.setVisible(false);				//this button only appears after the game ends
		gPanel.add(againButton);
		gPanel.add(quitButton);
		
		//add the buttons to the letter panel
		letterPanel.add(aButton);
		letterPanel.add(bButton);
		letterPanel.add(cButton);
		letterPanel.add(dButton);
		letterPanel.add(eButton);
		letterPanel.add(fButton);
		letterPanel.add(gButton);
		letterPanel.add(hButton);
		letterPanel.add(iButton);
		letterPanel.add(jButton);
		letterPanel.add(kButton);
		letterPanel.add(lButton);
		letterPanel.add(mButton);
		letterPanel.add(nButton);
		letterPanel.add(oButton);
		letterPanel.add(pButton);
		letterPanel.add(qButton);
		letterPanel.add(rButton);
		letterPanel.add(sButton);
		letterPanel.add(tButton);
		letterPanel.add(uButton);
		letterPanel.add(vButton);
		letterPanel.add(wButton);
		letterPanel.add(xButton);
		letterPanel.add(yButton);
		letterPanel.add(zButton);
		
		//assign action listener to the letter buttons
		buttonListens = new ButtonListener();
		aButton.addActionListener(buttonListens);
		bButton.addActionListener(buttonListens);
		cButton.addActionListener(buttonListens);
		dButton.addActionListener(buttonListens);
		eButton.addActionListener(buttonListens);
		fButton.addActionListener(buttonListens);
		gButton.addActionListener(buttonListens);
		hButton.addActionListener(buttonListens);
		iButton.addActionListener(buttonListens);
		jButton.addActionListener(buttonListens);
		kButton.addActionListener(buttonListens);
		lButton.addActionListener(buttonListens);
		mButton.addActionListener(buttonListens);
		nButton.addActionListener(buttonListens);
		oButton.addActionListener(buttonListens);
		pButton.addActionListener(buttonListens);
		qButton.addActionListener(buttonListens);
		rButton.addActionListener(buttonListens);
		sButton.addActionListener(buttonListens);
		tButton.addActionListener(buttonListens);
		uButton.addActionListener(buttonListens);
		vButton.addActionListener(buttonListens);
		wButton.addActionListener(buttonListens);
		xButton.addActionListener(buttonListens);
		yButton.addActionListener(buttonListens);
		zButton.addActionListener(buttonListens);
		
		//assign action listener to the continue and quit buttons
		continueListens = new ContinueListener();
		againButton.addActionListener(continueListens);
		quitButton.addActionListener(continueListens);
		
		//add panels to the frame
		add(letterPanel, BorderLayout.SOUTH);
		add(gPanel);
		
		//get the words from HangmanWords.txt and place them in an array
		readFile();
		
		//gets a random word for player to guess
		getWord();
	}
	
	public void getWord()									//gets a random word for the player to guess
	{
		Random rand = new Random();
		randNum = rand.nextInt(MAX_WORDS);							//chooses a random word for user to guess
		word = "";
		wordRevealed = new char[(wordList[randNum]).length()];		//length of wordRevealed array is the same as the length of the word chosen by rand
		for (int i = 0; i < (wordList[randNum]).length(); i++)
		{
			//turn letters of word into blanks
			if ((wordList[randNum]).charAt(i) >= 'A' && (wordList[randNum]).charAt(i) <= 'Z')
			{
				wordRevealed[i] = '_';
				word = word + wordRevealed[i] + " ";							//put a blank line to represent a letter in the word
			}
			//non letters stay the same
			else
			{
				wordRevealed[i] = (wordList[randNum]).charAt(i);
				word = word + wordRevealed[i] + " ";
			}
		}
		wordLabel.setText(word);						//set the text of the word label to show to player
		
		//set the text for the hint to show to player
		if (randNum >= 0 && randNum <= 9)
			hintLabel.setText("Hint: Basketball Teams");
		else if (randNum >= 10 && randNum <= 19)
			hintLabel.setText("Hint: Nintendo Games");
		else if (randNum >= 20 && randNum <= 29)
			hintLabel.setText("Hint: Animated Cartoons");
		else if (randNum >= 30 && randNum <= 34 || randNum >= 60 && randNum <= 64)
			hintLabel.setText("Hint: U.S. Presidents");
		else if (randNum >= 35 && randNum <= 39)
			hintLabel.setText("Hint: Office/School Supplies");
		else if (randNum >= 40 && randNum <= 45)
			hintLabel.setText("Hint: Programming Languages");
		else if (randNum >= 46 && randNum <= 59)
			hintLabel.setText("Hint: Disney Movies");
		else
			hintLabel.setText("Hint: Dog Breeds");
		
		//change font of word label to make it bigger
		Font font = new Font("Arial", Font.PLAIN, 30);
		wordLabel.setFont(font);
		
		//add word label, hint label and answer label to word panel
		wordPanel.add(hintLabel, BorderLayout.NORTH);
		wordPanel.add(wordLabel);
		wordPanel.add(answerLabel, BorderLayout.SOUTH);		//answerLabel only visible after the game ends
		
		//add word panel to the frame
		add(wordPanel, BorderLayout.NORTH);
	}
	
	public void readFile() throws FileNotFoundException			//reads the words and hints from a file and places them in an array
	{
		wordList = new String[MAX_WORDS];
		File wordFile = new File("HangmanWords.txt");
		Scanner input = new Scanner(wordFile);
		if (!wordFile.exists())
		{
			System.out.println("HangmanWords.txt cannot be found");
			System.exit(0);
		}
		for (int i = 0; i < MAX_WORDS; i++)
			wordList[i] = input.nextLine();
	}
	
	public boolean checkWinner()							//checks if user has successfully guessed the word
	{
		for (int i = 0; i < wordRevealed.length; i++)
		{
			if (wordRevealed[i] == '_')						//if user has not yet revealed all the letters of the word
				return false;
		}
		return true;
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int correct = 0;						//checks if the letter chosen by player has a match in the word being guessed
			if (e.getSource() == aButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'A')
					{
						wordRevealed[i] = 'A';
						correct++;
					}
				}
				aButton.setEnabled(false);
			}
			else if (e.getSource() == bButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'B')
					{
						wordRevealed[i] = 'B';
						correct++;
					}
				}
				bButton.setEnabled(false);
			}
			else if (e.getSource() == cButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'C')
					{
						wordRevealed[i] = 'C';
						correct++;
					}
				}
				cButton.setEnabled(false);
			}
			else if (e.getSource() == dButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'D')
					{
						wordRevealed[i] = 'D';
						correct++;
					}
				}
				dButton.setEnabled(false);
			}
			else if (e.getSource() == eButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'E')
					{
						wordRevealed[i] = 'E';
						correct++;
					}
				}
				eButton.setEnabled(false);
			}
			else if (e.getSource() == fButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'F')
					{
						wordRevealed[i] = 'F';
						correct++;
					}
				}
				fButton.setEnabled(false);
			}
			else if (e.getSource() == gButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'G')
					{
						wordRevealed[i] = 'G';
						correct++;
					}
				}
				gButton.setEnabled(false);
			}
			else if (e.getSource() == hButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'H')
					{
						wordRevealed[i] = 'H';
						correct++;
					}
				}
				hButton.setEnabled(false);
			}
			else if (e.getSource() == iButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'I')
					{
						wordRevealed[i] = 'I';
						correct++;
					}
				}
				iButton.setEnabled(false);
			}
			else if (e.getSource() == jButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'J')
					{
						wordRevealed[i] = 'J';
						correct++;
					}
				}
				jButton.setEnabled(false);
			}
			else if (e.getSource() == kButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'K')
					{
						wordRevealed[i] = 'K';
						correct++;
					}
				}
				kButton.setEnabled(false);
			}
			else if (e.getSource() == lButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'L')
					{
						wordRevealed[i] = 'L';
						correct++;
					}
				}
				lButton.setEnabled(false);
			}
			else if (e.getSource() == mButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'M')
					{
						wordRevealed[i] = 'M';
						correct++;
					}
				}
				mButton.setEnabled(false);
			}
			else if (e.getSource() == nButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'N')
					{
						wordRevealed[i] = 'N';
						correct++;
					}
				}
				nButton.setEnabled(false);
			}
			else if (e.getSource() == oButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'O')
					{
						wordRevealed[i] = 'O';
						correct++;
					}
				}
				oButton.setEnabled(false);
			}
			else if (e.getSource() == pButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'P')
					{
						wordRevealed[i] = 'P';
						correct++;
					}
				}
				pButton.setEnabled(false);
			}
			else if (e.getSource() == qButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'Q')
					{
						wordRevealed[i] = 'Q';
						correct++;
					}
				}
				qButton.setEnabled(false);
			}
			else if (e.getSource() == rButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'R')
					{
						wordRevealed[i] = 'R';
						correct++;
					}
				}
				rButton.setEnabled(false);
			}
			else if (e.getSource() == sButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'S')
					{
						wordRevealed[i] = 'S';
						correct++;
					}
				}
				sButton.setEnabled(false);
			}
			else if (e.getSource() == tButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'T')
					{
						wordRevealed[i] = 'T';
						correct++;
					}
				}
				tButton.setEnabled(false);
			}
			else if (e.getSource() == uButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'U')
					{
						wordRevealed[i] = 'U';
						correct++;
					}
				}
				uButton.setEnabled(false);
			}
			else if (e.getSource() == vButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'V')
					{
						wordRevealed[i] = 'V';
						correct++;
					}
				}
				vButton.setEnabled(false);
			}
			else if (e.getSource() == wButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'W')
					{
						wordRevealed[i] = 'W';
						correct++;
					}
				}
				wButton.setEnabled(false);
			}
			else if (e.getSource() == xButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'X')
					{
						wordRevealed[i] = 'X';
						correct++;
					}
				}
				xButton.setEnabled(false);
			}
			else if (e.getSource() == yButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'Y')
					{
						wordRevealed[i] = 'Y';
						correct++;
					}
				}
				yButton.setEnabled(false);
			}
			else if (e.getSource() == zButton)
			{
				for (int i = 0; i < (wordList[randNum]).length(); i++)
				{
					if ((wordList[randNum]).charAt(i) == 'Z')
					{
						wordRevealed[i] = 'Z';
						correct++;
					}
				}
				zButton.setEnabled(false);
			}
			
			if (correct > 0)					//if there was a match, then update the word label
			{
				word = "";
				for (int i = 0; i < wordRevealed.length; i++)
					word = word + wordRevealed[i] + " ";
				wordLabel.setText(word);
				repaint();
			}
			else
			{
				gPanel.incrementTries();
				tries++;
				repaint();
			}
			
			if(checkWinner())					//if the user successfully guessed the word
			{
				answerLabel.setText("Congratulations!");
				againButton.setVisible(true);
				quitButton.setVisible(true);
				answerLabel.setVisible(true);
				//disable all letter buttons
				aButton.setEnabled(false);
				bButton.setEnabled(false);
				cButton.setEnabled(false);
				dButton.setEnabled(false);
				eButton.setEnabled(false);
				fButton.setEnabled(false);
				gButton.setEnabled(false);
				hButton.setEnabled(false);
				iButton.setEnabled(false);
				jButton.setEnabled(false);
				kButton.setEnabled(false);
				lButton.setEnabled(false);
				mButton.setEnabled(false);
				nButton.setEnabled(false);
				oButton.setEnabled(false);
				pButton.setEnabled(false);
				qButton.setEnabled(false);
				rButton.setEnabled(false);
				sButton.setEnabled(false);
				tButton.setEnabled(false);
				uButton.setEnabled(false);
				vButton.setEnabled(false);
				wButton.setEnabled(false);
				xButton.setEnabled(false);
				yButton.setEnabled(false);
				zButton.setEnabled(false);
			}
			else if(tries == 6)
			{
				answerLabel.setText("The answer is: " + wordList[randNum]);
				againButton.setVisible(true);
				quitButton.setVisible(true);
				answerLabel.setVisible(true);
				//disable all letter buttons
				aButton.setEnabled(false);
				bButton.setEnabled(false);
				cButton.setEnabled(false);
				dButton.setEnabled(false);
				eButton.setEnabled(false);
				fButton.setEnabled(false);
				gButton.setEnabled(false);
				hButton.setEnabled(false);
				iButton.setEnabled(false);
				jButton.setEnabled(false);
				kButton.setEnabled(false);
				lButton.setEnabled(false);
				mButton.setEnabled(false);
				nButton.setEnabled(false);
				oButton.setEnabled(false);
				pButton.setEnabled(false);
				qButton.setEnabled(false);
				rButton.setEnabled(false);
				sButton.setEnabled(false);
				tButton.setEnabled(false);
				uButton.setEnabled(false);
				vButton.setEnabled(false);
				wButton.setEnabled(false);
				xButton.setEnabled(false);
				yButton.setEnabled(false);
				zButton.setEnabled(false);
			}
		}
	}
	
	//action listener for play again and quit buttons
	private class ContinueListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == againButton)
			{
				//reset the buttons and labels
				againButton.setVisible(false);
				quitButton.setVisible(false);
				answerLabel.setVisible(false);
				aButton.setEnabled(true);
				bButton.setEnabled(true);
				cButton.setEnabled(true);
				dButton.setEnabled(true);
				eButton.setEnabled(true);
				fButton.setEnabled(true);
				gButton.setEnabled(true);
				hButton.setEnabled(true);
				iButton.setEnabled(true);
				jButton.setEnabled(true);
				kButton.setEnabled(true);
				lButton.setEnabled(true);
				mButton.setEnabled(true);
				nButton.setEnabled(true);
				oButton.setEnabled(true);
				pButton.setEnabled(true);
				qButton.setEnabled(true);
				rButton.setEnabled(true);
				sButton.setEnabled(true);
				tButton.setEnabled(true);
				uButton.setEnabled(true);
				vButton.setEnabled(true);
				wButton.setEnabled(true);
				xButton.setEnabled(true);
				yButton.setEnabled(true);
				zButton.setEnabled(true);
				//reset the number of tries
				tries = 0;
				gPanel.resetTries();		//reset the value of tries in DrawGallows object
				getWord();					//get a new word from the array
			}
			else if (e.getSource() == quitButton)
				System.exit(0);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		JFrame frame = new HangmanGUI();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}