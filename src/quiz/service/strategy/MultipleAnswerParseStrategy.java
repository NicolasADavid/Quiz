package quiz.service.strategy;

import quiz.entities.MultipleAnswerQuestion;
import quiz.entities.Question;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.ResourceBundle.getBundle;

public class MultipleAnswerParseStrategy implements QuestionStrategy {
    @Override
    public void parse(String[] questarray, List<Question> questions) {
        String vettedness = "v".equals(questarray[1]) ? Question.VETTED : Question.TRIAL;
        String explanation = questarray[2];
        String category = questarray[3];
        String difficulty = questarray[4];
        String questionText = QuestionStrategy.formateaPregunta(questarray[5]);
        Map<String, Boolean> choices =
                parseChoicesMap(Arrays.copyOfRange(questarray, 6, questarray.length));
        addMultipleAnswerQuestion(
                questions, vettedness, explanation, questionText, category, difficulty, choices);
    }

    /**
     * Parses the choices and relates them with their values.
     *
     * @param questionMapArr list with options and then values of each question
     * @return options maped with their values
     */
    public static Map<String, Boolean> parseChoicesMap(final String... questionMapArr) {
        final String[] questarray = Arrays.copyOfRange(questionMapArr, 0, questionMapArr.length / 2);
        final String[] strAnswerValidities =
                Arrays.copyOfRange(questionMapArr, questionMapArr.length / 2, questionMapArr.length);
        final Boolean[] answerValidities = new Boolean[strAnswerValidities.length];
        Arrays.stream(strAnswerValidities)
                .map(
                        (booleanAnswer) ->
                                "true".equalsIgnoreCase(booleanAnswer) ? Boolean.TRUE : Boolean.FALSE)
                .collect(Collectors.toList())
                .toArray(answerValidities);
        return createAnswerChoicesMap(questarray, answerValidities);
    }

    /** */
    private static Map<String, Boolean> createAnswerChoicesMap(
            final String[] answerTexts, final Boolean... answerValidities) {
        final int numsOfAnswers = answerTexts.length;
        if (numsOfAnswers != answerValidities.length) {
            throw new RuntimeException(
                    MessageFormat.format(
                            getBundle("quiz/resources/quiz").getString("ANSWER_TEXTS_ERR"), new Object[] {}));
        }
        final HashMap<String, Boolean> hashMap = new HashMap<>(2);
        for (int answIdx = 0; answIdx < answerTexts.length; answIdx++) {
            hashMap.put(answerTexts[answIdx], answerValidities[answIdx]);
        }
        return hashMap;
    }

    /** */
    private static void addMultipleAnswerQuestion(
            final List<Question> questions,
            final String vettedness,
            final String explanation,
            final String questionText,
            final String category,
            final String difficulty,
            final Map<String, Boolean> answerChoicesMap) {
        final MultipleAnswerQuestion choiceQuestion = new MultipleAnswerQuestion(vettedness);
        choiceQuestion.setExplanation(explanation);
        choiceQuestion.setText(questionText);
        choiceQuestion.setCategory(category);
        choiceQuestion.setDifficulty(QuestionStrategy.str2difficulty(difficulty));
        answerChoicesMap.forEach(choiceQuestion::setChoice);
        questions.add(choiceQuestion);
    }
}
