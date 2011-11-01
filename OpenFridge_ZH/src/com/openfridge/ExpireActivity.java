package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExpireActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expire);
	}
	
	public void EditPostponeClick(View view){
		Intent intent = new Intent(this, ItemEditActivity.class);
		startActivity(intent);
	}
	
	public void DoneClick(View view){
		Intent intent = new Intent(this, ExpirationListActivity.class);
		startActivity(intent);
	}
	
	public void CancelClick(View view){
		Intent intent = new Intent(this, ExpirationListActivity.class);
		startActivity(intent);
	}
}
