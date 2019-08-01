package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6]; // why is place an int? // max players is 6?
    // place determines the next category?
    int[] purses  = new int[6]; // contains player "Gold". Need to win the game
    boolean[] inPenaltyBox  = new boolean[6]; // no real impact on game, just a player status


    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public  Game(){
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index){
        return "Rock Question " + index;
    }

    // not even called, but supposed to have 2 or more players?
    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true; // redundant
    }

    public int howManyPlayers() {
        return players.size();
    }

    // A turn involves:
    // getting a player's roll
    // handling penalty box situation
    // move player's place/location
    // asking a question
    // handling answering (correct/wrong)

    // set next player's turn
    // check if current player has won


    // Penalty abstraction is not clear
    // player can be placed in penalty box but there's nothing in this code that removes player from penalty box
    // also, is the only reason to have a penalty box to prevent someone "moving out" from the penalty box
    // a chance to win that round? can't "get out of penalty box" if roll is even.

    public void roll(int roll) { // more like "playTurn". roll gets a random 1-5
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        // can only get out of penalty box if roll is odd

        // can use guard clause here for just not getting out penalty box, e.g. if roll is even
        // get player out of penalty box
        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 == 0) {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            } else {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            }
        }

        // set player place -> a way to use roll to affect the next category
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);

        // get new category's question
        System.out.println("The category is " + currentCategory());
        askQuestion();

    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]){
            if (isGettingOutOfPenaltyBox) { // does he not get out of penalty box ever?
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                System.out.println(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true; // true will actually continue the loop here
            }

        } else {

            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer(){
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++; // use % to make the looping back to 0 index intent more explicit
        if (currentPlayer == players.size()) currentPlayer = 0; // next turn
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6); // why returning a NOT just because the outer loop needs a false to exist the
        // while loop!!
    }
}