package quiz.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
  }

  @Override
  public String getAnswer() {
    return answer;
  }

  @Override
  public double checkQuestionProvidingAnswer(String answer) {
    Map<String, String> answerTermsDefinitions = getAnswerTermsDefinitionsMap(answer);
    //    long count =
    //        termsDefinitions.entrySet().stream()
    //            .filter(e -> e.getValue().equals(answerTermsDefinitions.get(e.getKey())))
    //            .count();
    //    return (termsDefinitions.size() + 1.0) / (count + 1.0);
    return answer.equals(this.answer) ? 1 : 0;
  }

  /**
   * 1 2 2 1
   *
   * @param answer
   * @return
   */
  private Map<String, String> getAnswerTermsDefinitionsMap(String answer) {
    String[] answersArray = getAnswersArray(answer);
    Map<String, String> answerTermsDefinitions = new HashMap<>();
    for (int i = 0; i < answersArray.length; i += 2) {
      int termIndex = Integer.parseInt(answersArray[i]);
      int definitionIndex = Integer.parseInt(answersArray[i + 1]);
      answerTermsDefinitions.put(terms.get(termIndex - 1), definitions.get(definitionIndex - 1));
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
    List<Integer> definitionsOrder =
        IntStream.range(0, definitions.size()).boxed().collect(Collectors.toList());
    Collections.shuffle(definitionsOrder);

    StringBuilder expectedAnswer = new StringBuilder();
    for (int i = 0; i < definitionsOrder.size(); i++) {
      final int choiceNumber = i + 1; // TODO mostrar letras en vez de numeros
      display.append(choiceNumber).append(": ").append(definitions.get(i)).append("\n");
      expectedAnswer.append(i).append(" ").append(choiceNumber);
      if (i != (definitionsOrder.size() - 2)) {
        expectedAnswer.append(" ");
      }
    }

    answer = expectedAnswer.toString();

    //    displayListElements(definitions, display);

    return display.toString();
  }

  private void displayListElements(List<String> list, StringBuilder display) {
    for (int i = 0; i < list.size(); i++) {
      final int choiceNumber = i + 1;
      String term = list.get(i);
      display.append(choiceNumber).append(": ").append(term).append("\n");
    }
  }

  public void setTerms(List<String> terms) {
    this.terms = terms;
  }

  public void setDefinitions(List<String> definitions) {
    this.definitions = definitions;
  }
}
