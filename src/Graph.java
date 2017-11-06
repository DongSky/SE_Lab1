import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/*this is the first edit in Lab 3*/

public class Graph {
    /*
     * Edge Class:store edges in the graph paramaters: int weightIndex: the
     * number of the edge boolean walked: a flag to judge in the Random Walk
     * process int from: the number of start word in the edge int to: the number
     * of destination in the edge
     */
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
    int[] weight;
    int edgeNum;
    int[] distance;
    boolean[] visited;
    boolean[] walked;
    ArrayList<Edge>[] intestEdge;
    HashMap<String, Vertex> contentMap;
    public ArrayList<String> sequence;

    /** new a graph. */
    public Graph() {
      int maxWeightListSize = 2000000;
      weight = new int[maxWeightListSize];
      edgeNum = 0;
      contentMap = new HashMap<String, Vertex>();
      sequence = new ArrayList<String>();
      vertexList = new ArrayList<Vertex>();
    }

    Vertex addVertex(String word) {
      int index = vertexList.size();
      Vertex current = new Vertex(word);

      current.setIndex(index);
      vertexList.add(current);

      contentMap.put(word, vertexList.get(vertexList.size() - 1));
      return current;
    }

    /** read file. */
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

    /** add path. */
    public void addPath() {
      String headContent = sequence.get(0);
      Vertex current = contentMap.get(headContent);

      if (current == null) {
        current = addVertex(headContent);
      }

      for (int i = 1; i < sequence.size(); i++) {
        String nextContent = sequence.get(i);
        Vertex nextVertex = contentMap.get(nextContent);

        if (nextVertex == null) {
          nextVertex = addVertex(nextContent);
        }
        int nextIndex = nextVertex.getIndex();
        current.addEdge(nextIndex);
        current = nextVertex;

      }
      for (int i = 0; i < vertexList.size(); i++) {
        Vertex v = vertexList.get(i);
        //System.out.println(v.content + " " + v.getEdges());
      }
    }

    class ComparisonPairs {
      int key;
      int value;

      public ComparisonPairs(int initKey, int initValue) {
        key = initKey;
        value = initValue;
      }
    }

    /** get shortest path orz. */
    public void getshortestPath(int sourceVertexIndex) {
      Vertex sourceVertex = vertexList.get(sourceVertexIndex);

      distance = new int[vertexList.size()];
      intestEdge = new ArrayList[vertexList.size()];
      cleanVisited();
      for (int i = 0; i < vertexList.size(); i++) {
        distance[i] = 0x7fffffff;
      }

      distance[sourceVertexIndex] = 0;

      PriorityQueue<ComparisonPairs> heap = new PriorityQueue<ComparisonPairs>(
          new Comparator<ComparisonPairs>() {
          public int compare(ComparisonPairs p1, ComparisonPairs p2) {
            return p1.key - p2.key;
          }
        });

      ComparisonPairs headPair = new ComparisonPairs(
          distance[sourceVertexIndex], sourceVertexIndex
        );
      heap.add(headPair);

      while (!heap.isEmpty()) {
        while (!heap.isEmpty() && visited[heap.peek().value]) {
          heap.poll();
        }
        if (heap.isEmpty()) {
          break;
        }
        int current = heap.poll().value;
        Vertex currentVertex = vertexList.get(current);
        visited[current] = true;
        for (int i = 0; i < currentVertex.edges.size(); i++) {
          Edge currentEdge = currentVertex.edges.get(i);
          int newDistance = distance[currentEdge.getFrom()] + currentEdge.getWeight();
          int prevDistance = distance[currentEdge.getTo()];

          if (newDistance < prevDistance) {
            distance[currentEdge.getTo()] = newDistance;
            ArrayList<Edge> newEdgeList = new ArrayList<Edge>();
            newEdgeList.add(currentEdge);
            intestEdge[currentEdge.getTo()] = newEdgeList;
            ComparisonPairs nextPair = new ComparisonPairs(
                distance[currentEdge.getTo()], currentEdge.getTo()
              );
            heap.add(nextPair);
          } else if (newDistance == prevDistance) {
            intestEdge[currentEdge.getTo()].add(currentEdge);
          }
        }
      }
    }

    /** clean visited . */
    public void cleanVisited() {
      visited = new boolean[vertexList.size()];
      for (int i = 0; i < vertexList.size(); i++) {
        visited[i] = false;
      }
    }

    int shortestPath(final String sourceString, final String targetString) {
      Vertex sourceVertex = contentMap.get(sourceString);
      Vertex targetVertex = contentMap.get(targetString);
      if (sourceVertex == null) {
        return -1;
      }
      int sourceVertexIndex = sourceVertex.getIndex();
      getshortestPath(sourceVertexIndex);
      if (targetVertex == null) {
        return -2;
      }
      int targetVertexIndex = targetVertex.index;
      //System.out.println(targetVertexIndex);
      if (sourceVertex == null || targetVertex == null) {
        return 0x7fffffff;
      }
      for (int i = 0; i < vertexList.size(); i++) {
        String byCheck = "The i th " + vertexList.get(i).content + " is " + i + " : " + distance[i];
        //System.out.println(byCheck);
      }
      return distance[targetVertexIndex];
    }

    public boolean breakWalkingFlag = false;

    public void breakWalking() {
      this.breakWalkingFlag = true;
    }

    private boolean hasUnwalkedEdge(Vertex current) {
      if (current.edges.size() == 0) {
        return false;
      } else {
        boolean hasNextUnwalkedEdge = false;
        for (int i = 0; i < current.edges.size(); i++) {
          if (!walked[current.edges.get(i).getGlobalIndex()]) {
            hasNextUnwalkedEdge = true;
            break;
          }
        }
        if (hasNextUnwalkedEdge) {
          return true;
        } else {
          return false;
        }
      }
    }

    /** rrandom walk. */
    public String randomWalking(int delayTime) {
      Random random = new Random();
      int currentIndex = random.nextInt(vertexList.size() - 1);
      String text = "";
      Vertex current = vertexList.get(currentIndex);
      cleanWalkedEdge();
      text += current.getContent();

      while (!breakWalkingFlag && hasUnwalkedEdge(current)) {
        try {
          Thread.currentThread().sleep(delayTime);
        } catch (Exception timeDelayError) {
          timeDelayError.printStackTrace();
        }

        int nextEdgeIndex;
        nextEdgeIndex = random.nextInt(current.edges.size());

        Edge currentEdge = current.edges.get(nextEdgeIndex);
        if (walked[currentEdge.getGlobalIndex()]) {
          break;
        }
        walked[currentEdge.getGlobalIndex()] = true;
        currentIndex = currentEdge.getTo();
        current = vertexList.get(currentIndex);
        text += " -> " + current.getContent();
      }
      if (breakWalkingFlag == false) {
        //System.out.println("false");
      }
      breakWalkingFlag = false;
      //ranFlag = false;
      if (breakWalkingFlag == false) {
        //System.out.println("reset complete");
      }
      return text;
    }

    /** clean walk edge. */
    public void cleanWalkedEdge() {
      walked = new boolean[edgeNum];
      for (int i = 0; i < edgeNum; i++) {
        walked[i] = false;
      }
    }

    /** get bridge words by vertex. */
    public ArrayList<Vertex> getBridgeWords(Vertex vertex1, Vertex vertex2) {
      ArrayList<Vertex> bridgeVertex = new ArrayList<>();
      cleanVisited();
      for (int i = 0; i < vertex1.edges.size(); i++) {
        Edge edge1 = vertex1.edges.get(i);
        Vertex nextVertex = vertexList.get(edge1.to);
        for (int j = 0; j < nextVertex.edges.size(); j++) {
          Edge edge2 = nextVertex.edges.get(j);
          if (edge2.getTo() == vertex2.index) {
            if (!visited[edge1.getTo()]) {
              bridgeVertex.add(vertexList.get(edge1.getTo()));
              visited[edge1.getTo()] = true;
            }
          }
        }
      }
      return bridgeVertex;
    }

    /** get bridge words by string. */
    public String getBridgeWords(String str1, String str2) {
      StringBuffer answer = new StringBuffer("");
      Vertex vertex1 = contentMap.get(str1);
      Vertex vertex2 = contentMap.get(str2);
      // System.out.println(vertex1.content+"+"+vertex2.content);
      if (vertex1 == null || vertex2 == null) {
        answer.append("No word1 or word2 in the graph!") ;
      } else {
        ArrayList<Vertex> bridgeVertex = getBridgeWords(vertex1, vertex2);
        if (bridgeVertex.size() == 0) {
          answer.append("No bridge words from word1 and word2");
        } else {
          answer.append("The bridge words from word1 to word2 are: ");
          if (bridgeVertex.size() == 1) {
            answer.append(bridgeVertex.get(0).getContent() + ".");
          } else {
            for (int i = 0; i < bridgeVertex.size() - 1; i++) {
              answer.append(bridgeVertex.get(i).getContent() + ", ");
            }
            answer.append("and " + bridgeVertex.get(bridgeVertex.size() - 1).getContent() + ".");
          }
        }
      }
      return answer.toString();
    }

    boolean isLetter(char x) {
      return ('a' <= x && x <= 'z') || ('A' <= x && x <= 'Z');
    }

    /** generate new text. */
    public String generateNewText(String originalText) {
      int idx = 0;
      String newText = "";
      String current = "";
      String temp ;
      while (idx < originalText.length() && !isLetter(originalText.charAt(idx))) {
        newText += originalText.charAt(idx);
        idx += 1;
      }
      while (idx < originalText.length() && isLetter(originalText.charAt(idx))) {
        newText += originalText.charAt(idx);
        current += originalText.charAt(idx);
        idx += 1;
      }

      while (idx < originalText.length() ) {
        temp = "";
        String nextWord = "";

        while (idx < originalText.length() && !isLetter(originalText.charAt(idx))) {
          temp += originalText.charAt(idx);
          idx += 1;
        }

        while (idx < originalText.length() && isLetter(originalText.charAt(idx))) {
          nextWord += originalText.charAt(idx);
          idx += 1;
        }

        Vertex vertex1 = contentMap.get(current);
        Vertex vertex2 = contentMap.get(nextWord);

        if (vertex1 != null && vertex2 != null) {
          ArrayList<Vertex> bridgeWords = getBridgeWords(vertex1, vertex2);
          if (bridgeWords.size() != 0) {
            Random random = new Random();
            Vertex bridge = bridgeWords.get(random.nextInt(bridgeWords.size()));
            newText += " " + bridge.getContent();
          }
        }
        newText += temp + nextWord;
        current = nextWord;
      }
      return newText;
    }

    /** get fully paths. */
    public ArrayList<ArrayList<String>> getFullyPaths(Vertex currentVertex, Vertex targetVertex) {
      ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

      if (currentVertex == targetVertex) {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(targetVertex.content);
        result.add(ret);
        return result;
      } else {
        int currentIndex = currentVertex.getIndex();
        for (int i = 0; i < intestEdge[currentIndex].size(); i++) {
          Edge nextEdge = intestEdge[currentIndex].get(i);
          int nextIndex = nextEdge.getFrom();
          Vertex nextVertex = vertexList.get(nextIndex);

          if (distance[nextIndex] + nextEdge.getWeight() == distance[currentIndex]) {
            ArrayList<ArrayList<String>> nextResult = getFullyPaths(nextVertex, targetVertex);

            for (int j = 0; j < nextResult.size(); j++) {
              ArrayList<String> currentPath = nextResult.get(j);
              currentPath.add(currentVertex.content);

              result.add(currentPath);
            }
          }
        }
      }

      return result;
    }
}