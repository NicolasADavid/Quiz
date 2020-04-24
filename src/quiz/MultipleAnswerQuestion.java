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
package quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MultipleAnswerQuestion extends MultipleChoiceQuestion {

    private ArrayList<String> choices;
    private ArrayList<String> correctAnswers;

    /**
     * Constructs a choice question with no choices.
     */
    public MultipleAnswerQuestion(String vettedness) {
        super(vettedness);
        choices = new ArrayList<>();
        correctAnswers = new ArrayList<>();
    }

    /**
     * Adds an answer choice to this question.
     *
     * @param choice the choice to add
     * @param correct true if this is the correct choice, false otherwise
     */
    public void setChoice(String choice, boolean correct) {
        choices.add(choice);
        if (correct) {
            // Convert choices.size() to string
            String choiceString = "" + choices.size();
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

        double totRightAnswers = correctAnswers.size();
        double totAnswers = tokens.length;
        double wrongAnswers = tokens.length;
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

        String display = text + "\n";

        for (int i = 0; i < choices.size(); i++) {
            final int choiceNumber = i + 1;
            display = display.concat(choiceNumber + ": " + choices.get(i) + "\n");
        }

        return display;

    }

}
