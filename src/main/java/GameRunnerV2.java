import trivia.GameV2;

import java.util.Random;

public class GameRunnerV2 {

    public static void main(String[] args) {
        GameV2 game = new GameV2();
        game.addPlayer("Chet");
        game.addPlayer("Pat");
        game.addPlayer("Sue");

        Random rand = new Random();

        while (!game.isOver()) {
            game.nextTurn(rand.nextInt(5) + 1);
            game.answer(rand.nextInt(9));
        }
    }
}
