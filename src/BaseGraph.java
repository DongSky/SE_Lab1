import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseGraph {
	
    int[] weight;
    int edgeNum;
    int[] distance;
    boolean[] visited;
    boolean[] walked;
    public ArrayList<String> sequence;
    public BaseGraph() {   
    }
    public void fileRead(String filename) {
        sequence = new ArrayList<String>();
        File file = new File(filename);
        try {
          InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
          BufferedReader br = new BufferedReader(reader);
          String line = "";
          try {
            line = br.readLine();
            while (line != null) {
              String piece = "";
              for (int i = 0; i < line.length(); i++) {
                if ((line.charAt(i) >= 'A' && line.charAt(i) <= 'Z')
                    || (line.charAt(i) >= 'a' && line.charAt(i) <= 'z')) {
                  piece += line.charAt(i);
                } else {
                  if (!piece.equals("")) {
                    sequence.add(piece.toLowerCase());
                  }
                  piece = "";
                }
              }
              if (!piece.equals("")) {
                sequence.add(piece.toLowerCase());
              }
              line = br.readLine();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
    }
    /** clean walk edge. */
    public void cleanWalkedEdge() {
      walked = new boolean[edgeNum];
      for (int i = 0; i < edgeNum; i++) {
        walked[i] = false;
      }
    }

    boolean isLetter(char x) {
      return ('a' <= x && x <= 'z') || ('A' <= x && x <= 'Z');
    }
}
