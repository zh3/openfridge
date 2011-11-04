package com.openfridge;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ExpireActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expire);
	}
	
	public void EditPostponeClick(View view){
		startActivity(MainMenuActivity.itemEdit);
	}
	
	public void DoneClick(View view){
		startActivity(MainMenuActivity.expirationList);
	}
	
	public void CancelClick(View view){
		startActivity(MainMenuActivity.expirationList);
	}
}
