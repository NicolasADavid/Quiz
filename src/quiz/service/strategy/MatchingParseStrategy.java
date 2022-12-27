package quiz.service.strategy;

import quiz.entities.MatchingQuestion;
import quiz.entities.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class MatchingParseStrategy implements QuestionStrategy {
  @Override
  public void parse(String[] questarray, List<Question> questions) {
    String vettedness = "v".equals(questarray[1]) ? Question.VETTED : Question.TRIAL;
    String explanation = questarray[2];
    String category = questarray[3];
    String difficulty = questarray[4];
    String questionText = QuestionStrategy.formateaPregunta(questarray[5]);
    String[] strings = Arrays.copyOfRange(questarray, 6, questarray.length);
    addMatchingQuestion(
        questions,
        vettedness,
        explanation,
        questionText,
        category,
        difficulty,
        strings,
        this::parseMap);
  }

  private void parseMap(String[] answerSection, MatchingQuestion question) {
    int realsAnswers = answerSection.length / 2;
    List<String> terms = new ArrayList<>(realsAnswers);
    List<String> definitions = new ArrayList<>(realsAnswers);
    int i = 0;
    while (!answerSection[i].startsWith("***")) {
      terms.add(answerSection[i]);
      i++;
    }
    question.setTerms(terms);
    while (i < answerSection.length) {
      definitions.add(answerSection[i]);
      i++;
    }
    question.setDefinitions(definitions);
  }

  private static void addMatchingQuestion(
      final List<Question> questions,
      String vettedness,
      final String explanation,
      final String questionText,
      final String category,
      final String difficulty,
      String[] strings,
      BiConsumer<String[], MatchingQuestion> mapParsingFunction) {
    MatchingQuestion question = new MatchingQuestion(vettedness);
    question.setExplanation(explanation);
    question.setText(questionText);
    question.setCategory(category);
    question.setDifficulty(QuestionStrategy.str2difficulty(difficulty));
    mapParsingFunction.accept(strings, question);
    questions.add(question);
  }
}
