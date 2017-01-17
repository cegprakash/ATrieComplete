import java.util.TreeMap;

public class TrieNode {
    public char ch;
    public int webScore;
    public int userScore;
    boolean isWord;    
    TrieNode[] best3;
    
    TreeMap<Character, TrieNode> childs = new TreeMap<Character, TrieNode>();
 
    public TrieNode(char c) {
    	ch = c;
    	best3 = new TrieNode[3];
    	webScore = userScore = 0;
    	childs.clear();
    	isWord = false;
    }
    
    public TrieNode() {
    	ch = '|';
    	best3 = new TrieNode[3];
    	webScore = userScore = 0;
    	childs.clear();
    	isWord = false;
    }
}