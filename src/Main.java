import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

class Graph {
    public class Edge {
        int weightIndex;
        int from, to;

        public Edge(int initWeight, int initFrom, int initTo) {
            weightIndex = weightNum;
            weightNum += 1;
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
        public int getFrom() {
            return from;
        }
        public int getTo() {
            return to;
        }
    }
    public class Vertex {
        int index;
        String content;
        ArrayList<Edge> edges=null;

        public Vertex(String initContent) {
            content = new String();
            content = initContent;
            edges = new ArrayList<Edge>();
            index = 0;
        }
        public int getEdges(){
            if(edges!=null)return edges.size();
            else return 0;
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
            content = new String();
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
    int [] weight;
    int weightNum;
    HashMap<String, Vertex> contentMap;
    public static ArrayList<String> Sequence;

    public Graph() {
        int maxWeightListSize = 20000;
        weight = new int[maxWeightListSize];
        weightNum = 0;
        contentMap = new HashMap<String, Vertex>();
        Sequence = new ArrayList<String>();
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

    public static void fileRead(String filename) {
        Sequence=new ArrayList<String>();
        File file=new File(filename);
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            try {
                line = br.readLine();
                while (line != null) {
                    String piece = "";
                    for (int i = 0; i < line.length(); i++) {
                        if ((line.charAt(i) >= 'A' && line.charAt(i) <= 'Z') ||
                                (line.charAt(i) >= 'a' && line.charAt(i) <= 'z')) {
                            piece += line.charAt(i);
                        }
                        else {
                            Sequence.add(piece);
                            piece = "";
                        }
                    }
                    if (piece != "") {
                        Sequence.add(piece);
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

    public void addPath() {
        String headContent = Sequence.get(0);
        Vertex current = contentMap.get(headContent);

        if (current == null) {
            current = addVertex(headContent);
        }

        for (int i = 1; i < Sequence.size(); i++) {
            String nextContent = Sequence.get(i);
            Vertex nextVertex = contentMap.get(nextContent);

            if (nextVertex == null) {
                nextVertex = addVertex(nextContent);
            }
            int nextIndex = nextVertex.getIndex();
            current.addEdge(nextIndex);
            current = nextVertex;

        }
        for(int i=0;i<vertexList.size();i++){
            Vertex v=vertexList.get(i);
            System.out.println(v.content+" "+v.getEdges());
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Graph g=new Graph();
        g.fileRead("/Users/DongSky/test.txt");
        g.addPath();
        GraphViz gv=new GraphViz();
        gv.addln(gv.start_graph());
        for(int i=0;i<g.vertexList.size();i++) {
        		String s=g.vertexList.get(i).content;
        		for(int j=0;j<g.vertexList.get(i).getEdges();j++) {
        			int t_num=g.vertexList.get(i).edges.get(j).to;
        			String t=g.vertexList.get(t_num).content;
        			gv.addln(s+" -> "+t+";");
        		}
        }
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());
        String type = "png";
        File out=new File("/Users/DongSky/result."+type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(),type), out);
    }
}
