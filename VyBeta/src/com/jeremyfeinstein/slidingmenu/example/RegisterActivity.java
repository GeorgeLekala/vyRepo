package com.jeremyfeinstein.slidingmenu.example;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class RegisterActivity extends Activity{

	public SharedPreferences userpreferences;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        // Set View to register.xml
        setContentView(R.layout.register);
        
        Button buttonRegister = (Button)findViewById(R.id.btnnextreg);
        final EditText fname = (EditText) findViewById(R.id.reg_txtfname);
        final EditText lname = (EditText) findViewById(R.id.reg_txtlname);
        final EditText txtemail = (EditText) findViewById(R.id.reg_txtemail);
        final EditText password = (EditText) findViewById(R.id.reg_txtpassword);
        final EditText phonenumber = (EditText) findViewById(R.id.reg_txtphone);
        
		try {

			userpreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);	
		 
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(),"Error in MobileServiceClient: "+e1.getMessage(),Toast.LENGTH_LONG).show();
		}

	    buttonRegister.setOnClickListener(new View.OnClickListener() {
		
            public void onClick(View arg0) {
     
        		try {
        			
        			userpreferences.edit().putString("firstname_session", fname.getText().toString()).commit();
    		        userpreferences.edit().putString("lasttname_session", lname.getText().toString()).commit();
    		        userpreferences.edit().putString("email_session", txtemail.getText().toString()).commit();
    		        userpreferences.edit().putString("password_session",SHA1(password.getText().toString())).commit();
    		        userpreferences.edit().putString("phonenumber_session", phonenumber.getText().toString()).commit();
        			
	    			Intent i = new Intent(getApplicationContext(), CreditCard.class);
	                startActivity(i);
	                finish();
    	        			
            			
        		} catch (Exception e) {
        			Toast.makeText(getApplicationContext(),"Error in Registration: "+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        		}
            	
            }
	});
	
	    
	}

	  
	  public static String convertToHex(byte[] data)
	  {
			StringBuilder buf = new StringBuilder();
			for(byte b:data)
			{
				int halfbyte = (b>>>4) & 0x0F;
			    int two_halfs = 0;
			    
			    do{
			    	buf.append((0<=halfbyte)&&(halfbyte<=9)?(char)('0'+halfbyte):(char)('a'+(halfbyte-10)));
			    	halfbyte = b & 0x0F;
			    }while(two_halfs++ < 1);
			}
			return buf.substring(0,buf.length()-2);
	   }
	  
	  public static String SHA1(String text) throws NoSuchAlgorithmException,UnsupportedEncodingException{
		
		  MessageDigest md = MessageDigest.getInstance("SHA-1");
		  md.update(text.getBytes("iso-8859-1"),0,text.length());
		  byte[] sha1hash = md.digest();
		  return convertToHex(sha1hash);
	  }
	  
	  
}