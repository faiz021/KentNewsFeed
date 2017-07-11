package uk.ac.kent.am2230.kentnewsfeed;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editEmail;
    private EditText editPassword;
    private Button btnLogin;
    private TextView textView;
    private TextView textViewForgotPass;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private final static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
                // ...
            }
        };


        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        editEmail = (EditText) findViewById(R.id.loginEmail);
        editPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.BtnLogin);
        textView = (TextView) findViewById(R.id.text_If_Register);
        textViewForgotPass = (TextView) findViewById(R.id.text_forgot_password);

        btnLogin.setOnClickListener(this);
        textView.setOnClickListener(this);
        textViewForgotPass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btnLogin){
            loginUser();
        }

        if(v==textView){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }

        if(v==textViewForgotPass){
            forgotPassword();
        }
    }

    private void loginUser(){
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

        progressDialog.setMessage("Logging you in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed | "+ task.getException().getMessage());
                            Toast.makeText(LoginActivity.this, "Login failed! "+ task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void forgotPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        final EditText inputEmail = new EditText(LoginActivity.this);
        builder.setTitle("Reset Password");
        builder.setMessage("Enter you email address here:");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputEmail.setLayoutParams(lp);
        builder.setView(inputEmail);
        builder.setCancelable(true);


        builder.setPositiveButton(
                "Send Email",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String emailAddress = inputEmail.getText().toString().trim();
                        firebaseAuth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Email sent!", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "Email sent.");
                                        }
                                        if(!task.isSuccessful()){
                                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
        );
        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        builder.show();



    }
}
