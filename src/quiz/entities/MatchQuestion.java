package quiz.entities;

/**
 * for question where you should match the concept with the answers without duplicates
 * programming language  - Java
 * markup language  - HTML
 * natural language  - Sign language
 */
public class MatchQuestion extends Question {
    /**
     * Constructs a question with empty question and answer
     *
     * @param vettedness
     */
    public MatchQuestion(String vettedness) {
        super(vettedness);
    }

    @Override
    public void setAnswer(String answer) {

    }

    @Override
    public String getAnswer() {
        return null;
    }

    @Override
    public double checkQuestionProvidingAnswer(String answer) {
        return 0;
    }

    @Override
    public double getMaxPoints() {
        return 0;
    }
}
