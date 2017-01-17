import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ApplicationMain {
	
	static List<Word> words;
	static Set<String> mostUsedWords = new HashSet<String>();
	public static void main(String[] args) throws IOException{
		
		TopWords.getTopWords();
		System.out.println(words.size());
		for(int i=0;i<words.size();i++){
			if(words.get(i).occurances >= 5)
				mostUsedWords.add(words.get(i).word);			
		}
		long heapSize = Runtime.getRuntime().totalMemory();
	    SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");//dd/MM/yyyy
	    System.out.println(sdfDate.format(new Date()));
		System.out.println("heap Size initial : "+heapSize);
		Trie t = new Trie();
		FileInputStream in = new FileInputStream("C:/users/prakd/Downloads/hindistorywords_final.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		

		
		
		String strLine;
//		int c = 0;
//		while((strLine = br.readLine())!= null){
//			t.insertWithWebScore(t.root,strLine,0);
//		}


//		t.insertWithWebScore(t.root, "कल",0);
//		t.insertWithWebScore(t.root, "कलल", "हाल", 0);
//		t.insertWithWebScore(t.root, "हाल", "हाल", 0);
//		t.insertWithWebScore(t.root, "कलल", "हाल", 0);		
//		t.insertWithWebScore(t.root, "कल", "हाल", 0);

		//t.readTrie("C:/users/prakd/Downloads/trieTest.trie");

//		System.out.println("read trie");
//		String prevWord = br.readLine();
//		while((strLine = br.readLine())!= null){
//			if(mostUsedWords.contains(prevWord) && mostUsedWords.contains(strLine))
//				t.insertWithWebScore(t.root, prevWord, strLine, 0);
//			//System.out.println(prevWord+" "+strLine);
//			prevWord = strLine;			
//		}
//		System.out.println("modified trie");
//	
//		t.writeTrie("C:/users/prakd/Downloads/TrieFinalIntermediate.trie");
		//t.readTrie("C:/users/prakd/Downloads/TrieFinalIntermediate.trie");
		//System.out.println("intermediate trie stored");

		
//		t.print(t.root, 0);
//		List<String> res = t.autoComplete("क");
//		System.out.println("Results : "+res.size());
//		for(int i=0;i<res.size();i++)
//			System.out.println(res.get(i));
//
//		res = t.autoCompleteNextWord("क");//t.autoComplete("बाजा");
//		System.out.println("Results : "+res.size());
//		for(int i=0;i<res.size();i++)
//			System.out.println(res.get(i));
//		
		
		
		
		String prevWord = br.readLine();
		int c = 1;
		while((strLine = br.readLine())!= null){
			//System.out.println(prevWord+" "+strLine);
			//System.out.println(prevWord+" "+strLine);
			if(mostUsedWords.contains(prevWord) && mostUsedWords.contains(strLine)){
				try{
					//if(c>=3890122){
						t.insertWithWebScore(t.root, prevWord, strLine, 0);
	
						if(c%1000000==0){
							System.out.println(c);
							t.writeTrie("C:/users/prakd/Downloads/storiestrie_proper_5occurances.trie");			
							t.readTrie("C:/users/prakd/Downloads/storiestrie_proper_5occurances.trie");
						}
					//}
				}
				catch(Exception e){
					System.out.println("Problem reading after inserting : "+prevWord+" "+strLine+" on line "+c);
					System.exit(1);
				}
				prevWord = strLine;			
				c++;
			}
		}

//		t.writeTrie("C:/users/prakd/Downloads/test123.trie");
		
		t.writeTrie("C:/users/prakd/Downloads/storiestrie_proper_5occurances.trie");
		System.out.println("stored trie");		
		
		t.readTrie("C:/users/prakd/Downloads/storiestrie_proper_5occurances.trie");
		
//		t.writeTrie("C:/users/prakd/Downloads/test123.trie");
		
		//t.writeTrie("C:/users/prakd/Downloads/trieTest2.trie");

		
		//store trie in a file
		
		//t.print(t.root,0);
		
		List<String> res = t.autoComplete("कल");
		System.out.println("Results : "+res.size());
		for(int i=0;i<res.size();i++)
			System.out.println(res.get(i));
		
		res = t.autoCompleteNextWord("कल");//t.autoComplete("बाजा");
		System.out.println("Results : "+res.size());
		for(int i=0;i<res.size();i++)
			System.out.println(res.get(i));

		
	    System.out.println(sdfDate.format(new Date()));
		
//		long heapSizeFinal = Runtime.getRuntime().totalMemory();
//		System.out.println("heap Size initial : "+heapSizeFinal);
//		System.out.println("Bytes difference : "+(heapSizeFinal-heapSize));

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
		ApplicationMain.words = new ArrayList<Word>();
		for (Map.Entry<String, Integer> entry : mapp.entrySet()) {
			Word w = new Word(entry.getKey(), entry.getValue());
			ApplicationMain.words.add(w);
		}
		Collections.sort(ApplicationMain.words);
		
		File file = new File("C:/users/prakd/Downloads/jagranHindiSorted.txt");
		
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

