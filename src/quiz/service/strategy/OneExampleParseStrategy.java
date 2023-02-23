package quiz.service.strategy;

import quiz.entities.OneExampleQuestion;
import quiz.entities.Question;

import java.util.Arrays;
import java.util.List;

public class OneExampleParseStrategy extends MultipleChoiceParseStrategy {
  @Override
  public void parse(String[] questarray, List<Question> questions) {
    String vettedness = "v".equals(questarray[1]) ? Question.VETTED : Question.TRIAL;
    String explanation = questarray[2];
    String category = questarray[3];
    String difficulty = questarray[4];
    String questionText = QuestionStrategy.formateaPregunta(questarray[5]);
    String[] answersTexts = Arrays.copyOfRange(questarray, 6, questarray.length);
    addOneExampleQuestion(
        questions, vettedness, explanation, questionText, category, difficulty, answersTexts);
  }

  private static void addOneExampleQuestion(
      final List<Question> questions,
      final String vettedness,
      final String explanation,
      final String questionText,
      final String category,
      final String difficulty,
      final String... answersTexts) {
    OneExampleQuestion oneExampleQuestion = new OneExampleQuestion(vettedness);
    oneExampleQuestion.setExplanation(explanation);
    oneExampleQuestion.setText(questionText);
    oneExampleQuestion.setCategory(category);
    oneExampleQuestion.setDifficulty(QuestionStrategy.str2difficulty(difficulty));
    for (final String answerText : answersTexts) {
      oneExampleQuestion.setChoice(answerText);
    }
    questions.add(oneExampleQuestion);
  }
}
