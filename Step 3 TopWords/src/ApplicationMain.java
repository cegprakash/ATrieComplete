import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


class Word implements Comparable<Word>{
	public String word;
	public int occurances;
	
	public Word(String s, int c){
		word = s;
		occurances = c;
	}
	
	@Override
	public int compareTo(Word word2) {
		return word2.occurances -  this.occurances;
	}
};

public class ApplicationMain {
	static FileWriter fw;
	static BufferedWriter bw;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream in = new FileInputStream("C:/users/prakd/Downloads/jagranHindi.txt");
		String strLine;
		HashMap<String, Integer> mapp = new HashMap<String, Integer>();
		
		System.out.println("गई");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while((strLine = br.readLine())!= null){
			if(mapp.containsKey(strLine)){
				int c = mapp.get(strLine);
				mapp.put(strLine, c+1);
			}
			else
				mapp.put(strLine, 1);
		}
		List<Word> words = new ArrayList<Word>();
		for (Map.Entry<String, Integer> entry : mapp.entrySet()) {
			Word w = new Word(entry.getKey(), entry.getValue());
			words.add(w);
		}
		Collections.sort(words);
		
		File file = new File("C:/users/prakd/Downloads/jagranHindiSorted.txt");
		
		// if file doesn't exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
	
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		
		for(int c = 1; c<= words.size(); c++){
			Word w = words.get(c-1);
			fw.write(c+" "+w.word+ " " + w.occurances+System.lineSeparator());
		}
		
	}

}
