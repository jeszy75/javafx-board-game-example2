package boardgame.model;

public class BoardGameModel {

    public static final int BOARD_SIZE = 5;

    private final Square[][] board;

    private int numberOfCoins;

    public BoardGameModel() {
        board = new Square[BOARD_SIZE][BOARD_SIZE];
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = Square.NONE;
            }
        }
        numberOfCoins = 0;
    }

    public Square getSquare(int i, int j) {
        return board[i][j];
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public boolean isGameOver() {
        return numberOfCoins == BOARD_SIZE * BOARD_SIZE;
    }

    public void makeMove(int row, int col) {
        board[row][col] = switch (board[row][col]) {
            case NONE -> {
                numberOfCoins++;
                yield Square.HEAD;
            }
            case HEAD -> Square.TAIL;
            case TAIL -> {
                numberOfCoins--;
                yield Square.NONE;
            }
        };
    }

    public String toString() {
        var sb = new StringBuilder();
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j].ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var model = new BoardGameModel();
        System.out.println(model);
    }

}
