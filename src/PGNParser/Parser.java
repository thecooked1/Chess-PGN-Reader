package PGNParser;

import java.io.*;
import java.util.*;


public class Parser {

    private final Map<String, String> headers = new HashMap<>();
    private final List<String> moves = new ArrayList<>();
    private String result;


    public void loadPGN(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder moveSection = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue; // skip empty lines

            if (line.startsWith("[")) {
                parseHeader(line);
            } else if (Character.isDigit(line.charAt(0))) {
                moveSection.append(line).append(" ");
            } else {
                throw new IOException("Unexpected line in PGN: " + line);
            }
        }
        parseMoves(moveSection.toString().trim());


    }

    public void loadPGNFromString(String pgnContent) throws IOException {
        headers.clear();
        moves.clear();
        result = null;

        String[] lines = pgnContent.split("\n");
        StringBuilder moveSection = new StringBuilder();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("[")) {
                parseHeader(line);
            } else if (Character.isDigit(line.charAt(0))) {
                moveSection.append(line).append(" ");
            } else {
                throw new IOException("Unexpected line in PGN: " + line);
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
            if (token.equals("1-0") || token.equals("0-1") || token.equals("1/2-1/2")) {
                result = token;
            } else if (!token.isEmpty()) {
                moves.add(token);
            }
        }


    }

    public String getResult() {
        return result;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public List<String> getMoves() {
        return moves;
    }
}
