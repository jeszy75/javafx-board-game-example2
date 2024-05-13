package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.Square;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;

public class BoardGameController {

    @FXML
    private GridPane board;

    @FXML
    private TextField numberOfCoinsField;

    private final BoardGameModel model = new BoardGameModel();

    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        updateNumberOfCoins();
    }

    private StackPane createSquare(int row, int col) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);
        var color = getColor(model.getSquare(row, col));
        piece.setFill(color);
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        Logger.debug("Click on square ({},{})", row, col);
        model.makeMove(row, col);
        updateUI(row,col);
        if (model.isGameOver()) {
            showGameOverAlertAndExit();
        }
    }

    private void showGameOverAlertAndExit() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game Over");
        alert.setContentText("Game board is full");
        alert.showAndWait();
        Platform.exit();
    }

    private void updateUI(int row, int col) {
        updateSquare(row, col);
        updateNumberOfCoins();
    }

    private void updateSquare(int row, int col) {
        var piece = (Circle) ((StackPane) getNodeFromGrid(row, col)).getChildren().get(0);
        var color = getColor(model.getSquare(row, col));
        piece.setFill(color);
    }

    private Color getColor(Square square) {
        return switch (square) {
            case NONE -> Color.TRANSPARENT;
            case HEAD -> Color.RED;
            case TAIL -> Color.BLUE;
        };
    }

    private void updateNumberOfCoins() {
        numberOfCoinsField.setText(Integer.toString(model.getNumberOfCoins()));
    }

    public Node getNodeFromGrid(int row, int col) {
        for (var square : board.getChildren()) {
            if (GridPane.getRowIndex(square) == row && GridPane.getColumnIndex(square) == col) {
                return square;
            }
        }
        throw new AssertionError();
    }

}
