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
    // grid size
    private final int gridSize = 5;
    private final int maxAttempts = 6;

    // word file import
    private final String FILE_PATH = "wordle_words.txt";
    private final String FILE_PATH2 = "src\\words.txt";
    private List<String> wordList;
    private List<String> wordList2;

    // player variables
    private String targetWord;
    private String[] guesses;
    private int currentAttempt;
    private int currentLetter;
    private boolean gameWon;
    private boolean isitWord;

    // entire screen size
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

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
        System.out.println("Word list loaded with " + wordList.size() + " words.");

        wordList2 = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH2))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList2.add(line.trim().toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Word list2 loaded with " + wordList2.size() + " words."); // Debug print
    }

    public GamePanel() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new KeyHandler());

        // initialize guesses array
        guesses = new String[maxAttempts];
        for (int i = 0; i < maxAttempts; i++) {
            guesses[i] = "";
        }

        // initialize setting
        loadWords();
        targetWord = GameLogic.pickRandomWord(wordList);
        currentAttempt = 0;
        currentLetter = 0;
        gameWon = false;
        isitWord = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraphics(g);
        System.out.println(isitWord);
        if (isitWord) {
            if (gameWon) {
                g.setColor(Color.GREEN);
                g.drawString("You Won!", getWidth() / 2 - 50, getHeight() - 50);
            } else if (currentAttempt == maxAttempts) {
                g.setColor(Color.RED);
                g.drawString("Game Over!", getWidth() / 2 - 50, getHeight() - 50);
                g.drawString("Word was: " + targetWord, getWidth() / 2 - 50, getHeight() - 20);
            } 
        }else {
            g.setColor(Color.RED);
            g.drawString("It is not word!", getWidth() / 2 - 50, getHeight() - 50);
        }
    }

    private void drawGraphics(Graphics g) {
        int width = 70;
        int height = 70;
        for (int i = 0; i < maxAttempts; i++) {
            for (int j = 0; j < gridSize; j++) {
                int fontSize = 40;
                Font f = new Font("Verdana", Font.BOLD, fontSize);
                g.setFont(f);

                int x = (int) (j * 80 + (int) size.getWidth() / 2 - width * 2.5);
                int y = i * 80 + (int) size.getHeight() / 8;
                g.drawRect((int) x, y, width, height);
                if (j < guesses[i].length()) {
                    char letter = guesses[i].charAt(j);
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(letter), x + 20, y + 50);
                }
            }
        }

        for (int i = 0; i < currentAttempt; i++) {
            for (int j = 0; j < gridSize; j++) {
                int x = (int) (j * 80 + (int) size.getWidth() / 2 - width * 2.5);
                int y = i * 80 + (int) size.getHeight() / 8;
                char letter = guesses[i].charAt(j);

                int fontSize = 40;
                Font f = new Font("Verdana", Font.BOLD, fontSize);
                g.setFont(f);

                if (letter == targetWord.charAt(j)) {
                    g.setColor(Color.GREEN.darker());
                } else if (targetWord.indexOf(letter) != -1) {
                    g.setColor(Color.YELLOW.darker());
                } else {
                    g.setColor(Color.GRAY);
                }

                g.fillRect(x + 1, y + 1, width - 1, height - 1);
                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(letter), x + 20, y + 50);
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

    private boolean isWordValid(String word) {
        isitWord = true;
            isitWord = wordList2.contains(word);
        
        return wordList2.contains(word);
    }

    private void checkGuess() {
        String guess = guesses[currentAttempt];

        if (isWordValid(guess)) {
            if (guess.equals(targetWord)) {
                gameWon = true;
            } else {
                currentAttempt++;
                currentLetter = 0;
            }
        } else {
            guesses[currentAttempt] = "";
            currentLetter = 0;
            repaint();
        }
    }

}
