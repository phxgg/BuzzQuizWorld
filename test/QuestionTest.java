import bqz.model.Question;
import bqz.model.enums.Category;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class QuestionTest {
    @Test
    public void correctAnswerTest() {
        ArrayList<String> answers = new ArrayList<>();
        answers.add("correct answer");
        answers.add("lol");
        answers.add("hehe");
        answers.add("another wrong answer");
        Question q = new Question(Category.General, "Is this a question?", answers, 0, null);
        Assert.assertEquals("correct answer", q.getCorrectAnswer());
    }
}
