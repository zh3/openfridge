package com.openfridge;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
    
    //Tag for debug log
    String tag = "Openfridge";
    
    //Arraylist for data from XML
    ArrayList<String> dataArray; 
   
   
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       Log.d(tag, "checkpoint");
       
       //This code gets the xml and sets up the SAX Parser
       try {
           /* Create a URL we want to load some xml-data from. */
           /* If you check this url, it's a mini xml from elvin's db */
           URL url = new URL("http://openfridge.heroku.com/fridge_foods.xml");

           /* Get a SAXParser from the SAXPArserFactory. */
           SAXParserFactory spf = SAXParserFactory.newInstance();
           SAXParser sp = spf.newSAXParser();

           /* Get the XMLReader of the SAXParser we created. */
           XMLReader xr = sp.getXMLReader();
           
           /* Create a new ContentHandler and apply it to the XML-Reader*/ 
           FridgeFoodHandler userXmlHandler = new FridgeFoodHandler();
           xr.setContentHandler(userXmlHandler);
           
           /* Parse the xml-data from our URL. */
           xr.parse(new InputSource(url.openStream()));
           /* Parsing has finished. */

           /* Our ExampleHandler now provides the parsed data to us. */
           dataArray = userXmlHandler.getParsedData();
       } catch (Exception e) {
           // For debugging
           e.printStackTrace();
       }

       //Setup the Listview
       ListView lstView = getListView();
       lstView.setChoiceMode(2);   //CHOICE_MODE_MULTIPLE
       lstView.setTextFilterEnabled(true);
       
       //Attach our data Array to the listview
       setListAdapter(new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_checked, dataArray));
   }
   
   public void onListItemClick(
   ListView parent, View v, int position, long id) 
   {   
       //---toggle the check displayed next to the item---
       parent.setItemChecked(position, parent.isItemChecked(position));    
       
       Toast.makeText(this, 
           "You have selected " + dataArray.get(position), 
           Toast.LENGTH_SHORT).show();
   }  
   
}