package quiz.service;

import quiz.service.strategy.FillBlankQuestionStrategy;
import quiz.service.strategy.MultipleAnswerQuestionStrategy;
import quiz.service.strategy.MultipleChoiceQuestionStrategy;
import quiz.service.strategy.QuestionStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StrategyChooser {
  private StrategyChooser() {
    // do not instantiate this utility class
  }

  // java 9+
  //    private static Map<String, QuestionStrategy> strategies = Map.of(
  //            "MC", new MultipleChoiceQuestionStrategy(),
  //            "FB", new FillBlankQuestionStrategy(),
  //            "MA", new MultipleAnswerQuestionStrategy()
  //    );
  // guava
  // private static Map<String, QuestionStrategy> strategies = ImmutableMap.of(
  //        "MC", new MultipleChoiceQuestionStrategy(),
  //        "FB", new FillBlankQuestionStrategy(),
  //        "MA", new MultipleAnswerQuestionStrategy()
  // );
  private static final Map<String, QuestionStrategy> strategies =
      Collections.unmodifiableMap(
          new HashMap<String, QuestionStrategy>() {
            {
              put("MC", new MultipleChoiceQuestionStrategy());
              put("FB", new FillBlankQuestionStrategy());
              put("MA", new MultipleAnswerQuestionStrategy());
            }
          });

  public static QuestionStrategy getStrategy(final String questionType) {
    return strategies.get(questionType);
  }
}
