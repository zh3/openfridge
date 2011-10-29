package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        View expB = findViewById(R.id.Exp_button);
        expB.setBackgroundColor(getExpiredColor());
        
        View shoB = findViewById(R.id.Sho_button);
        shoB.setBackgroundColor(getShoppingColor());
    }
	
	public int getExpiredColor(){
		//TODO Use this method to check the state of expired goods and return the appropriate color to set the button
		
		return Color.parseColor("#00DD00");
	}
	
	public int getShoppingColor(){
		//TODO Use this method to check the state of the shopping list and return the appropriate color to set the button
		
		return Color.parseColor("#FFFFFF");
	}
	
	public void loadExpiration(View view){
		Intent intent = new Intent(this, ExpirationListActivity.class);
		startActivity(intent);
	}
	
	public void loadShopping(View view){
		//TODO Change current activity to ShoppingListActivity
	}
}
