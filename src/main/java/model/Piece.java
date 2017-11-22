package model;

public class Piece {
    private String leftPiece;
    private String rightPiece;

    public Piece(String leftPiece, String rightPiece) {
        this.leftPiece = leftPiece;
        this.rightPiece = rightPiece;
    }


    public String getLeftPiece() {
        return leftPiece;
    }

    public String getRightPiece() {
        return rightPiece;
    }
}
