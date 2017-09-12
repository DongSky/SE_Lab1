import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
public class Main extends JFrame{
	JButton readFileButton,buildButton,randomShiftButton,SPButton,bridgeWordButton;
	JTextField sourceText,destText,textFilePathText;
	Font font=new Font("黑体",Font.PLAIN,12);
	public Main() {
		setLayout(null);
		setSize(720,600);
		JLabel ImageLabel=new JLabel();
		
		readFileButton=new JButton("Read file");
		buildButton=new JButton("Build graph");
		randomShiftButton=new JButton("Random shift");
		SPButton=new JButton("Shortest path");
		bridgeWordButton=new JButton("Bridge word");
		sourceText=new JTextField();
		sourceText.setFont(font);
		destText=new JTextField();
		destText.setFont(font);
		textFilePathText=new JTextField();
		textFilePathText.setFont(font);
		textFilePathText.setBounds(5, 5, 240, 30);
		sourceText=new JTextField();
		sourceText.setFont(font);
		sourceText.setBounds(5, 125, 240, 30);
		destText=new JTextField();
		destText.setFont(font);
		destText.setBounds(5, 165, 240, 30);
		
		textFilePathText.setText("input the text file path");
		sourceText.setText("input the source word");
		destText.setText("input the destination word");
		
		
		ImageLabel.setSize(400, 550);
		ImageLabel.setLocation(300, 10);
		ImageIcon image = new ImageIcon("/Users/DongSky/result.png");
		double width=(double)image.getIconWidth();
		double height=(double)image.getIconHeight();
		System.out.println(width+" "+height);
		double x1=width/400;
		double x2=height/550;
		double x=max(x1,x2);
		image.setImage(image.getImage().getScaledInstance((int)(width/x),(int)(height/x),Image.SCALE_DEFAULT)); 
		ImageLabel.setIcon(image);
		readFileButton.setSize(240, 30);
		readFileButton.setLocation(5, 45);
		buildButton.setSize(240,30);
		buildButton.setLocation(5, 85);
		SPButton.setSize(240,30);
		SPButton.setLocation(5, 205);
		bridgeWordButton.setSize(240,30);
		bridgeWordButton.setLocation(5, 245);
		randomShiftButton.setSize(240,30);
		randomShiftButton.setLocation(5, 285);
		readFileButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String path=textFilePathText.getText();
			}
		});
		buildButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//add build procedure
				//next is the template of showing the pic
				
				ImageIcon image = new ImageIcon("/Users/DongSky/result.png");
				double width=(double)image.getIconWidth();
				double height=(double)image.getIconHeight();
				System.out.println(width+" "+height);
				double x1=width/400;
				double x2=height/550;
				double x=max(x1,x2);
				image.setImage(image.getImage().getScaledInstance((int)(width/x),(int)(height/x),Image.SCALE_DEFAULT)); 
				ImageLabel.setIcon(image);
			}
		});
		SPButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String sourceWord=sourceText.getText();
				String destWord=destText.getText();
			}
		});
		bridgeWordButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String sourceWord=sourceText.getText();
				String destWord=destText.getText();
			}
		});
		randomShiftButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		add(ImageLabel);
		add(textFilePathText);
		add(sourceText);
		add(destText);
		add(readFileButton);
		add(buildButton);
		add(SPButton);
		add(bridgeWordButton);
		add(randomShiftButton);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
    double max(double x1, double x2) {
		// TODO Auto-generated method stub
    		if(x1>x2)return x1;
		return x2;
	}
	public static void main(String[] args) {
    		new Main();
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
