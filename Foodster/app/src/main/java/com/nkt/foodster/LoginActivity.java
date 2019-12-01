package com.nkt.foodster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN = 123;
    static FirebaseAuth mAuth;
    Button btn_login, btn_logout;
    TextView text,text1;
    ImageView image;
    ProgressBar progressBar;
    static GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.login);
        btn_logout = findViewById(R.id.logout);
        text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        image = findViewById(R.id.image);
        progressBar = findViewById(R.id.progress_circular);


        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        btn_login.setOnClickListener(v->SignInGoogle());
        btn_logout.setOnClickListener(v->Logout());

        if(mAuth.getCurrentUser() !=null){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }
        text1.setOnClickListener(v->{
            Intent intent = new Intent(this, AdminLoginActivity.class);
            startActivity(intent);
        });


    }

    void SignInGoogle(){
        progressBar.setVisibility(View.VISIBLE);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GOOGLE_SIGN){
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try{

                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null) {
                    firebaseAuthWithGoogle(account);
                    //make request firebase
                }

            }catch(ApiException e){
                e.printStackTrace();
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG","firebaseAuthWithGoogle: "+ account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d("TAG","sign in success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.w("TAG","sign in failure", task.getException());
                    Toast.makeText(this, "Signin failed!", Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
                });

    }

    private void updateUI(FirebaseUser user) {

        if( user != null){

            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());

            btn_login.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("isAdmin",0);
            startActivity(intent);
        }
        else{
            btn_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);

        }

    }


    public static void Logout(){
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut();
    }



}
