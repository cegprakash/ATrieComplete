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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


class TrieNodeComparable implements Comparable<TrieNodeComparable>{
	public TrieNode node;
	public int score;
	public int compareTo(TrieNodeComparable other) {
		return other.score-this.score;
	}
}

public class Trie {
    public TrieNode root;
 
    public Trie() {
        root = new TrieNode('$');
    }
 
    
    public void print(TrieNode node, int level){
    	if(node == null)
    		return;
    	    	
    	System.out.print(node.ch +" "+level+" "+(node.isWord));
    	for(int i=0;i<3;i++)
    		if(node.best3[i]!=null){
    			System.out.print(" "+node.best3[i].ch+" "+(node.best3[i].userScore+node.best3[i].webScore));
    		}
    	System.out.println("");
        for(Entry<Character, TrieNode> entry : node.childs.entrySet()) {
        	print(entry.getValue(), level+1);
        }    	
    }
    
    
    String result;
    private void writeThisNodeOnly(TrieNode node){
    	if(node == null)
    		result+="n";
    	else{
	    	result += node.ch +",";
	    	result += String.valueOf(node.webScore) + ",";
	    	result += String.valueOf(node.userScore) + ",";
	    	result += String.valueOf(node.isWord?1:0);
    	}
    }
    private void writeNodeAndBest3(TrieNode node){
    	writeThisNodeOnly(node);
    	if(node!=null){
	    	result += ",";
	    	for(int i=0;i<3;i++){    		
	    		writeThisNodeOnly(node.best3[i]);
	    		if(i!=2)
	    			result += ",";
	    	}
    	}
    }
        
    
    private void writeTrie(TrieNode node){    	
    	writeNodeAndBest3(node);
        TreeMap<Character, TrieNode> children = node.childs;
        for(Entry<Character, TrieNode> entry : children.entrySet()) {
        	result += ",";
        	writeTrie(entry.getValue());
        }
        result += ")";
    }
        
    
    public void writeTrie(String fileName) throws IOException{
    	result = "";
		File outputFile = new File(fileName);

		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		FileWriter fw = new FileWriter(outputFile);
		BufferedWriter bw = new BufferedWriter(fw);
		writeTrie(root);	
		bw.write(result);
		bw.close();
		fw.close();
    }
    
    
    String stream;
    int indexx;    

    private String loadString(){
    	if(indexx == -1){
    		return null;
    	}
    	else{
        	int nextIndex;
	    	int commaIndex = stream.indexOf(",",indexx);
	    	int closeIndex = stream.indexOf(")",indexx);    	
	    	if(commaIndex == -1)
	    		nextIndex = closeIndex;
	    	else if(closeIndex == -1)
	    		nextIndex = commaIndex;
	    	else nextIndex = Math.min(commaIndex,closeIndex);
	    	String res;
	    	if(nextIndex == -1)
	    		res = stream.substring(indexx);
	    	else
	    		res = stream.substring(indexx,nextIndex);
    		indexx = nextIndex;
	    	return (res.equals("n"))?null:res;
    	}
    }
        
    private TrieNode loadThisNodeOnly(){
    	String str = loadString();
    	
    	if(str == null || indexx == -1 || indexx >= stream.length()){
    		return null;
    	}
    	//System.out.println(indexx+" "+stream.charAt(indexx) + " str:"+str+".");
    	TrieNode node = new TrieNode(str.charAt(0));
    	    	
    	indexx++;
    	str = loadString();
    	node.webScore = Integer.valueOf(str);
    	
    	indexx++;
    	str = loadString();
    	node.userScore = Integer.valueOf(str);
    	
    	indexx++;
    	str = loadString();
    	node.isWord = Integer.valueOf(str).intValue() == 1;    	
    	
    	//System.out.println("Successfully loaded node : "+node.ch+" "+node.webScore+" "+node.userScore+" "+node.isWord+" "+indexx);
    	
    	return node;
    }
    
    private void fixReferences(TrieNode node){
    	if(node == null)
    		return;
        TreeMap<Character, TrieNode> children = node.childs;
        for(Entry<Character, TrieNode> entry : children.entrySet()) {
        	fixReferences(entry.getValue());        	
        }
        updateMax3(node);
    }
    
    private TrieNode loadNodeWithBest3(){
    	TrieNode nodeWithBest3 = loadThisNodeOnly();
    	if(nodeWithBest3 == null)
    		return null;
    	else{
    		for(int i=0;i<3;i++){
        		indexx++;
        		nodeWithBest3.best3[i] = loadThisNodeOnly();
    		}
    		return nodeWithBest3;
    	}    	
    }
    
    private TrieNode loadTrie(){
    	TrieNode resTrie = loadNodeWithBest3();
    	if(resTrie == null)
    		return null;
    	while(indexx != -1 && indexx<stream.length()){
	    	if(stream.charAt(indexx)==')')
	    	{
	    		indexx++;
	    		return resTrie;
	    	}
	    	indexx++;
    		TrieNode child = loadTrie();
    		if(child != null)
    			resTrie.childs.put(child.ch, child);
    	}
    	return resTrie;
    }
    
    public void readTrie(String fileName) throws IOException{
    	FileInputStream in = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = br.readLine();
		br.close();
		in.close();
		if(strLine == null){
			return;
		}		
		stream = strLine;
		indexx = 0;
		root = loadTrie();
		fixReferences(root);
//		stream = "";
//		index = 0;
    } 	
    
    // Inserts a word into the trie.
    public void insert(String word) {
        TreeMap<Character, TrieNode> children = root.childs;
 
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
 
            TrieNode t;
            if(children.containsKey(c)){
                    t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c, t);
            }
 
            children = t.childs;
 
            //set leaf node
            if(i==word.length()-1)
                t.isWord = true;    
        }
    }
    
    public List<String> getTopWords(TrieNode node, int count){    	
    	List<String> results = new ArrayList<String>();
    	HashMap<TrieNode, Integer> maxx = new HashMap<TrieNode, Integer>();
    	if(count == 0 || node == null){
    		return results;
    	}
    	for(int i=0;i<Math.min(count,3);i++){
    		if(node.best3[i] != null){
    			int cnt = maxx.getOrDefault(node.best3[i], Integer.valueOf(0)).intValue();
    			maxx.put(node.best3[i],cnt+1);
    		}
    	} 
    	//System.out.println("maxx size : "+maxx.size());
    	for(Entry<TrieNode, Integer> entry : maxx.entrySet()) {
        	//System.out.println("maxx entry:"+entry.getKey().ch);
    		TrieNode key = entry.getKey();
        	int cnt = entry.getValue();
        	//System.out.println(key+" "+node);
        	if(key == node){
        		results.add("");
        		//System.out.println("if part");
        	}
        	else{
        		//System.out.println("else part");

        		List<String> tempRes = getTopWords(key,cnt);
        		for(int i=0;i<tempRes.size(); i++){
        			tempRes.set(i, key.ch + tempRes.get(i));
            		//System.out.println("else part inner loop");
        		}
        		results.addAll(tempRes);
        		//System.out.println("else part results size : "+results.size());
        	}		
        }    	
    	return results;
    }
    
    public void updateMax3(TrieNode node){
    	int i;
    	if(node == null)
    		return;
    	for(i=0;i<3;i++){        		  
    		node.best3[i] = null;
    	}
    	List<TrieNodeComparable> nodes = new ArrayList<TrieNodeComparable>();
        TreeMap<Character, TrieNode> children = node.childs;
        for(Entry<Character, TrieNode> entry : children.entrySet()) {
        	  char key = entry.getKey().charValue();
        	  TrieNode value = entry.getValue();
        	  for(i=0;i<3;i++){        		  
        		  TrieNodeComparable n = new TrieNodeComparable();
        		  n.node = value;
        		  if(value.best3[i]!= null){
        			  n.score = value.best3[i].userScore+value.best3[i].webScore;
        			  nodes.add(n);
        		  }
        	  }
        }
        if(node.isWord){
        	TrieNodeComparable n = new TrieNodeComparable();
  		  	n.node = node;
  		  	n.score = node.userScore + node.webScore;
  	        nodes.add(n);
        }
        Collections.sort(nodes);
        for(i=0;i<Math.min(3,nodes.size());i++){
        	node.best3[i] = nodes.get(i).node;
        }
    }
    
    public void insertWithWebScore(TrieNode node, String word, int i){
        TreeMap<Character, TrieNode> children = node.childs;
        TrieNode t;
        char ch = word.charAt(i);
        if(children.containsKey(ch)){
            t = children.get(ch);
        }
        else{
        	t = new TrieNode(ch);
        	children.put(ch, t);
        }
        t.webScore++;

    	if(i != word.length()-1 )
    		insertWithWebScore(t, word, i+1);
    	else
    		t.isWord = true;
    	
    	updateMax3(t);
    	if(i==0)
    		updateMax3(node);
    }
    
    public void insertWithWebScore(TrieNode node, String prevWord, String word, int i){
        TreeMap<Character, TrieNode> children = node.childs;
        TrieNode t;
        char ch;
        if (i < prevWord.length())
        {
        	ch = prevWord.charAt(i);
        }
        else if(i == prevWord.length())
        {
        	ch = ' ';
        }
        else{
        	ch = word.charAt(i-prevWord.length()-1);
        }         

        if(children.containsKey(ch)){
            t = children.get(ch);
        }
        else{
        	t = new TrieNode(ch);
        	children.put(ch, t);
        }
        t.webScore++;

        if(i == prevWord.length()-1)
        	t.isWord = true;
    	if(i != prevWord.length()+word.length() )
    		insertWithWebScore(t, prevWord, word, i+1);
    	else
    		t.isWord = true;
    	
    	updateMax3(t);
    	if(i==0)
    		updateMax3(node);
    }
    
    
 
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);
 
        if(t != null && t.isWord) 
            return true;
        else
            return false;
    }
 
    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if(searchNode(prefix) == null) 
            return false;
        else
            return true;
    }
 
    public TrieNode searchNode(String str){
    	if(str == "" && root != null)
    		return root;
    	TreeMap<Character, TrieNode> children = root.childs; 
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.childs;
            }else{
                return null;
            }
        }
 
        return t;
    }
    
    public List<String> autoComplete(String prefix){
    	TrieNode n = searchNode(prefix);
    	if(n == null)
    		return new ArrayList<String>();
    	else System.out.println("Prefix found");
    	List<String> ans = getTopWords(n, 3);
    	for(int i=0;i<ans.size();i++)
    		ans.set(i,prefix+ans.get(i));
    	return ans;
    }    
    
    public List<String> autoCompleteNextWord(String previousWord){
    	TrieNode n = searchNode(previousWord+" ");
    	if(n == null)
    		return new ArrayList<String>();
    	else System.out.println("Prefix found");
    	List<String> ans = getTopWords(n, 3);
    	return ans;
    }    
    
}