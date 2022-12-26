package quiz.service;

import quiz.entities.Question;
import quiz.service.strategy.QuestionStrategy;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.ResourceBundle.getBundle;
import static quiz.service.StrategyChooser.getStrategy;

public class QuestionParser {
  public static final String FIELD_DIVIDER = "@@";
  private static final Pattern SEPARATE_BY_DIVIDER_PATTERN = Pattern.compile(FIELD_DIVIDER);

  /**
   * @param questionString the question fields split by @@
   */
  public static void parseQuestion(final String questionString, List<Question> questions) {
    final String[] questarray = SEPARATE_BY_DIVIDER_PATTERN.split(questionString);
    String tipoPregunta = questarray[0];
    try {
      QuestionStrategy questionStrategy =
          Optional.ofNullable(getStrategy(tipoPregunta)).orElseThrow(NoSuchElementException::new);
      questionStrategy.parse(questarray, questions);
    } catch (NoSuchElementException nse) {
      System.err.println(
          MessageFormat.format(
              getBundle("quiz/resources/quiz").getString("QUESTION_TYPE_ERR"), new Object[] {}));
    }
  }
}
