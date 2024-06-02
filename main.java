package Wordle;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class main {
	    private static final List<String> WORD_LIST = Arrays.asList("apple", "grape", "peach", "mango", "berry", "lemon");
	    private static final int MAX_ATTEMPTS = 6;

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        String chosenWord = selectRandomWord();
	        int currentAttempt = 0;
	        boolean isGuessed = false;

	        System.out.println("Welcome to Wordle! You have " + MAX_ATTEMPTS + " attempts to guess the correct word.");
	        System.out.println("Each guess must be a 5-letter word.");

	        while (currentAttempt < MAX_ATTEMPTS && !isGuessed) {
	            System.out.print("Enter your guess (" + (currentAttempt + 1) + "/" + MAX_ATTEMPTS + "): ");
	            String userGuess = scanner.nextLine().toLowerCase();

	            if (validateGuess(userGuess)) {
	                currentAttempt++;
	                String feedback = getFeedback(userGuess, chosenWord);
	                System.out.println("Feedback: " + feedback);
	                
	                if (userGuess.equals(chosenWord)) {
	                    isGuessed = true;
	                    System.out.println("Congratulations! You've guessed the word correctly.");
	                }
	            } else {
	                System.out.println("Invalid guess. Please enter a 5-letter word.");
	            }
	        }

	        if (!isGuessed) {
	            System.out.println("Sorry, you've used all attempts. The correct word was: " + chosenWord);
	        }

	        scanner.close();
	    }

	    private static String selectRandomWord() {
	        Random random = new Random();
	        return WORD_LIST.get(random.nextInt(WORD_LIST.size()));
	    }

	    private static boolean validateGuess(String guess) {
	        return guess.length() == 5 && WORD_LIST.contains(guess);
	    }

	    private static String getFeedback(String guess, String chosenWord) {
	        StringBuilder feedback = new StringBuilder();
	        for (int i = 0; i < guess.length(); i++) {
	            if (guess.charAt(i) == chosenWord.charAt(i)) {
	                feedback.append(guess.charAt(i)); // Correct letter in the correct place
	            } else if (chosenWord.contains(String.valueOf(guess.charAt(i)))) {
	                feedback.append("+"); // Correct letter in the wrong place
	            } else {
	                feedback.append("-"); // Incorrect letter
	            }
	        }
	        return feedback.toString();
	    }
}
