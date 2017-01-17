import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

public class ApplicationMain {
	static BufferedWriter bw2;
	static File file, allFile;
	static FileWriter fw2;
	
	public static void main(String[] args) throws LangDetectException, IOException{
	    System.out.println("नमस्ते");

		try
		{
			  FileInputStream in = new FileInputStream("C:/users/prakd/Downloads/hindistorywords_onlyHindiChar.txt");
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  int c = 0;
				allFile = new File("C:/users/prakd/Downloads/hindistorywords_final.txt");
			    DetectorFactory.loadProfile("lib/LangDetect/profiles");

				if(!allFile.exists()){
					allFile.createNewFile();
				}
				fw2 = new FileWriter(allFile);
				bw2 = new BufferedWriter(fw2);
				String[] docWords;
				int words = 0,i,j,len,k,l;
				boolean exists;
				ArrayList<Language> languages;
				Detector detector = null;
			  while((strLine = br.readLine())!= null)
			  {				
					try{										    
						//docWords = strLine.split("[\\p{Punct}\\s]+");
						
						//for(i=0,l=docWords.length;i<l;i++){
							try{
								//if(docWords[i].contains("") || docWords[i].contains(""))
								//	continue;
								detector = DetectorFactory.create();
								detector.append(strLine);
						    	//detector.append(docWords[i]);
						    	if(detector.detect().equals("hi") && detector.getProbabilities().size() == 1 && !strLine.matches(".*[a-zA-Z].*"))
					    			//bw2.append(docWords[i]+System.lineSeparator());
						    		bw2.append(strLine+System.lineSeparator());
//								for(j=0,len=docWords[i].length();j<len;j++){
//									detector = DetectorFactory.create();								
//									detector.append(String.valueOf(docWords[i].charAt(j)));							    	
//							    	languages = detector.getProbabilities();
//							    	exists = false;
//							    	for(k=0;k<languages.size();k++){
//							    		if(languages.get(k).lang.equals("hi") && languages.get(k).prob>0.0)
//							    		{
//							    			exists = true;
//							    			break;
//							    		}
//									}
//							    	if(!exists)
//							    		break;
							    	
//						    		if(j==len-1){
//						    			bw2.append(docWords[i]+System.lineSeparator());
//						    		}
//					    		}
							}						
							catch(LangDetectException e){
								//System.out.println("Error occured finding the language for the word : "+docWords[i]);	    		
							}
					    	words++;
						//}
					}
				    catch(Exception e){
				    	System.out.println("text error "+e.getMessage()+" "+strLine);
				    }	
					c++;
					if(c%100000 == 0)
						System.out.println(c);
					
			  }
			  bw2.close();
			  fw2.close();	
			  System.out.println("Words checked "+words);
		}
		catch(Exception e){
			  System.out.println(e);
		}
	}	
}
