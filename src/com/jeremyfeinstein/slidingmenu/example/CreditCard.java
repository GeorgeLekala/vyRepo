package com.jeremyfeinstein.slidingmenu.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreditCard extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        // Set View to register.xml
        setContentView(R.layout.creditcard);
        
        Button buttonRegister = (Button)findViewById(R.id.btnfinishreg);
        
        buttonRegister.setOnClickListener(new View.OnClickListener() {
    		
            public void onClick(View arg0) {
     
        		try {
        			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
	     
        		} catch (Exception e) {
        			Toast.makeText(getApplicationContext(),"Error in Credit Card: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        		}
            	
            }
	});
	}

}
