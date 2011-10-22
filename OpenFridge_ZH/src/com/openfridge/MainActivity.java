package com.openfridge;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
    //Tag for debug log
   private static final String DEBUG_TAG = "Openfridge";

   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       Log.d(DEBUG_TAG, "checkpoint");
       
       //This code gets the xml and sets up the SAX Parser
       try {
           FridgeFoodDataClient client = new FridgeFoodDataClient();
           foods = client.getFoods();
       } catch (Exception e) {
           // For debugging
           e.printStackTrace();
       }

       //Setup the Listview
       ListView lstView = getListView();
       lstView.setChoiceMode(2);   //CHOICE_MODE_MULTIPLE
       lstView.setTextFilterEnabled(true);

       //Attach our data Array to the listview
       setListAdapter(new ArrayAdapter<FridgeFood>(this,
              android.R.layout.simple_list_item_checked, foods));
   }
   
   public void onListItemClick(
   ListView parent, View v, int position, long id) 
   {   
       //---toggle the check displayed next to the item---
       parent.setItemChecked(position, parent.isItemChecked(position));    
       
       if (parent.isItemChecked(position)) {
           Toast.makeText(this, foods.get(position).getSummary(), 
                   Toast.LENGTH_SHORT).show();
       }
   }  
   
   //Arraylist for data from XML
   private ArrayList<FridgeFood> foods; 
   
}