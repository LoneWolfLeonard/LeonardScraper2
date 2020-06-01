package leonard.scraper.pkg2;

import java.io.IOException;
import java.net.URL;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import java.io.*;  
import javax.swing.JFrame;

public class leonardscraper {
   public static void main(String[] args) throws IOException {
      int filenamecounter = 0;
      int filenamecounter2 = 0;
      int clockcounter = 0;
      int clockcounter2 = 0;
      int clockcounter3 = 0;
      int clockcounterphase2 = 0;
      int refinedcounter = 0;
      int refinedcounterphase2 = 0;
      int refinedcounterphase3 = 0;
      int importantcounter = 0;
      String holder = "";
      int[] supercounter1 = new int[99999];
      String[] superholder = new String[99999];
      String[] refinedholder = new String[99999];
      String[] refinedholderphase2 = new String[99999];
      String[] clockholder = new String[99999];
      String Tester = "Buy Now";
      int RefinedHolderStarter = 0;
      int RememberWhereItStarted = 0;
      String StuffToWrite = "";
      String GimmeAName ="";
      int filereadthingcount = 0;
      File file = new File("C:\\Users\\tremanleo\\Documents\\NetBeansProjects\\LeonardScraperLauncher\\CombineUS\\Counter.txt"); 
      File file2 = new File("C:\\Users\\tremanleo\\Documents\\NetBeansProjects\\LeonardScraperLauncher\\CombineUS\\UrlLoader.txt"); 
//Phase -1 Choose the url to scrape
String URL = ("http://www.pcs-company.com/viewproduct/mold-components/part-ejection/ejector-pins/PSH");
BufferedReader br2 = new BufferedReader(new FileReader(file2));                      

String line2 = "";
			while ((line2 = br2.readLine()) != null) 
                        {
                            URL = line2;
			}	            
                       br2.close();
//Phase 0 Deciding The Name of the CSV File
// Read The Counter      
      BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) 
                        {
                                filenamecounter = Integer.parseInt(line);  
			}
                        br.close();
                                                
//Rename The Output File
GimmeAName = ("C:\\Users\\tremanleo\\Documents\\NetBeansProjects\\LeonardScraperLauncher\\CSVSection\\Leonardrocks" + filenamecounter + ".csv");    
File f = new File(GimmeAName);
FileWriter fw = new FileWriter(f);
PrintWriter out = new PrintWriter(fw);     

filenamecounter = filenamecounter + 1;

//Save The New Number
   FileWriter fw2 = new FileWriter("C:\\Users\\tremanleo\\Documents\\NetBeansProjects\\LeonardScraperLauncher\\CombineUS\\Counter.txt");
   PrintWriter out2 = new PrintWriter(fw2);  
   out2.print(filenamecounter);

   out2.flush(); 
   //Close the Print Writer
   out2.close();       
   //Close the File Writer
   fw2.close();      

//Phase 1 starts, connect to the url, grab the data we want into refinedholder[] array.  
    Document document = Jsoup.connect(URL).timeout(1000 * 1000).get();
    Elements tdgrabber = document.select("td");
    for (Element tdgrab : tdgrabber) {
         holder = tdgrab.text();
         superholder[clockcounter] = holder;
         //System.out.println(holder);
         clockcounter = clockcounter +1;       

          refinedholder[refinedcounter] =  holder;
          refinedcounter++;

          // The below turns on when we get to the right area. The right area always starts after "Buy Now"
         //Remember where it started will mark the <TD> after the one containing "Buy Now". It also turns on the refined holder
         if ( holder.toLowerCase().indexOf(Tester.toLowerCase()) != -1 ) 
              {
                 RefinedHolderStarter = 1;
                 RememberWhereItStarted = clockcounter;
                 RememberWhereItStarted++;
              }         
        }
    
             System.out.println("------------------------------------------------------------");
    //PHASE 2 STARTS (PROCESS THE DATA Pulled From The Page) Here we take the data from the refinedholder[] array and process it. 
    //Currently each iteration is made of 10 items Starting At the, "item number," and ending with "Add".
    //The Data count 0 -> 1 -> 2-> 3-> up until 9 then 0 is a new set.
    //Slots 1, 7 and 9 are useless data and I will get rid of them.
    //This means I want the data passed to the excel sheet in phase 3 down to 7 strings, from 10.
    while(refinedcounterphase2 != refinedcounter)
    { 
        System.out.println("Clock Counter2: " + clockcounter2);
                 System.out.println("Text: " + refinedholder[refinedcounterphase2]);
    if(clockcounter2 != 7)
    {
     if(clockcounter2 != 9)
     {
        System.out.println("Text To Print: " + refinedholder[refinedcounterphase2]);
        refinedholderphase2[importantcounter] = refinedholder[refinedcounterphase2];
        importantcounter++;
     }
    }
     refinedcounterphase2++;
     clockcounter2++;
        
     if(clockcounter2 == 10)
         {
             clockcounter2 = 0;
         }
    }
  //Phase 3 Starts, Export The data to a spread sheet
 while(refinedcounterphase3 != importantcounter)
 {  
    StuffToWrite = refinedholderphase2[refinedcounterphase3];
    //System.out.println(StuffToWrite);
    if(clockcounter3 != 7)
    {
    out.print("'" + StuffToWrite + "'");
    out.print(",");  
    }

    if(clockcounter3 == 7)
    {
     out.print("'" + StuffToWrite + "'");  
      out.print("\n");  
    }  
    
clockcounter3++;
refinedcounterphase3++;

if(clockcounter3 == 8)
{
  clockcounter3 = 0;  
}
 }
     //Flush the output to the file
    out.flush(); 
    //Close the Print Writer
   out.close();       
   //Close the File Writer
   fw.close(); 
   }
}