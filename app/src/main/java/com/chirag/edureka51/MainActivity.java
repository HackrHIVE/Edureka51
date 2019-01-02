package com.chirag.edureka51;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences loginCred;
    private String usernamePref,passwordPref;
    private EditText id_view , password_view;
    private AlphaAnimation buttonClick;
    private final String key_USERNAME = "username";
    private final String key_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        id_view = findViewById( R.id.IDview );
        password_view = findViewById( R.id.PASSview );
        loginCred = getSharedPreferences( "login_cred", Context.MODE_PRIVATE );
        boolean exists = checkSharedPref();
        if(exists==false){
            if(loginCred.contains( key_USERNAME )&& loginCred.contains( key_PASSWORD )){
                String userID = loginCred.getString( key_USERNAME,"");
                String userPass = loginCred.getString( key_PASSWORD ,"");
                if(!TextUtils.isEmpty( userID )&& !TextUtils.isEmpty( userPass )){
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }
        else if(exists==true){
            Toast.makeText( this, "Loaded from SharedPreferences\nID : "+usernamePref+"\nPassword : "+passwordPref, Toast.LENGTH_LONG ).show();
        }
        buttonClick = new AlphaAnimation(1.0f, 0.0f);
        buttonClick.setDuration(100);
        buttonClick.setStartOffset(100);
    }

    private boolean checkSharedPref() {
        loginCred = getSharedPreferences( "login_cred", Context.MODE_PRIVATE );
        usernamePref = loginCred.getString( key_USERNAME,"" );
        passwordPref = loginCred.getString( key_PASSWORD,"" );
        if(!TextUtils.isEmpty( usernamePref ) && !TextUtils.isEmpty( passwordPref )){
            id_view.setText( usernamePref );
            password_view.setText( passwordPref );
            return true;
        }
        else
            return false;
    }

    public void SaveToSharedPrefs(View view) {
        view.startAnimation(buttonClick);
        String id = id_view.getText().toString();
        String pass = password_view.getText().toString();
        if(TextUtils.isEmpty( id )&&TextUtils.isEmpty( pass ))
            Toast.makeText( this, "Fill all details!", Toast.LENGTH_SHORT ).show();
        else{
            loginCred.edit().putString( key_USERNAME,id ).commit();
            loginCred.edit().putString( key_PASSWORD,pass ).commit();
            Toast.makeText( this, "Credentials Saved to SharedPreferences\nID : "+id+"\nPassword : "+pass, Toast.LENGTH_SHORT ).show();
        }
    }
}
