package PGNParser;

public class Move {
    private String piece;
    private char targetFile;
    private int targetRank;
    private boolean isCapture;
    private boolean isCheck;
    private boolean isCheckmate;
    private String disambiguation;
    private String promotion;
    private String raw;

    public Move(String raw) {
        this.raw = raw;
    }

    // Getters
    public String getPiece() { return piece; }
    public char getTargetFile() { return targetFile; }
    public int getTargetRank() { return targetRank; }
    public boolean isCapture() { return isCapture; }
    public boolean isCheck() { return isCheck; }
    public boolean isCheckmate() { return isCheckmate; }
    public String getDisambiguation() { return disambiguation; }
    public String getPromotion() { return promotion; }
    public String getRaw() { return raw; }

    // Setters
    public void setPiece(String piece) { this.piece = piece; }
    public void setTargetFile(char targetFile) { this.targetFile = targetFile; }
    public void setTargetRank(int targetRank) { this.targetRank = targetRank; }
    public void setCapture(boolean capture) { isCapture = capture; }
    public void setCheck(boolean check) { isCheck = check; }
    public void setCheckmate(boolean checkmate) { isCheckmate = checkmate; }
    public void setDisambiguation(String disambiguation) { this.disambiguation = disambiguation; }
    public void setPromotion(String promotion) { this.promotion = promotion; }

    @Override
    public String toString() {
        return raw;
    }
}
