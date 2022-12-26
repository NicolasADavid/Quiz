package quiz.service.strategy;

import quiz.entities.MultipleChoiceQuestion;
import quiz.entities.Question;

import java.util.Arrays;
import java.util.List;

public class MultipleChoiceQuestionStrategy implements QuestionStrategy {
    @Override
    public void parse(String[] questarray, List<Question> questions) {
        String vettedness = "v".equals(questarray[1]) ? Question.VETTED : Question.TRIAL;
        String explanation = questarray[2];
        String category = questarray[3];
        String difficulty = questarray[4];
        String questionText = QuestionStrategy.formateaPregunta(questarray[5]);
        int correctAnswerIdx = Integer.parseInt(questarray[6]);
        String[] answersTexts = Arrays.copyOfRange(questarray, 7, questarray.length);
        addMultipleChoiceQuestion(
                questions,
                vettedness,
                explanation,
                questionText,
                correctAnswerIdx,
                category,
                difficulty,
                answersTexts);
    }

    /** Adds a new multiple choice question to the specified question list. */
    private static void addMultipleChoiceQuestion(
            final List<Question> questions,
            final String vettedness,
            final String explanation,
            final String questionText,
            final int correctAnswerIdx,
            final String category,
            final String difficulty,
            final String... answersTexts) {
        final MultipleChoiceQuestion choiceQuestion = new MultipleChoiceQuestion(vettedness);
        choiceQuestion.setExplanation(explanation);
        choiceQuestion.setText(questionText);
        choiceQuestion.setCategory(category);
        choiceQuestion.setDifficulty(QuestionStrategy.str2difficulty(difficulty));
        for (int i = 0; i < answersTexts.length; i++) {
            final String answerText = answersTexts[i];
            choiceQuestion.setChoice(answerText, i == correctAnswerIdx);
        }
        questions.add(choiceQuestion);
    }
}
