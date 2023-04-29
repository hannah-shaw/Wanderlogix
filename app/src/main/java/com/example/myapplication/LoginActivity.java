package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        //TextInputLayout textInputLayout = findViewById(R.id.text_input_layout01);
        final TextInputEditText emailEditText = findViewById(R.id.emailEditText);
        final TextInputEditText passwordEditText = findViewById(R.id.passwordEditText);
        MaterialButton registerButton =findViewById(R.id.signupButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));
            }
        }
        );
        Button loginButton =findViewById(R.id.signinButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_Email = emailEditText.getText().toString();
                String txt_Pwd = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(txt_Email) ||
                        TextUtils.isEmpty(txt_Pwd)) {
                    String msg = "Empty Username or Password";
                    toastMsg(msg);
                } else if (txt_Pwd.length() < 6) {
                    String msg = "Password must be at least 6 characters.";
                    toastMsg(msg);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_Email).matches()) {
                    String msg = "Invalid email address.";
                    toastMsg(msg);
                } else if (!txt_Pwd.matches("(.*[A-Z].*)")) {
                    String msg = "Password must contain at least one capital letter.";
                    toastMsg(msg);
                } else
                    loginUser(txt_Email,txt_Pwd);
            }


        });
    }
    private void loginUser(String txt_email, String txt_pwd) {
        // call the object and provide it with email id and password
        auth.signInWithEmailAndPassword(txt_email,
                txt_pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String msg = "Login Successful";
                toastMsg(msg);
                startActivity(new Intent(LoginActivity.this,
                        AddTravelDiaryActivity.class));
            }
        });

        if(!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
            String msg = "Invalid email address.";
            toastMsg(msg);
        }
        auth.signInWithEmailAndPassword(txt_email, txt_pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            String msg = "Login Successful";
                            toastMsg(msg);
                            startActivity(new Intent(LoginActivity.this,
                                    AddTravelDiaryActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            String msg = "The password or email address is incorrect.";
                            toastMsg(msg);
                        }
                    }
                });

    }
    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}