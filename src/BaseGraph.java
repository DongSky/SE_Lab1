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
    
    class Edge {
        int weightIndex;
        boolean walked;
        int from = 0;
        int to = 0;

        public Edge(int initWeight, int initFrom, int initTo) {
          weightIndex = edgeNum;
          edgeNum += 1;
          weight[weightIndex] = initWeight;
          from = initFrom;
          to = initTo;
        }

        public void addWeight() {
          weight[weightIndex] += 1;
        }

        public int getWeight() {
          return weight[weightIndex];
        }

        public int getGlobalIndex() {
          return weightIndex;
        }

        public int getFrom() {
          return from;
        }

        public int getTo() {
          return to;
        }
      }

      class Vertex {
        int index;
        String content;
        ArrayList<Edge> edges = null;

        public Vertex(String initContent) {
      
          content = initContent;
          edges = new ArrayList<Edge>();
          index = 0;
        }

        public int getEdges() {
          if (edges != null) {
            return edges.size();
          } else {
            return 0;
          }
        }

        public void addEdge(int nextIndex) {
          Edge current = getEdge(nextIndex);
          if (current == null) {
            current = new Edge(1, index, nextIndex);
            edges.add(current);
          } else {
            current.addWeight();
          }
        }

        public void setIndex(int index) {
          this.index = index;
        }

        public void setContent(String newContent) {
          
          content = newContent;
        }

        public String getContent() {
          return content;
        }

        public int getIndex() {
          return index;
        }

        public Edge getEdge(int nextIndex) {
          for (int i = 0; i < edges.size(); i++) {
            Edge currentEdge = edges.get(i);
            if (currentEdge.getTo() == nextIndex) {
              return currentEdge;
            }
          }
          return null;
        }
      }

      ArrayList<Vertex> vertexList;
      ArrayList<Edge>[] intestEdge;
      HashMap<String, Vertex> contentMap;
    
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
