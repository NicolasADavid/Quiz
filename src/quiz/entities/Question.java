package quiz.entities;

import quiz.entities.Difficulty;
import quiz.entities.FillBlankQuestion;

/**
 * @author NicolasADavid
 * @author Javatlacati
 */
abstract public class Question {

    public static final String VETTED = "vetted";
    public static final String TRIAL = "trial";

    /**
     * Question enunciate
     */
    protected String text;
    /**
     * default correct answer
     */
    protected String answer;
    /**
     * Justification of why the answer is correct
     */
    protected String explanation;
    /**
     * user provided answer
     */
    protected String userAnswer;
    protected String vettedOrTrial;
    protected String category;
    protected Difficulty difficulty;

    /**
     * Constructs a question with empty question and answer
     *
     * @param vettedness
     */
    public Question(final String vettedness) {
//        text = "";//only necessary for Java 5 where default value for String variable at class scope was null instead of ""
//        answer = "";
        vettedOrTrial = vettedness;
        category = "default";
        difficulty = Difficulty.NORMAL;
    }

    /**
     * Sets the question text.
     *
     * @param questionText the text of this question
     */
    public void setText(final String questionText) {
        text = questionText;
    }

    /**
     * Sets the correct answer(s)
     *
     * @param answer answer text
     */
    public abstract void setAnswer(final String answer);

    public abstract String getAnswer();

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * Show question Text
     *
     * @return question text
     */
    public String display() {
        return text;
    }

    public boolean gradeQuestion() {
        return vettedOrTrial.equals(VETTED);
    }

    public abstract double checkQuestionProvidingAnswer(final String answer);

    public double checkQuestion() {
        if (userAnswer != null) {
            if (userAnswer.equalsIgnoreCase(answer)) {
                return 1.0;
            }
        }
        //TODO tal vez aquí llamar a checkQuestionProvidingAnswer para llenar
        return 0.0;

    }

    /**
     * Maximum number of point that can be awarded by this question.
     */
    public abstract double getMaxPoints();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
