import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private final int gridSize = 5;
    private final int maxAttempts = 6;
    private final String FILE_PATH = "C:\\Users\\markm\\Documents\\Handong 4th Sem\\Java Programming\\Wordle_Project\\Wordle\\src\\wordslistfile.txt";
    private List<String> wordList;
    private String targetWord;
    private String[] guesses;
    private int currentAttempt;
    private int currentLetter;
    private boolean gameWon;

    private void loadWords() {
        wordList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line.trim().toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Word list loaded with " + wordList.size() + " words."); // Debug print
    }

    public GamePanel() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new KeyHandler());

        guesses = new String[maxAttempts];
        for (int i = 0; i < maxAttempts; i++) {
            guesses[i] = "";
        }
        loadWords();
        // Convert the wordList to a String array and then pick a random word
        targetWord = GameLogic.pickRandomWord(wordList.toArray(new String[0]));
        currentAttempt = 0;
        currentLetter = 0;
        gameWon = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        if (gameWon) {
            g.setColor(Color.GREEN);
            g.drawString("You Won!", getWidth() / 2 - 50, getHeight() - 50);
        } else if (currentAttempt == maxAttempts) {
            g.setColor(Color.RED);
            g.drawString("Game Over!", getWidth() / 2 - 50, getHeight() - 50);
            g.drawString("Word was: " + targetWord, getWidth() / 2 - 50, getHeight() - 20);
        }
    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < maxAttempts; i++) {
            for (int j = 0; j < gridSize; j++) {
                int x = j * 80 + 60;
                int y = i * 80 + 60;
                g.drawRect(x, y, 60, 60);
                if (j < guesses[i].length()) {
                    char letter = guesses[i].charAt(j);
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(letter), x + 30, y + 40);
                }
            }
        }

        for (int i = 0; i < currentAttempt; i++) {
            for (int j = 0; j < gridSize; j++) {
                int x = j * 80 + 60;
                int y = i * 80 + 60;
                char letter = guesses[i].charAt(j);
                if (letter == targetWord.charAt(j)) {
                    g.setColor(Color.GREEN);
                } else if (targetWord.indexOf(letter) != -1) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(x, y, 60, 60);
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(letter), x + 30, y + 40);
                g.setColor(Color.WHITE);
            }
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar();
            if (Character.isLowerCase(key)) {
                key = Character.toUpperCase(key);
            }

            if (Character.isLetter(key) && currentLetter < gridSize) {
                guesses[currentAttempt] += key;
                currentLetter++;
                repaint();
            } else if (key == KeyEvent.VK_BACK_SPACE && currentLetter > 0) {
                guesses[currentAttempt] = guesses[currentAttempt].substring(0, guesses[currentAttempt].length() - 1);
                currentLetter--;
                repaint();
            } else if (key == KeyEvent.VK_ENTER && currentLetter == gridSize) {
                checkGuess();
                repaint();
            }
        }
    }

    private void checkGuess() {
        String guess = guesses[currentAttempt];
        if (guess.equals(targetWord)) {
            gameWon = true;
        } else {
            currentAttempt++;
            currentLetter = 0;
        }
    }
}
