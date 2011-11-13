package com.openfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

//TODO Make the add to list push to shopping list  EL
//DONE Change add to list to add to shopping list  FR
//TODO Log action (Thrown/Eaten)                   EL
//DONE Thrown/Eaten should delete from web         FR

public class ExpireActivity extends Activity {
	private FridgeFood food;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expire);

		food = FridgeFood.getFoodFromBundle(getIntent().getExtras());

		TextView fdTV = (TextView) findViewById(R.id.foodDescription);
		fdTV.setText(food.getDescription() + ":"
				+ Integer.toString(food.getId()));
	}

	public void EditPostponeClick(View view) {
		Intent itemEdit = new Intent(this, ItemEditActivity.class);
		itemEdit.putExtras(food.bundle());
		startActivity(itemEdit);
		finish();
	}

	public void DoneClick(View view) {
		CheckBox toShopping = (CheckBox) findViewById(R.id.checkBox1);

		if (toShopping.isChecked()) {
			DataClient.getInstance().doNetOp(this, NetOp.PUSH,
					new ShoppingItem(food.getDescription()));
			DataClient.getInstance().reloadFoods();
		}

		food.setEaten(((RadioGroup) findViewById(R.id.radioGroup1))
				.getCheckedRadioButtonId() == R.id.radioButton2);
		DataClient.getInstance().doNetOp(this, NetOp.REMOVE, food);

		finish();
	}

	public void CancelClick(View view) {
		finish();
	}
}
