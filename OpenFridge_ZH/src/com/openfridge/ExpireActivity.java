package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

//TODO Make the add to list push to shopping list  EL
//DONE Change add to list to add to shopping list  FR
//TODO Log action (Thrown/Eaten)                   EL
//TODO Thrown/Eaten should delete from web         EL

public class ExpireActivity extends Activity {
	private FridgeFood food;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expire);
        
        TextView fdTV = (TextView) findViewById(R.id.foodDescription);
        Bundle bundledFood = getIntent().getExtras();
        food = FridgeFood.getFoodFromBundle(bundledFood);
        
        fdTV.setText(food.getDescription());
	}
	
	public void EditPostponeClick(View view){
	    Intent itemEdit = new Intent(this, ItemEditActivity.class);
	    Bundle bundledFood = FridgeFood.bundleFood(food);
	    
	    itemEdit.putExtras(bundledFood);
		startActivity(itemEdit);
	}
	
	public void DoneClick(View view){
		CheckBox toShopping = (CheckBox)findViewById(R.id.checkBox1);
		
		if(toShopping.isChecked()){
			//add to shopping list
		}
		
		//Remove from expire list and be happy
		try {
			DataClient.getInstance().removeFood(food);
		} catch (Exception e) {
			Toast.makeText( toShopping.getContext(),
					"Connection error occurred", Toast.LENGTH_SHORT);
		}
		
		//log action
		finish();
	}
	
	public void CancelClick(View view){
		finish();
	}
}
