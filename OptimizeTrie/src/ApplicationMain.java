import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class ApplicationMain {
	static List<Word> words;
	static Set<String> mostUsedWords = new HashSet<String>();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TopWords.getTopWords();
		File file = new File("C:/users/prakd/Downloads/storyWordsSorted.txt");
		
		// if file doesn't exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
	
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		System.out.println(words.size());
		int i,c=0;
		for(i=0;i<words.size();i++){
			if(words.get(i).occurances>=5){
				c++;
				mostUsedWords.add(words.get(i).word);
			}
		}
		
		System.out.println(c);

	}

}

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

class TopWords {
	static FileWriter fw;
	static BufferedWriter bw;
	
	public static void getTopWords() throws IOException {
		// TODO Auto-generated method stub
		FileInputStream in = new FileInputStream("C:/users/prakd/Downloads/hindistorywords_final.txt");
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
		ApplicationMain.words = new ArrayList<Word>();
		for (Map.Entry<String, Integer> entry : mapp.entrySet()) {
			Word w = new Word(entry.getKey(), entry.getValue());
			ApplicationMain.words.add(w);
		}
		Collections.sort(ApplicationMain.words);
		
		File file = new File("C:/users/prakd/Downloads/storyWordsSorted.txt");
		
		// if file doesn't exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
	
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		
		for(int c = 1; c<= ApplicationMain.words.size(); c++){
			Word w = ApplicationMain.words.get(c-1);
			fw.write(c+" "+w.word+ " " + w.occurances+System.lineSeparator());
		}
		
	}

}