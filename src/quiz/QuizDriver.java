
package quiz;

import quiz.entities.Quiz;
import quiz.repository.FileQuestionRepository;
import quiz.service.QuestionParser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

/**
 * Administers a quiz consisting of multiple choice, multiple answer, and
 * fill-in-the-blank questions.
 *
 * @author NicolasADavid
 * @author Javatlacati
 */
public final class QuizDriver {

    /**
     * Default constructor.
     */
    private QuizDriver() {
    }

    /**
     * Application entry point
     *
     * @param args program arguments ( actually they are not parsed )
     */
    public static void main(final String... args) {
        final Quiz myQuiz = new Quiz();
        loadQuestions(myQuiz);
        myQuiz.askForCategories();
        myQuiz.askForDifficulty();
        //Display questions to user, receive and check response
        myQuiz.askForSubsetSize();
        myQuiz.displayAndCheckQuestions();
        myQuiz.summarizeResults();
        myQuiz.showFailed();
    }

  private static void loadQuestions(Quiz myQuiz) {
    String[] dir = detectFilenamesInResourcesFolder();
    Arrays.stream(dir)
        .sequential()
        .map(File::new)
        .filter(
            file -> {
              String fileName = file.getName();
              String extension = fileName.substring(fileName.lastIndexOf('.'));
              return ".txt".equalsIgnoreCase(extension);
            })
        .forEach(
            txtFile ->
                FileQuestionRepository.createQuestionsFromFile(
                    "quiz/resources/" + txtFile.getName(),
                    true,
                    questionString ->
                        QuestionParser.parseQuestion(questionString, myQuiz.getQuestions())));
  }

    private static String[] detectFilenamesInResourcesFolder() {
        URL pathUrl = QuizDriver.class.getClassLoader().getResource("quiz/resources/");
        String[] dir = new String[0];
        if ((pathUrl != null) && pathUrl.getProtocol().equals("file")) {
            try {
                dir= new File(pathUrl.toURI()).list();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return dir;
    }

}
