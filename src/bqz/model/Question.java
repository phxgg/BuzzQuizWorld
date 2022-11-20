package bqz.model;

import bqz.model.enums.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The Question class represents a question.
 *
 * @author      phxgg, NikosZoros3533
 * @version     1.0
 * @since       12/11/2020
 */
public class Question implements Serializable {

    /**
     * Question category.
     */
    private final Category category;

    /**
     * Actual question title.
     */
    private final String question;

    /**
     * Possible answers of question.
     */
    private final ArrayList<String> answers;

    /**
     * Correct answer index.
     */
    private int correctAnswer;

    /**
     * Picture path.
     */
    private final String picture;

    public Question(Category category, String question, ArrayList<String> answers, int correctAnswer, String picture) {
        this.category = category;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.picture = picture;

        this.answers = new ArrayList<>(); // this.answers = answers;
        this.answers.addAll(answers);
    }

    /**
     * This method is used to return the question category.
     * @return  Category    The question category
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * This method is used to return the actual question string.
     * @return  String  The actual question string.
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * This method is used to return the possible answers of the question.
     * @return ArrayList    Possible answers of question
     */
    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    /**
     * This method is used to re-add the answers in our ArrayList after we have randomly re-arranged them.
     * @param answers   Answers HashMap list
     */
    public void setAnswers(HashMap<Integer, String> answers) {
        for (Map.Entry<Integer, String> e : answers.entrySet()) {
            this.answers.add(e.getValue());
        }
    }

    /**
     * This method is used to return the question answers' correct answer index.
     * It is used to get the correct answer before we re-arrange
     * the possible answers in random indexing to display them.
     * @return  int Correct answer index
     */
    public int getCorrectAnswerIndex() {
        return this.correctAnswer;
    }

    /**
     * This method is used to return the question's correct answer title.
     * It is used to get the correct answer string before we re-arrange
     * the possible answers in random indexing to display them.
     * @return  String  Correct answer title
     */
    public String getCorrectAnswer() {
        return this.answers.get(this.correctAnswer);
    }

    /**
     * This method is used to set the new correct answer index, after we have re-arranged the possible answers
     * in random indexing.
     * @param correctAnswer New correct answer index
     */
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * This method is used to return whether this question has a picture to display.
     * @return  boolean Whether a picture to display exists
     */
    public boolean hasPicture() {
        if (this.picture != null)
            return true;
        return false;
    }

    /**
     * This method is used to return the picture path.
     * @return  String  The picture path
     */
    public String getPicture() {
        return this.picture;
    }

}
