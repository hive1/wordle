package premadewordle;

// import necessary libraries
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Wordle
{
    // instance variables
    private JFrame game = new JFrame("Wordle");
    private JButton [] buttons = new JButton [30];
    //private String letter = "";
    private int guessCount = 0;
    private boolean gameOver = false;
    private boolean playAgain = true;
    private String secretWord = "";
    private String guess = "";

    // the following path should indicate where your words.txt file is
    private String textFile = "C:\\Users\\jfs77\\Documents\\Code\\Java\\eclipse\\premadewordle\\src\\main\\java\\premadewordle\\Wordle.java";

    // construction with no parameters
    public Wordle() {
        for (int i = 0; i < 30; i++) {
            buttons[i] = new JButton("");
            game.add(buttons[i]);
            buttons[i].setBackground(Color.gray);
            buttons[i].setEnabled(false);
            buttons[i].setFont(new Font("Courier New", Font.BOLD, 30));
        }


        // set up the initial game board
        game.setSize(500, 600);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLayout(new GridLayout(6, 5));
        game.setLocation(200, 200);
        game.setVisible(true);
        game.setTitle("Wordle");

        // start the game
        while (playAgain) {
            gameOver = false;
            guessCount = 0;
            getWord();
            resetBoard();
            while (guessCount < 6 && !gameOver) {
                getGuess();
                setText();
                checkLetters();
                checkWin();
                guessCount++;
            }
            playAgain();
        }
    }

    public void getWord() {
        int number = (int)(Math.random()*571);
        int wordnum = 0;
        try {
            Scanner scanner = new Scanner(new File(textFile));
            while (scanner.hasNextLine()) {
                secretWord = scanner.nextLine().toUpperCase();
                wordnum ++;
                if (wordnum >= number) break;
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    // ask the player for a guess and make sure it's valid
    public void getGuess() {
        //Scanner scan = new Scanner(System.in);

        while (true) {
            guess = JOptionPane.showInputDialog("Enter guess #" + (guessCount+1) + ": must be 5 characters, all letters").toUpperCase();
            if(guess.length() == 5) {
                for(int i = 0; i < guess.length(); i++) {
                    char m = guess.charAt(i);

                    if(!(Character.isLetter(m))) {
                        System.out.println("Please retry input");
                        continue;
                    }
                }
                break;
            }
        }
    }

    public void setText() {
        int rows = guessCount * 5;
        buttons[rows].setText(guess.substring(0,1));
        buttons[rows+1].setText(guess.substring(1, 2));
        buttons[rows+2].setText(guess.substring(2, 3));
        buttons[rows+3].setText(guess.substring(3, 4));
        buttons[rows+4].setText(guess.substring(4, 5));
    }

    // compare the guess to the secret word and change the box colors accordingly
    public void checkLetters() {        
        for(int i = 0; i < guess.length(); i++) {
        	char x = guess.charAt(i);
        	
        	if(secretWord.charAt(i) == x) {
        		// if the letter is in the correct position
        		buttons[guessCount * 5 + i].setBackground(Color.green);
        	} else if (secretWord.indexOf(x) > 0) {
        		// if the letter is in the word but not in the correct position
        		buttons[guessCount * 5 + i].setBackground(Color.orange);
        	} else {
        		// here was the bug
        		; 
        	}
        }
    }

    // check if the player guessed the word or if the player use their guess
    public void checkWin() {
        if(guess.equals(secretWord)) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "You win!");
        } else if (guessCount == 6) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "They lost, show the word");
        }
    }

    public void resetBoard() {
        for(int i = 0; i <= 29; i++) {
            buttons[i].setBackground(Color.gray);
            buttons[i].setText("");
        }
    }

    public void playAgain() {
        JFrame frame = new JFrame();
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to play again?", "Swing Tester",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        playAgain = result == JOptionPane.YES_OPTION;
        if(playAgain == false) {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }

    // main method
    public static void main(String args[]) {
        Wordle myGame = new Wordle();
    }
}