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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import quiz.utils.Util;

/**
 * Represents a Quiz
 */
public class Quiz {

    private List<Question> questions;

    private double score;
    private double vettedScore;
    private double trialScore;

    private int totalVetted;
    private int totalTrial;

    private int totalCorrectVetted;
    private int totalIncorrectVetted;

    private int totalCorrectTrial;
    private int totalIncorrectTrial;

    /**
     * Default Constructor
     */
    public Quiz() {
        questions = new ArrayList<>();
    }

    /**
     * Creates the questions of the quiz. 10 Questions.
     */
    public void createQuestions() {
        addMultipleChoiceQuestion(questions, Question.VETTED,
                "Multiple choice: What is the first month of the year?",
                createAnswerChoicesMap(
                        Util.varargs("January", "February", "March"),
                        Util.varargs(true, false, false)
                )
        );

        addMultipleChoiceQuestion(questions, Question.TRIAL,
                "Multiple choice: What is an apple product?",
                createAnswerChoicesMap(
                        Util.varargs("ipod", "zune", "lenovo"),
                        Util.varargs(true, false, false)
                )
        );

        addMultipleChoiceQuestion(questions, Question.VETTED,
                "Multiple choice: What is Barack Obama known for?",
                createAnswerChoicesMap(
                        Util.varargs("Really likes the show Narcos",
                                "He's the president, dude",
                                "Doesn't actually like mac and cheese"),
                        Util.varargs(false, true, false)
                )
        );

        addMultipleAnswerQuestion(questions, Question.VETTED,
                "Multiple answer (more than one possible): What days are not work days?",
                createAnswerChoicesMap(
                        Util.varargs("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
                        Util.varargs(false, false, false, false, false, true, true)
                )
        );

        addMultipleAnswerQuestion(questions, Question.TRIAL,
                "Multiple answer (more than one possible): What words begin with the letter \"a\"?",
                createAnswerChoicesMap(
                        Util.varargs("apple", "chair", "astronomy", "desk"),
                        Util.varargs(true, false, true, false)
                )
        );
        addMultipleAnswerQuestion(questions, Question.VETTED,
                "Multiple answer (more than one possible): What are common household chores?",
                createAnswerChoicesMap(
                        Util.varargs("sweeping", "laundry", "skydiving", "basket weaving"),
                        Util.varargs(true, true, false, false)
                )
        );

        addMultipleChoiceQuestion(questions, Question.VETTED,
                "Multiple choice: What's the answer, bro?",
                createAnswerChoicesMap(
                        Util.varargs("The answer", "Not the answer", "Also not the answer"),
                        Util.varargs(true, false, false)
                )
        );

        addFillBlankQuestion(questions, Question.VETTED,
                "Fill in the blank: The quick red ___ jumped over the lazy brown ___. Hint: fox dog",
                "fox", "dog"
        );

        addFillBlankQuestion(questions, Question.VETTED,
                "Fill in the blank: This is a fill in the _____ question.",
                "blank"
        );

        addFillBlankQuestion(questions, Question.VETTED,
                "Fill in the blank: This assignment is for a _________ course.",
                "programming"
        );
    }

    /**
     * Display a message. Display the questions. Get input. Check if
     * correct/gradable and notify user of points earned.
     */
    public void displayAndCheckQuestions() {

        //Display a message
        final String message
                = "For multiple choice questions, enter the number of "
                + "your choice.\nFor multiple answer questions, enter the number(s) "
                + "of your choice(s) separated by space.\nFor Fill in the blank"
                + " questions enter your answer(s) separated by spaces.\n";

        System.out.println(message);
        final Scanner scanner = new Scanner(System.in);
        //For every question in questions
        for (int i = 0; i < questions.size(); i++) {
            final Question currentQuestion = questions.get(i);

            //Display the question
            System.out.println(questions.get(i).display());

            //Take input
            final String input = scanner.nextLine();
            System.out.println("Enter your answers in order separated by spaces \n");

            //Show user points earned for answer
            System.out.println("You received " + questions.get(i).checkQuestion(input) + " points.");

            //Add points to total score
            score += currentQuestion.checkQuestion(input);

            //Show user total points earned
            System.out.println("Total points: " + score + "\n");

            //Was the answer vetted/trial and correct, partially correct, or incorrect?
            int compare;

            if (currentQuestion.gradeQuestion()) { //Question is vetted

                //Count vetted questions
                totalVetted++;

                //Add to vetted score
                vettedScore += currentQuestion.checkQuestion(input);

                //Was question correct, partially corrct, or incorrct?
                compare = Double.compare(currentQuestion.checkQuestion(input), 0.0);
                if (compare > 0) {
                    //Count correct/partial vetted
                    totalCorrectVetted++;
                } else {
                    //Count incorrect vetted
                    totalIncorrectVetted++;
                }

            } else { //Question is trial

                //Count trial questions
                totalTrial++;

                //Add to trial score
                trialScore += currentQuestion.checkQuestion(input);

                //Was question correct, partially correct, or incorrect?
                compare = Double.compare(currentQuestion.checkQuestion(input), 0.0);
                if (compare > 0) {
                    //Count correct/partial trial
                    totalCorrectTrial++;
                } else {
                    //Count incorrect trial
                    totalIncorrectTrial++;
                }

            }

        }

    }

    /**
     * Report total questions, vetted questions, and trial questions. Report
     * score earned for each. Report questions answered correctly or partially
     * correctly for each. Report questions answered incorrectly for each.
     */
    public void summarizeResults() {
        final int totalCorrect = totalCorrectVetted + totalCorrectTrial;
        final int totalIncorrect = totalIncorrectVetted + totalIncorrectTrial;

        System.out.println("There were a total of " + questions.size() + " questions.");
        System.out.println("You received a total of " + score + " points.");
        System.out.println("Answered " + totalCorrect + " for full or partial credit");
        System.out.println("Answered " + totalIncorrect + " for no credit");
        System.out.println();

        System.out.println("There were " + totalVetted + " vetted questions.");
        System.out.println("You received a total of " + vettedScore + " points on them.");
        System.out.println("Answered " + totalCorrectVetted + " for full or partial credit");
        System.out.println("Answered " + totalIncorrectVetted + " for no credit");
        System.out.println();

        System.out.println("There were " + totalTrial + " trial questions.");
        System.out.println("You received a total of " + trialScore + " points on them.");
        System.out.println("Answered " + totalCorrectTrial + " for full or partial credit");
        System.out.println("Answered " + totalIncorrectTrial + " for no credit");

        System.out.println("Have a wonderful day.");
    }

    private void addMultipleChoiceQuestion(final List<Question> questions,
            final String vettedness, final String questionText,
            final Map<String, Boolean> answerChoicesMap) {
        final MultipleChoiceQuestion choiceQuestion = new MultipleChoiceQuestion(vettedness);
        choiceQuestion.setText(questionText);
        answerChoicesMap.entrySet().stream().forEach((choiceEntry) -> {
            choiceQuestion.setChoice(choiceEntry.getKey(), choiceEntry.getValue());
        });
        questions.add(choiceQuestion);
    }

    private void addMultipleAnswerQuestion(final List<Question> questions,
            final String vettedness, final String questionText,
            final Map<String, Boolean> answerChoicesMap) {
        final MultipleAnswerQuestion choiceQuestion = new MultipleAnswerQuestion(vettedness);
        choiceQuestion.setText(questionText);
        answerChoicesMap.entrySet().stream().forEach((choiceEntry) -> {
            choiceQuestion.setChoice(choiceEntry.getKey(), choiceEntry.getValue());
        });
        questions.add(choiceQuestion);
    }

    private void addFillBlankQuestion(final List<Question> questions,
            final String vettedness, final String questionText, final String... blanks) {
        final FillBlankQuestion fillBlankQuestion = new FillBlankQuestion(vettedness);
        fillBlankQuestion.setText(questionText);
        for (final String blank : blanks) {
            fillBlankQuestion.setAnswer(blank);
        }
        questions.add(fillBlankQuestion);
    }

    private Map<String, Boolean> createAnswerChoicesMap(final String[] answerTexts, final Boolean[] answerValidities) {
        final int numsOfAnswers = answerTexts.length;
        if (numsOfAnswers != answerValidities.length) {
            throw new RuntimeException("Answer texts should match answr values one by one");
        }
        final HashMap<String, Boolean> hashMap = new HashMap<>();
        for (int answIdx = 0; answIdx < answerTexts.length; answIdx++) {
            hashMap.put(answerTexts[answIdx], answerValidities[answIdx]);
        }
        return hashMap;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(final List<Question> questions) {
        this.questions = questions;
    }

    public double getScore() {
        return score;
    }

    public void setScore(final double score) {
        this.score = score;
    }

    public double getVettedScore() {
        return vettedScore;
    }

    public void setVettedScore(double vettedScore) {
        this.vettedScore = vettedScore;
    }

    public double getTrialScore() {
        return trialScore;
    }

    public void setTrialScore(final double trialScore) {
        this.trialScore = trialScore;
    }

    public int getTotalVetted() {
        return totalVetted;
    }

    public void setTotalVetted(final int totalVetted) {
        this.totalVetted = totalVetted;
    }

    public int getTotalTrial() {
        return totalTrial;
    }

    public void setTotalTrial(final int totalTrial) {
        this.totalTrial = totalTrial;
    }

    public int getTotalCorrectVetted() {
        return totalCorrectVetted;
    }

    public void setTotalCorrectVetted(final int totalCorrectVetted) {
        this.totalCorrectVetted = totalCorrectVetted;
    }

    public int getTotalIncorrectVetted() {
        return totalIncorrectVetted;
    }

    public void setTotalIncorrectVetted(final int totalIncorrectVetted) {
        this.totalIncorrectVetted = totalIncorrectVetted;
    }

    public int getTotalCorrectTrial() {
        return totalCorrectTrial;
    }

    public void setTotalCorrectTrial(final int totalCorrectTrial) {
        this.totalCorrectTrial = totalCorrectTrial;
    }

    public int getTotalIncorrectTrial() {
        return totalIncorrectTrial;
    }

    public void setTotalIncorrectTrial(final int totalIncorrectTrial) {
        this.totalIncorrectTrial = totalIncorrectTrial;
    }

}
