import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
public class Main {
	
	public static int tol=0;
	public static String FileName;
	static FileInputStream inputStream=null;
	public static ArrayList<String> Sequence=null;
	public static HashMap seqMap=null;
	public static HashMap edgeMap=null;

	public static void fileRead(String filename) {
		Sequence=new ArrayList<String>();
		File file=new File(filename);
		try {
			InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
			BufferedReader br=new BufferedReader(reader);
			String line="";
			try {
				line=br.readLine();
				while(line!=null) {
					String piece="";
					for(int i=0;i<line.length();i++) {
						if((line.charAt(i)>='A'&&line.charAt(i)<='Z')||(line.charAt(i)>='a'&&line.charAt(i)<='z')) {
							piece+=line.charAt(i);
						}
						else {
							Sequence.add(piece);
							piece="";
						}
					}
					if(piece!="") {
						Sequence.add(piece);
					}
					line=br.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void BuildEdge() {
		int cnt=0;
		seqMap=new HashMap();
		edgeMap=new HashMap();
		for(int i=0;i<Sequence.size();i++) {
			String piece=Sequence.get(i);
			if(!seqMap.containsKey(piece)) {
				seqMap.put(piece, cnt);
				cnt++;
			}
		}
		for(int i=0;i<Sequence.size()-1;i++) {
			int s=(int) seqMap.get(Sequence.get(i));
			int t=(int) seqMap.get(Sequence.get(i+1));
			String sign=s+"+"+t;
			if(edgeMap.containsKey(sign)) {
				seqMap.put(sign, (int)edgeMap.get(sign)+1);
			}
			else {
				seqMap.put(sign, 1);
			}
		}
	}
	public static void main(String[] args) {
		fileRead("/Users/DongSky/test.txt");
	}
}
