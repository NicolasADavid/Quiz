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
import java.util.Arrays;
import java.util.Collections;

/**
 * A multiple answer question
 */
public class MultipleAnswerQuestion extends MultipleChoiceQuestion {

    private final ArrayList<String> choices;
    private final ArrayList<String> correctAnswers;

    /**
     * Constructs a choice question with no choices.
     */
    public MultipleAnswerQuestion(String vettedness) {
        super(vettedness);
        choices = new ArrayList<>(2);
        correctAnswers = new ArrayList<>(1);
    }

    /**
     * Adds an answer choice to this question.
     *
     * @param choice the choice to add
     * @param correct true if this is the correct choice, false otherwise
     */
    @Override
    public void setChoice(String choice, boolean correct) {
        choices.add(choice);
        if (correct) {
            // Convert choices.size() to string
            String choiceString = String.valueOf(choices.size());
            setAnswer(choiceString);
        }
    }

    public void setAnswer(String answer) {
        correctAnswers.add(answer);
    }

    @Override
    public String getAnswer() {
        return Arrays.toString(correctAnswers.toArray());
    }

    public double checkQuestionProvidingAnswer(final String answer) {
        userAnswer = answer;
        return checkQuestion();
    }

    /**
     *
     * @return
     */
    @Override
    public double checkQuestion() {

        String delims = "[ ]+";
        String[] tokens = userAnswer.split(delims);

        ArrayList<String> answers = new ArrayList<>(tokens.length);
        Collections.addAll(answers, tokens);

        double totRightAnswers = (double) correctAnswers.size();
        double totAnswers = (double) tokens.length;
        double wrongAnswers = (double) tokens.length;
        double grade = 0.0;

        for (String answer : answers) {
            for (String correctAnswer : correctAnswers) {
                if (answer.equals(correctAnswer)) {
                    grade += 1.0 / totRightAnswers;
                    wrongAnswers -= 1.0;
                }
            }
        }

        //Deducts points for answers given that were not correct or in excess.
        grade -= wrongAnswers * (1.0 / totRightAnswers);

        /*
         * If more answers were wrong/in excess than were right, causing a
         * negative grade, it is brought back up to 0.
         */
        if (grade < 0.0) {
            grade = 0.0;
        }

        return grade;
    }

    /**
     * @return
     */
    @Override
    public String display() {

        StringBuilder display = new StringBuilder(text);
        display.append("\n");

        for (int i = 0; i < choices.size(); i++) {
            final int choiceNumber = i + 1;
            display.append(choiceNumber).append(": ").append(choices.get(i)).append("\n");
        }

        return display.toString();

    }

}
