package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        MaterialButton registerButton=findViewById(R.id.addButton);
        final TextInputEditText emailEditText= findViewById(R.id.emailEditText);
        final TextInputEditText passwordEditText= findViewById(R.id.passwordEditText);
        final TextInputEditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = emailEditText.getText().toString();
                String password_txt =
                        passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                if (TextUtils.isEmpty(email_txt) ||
                        TextUtils.isEmpty(password_txt)) {
                    String msg = "Empty Username or Password";
                    toastMsg(msg);
                } else if (password_txt.length() < 6) {
                    String msg = "Password must be at least 6 characters.";
                    toastMsg(msg);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email_txt).matches()) {
                    String msg = "Invalid email address.";
                    toastMsg(msg);
                } else if (!password_txt.matches("(.*[A-Z].*)")) {
                    String msg = "Password must contain at least one capital letter.";
                    toastMsg(msg);
                } else if (!password_txt.equals(confirmPassword)) {
                    String msg = "Passwords must match.";
                    toastMsg(msg);
                } else
                    registerUser(email_txt, password_txt);
            }
        });
    }
    private void registerUser(String email_txt, String password_txt) {
        // To create username and password

        auth.createUserWithEmailAndPassword(email_txt,password_txt).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String msg = "Registration Successful";
                            toastMsg(msg);
                            startActivity(new Intent(Register.this,
                                    Login.class));
                        }else {
                            String msg = "Registration Unsuccessful";
                            toastMsg(msg);
                        }
                    }
                });
    }
    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}