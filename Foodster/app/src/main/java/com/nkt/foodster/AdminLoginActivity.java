package com.nkt.foodster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import static com.nkt.foodster.MainActivity.MyPREFERENCES;

public class AdminLoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btn_login = findViewById(R.id.login);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        btn_login.setOnClickListener(v->{
            Log.d("TAG","Helloo"+usernameEditText.getText());
            Log.d("TAG","Helloo"+passwordEditText.getText());
            if(usernameEditText.getText().toString().equals("")&&passwordEditText.getText().toString().equals("")){
                Toast.makeText(this, "Correct credentials", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isAdmin",true);
                editor.putBoolean("is_really_admin",true);
                editor.apply();
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
