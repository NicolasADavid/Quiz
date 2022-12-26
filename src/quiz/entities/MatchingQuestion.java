package quiz.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MatchingQuestion extends Question {

  private static final Pattern BLANKS = Pattern.compile("\\s+");

  List<String> terms;
  List<String> definitions;
  Map<String, String> termsDefinitions;

  /**
   * Constructs a question with empty question and answer
   *
   * @param vettedness
   */
  public MatchingQuestion(String vettedness) {
    super(vettedness);
    terms = new ArrayList<>(2);
    definitions = new ArrayList<>(2);
  }

  @Override
  public void setAnswer(String answer) {
    this.answer = answer;
    String[] answersArray = getAnswersArray(answer);
    for (int i = 0; i < answersArray.length; i += 2) {
      terms.add(answersArray[i]);
      definitions.add(answersArray[i + 1]);
      termsDefinitions.put(answersArray[i], answersArray[i + 1]);// TODO fix
    }
  }

  @Override
  public String getAnswer() {
    return answer;
  }

  @Override
  public double checkQuestionProvidingAnswer(String answer) {
    Map<String, String> answerTermsDefinitions = getAnswerTermsDefinitionsMap(answer);
    long count =
        termsDefinitions.entrySet().stream()
            .filter(e -> e.getValue().equals(answerTermsDefinitions.get(e.getKey())))
            .count();
    return (termsDefinitions.size() + 1.0) / (count + 1.0);
  }

  private static Map<String, String> getAnswerTermsDefinitionsMap(String answer) {
    String[] answersArray = getAnswersArray(answer);
    Map<String, String> answerTermsDefinitions = new HashMap<>();
    for (int i = 0; i < answersArray.length; i += 2) {
      answerTermsDefinitions.put(answersArray[i], answersArray[i + 1]);
    }
    return answerTermsDefinitions;
  }

  private static String[] getAnswersArray(String answer) {
    return BLANKS.split(answer);
  }

  @Override
  public double getMaxPoints() {
    return 1.0d;
  }

  @Override
  public String display() {

    StringBuilder display = new StringBuilder(text);
    display.append("\n").append("First list:").append("\n");

    displayListElements(terms, display);

    display.append("Second list:").append("\n");

    displayListElements(definitions, display);

    return display.toString();
  }

  private void displayListElements(List<String> list, StringBuilder display) {
    for (int i = 0; i < list.size(); i++) {
      final int choiceNumber = i + 1;
      String term = list.get(i);
      display.append(choiceNumber).append(": ").append(term).append("\n");
    }
  }
}
