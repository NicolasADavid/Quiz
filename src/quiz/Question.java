package quiz;

interface Gradable {

    public boolean gradeQuestion();
}

/**
 * @author NicolasADavid
 * @author Javatlacati
 */
abstract public class Question {

    public static final String VETTED = "vetted";
    public static final String TRIAL = "trial";

    protected String text;
    protected String answer;
    protected String explanation;
    protected String userAnswer;
    protected String vettedOrTrial;
    protected String category;
    protected int difficulty;

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
//    /**
//     * Checks the answer(s)
//     * @return true for correct, false otherwise
//     */
//    public abstract double checkAnswer(String answer);

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
            if (this instanceof FillBlankQuestion) {
                if (userAnswer.equalsIgnoreCase(answer)) {
                    return 1.0;
                }
//                else {
                //TODO tal vez aquí llamar a checkQuestionProvidingAnswer para llenar
//                    return 0.0;
//                }
            } else {
                if (userAnswer.equals(answer)) {
                    return 1.0;
                }
//                else {
                //TODO tal vez aquí llamar a checkQuestionProvidingAnswer para llenar
//                    return 0.0;
//                }
            }
        }
        //TODO tal vez aquí llamar a checkQuestionProvidingAnswer para llenar
        return 0.0;

    }

    /**
     * Maximum number of point that can be awarded by this question.
     */
    abstract double getMaxPoints();

}
