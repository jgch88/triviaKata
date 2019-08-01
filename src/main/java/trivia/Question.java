package trivia;

public class Question {

    private String _question;

    public Question(String question) {
        _question = question;
    }

    public String text() {
        return _question;
    }
}
