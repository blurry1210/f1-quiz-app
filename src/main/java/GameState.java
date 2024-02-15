import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int currentQuestionIndex;
    private final int userScore;
    private final List<Question> questions;

    public GameState(int currentQuestionIndex, int userScore, List<Question> questions) {
        this.currentQuestionIndex = currentQuestionIndex;
        this.userScore = userScore;
        this.questions = questions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getUserScore() {
        return userScore;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
