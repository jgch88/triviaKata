package trivia;

import java.util.ArrayList;
import java.util.Arrays;

public class GameV2 {

    ArrayList<Player> players = new ArrayList<Player>();
    Questions questions = new Questions();
    Player currentPlayer;
    int currentRoll;
    String currentCategory;
    boolean gameOver = false;

    public GameV2() {
        ArrayList<String> categories = new ArrayList<String>(Arrays.asList("Pop", "Science", "Sports", "Rock"));
        for (int i = 0; i < 50; i++) {
            for (String category : categories) {
                questions.add(category, category + " " + i);
            }
        }
    }

    private int changeToNextPlayer() {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        currentPlayerIndex += 1;
        currentPlayerIndex = currentPlayerIndex % players.size();

        return currentPlayerIndex;
    };

    public void addPlayer(String playerName) {
        Player newPlayer = new Player(playerName);
        players.add(newPlayer);

        if (currentPlayer == null) {
            currentPlayer = newPlayer;
        }

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    };

    public boolean isOver() {
        return gameOver == true;
    }

    public void nextTurn(int roll) {
        currentRoll = roll;
        displayTurnInformation();
        assessPenalty();
        movePlayer();
        suggestCategory();
        printCategory();
        askQuestion();
    }


    private void displayTurnInformation() {
        System.out.println(currentPlayer.name() + " is the current player");
        System.out.println("They have rolled a " + currentRoll);
    }

    private void assessPenalty() {
        if (!currentPlayer.isPenalized()) {
            return;
        }

        if (isPenaltyRoll()) {
            System.out.println(currentPlayer.name() + " is not getting out of the penalty box");
            currentPlayer.addPenalty();
        } else {
            System.out.println(currentPlayer.name() + " is getting out of the penalty box");
            currentPlayer.removePenalty();
        }
    }

    private boolean isPenaltyRoll() {
        return currentRoll % 2 == 0;
    }

    private void movePlayer() {
        currentPlayer.changePlace(currentRoll);
    }

    private void printCategory() {
        System.out.println("The category is " + currentCategory);
    }

    // This feels like a place to configure when questions are populated, belonging with constructor
    private void suggestCategory() {
        int _place = currentPlayer.place();

        if (_place == 0 || _place == 4 || _place == 8) {
            currentCategory = "Pop";
        } else if (_place == 1 || _place == 5 || _place == 9) {
            currentCategory = "Science";
        } else if (_place == 2 || _place == 6 || _place == 10) {
            currentCategory = "Sports";
        } else {
            currentCategory = "Rock";
        }
    }

    private void askQuestion() {
        System.out.println(questions.getQuestionByCategory(currentCategory));
    }


    public void answer(int roll) {
        if (roll == 7) {
            answerWrongly();
        } else {
            answerCorrectly();
            checkForWinner();
        }
        changeToNextPlayer();
    }

    private void answerWrongly() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer.name() + " was sent to the penalty box");
        currentPlayer.addPenalty();
    }

    private void answerCorrectly() {
        System.out.println("Answer was correct!!!!");
        currentPlayer.addGold();
    }

    private boolean isGettingOutOfPenaltyBox() {
        return currentRoll % 2 != 0;
    }

    private void checkForWinner() {
        if (currentPlayer.hitGoldTarget() && isGettingOutOfPenaltyBox()) {
            gameOver = true;
        }
    }
}