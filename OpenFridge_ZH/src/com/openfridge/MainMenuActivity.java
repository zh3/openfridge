package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

//TODO no color icons SC
//TODO Colors need to actually return correctly SC
//TODO Checking database on regular basis  SC/JW
//TODO Come up with way to link to a hidden shopping list    :later
//TODO Optimize accesses to data, get it when we need, and not otherwise   :later
//DONE get data on program start JW
//DONE get data asynchronously JW
//TODO Separate network code, make more locally capable FR JW


public class MainMenuActivity extends Activity {
	private Intent expirationList, shoppingList;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //mainMenu = new Intent(this, MainMenuActivity.class);
        expirationList = new Intent(this, ExpirationListActivity.class);
        //expire = new Intent(this, ExpireActivity.class);
        //itemEdit = new Intent(this, ItemEditActivity.class);
        //shopping = new Intent(this, ShoppingActivity.class);
        shoppingList = new Intent(this, ShoppingListActivity.class);
        
        DataClient.getInstance().reloadFoods(); //to keep the colors up to date
        
        setContentView(R.layout.main);

        View expB = findViewById(R.id.Exp_button);
        expB.setBackgroundColor(getExpiredColor());
        
        View shoB = findViewById(R.id.Sho_button);
        shoB.setBackgroundColor(getShoppingColor());
    }
	
	public int getExpiredColor(){
		//TODO Use this method to check the state of expired goods and return the appropriate color to set the button
		
		return Color.parseColor("#FFD300");
	}
	
	public int getShoppingColor(){
		//TODO Use this method to check the state of the shopping list and return the appropriate color to set the button
		
		return Color.parseColor("#FFFFFF");
	}
	
	public void loadExpiration(View view){
		startActivity(expirationList);
	}
	
	public void loadShopping(View view) {
		startActivity(shoppingList);
	}
}
/* 
 * Notes about what connects to what:
 * 
 * MainMenu -> ExpirationList & ShoppingList (if it has items)
 * ExpirationList -> Expire, ItemEdit (as a new item)
 * Expire -> ExpirationList & ItemEdit (to edit the item in question)
 * ItemEdit -> ExpirationList
 * ShoppingList -> Shopping (adding items handled on-page)
 * Shopping -> ShopingList
 * */
 