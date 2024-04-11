package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class phonenumberverification extends AppCompatActivity {

    ArrayList<EditText> editTexts;
    EditText otpNumberOne,getOtpNumberTwo,getOtpNumberThree,getOtpNumberFour,getOtpNumberFive,otpNumberSix;
    Button verifyPhone,resendOTP;
    Boolean otpValid = true;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    PhoneAuthCredential phoneAuthCredential;
    DatabaseReference usersRef;
    PhoneAuthProvider.ForceResendingToken token;
    String verificationId;
    String  phone , email , password , phonenumber , name, balance;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumberverification);
        TextView tv = findViewById(R.id.photp);
        phone = getIntent().getStringExtra("phonenumber");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        balance = getIntent().getStringExtra("balance");
        String newPhone = hidePhone(String.valueOf(phone));
        phone = "+91 "+phone;
        tv.setText(tv.getText() + " " + newPhone);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        otpNumberOne = findViewById(R.id.otpNumberOne);
        getOtpNumberTwo = findViewById(R.id.optNumberTwo);
        getOtpNumberThree = findViewById(R.id.otpNumberThree);
        getOtpNumberFour = findViewById(R.id.otpNumberFour);
        getOtpNumberFive = findViewById(R.id.otpNumberFive);
        otpNumberSix = findViewById(R.id.optNumberSix);


        editTexts =  new ArrayList<EditText>(Arrays.asList(otpNumberOne ,getOtpNumberTwo ,getOtpNumberThree , getOtpNumberFour , getOtpNumberFive , otpNumberSix));

        setupEditTextListeners();

        verifyPhone = findViewById(R.id.verifyPhoneBTn);
        resendOTP = findViewById(R.id.resendOTP);

        verifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpWithEmailAndPassword(email , password);
                Intent intentti = new Intent(phonenumberverification.this, MainActivity.class);
                startActivity(intentti);

                validateOTPAndVerify();

                /*validateField(otpNumberOne);
                validateField(getOtpNumberTwo);
                validateField(getOtpNumberThree);
                validateField(getOtpNumberFour);
                validateField(getOtpNumberFive);
                validateField(otpNumberSix);

                if(otpValid){
                    // send otp to the user
                    String otp = otpNumberOne.getText().toString()+getOtpNumberTwo.getText().toString()+getOtpNumberThree.getText().toString()+getOtpNumberFour.getText().toString()+
                            getOtpNumberFive.getText().toString()+otpNumberSix.getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,otp);

                    verifyAuthentication(credential);*/

                }
            });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;
                token = forceResendingToken;
                resendOTP.setVisibility(View.GONE);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendOTP.setVisibility(View.VISIBLE);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuthentication(phoneAuthCredential);
                resendOTP.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(phonenumberverification.this, "OTP Verification Failed.", Toast.LENGTH_SHORT).show();
            }
        };

        sendOTP(phone);

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP(phone);
            }
        });

    }

    private void validateOTPAndVerify() {
        String otp = otpNumberOne.getText().toString() +
                getOtpNumberTwo.getText().toString() +
                getOtpNumberThree.getText().toString() +
                getOtpNumberFour.getText().toString() +
                getOtpNumberFive.getText().toString() +
                otpNumberSix.getText().toString();

        if (otp.length() == 6 && verificationId != null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
            verifyAuthentication(credential);
        } else {
            Toast.makeText(this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyAuthentication(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                storeUserDetailsInDatabase(user.getUid(), email);
                                // Sign up the user with email and password in Firebase Authentication
                                signUpWithEmailAndPassword(email, password);
                            } else {
                                Toast.makeText(phonenumberverification.this, "User is null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(phonenumberverification.this, "Authentication failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void signUpWithEmailAndPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User creation success
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            // Now, you can store user details in the Realtime Database
                            storeUserDetailsInDatabase(user != null ? user.getUid() : null, email);
                        } else {
                            // User creation failed
                            Toast.makeText(phonenumberverification.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    // Function to store user details in Realtime Database
    public void storeUserDetailsInDatabase(String userId, String email) {
        HashMap<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password" , password);
        user.put("phonenumber" , phone);
        user.put("name" , name);
        user.put("balance", 0);
        if (userId != null) {
            usersRef.child(userId).setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            // User details stored successfully
                            Toast.makeText(phonenumberverification.this, "User details stored in database", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to store user details
                            Toast.makeText(phonenumberverification.this, "Error storing user details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    public void sendOTP(String phoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,this,mCallbacks);
    }

    public void resendOTP(String phoneNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, TimeUnit.SECONDS,this,mCallbacks,token);
    }


    public void validateField(EditText field){
        if(field.getText().toString().isEmpty()){
            field.setError("Required");
            otpValid = false;
        }else {
            otpValid = true;
        }
    }

//    public void verifyAuthentication(PhoneAuthCredential credential){
//        Toast.makeText(phonenumberverification.this, "Acccount Created and Linked.", Toast.LENGTH_SHORT).show();
//    }
    private void setupEditTextListeners() {
        for (int i = 0; i < editTexts.size(); i++) {
            final int index = i;
            editTexts.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s != null && s.length() == 1) {
                        if (index < editTexts.size() - 1) {
                            editTexts.get(index + 1).requestFocus();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    public String hidePhone(String Phone) {
        int length = Phone.length();
        int toHide = (int) (length * 0.75); 

        // Replace characters with asterisks
        StringBuilder hiddenPhone = new StringBuilder(Phone);
        for (int i = 0; i < toHide; i++) {
            hiddenPhone.setCharAt(i, '*');
        }

        return hiddenPhone.toString();
    }

}