package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExpireActivity extends Activity {
	
	private Intent expirationList, itemEdit;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        expirationList = new Intent(this, ExpirationListActivity.class);
        itemEdit = new Intent(this, ItemEditActivity.class);

        setContentView(R.layout.expire);
	}
	
	public void EditPostponeClick(View view){
		startActivity(itemEdit);
	}
	
	public void DoneClick(View view){
		startActivity(expirationList);
	}
	
	public void CancelClick(View view){
		startActivity(expirationList);
	}
}
