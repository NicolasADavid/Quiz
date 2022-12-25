package quiz.repository;

import quiz.entities.Quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileQuestionRepository implements QuestionRepository {

    /**
     * Reads a file and call the method that processes it to fill the quiz.
     */
    public static void createQuestionsFromFile(String path, boolean isInJar, Consumer<String> parserFunction) {
        try (BufferedReader reader = createReader(path, isInJar)) {
            if (reader == null) {
                return;
            }
            parseQuestions(reader, parserFunction);
        } catch (IOException ex) {
            Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static BufferedReader createReader(String path, boolean isInJar) {
        System.out.println("Loading file: "+path);
        BufferedReader reader;
        if (isInJar) {
            reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path)));
        } else {
            try {
                reader = new BufferedReader(new FileReader(new File(path)));
            } catch (FileNotFoundException e) {
                Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, "The specified file does not exist:" + path, e);
                return null;
            }
        }
        return reader;
    }

    private static void parseQuestions(BufferedReader reader, Consumer<String> parserFunction) throws IOException {
        String thisLine;
        while ((thisLine = reader.readLine()) != null) {
            parserFunction.accept(thisLine);
        }
    }
}
