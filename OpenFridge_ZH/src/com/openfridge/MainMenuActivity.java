package com.openfridge;

import android.app.Activity;
import android.content.Intent;
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
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateColors();
    }

	public void updateColors() {
        View expB = findViewById(R.id.Exp_button);
        expB.setBackgroundColor(DataClient.getInstance().getExpirationListColor());
        
        View shoB = findViewById(R.id.Sho_button);
        shoB.setBackgroundColor(DataClient.getInstance().getShoppingListColor());
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
 