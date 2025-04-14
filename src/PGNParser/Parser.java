package PGNParser;

import java.io.*;
import java.util.*;


public class Parser {

    private final Map<String, String> headers = new HashMap<>();
    private final List<String> moves = new ArrayList<>();

    public void loadPGN(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder moveSection = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("[")) {
                parseHeader(line);
            } else {
                moveSection.append(line).append(" ");
            }
        }

        parseMoves(moveSection.toString().trim());
    }

    private void parseHeader(String line) {
        // Example: [White "Magnus Carlsen"]
        int firstQuote = line.indexOf("\"");
        int lastQuote = line.lastIndexOf("\"");
        String key = line.substring(1, line.indexOf(" ")).trim();
        String value = line.substring(firstQuote + 1, lastQuote).trim();
        headers.put(key, value);
    }

    private void parseMoves(String movesString) {
        // Remove comments and move numbers
        movesString = movesString.replaceAll("\\{[^}]*\\}", ""); // remove comments
        movesString = movesString.replaceAll("\\d+\\.", "");     // remove move numbers
        movesString = movesString.replaceAll("\\s+", " ").trim(); // cleanup

        String[] tokens = movesString.split(" ");
        for (String token : tokens) {
            if (!token.isEmpty() && !token.contains("1-0") && !token.contains("0-1") && !token.contains("1/2-1/2")) {
                moves.add(token);
            }
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public List<String> getMoves() {
        return moves;
    }
}
