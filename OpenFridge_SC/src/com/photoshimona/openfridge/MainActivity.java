package com.photoshimona.openfridge;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    String tag = "Events";
    String[] presidents = {
               "Dwight D. Eisenhower",
               "John F. Kennedy",
               "Lyndon B. Johnson",
               "Richard Nixon",
               "Gerald Ford",
               "Jimmy Carter",
               "Ronald Reagan",
               "George H. W. Bush",
               "Bill Clinton",
               "George W. Bush",
               "Barack Obama"
       };
   
   
   //String[] presidents;     
   
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       //setContentView(R.layout.main);
       Log.d(tag, "checkpoint");
       getPage("test");
       
       try {
           /* Create a URL we want to load some xml-data from. */
           URL url = new URL("http://openfridge.heroku.com/users/1.xml");

           /* Get a SAXParser from the SAXPArserFactory. */
           SAXParserFactory spf = SAXParserFactory.newInstance();
           SAXParser sp = spf.newSAXParser();

           /* Get the XMLReader of the SAXParser we created. */
           XMLReader xr = sp.getXMLReader();
           /* Create a new ContentHandler and apply it to the XML-Reader*/ 
           XMLHandler myExampleHandler = new XMLHandler();
           xr.setContentHandler(myExampleHandler);
           
           /* Parse the xml-data from our URL. */
           xr.parse(new InputSource(url.openStream()));
           /* Parsing has finished. */

           /* Our ExampleHandler now provides the parsed data to us. */
           String myResult =  myExampleHandler.getParsedData();

           /* Set the result to be displayed in our GUI. */
           presidents[0] = myResult;
           
   } catch (Exception e) {
           /* Display any Error to the GUI. */

   }

       ListView lstView = getListView();
       //lstView.setChoiceMode(0); //CHOICE_MODE_NONE
       //lstView.setChoiceMode(1); //CHOICE_MODE_SINGLE
       lstView.setChoiceMode(2);   //CHOICE_MODE_MULTIPLE
       lstView.setTextFilterEnabled(true);
       Log.d(tag, "another fucking debug msg");
       //presidents = getResources().getStringArray(R.array.presidents_array);
       
       setListAdapter(new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_checked, presidents));
   }
   
   public void onListItemClick(
   ListView parent, View v, int position, long id) 
   {   
       //---toggle the check displayed next to the item---
       parent.setItemChecked(position, parent.isItemChecked(position));    
       
       Toast.makeText(this, 
           "You have selected " + presidents[position], 
           Toast.LENGTH_SHORT).show();
   }  
   
   private String getPage(String url) {
       String str = "***";

       try
       {
           HttpClient hc = new DefaultHttpClient();
           HttpPost post = new HttpPost("http://openfridge.heroku.com/users/1.xml");

           HttpResponse rp = hc.execute(post);

           if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
           {
               str = EntityUtils.toString(rp.getEntity());
           }
       }catch(IOException e){
           e.printStackTrace();
       }  
       Log.d(tag, str);
       return str;
   }
}