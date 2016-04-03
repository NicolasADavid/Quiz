///////////////////////////////////////////////////////////////////////////////                  
// Title:            Quiz
// Files:            
// Semester:         COP3337 Fall 2015
//
// Author:           3587814
// Lecturer's Name:  Prof. Maria Charters
//
// Description of Program’s Functionality: 
//////////////////////////// 80 columns wide/////////////////////////////////
package quiz;

interface Gradable {

    public boolean gradeQuestion();
}

abstract public class Question {

    public static final String VETTED = "vetted";
    public static final String TRIAL = "trial";

    protected String text;
    protected String answer;
    protected String vettedOrTrial;

    /**
     * Constructs a question with empty question and answer
     */
    public Question(String vettedness) {
//        text = "";//only necessary for Java 5 where default value for String variable at class scope was null instead of ""
//        answer = "";
        vettedOrTrial = vettedness;
    }

    /**
     * Sets the question text.
     *
     * @param questionText the text of this question
     */
    public void setText(String questionText) {
        text = questionText;
    }

    /**
     * Sets the correct answer(s)
     */
    public abstract void setAnswer(String answer);

//    /**
//     * Checks the answer(s)
//     * @return true for correct, false otherwise
//     */
//    public abstract double checkAnswer(String answer);
    /**
     * Returns the question text
     */
    public String display() {
        return text;
    }

    public boolean gradeQuestion() {
        return vettedOrTrial.equals(VETTED);
    }

    public abstract double checkQuestion(String answer);

}
