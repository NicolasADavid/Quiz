package quiz.entities;

import quiz.entities.MultipleChoiceQuestion;

/**
 * Give an example of natural number:
 * you answered 2
 * other possibly correct choices: 1,3,4,5,6,7,8,9,0
 */
public class OneExampleQuestion extends MultipleChoiceQuestion {
  public OneExampleQuestion(String vettedness) {
    super(vettedness);
  }

  public void setChoice(String choice) {
    super.setChoice(choice, true);
  }

  @Override
  public double checkQuestionProvidingAnswer(final String answer) {
    this.userAnswer = answer;
    return checkQuestion();
  }

  @Override
  public double checkQuestion() {
    if (userAnswer != null) {
      return choices.stream().anyMatch(correctChoice -> correctChoice.equals(userAnswer))
          ? 1.0
          : 0.0;
    }
    // TODO tal vez aquí llamar a checkQuestionProvidingAnswer para llenar
    return 0.0;
  }
}
