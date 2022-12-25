///////////////////////////////////////////////////////////////////////////////                  
// Title:            Quiz
// Files:            
// Semester:         COP3337 Fall 2015
//
// Author:           3587814
// Lecturer's Name:  Prof. Maria Charters
//
// Description of Program’s Functionality: 
//////////////////////////// 80 columns wide/////////////////////////////////
package quiz.entities;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A question with multiple choices. Includes TrueFalse questions
 */
public class MultipleChoiceQuestion extends Question {

    private static final Pattern BLANKS = Pattern.compile("\\s+");
    /**
     * answer options
     */
    protected final ArrayList<String> choices;

    /**
     * Constructs a multiple choice question with no choices.
     */
    public MultipleChoiceQuestion(String vettedness) {
        super(vettedness);
        choices = new ArrayList<>(2);
    }

    /**
     * Adds an answer choice to this question.
     *
     * @param choice  the choice to add
     * @param correct true if this is the correct choice, false otherwise
     */
    public void setChoice(String choice, boolean correct) {
        choices.add(choice);
        if (correct) {
            // Convert choices.size() to string
            String choiceString = String.valueOf(choices.size());
            setAnswer(choiceString);
        }
    }

    /**
     * Sets the correct answer. A number in a string.
     *
     * @param answer a number in a string that corresponds to the answers place
     *               in the choices arrayList
     */
    @Override
    public void setAnswer(String answer) {
        this.answer = answer;
//        int correctAnswer = choices.indexOf(answer);
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public double checkQuestionProvidingAnswer(final String answer) {
        userAnswer = BLANKS.matcher(answer).replaceAll("");
        return checkQuestion();
    }

    /**
     * Returns a string with the question text and choices
     *
     * @return string with question text and choice
     */
    @Override
    public String display() {

        StringBuilder display = new StringBuilder(text);
        display.append("\n");

        for (int i = 0; i < choices.size(); i++) {
            int choiceNumber = i + 1;
            display.append(choiceNumber).append(": ").append(choices.get(i)).append("\n");
        }

        return display.toString();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public double getMaxPoints() {
        return 1.0;
    }

}
