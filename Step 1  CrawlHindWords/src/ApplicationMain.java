import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;

public class ApplicationMain {
	static DesiredCapabilities capabilities;
	static PhantomJSDriver driver;
	static BufferedWriter bw,bw2;
	static File file, allFile;
	static FileWriter fw, fw2;
	
	public static void main(String[] args) throws LangDetectException, IOException{
		Capabilities capabilities = new DesiredCapabilities();
        ((DesiredCapabilities) capabilities).setJavascriptEnabled(true);                
        ((DesiredCapabilities) capabilities).setCapability("takesScreenshot", true);  
        ((DesiredCapabilities) capabilities).setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "C:\\phantomjs-2.0.0-windows\\phantomjs-2.0.0-windows\\bin\\phantomjs.exe"
            );
	    driver = new PhantomJSDriver(capabilities);
	    DetectorFactory.loadProfile("lib/LangDetect/profiles");
	    System.out.println("गई");
	    Detector detector = null;
	    int i,j,len;
		try
		{
			  FileInputStream in = new FileInputStream("resources/grabLinks.txt");
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  int c = 0;
				allFile = new File("output/all.txt");

				if(!allFile.exists()){
					allFile.createNewFile();
				}
				fw2 = new FileWriter(allFile);
				bw2 = new BufferedWriter(fw2);

				
			  while((strLine = br.readLine())!= null)
			  {
				c++;
				file = new File("output/"+String.valueOf(c)+".txt");
				
				// if file doesn't exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
			
				fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				
				
			    try{
			    	System.out.println("Getting .. "+strLine);
				    driver.get(strLine);
				    WebElement doc = driver.findElement(By.tagName("html"));
				    String docText = doc.getText();
			    					    
				    String[] docWords = docText.split("[\\p{Punct}\\s]+");
				    for(i=0;i<docWords.length;i++){
				    	try{				    		
				    		for(j=0,len=docWords[i].length();j<len;j++){
						    	detector = DetectorFactory.create();
						    	detector.append(String.valueOf(docWords[i].charAt(j)));
						    	if(detector.detect().equals("hi")){
						    		if(j==len-1){
						    			bw.append(docWords[i]+System.lineSeparator());
						    			bw2.append(docWords[i]+System.lineSeparator());
						    		}
					    		}
						    	else break;
				    		}
				    	}
				    	catch(LangDetectException e){
				    		//System.out.println("Error occured finding the language for the word : "+docWords[i]);	    		
				    	}
				    }				    
			    }
			    catch(Exception e){
			    	System.out.println("html error "+e.getMessage()+" "+strLine);
			    }
			    bw.close();
				fw.close();	
			  }
		}
		catch(Exception e){
			  System.out.println(e);
		}
	    bw2.close();
		fw2.close();	

	}	
}
