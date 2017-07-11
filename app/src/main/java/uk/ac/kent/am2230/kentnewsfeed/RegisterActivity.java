package uk.ac.kent.am2230.kentnewsfeed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editEmail;
    private EditText editPassword;
    private Button btnRegister;
    private TextView textView;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private final static String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        editEmail = (EditText) findViewById(R.id.regEmail);
        editPassword = (EditText) findViewById(R.id.regPassword);
        btnRegister = (Button) findViewById(R.id.BtnRegister);
        textView = (TextView) findViewById(R.id.text_If_Login);

        btnRegister.setOnClickListener(this);
        textView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnRegister){
            registerUser();
        }
        if (v == textView) {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    private void registerUser(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                        }

                        if(!task.isSuccessful()){
                            Log.d(TAG, "onComplete: Failed=" + task.getException().getMessage());
                            Toast.makeText(RegisterActivity.this, "Registration failed! Please try again! | "+
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}
