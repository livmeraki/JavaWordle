import java.util.Random;
import java.util.List;

public class GameLogic {
    public static String pickRandomWord(List<String> wordsArray) {
        Random random = new Random();
        int randomIndex = random.nextInt(wordsArray.size());
        return wordsArray.get(randomIndex);
    }
}
