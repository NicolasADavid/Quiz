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
import java.util.List;

public class FillBlankQuestion extends Question {

    private final List<String> correctAnswers;

    public FillBlankQuestion(final String vettedness) {
        super(vettedness);
        correctAnswers = new ArrayList<>(1);
    }

    @Override
    public void setAnswer(final String answer) {
        correctAnswers.add(answer);
    }

    @Override
    public String getAnswer() {
        return Arrays.toString(correctAnswers.toArray());
    }

    @Override
    public double checkQuestionProvidingAnswer(final String daAnswer) {
        userAnswer = daAnswer;
        return checkQuestion();
    }
    private static final String delims = "[\\-]+";

    @Override
    public double checkQuestion() {
        final String[] tokens = this.userAnswer.split(delims);

        final ArrayList<String> answers = new ArrayList<>(tokens.length);
        answers.addAll(Arrays.asList(tokens));

        final double totRightAnswers = (double) correctAnswers.size();
        //final double totAnswers = tokens.length;
        double grade = 0.0;

        for (int i = 0; i < answers.size(); i++) {
            if (i < correctAnswers.size() && answers.get(i).equals(correctAnswers.get(i))) {
                grade += 1.0 / totRightAnswers;
            }
        }

        return grade;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected double getMaxPoints() {
        return 1.0;
    }
}
