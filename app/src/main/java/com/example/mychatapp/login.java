package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    Button button;
    EditText email,password;
    FirebaseAuth auth;
    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        button=findViewById(R.id.logbutton);
        email=findViewById(R.id.editTextlogEmail);
        password=findViewById(R.id.editTextlogPassword);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email= email.getText().toString();
                String pass= password.getText().toString();

                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(login.this, "Enter The Email.", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(pass)){
                    Toast.makeText(login.this, "Enter The Password.", Toast.LENGTH_SHORT).show();
                }else if (!Email.matches(emailPattern)){
                    email.setError("Provide Valid Email Address.");
                }else if (password.length()<6){
                    password.setError("More than 6 characters needed.");
                    Toast.makeText(login.this, "Password must need longer than six characters.", Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                try {
                                    Intent intent=new Intent(login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }catch (Exception e){
                                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}