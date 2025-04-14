package PGNParser;

public class Interpreter {
    public Move parseMove(String token) {
        Move move = new Move(token);

        String t = token;

        if (t.contains("+")) {
            move.setCheck(true);
            t = t.replace("+", "");
        } else if (t.contains("#")) {
            move.setCheckmate(true);
            t = t.replace("#", "");
        }

        if (t.contains("=")) {
            int idx = t.indexOf("=");
            move.setPromotion(t.substring(idx + 1));
            t = t.substring(0, idx);
        }

        if (t.equals("O-O") || t.equals("O-O-O")) {
            move.setPiece("K"); // King is moving
            move.setTargetFile(t.equals("O-O") ? 'g' : 'c');
            move.setTargetRank(-1); // Set dynamically based on color
            return move;
        }

        move.setCapture(t.contains("x"));
        t = t.replace("x", "");

        // If it starts with a piece char, pull it out
        if ("KQRBN".indexOf(t.charAt(0)) != -1) {
            move.setPiece(String.valueOf(t.charAt(0)));
            t = t.substring(1);
        } else {
            move.setPiece("P"); // Default to pawn
        }

        // Disambiguation (e.g., Nbd2 or R1e2)
        if (t.length() == 3) {
            move.setDisambiguation(String.valueOf(t.charAt(0)));
            move.setTargetFile(t.charAt(1));
            move.setTargetRank(Character.getNumericValue(t.charAt(2)));
        } else {
            move.setTargetFile(t.charAt(0));
            move.setTargetRank(Character.getNumericValue(t.charAt(1)));
        }

        return move;
    }
}
