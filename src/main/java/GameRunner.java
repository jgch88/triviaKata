import java.util.Random;

import trivia.Game;


public class GameRunner {

    private static boolean notAWinner;

    public static void main(String[] args) {
        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random();

        // game engine should... be encapsulated too
        do {

            aGame.roll(rand.nextInt(5) + 1); // nextInt is 0 inclusive bound exclusive, i.e. returns 1-5
            // roll will also print out the question

            if (rand.nextInt(9) == 7) { // roll 0-8, if magic 7
                // makes player answer wrongly, returns true
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }

        } while (notAWinner); // should be true most of the time?

    }
}