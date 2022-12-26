package quiz.service.strategy;

import quiz.entities.FillBlankQuestion;
import quiz.entities.Question;

import java.util.Arrays;
import java.util.List;

public class FillBlankParseStrategy implements QuestionStrategy {
    @Override
    public void parse(String[] questarray, List<Question> questions) {
        String vettedness = "v".equals(questarray[1]) ? Question.VETTED : Question.TRIAL;
        String explanation = questarray[2];
        String category = questarray[3];
        String difficulty = questarray[4];
        String questionText = QuestionStrategy.formateaPregunta(questarray[5]);
        String[] blanks = Arrays.copyOfRange(questarray, 6, questarray.length);
        addFillBlankQuestion(
                questions, vettedness, explanation, questionText, category, difficulty, blanks);
    }

    /** */
    private static void addFillBlankQuestion(
            final List<Question> questions,
            final String vettedness,
            final String explanation,
            final String questionText,
            final String category,
            final String difficulty,
            final String... blanks) {
        final FillBlankQuestion fillBlankQuestion = new FillBlankQuestion(vettedness);
        fillBlankQuestion.setExplanation(explanation);
        fillBlankQuestion.setText(questionText);
        fillBlankQuestion.setCategory(category);
        fillBlankQuestion.setDifficulty(QuestionStrategy.str2difficulty(difficulty));
        for (final String blank : blanks) {
            fillBlankQuestion.setAnswer(blank);
        }
        questions.add(fillBlankQuestion);
    }
}
