package com.openfridge;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//TODO Make the add to list push to shopping list  EL
//DONE Change add to list to add to shopping list  FR
//TODO Log action (Thrown/Eaten)                   EL
//DONE Thrown/Eaten should delete from web         FR

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
			addToShoppingList();
		}
		
		boolean eaten;
		RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup1);
		
		if(group.getCheckedRadioButtonId() == R.id.radioButton2){ //check if the button checked is the eaten button
			eaten = true;
		}else{
			eaten = false;
		}
		
		//Remove from expire list and be happy
		try {
			DataClient.getInstance().removeFridgeFood(food, eaten);
		} catch (Exception e) {
			Toast.makeText( toShopping.getContext(),
					"Connection error occurred", Toast.LENGTH_SHORT);
		}
		
		//log action
		finish();
	}
	
	private void addToShoppingList() {
		ShoppingItem x = new ShoppingItem(food.getDescription());
		DataClient.getInstance().getShoppingList().add(x);
		try {
			DataClient.getInstance().pushShoppingItem(x);
		} catch (IOException e) {
			Toast.makeText(getBaseContext(), "Communication Error",
					Toast.LENGTH_SHORT).show();
		}
		DataClient.getInstance().reloadFoods();
	}

	public void CancelClick(View view){
		finish();
	}
}
