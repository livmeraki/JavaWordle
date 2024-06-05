import java.util.Random;

public class GameLogic {
    public static String pickRandomWord(String[] wordsArray) {
        Random random = new Random();
        int randomIndex = random.nextInt(wordsArray.length);
        return wordsArray[randomIndex];
    }
}
