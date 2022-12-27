package quiz.service.strategy;

import quiz.entities.Difficulty;
import quiz.entities.Question;

import java.util.List;
import java.util.regex.Pattern;

public interface QuestionStrategy {
  Pattern LINE_BREAK = Pattern.compile("\\\\n");

  void parse(final String[] questarray, List<Question> questions);

  static String formateaPregunta(final String strPregunta) {
    return LINE_BREAK.matcher(strPregunta).replaceAll("\n");
  }

  static Difficulty str2difficulty(String idxStr) {
    switch (idxStr) {
      case "2":
        return Difficulty.EASY;
      case "1":
        return Difficulty.HARD;
      case "0":
      default:
        return Difficulty.NORMAL;
    }
  }
}
