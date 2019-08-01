package trivia;

import java.util.ArrayList;
import java.util.HashMap;

public class Questions {
    private HashMap<String, ArrayList<Question>> questionCategoryMap = new HashMap<String, ArrayList<Question>>();

    public Questions() {}

    public String getQuestionByCategory(String category) {
        Question question = questionCategoryMap.get(category).remove(0);
        return question.text();
    }

    public void add(String category, String questionText) {
        if (!questionCategoryMap.containsKey(category)) {
            questionCategoryMap.put(category, new ArrayList<Question>());
        }

        questionCategoryMap.get(category).add(new Question(questionText));
    }
}
