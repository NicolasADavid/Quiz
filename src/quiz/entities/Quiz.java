package quiz.entities;

import quiz.service.QuestionParser;
import quiz.service.strategy.QuestionStrategy;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.ResourceBundle.getBundle;

/**
 * Represents a Quiz
 *
 * @author NicolasADavid
 * @author Javatlacati
 */
public class Quiz {
    private static final Pattern BLANKS = Pattern.compile("\\s+");

    private static final Scanner scanner = new Scanner(System.in);

    private int maxQuestions = -1;
    /**
     *
     */
    private List<Question> questions;

    List<String> selectedCategories;
    /**
     * Score obtained in the quiz.
     */
    private double score;
    /**
     * Mandatory questions.
     */
    private double vettedScore;
    /**
     * Optional questions.
     */
    private double trialScore;
    /**
     * Number of vetted questions asked.
     */
    private int totalVetted;
    /**
     * Number of trial questions asked.
     */
    private int totalTrial;
    /**
     * Number of vetted questions answered correcly.
     */
    private int totalCorrectVetted;
    /**
     * Number of vetted questions not answered correcly.
     */
    private int totalIncorrectVetted;
    /**
     *
     */
    private int totalCorrectTrial;
    /**
     *
     */
    private int totalIncorrectTrial;

    /**
     * Default Constructor
     */
    public Quiz() {
        questions = new ArrayList<>(50);
    }


    /**
     * Display a message. Display the questions. Get input. Check if
     * correct/gradable and notify user of points earned.
     */
    public void displayAndCheckQuestions() {

        //Display a message
        final String message
                = MessageFormat.format(getBundle("quiz/resources/quiz").getString("FOR_MULTIPLE_CHOICE"), new Object[]{});
        Collections.shuffle(questions);//pregunta aleatoriamente
        System.out.println(message);

        int questionsToAsk = getQuestionsToAsk();
        //For every question in questions
        for (int i = 0; i < questionsToAsk; i++) {
            Question currentQuestion = questions.get(i);
            //Display the question
            System.out.println(currentQuestion.display());
            System.out.println(getBundle("quiz/resources/quiz").getString("ENTER_YOUR_ANSWERS"));
            //Take input
            final String input = scanner.nextLine();

            //Show user points earned for answer
            //TODO perhaps instead of
            //currentQuestion.checkQuestionProvidingAnswer(input) userAnswer
            //could be just setted
            System.out.println(MessageFormat.format(getBundle("quiz/resources/quiz").getString("RECEIVED_POINTS"), currentQuestion.checkQuestionProvidingAnswer(input)));

            //Add points to total score
            final double puntosRespuesta = currentQuestion.checkQuestion();
            score += puntosRespuesta;

            //Show user total points earned
            System.out.printf(getBundle("quiz/resources/quiz").getString("TOTAL_POINTS"), score);

            //Was the answer vetted/trial and correct, partially correct, or incorrect?
            if (currentQuestion.gradeQuestion()) {
                countForVetted(puntosRespuesta);
            } else {
                countForTrial(puntosRespuesta);
            }

        }

        //    /**
//     * Takes an answer from input
//     */
//    public boolean takeAnswer() {
//        String input;
//        System.out.println("Enter the number of the answer");
//        Scanner in = new Scanner(System.in); //Create new scanner for user input
//        input = in.nextLine();
//        return checkAnswer(input);
//    }

    }

    private int getQuestionsToAsk() {
        int questionsToAsk;
        if (maxQuestions != -1 && maxQuestions < questions.size()) {
            questionsToAsk = maxQuestions;
        } else {
            questionsToAsk = questions.size();
        }
        return questionsToAsk;
    }

    private void countForVetted(double puntosRespuesta) {
        //Count vetted questions
        totalVetted++;

        //Add to vetted score
        vettedScore += puntosRespuesta;

        //Was question correct, partially corrct, or incorrct?
        //Question is vetted
        int compare = Double.compare(puntosRespuesta, 0.0);
        if (compare > 0) {
            //Count correct/partial vetted
            totalCorrectVetted++;
        } else {
            //Count incorrect vetted
            totalIncorrectVetted++;
        }
    }

    private void countForTrial(double puntosRespuesta) {

        //Count trial questions
        totalTrial++;

        //Add to trial score
        trialScore += puntosRespuesta;

        //Was question correct, partially correct, or incorrect?
        //Question is trial
        int compare = Double.compare(puntosRespuesta, 0.0);
        if (compare > 0) {
            //Count correct/partial trial
            totalCorrectTrial++;
        } else {
            //Count incorrect trial
            totalIncorrectTrial++;
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

        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("TOTAL_QUESTIONS"), new Object[]{}), questions.size());
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("TOTAL_POINTS"), new Object[]{}), score);
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("FOR_CREDIT"), new Object[]{}), totalCorrect);
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("FOR_NO_CREDIT"), new Object[]{}), totalIncorrect);
        System.out.println();

        System.out.println(MessageFormat.format(getBundle("quiz/resources/quiz").getString("VETTED_QUESTIONS"), totalVetted));
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("TOTAL_OF_POINTS"), new Object[]{}), vettedScore);
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("FOR_CREDIT"), new Object[]{}), totalCorrectVetted);
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("FOR_NO_CREDIT"), new Object[]{}), totalIncorrectVetted);
        System.out.println();

        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("TRIAL_QUESTIONS"), new Object[]{}), totalTrial);
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("TOTAL_OF_POINTS"), new Object[]{}), trialScore);
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("FOR_CREDIT"), new Object[]{}), totalCorrectTrial);
        System.out.printf(MessageFormat.format(getBundle("quiz/resources/quiz").getString("FOR_NO_CREDIT"), new Object[]{}), totalIncorrectTrial);

        System.out.println(MessageFormat.format(getBundle("quiz/resources/quiz").getString("HAVE_A_W"), new Object[]{}));
    }

    /**
     * Prints again failed questions showing also their correct answers
     */
    public void showFailed() {
        System.out.println(MessageFormat.format(getBundle("quiz/resources/quiz").getString("FAILED_QUESTIONS"), new Object[]{}));

        int questionsToAsk = getQuestionsToAsk();

        for (int i = 0; i < questionsToAsk; i++) {
            final Question currentQuestion = questions.get(i);
            final double points = currentQuestion.checkQuestion();
            if (points < currentQuestion.getMaxPoints()) {
                //Show user points earned for incorrect answer
                System.out.println(questions.get(i).display());
                System.out.println(MessageFormat.format(getBundle("quiz/resources/quiz").getString("RECEIVED_POINTS"), points));
                System.out.println(MessageFormat.format(getBundle("quiz/resources/quiz").getString("CORRECT_ANSWER"), questions.get(i).getAnswer()));
                System.out.println(MessageFormat.format(getBundle("quiz/resources/quiz").getString("EXPLANATION"), questions.get(i).getExplanation()));
            }
        }
    }

    public void askForSubsetSize() {
        System.out.printf("Currently there are %d questions how many of them you want in the test?\n", questions.size());
        try {
            maxQuestions = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.err.println("problem reading number of questions maximum asserted");
            maxQuestions = -1;
        }

    }

    public void askForCategories() {
        List<String> categories = questions.stream().map(Question::getCategory).distinct().collect(Collectors.toList());
        System.out.printf("Currently there are %d categories of the questions, please write the number of the categories you want separated by spaces in the same line\n", categories.size());
        StringBuilder stringBuilder = new StringBuilder(8);
        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i);
            stringBuilder.append(i + 1).append(" - ").append(category).append("\n");
        }
        System.out.println(stringBuilder.toString());
        try {
            selectedCategories = Arrays.stream(BLANKS.split(scanner.nextLine())).map(idxStr -> categories.get(Integer.parseInt(idxStr) - 1)).filter(Objects::nonNull).distinct().collect(Collectors.toList());
            System.out.println("You selected the categories:" + Arrays.toString(selectedCategories.toArray()));
            //filter questions by category
            questions = questions.stream().filter(question -> selectedCategories.contains(question.getCategory())).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("problem reading the categories");
            e.printStackTrace();
            selectedCategories = Collections.emptyList();
        }
    }

    public void askForDifficulty() {
        System.out.println("Difficulty levels:");
        System.out.println("0 - normal\n 1 - hard\n 2 - easy");
        List<Difficulty> selectedDifficulties = Arrays.stream(BLANKS.split(scanner.nextLine())).distinct()
                .map(String::trim).map(QuestionStrategy::str2difficulty).collect(Collectors.toList());
        System.out.println("You selected the categories:" + Arrays.toString(selectedDifficulties.toArray()));
        //filter questions by category
        questions = questions.stream().filter(question -> selectedDifficulties.contains(question.getDifficulty())).collect(Collectors.toList());
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
