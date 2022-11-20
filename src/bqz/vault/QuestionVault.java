package bqz.vault;

import bqz.utility.Helper;
import bqz.model.Question;
import bqz.model.enums.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The QuestionVault class represents a vault in which all the questions are saved.
 *
 * @author      phxgg
 * @version     1.0
 * @since       13/11/2020
 *
 * This was originally made using the org.json library. Swapped to CSV version after specifications.
 *
 * The project instructions were not specific about loading the questions from a file.
 * We created and used this class after specifications were given in this post:
 * https://elearning.auth.gr/mod/forum/discuss.php?d=102002
 */
public class QuestionVault implements Serializable {
    private ArrayList<Question> questions;

    /**
     * Read through CSV file and generate questions. Then put them in the 'questions' ArrayList.
     */
    public QuestionVault(String csvPath) {
        this.questions = new ArrayList<>();

        try {
            // Load file into buffered reader
            BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));

            // For each line loop
            String line;
            while ((line = csvReader.readLine()) != null) {
                // Split line to String array based on the character ";"
                String[] data = line.split(";");

                String csvTitle = data[0];
                String csvCategory = data[1];
                String csvCorrectAnswer = data[2];
                String csvAnswer1 = data[3];
                String csvAnswer2 = data[4];
                String csvAnswer3 = data[5];
                String csvAnswer4 = data[6];
                String csvPicturePath = data[7];

                Category qCategory;
                int qCorrectAnswer = Integer.parseInt(csvCorrectAnswer) - 1;
                String qPicturePath = csvPicturePath.equals("null") ? null : csvPicturePath;

                // Add possible answers into the answers array list
                ArrayList<String> answers = new ArrayList<>();
                answers.add(csvAnswer1);
                answers.add(csvAnswer2);
                answers.add(csvAnswer3);
                answers.add(csvAnswer4);

                // Convert category string to Category enum
                switch (csvCategory) {
                    case "Movies":
                        qCategory = Category.Movies;
                        break;
                    case "Sports":
                        qCategory = Category.Sports;
                        break;
                    case "Geography":
                        qCategory = Category.Geography;
                        break;
                    default:
                        qCategory = Category.General;
                        break;
                }

                // Insert question to ArrayList
                Question q = new Question(qCategory, csvTitle, answers, qCorrectAnswer, qPicturePath);
                this.questions.add(q);
            }
            csvReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to return the generated questions
     * @return  ArrayList   Questions that were loaded
     */
    public ArrayList<Question> getAllQuestions() {
        return this.questions;
    }

    /**
     * This method is used to return a random question.
     * That question is then removed from the ArrayList to prevent it from showing up again.
     * @return  Question    Random question
     */
    public Question getRandomQuestion() {
        int randomIndex = Helper.getRandomInt(0, this.questions.size());

        Question q = this.questions.get(randomIndex);

        // Re-arrange possible questions in random indexing
        HashMap<Integer, String> answersRandom = new HashMap<>();

        int i_size = q.getAnswers().size();
        int i_correctAnswerIndex = -1;
        String s_correctAnswer = q.getCorrectAnswer();

        for (int k = 0; k < i_size; k++) {
            int i_randomIndex = Helper.getRandomInt(0, q.getAnswers().size());
            answersRandom.put(k, q.getAnswers().get(i_randomIndex));
            q.getAnswers().remove(i_randomIndex);
        }

        for (Map.Entry<Integer, String> e : answersRandom.entrySet()) {
            if (e.getValue() == s_correctAnswer) {
                i_correctAnswerIndex = e.getKey();
            }
        }

        q.setAnswers(answersRandom);
        q.setCorrectAnswer(i_correctAnswerIndex);

        // Remove question from vault, so there's no possibility that this is displayed again.
        this.questions.remove(randomIndex);
        return q;
    }
}
