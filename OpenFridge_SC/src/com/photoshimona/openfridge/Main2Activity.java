package com.photoshimona.openfridge;

import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        
        final Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View arg0)
            {
                int mul;
                int add;
                EditText mulText = (EditText) findViewById(R.id.multText);
                EditText addText = (EditText) findViewById(R.id.addText);
                mul =  Integer.parseInt(mulText.getText().toString(), 16);
                add = Integer.parseInt(addText.getText().toString(), 16);
                btn.getBackground().setColorFilter(new LightingColorFilter(mul, add));

            }
        });
        
    }
    
}
