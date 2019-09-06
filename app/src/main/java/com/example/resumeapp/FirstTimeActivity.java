package com.example.resumeapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstTimeActivity extends AppCompatActivity {
    EditText name, school, college, email, phone, qualification, address, objective, c1, c2, c3, github;
    Button submit;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    //private FirebaseAuth firebaseAuth;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkAvailable()!=true){
            Toast.makeText(FirstTimeActivity.this,"You are Offline.Try Again.",Toast.LENGTH_LONG).show();
            finish();
        }
        //setStatusBarTransparent();
        setContentView(R.layout.activity_first_time);


        name = (EditText) findViewById(R.id.name);
        school = (EditText) findViewById(R.id.school);
        college = (EditText) findViewById(R.id.college);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.contact);
        qualification = (EditText) findViewById(R.id.qualification);
        address = (EditText) findViewById(R.id.address);
        objective = (EditText) findViewById(R.id.objective);
        c1 = (EditText) findViewById(R.id.choice1);
        c2 = (EditText) findViewById(R.id.choice2);
        c3 = (EditText) findViewById(R.id.choice3);
        github = (EditText) findViewById(R.id.github);
        submit = (Button) findViewById(R.id.btn_add);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //String TAG;
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(FirstTimeActivity.this, "Successfully signed in with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(FirstTimeActivity.this, "Successfully signed out.", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mAuth.getCurrentUser().getUid()).exists()) {
                    //flag=1;

                    Intent i = new Intent(FirstTimeActivity.this, MainActivity.class);
                    startActivity(i);
                    //finish();
                }else{

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Log.d(TAG, "onClick: Attempting to add object to database.");
                            String Name = name.getText().toString();
                            String Email = email.getText().toString();
                            String Phone = phone.getText().toString();
                            String Address = address.getText().toString();
                            String School = school.getText().toString();
                            String College = college.getText().toString();
                            String C1 = c1.getText().toString();
                            String C2 = c2.getText().toString();
                            String C3 = c3.getText().toString();
                            String Qualiication = qualification.getText().toString();
                            String Objective = objective.getText().toString();
                            String Github = github.getText().toString();
                            if (!Name.equals("") && !Email.equals("") && !Phone.equals("") && !Address.equals("") && !School.equals("") && !College.equals("") && !C1.equals("") && !C2.equals("") && !C3.equals("") && !Qualiication.equals("") && !Objective.equals("") && !Github.equals("")) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userID = user.getUid();
                                myRef.child(userID).child("Person").child("1").setValue(Name);
                                myRef.child(userID).child("Person").child("2").setValue(Email);
                                myRef.child(userID).child("Person").child("3").setValue(Phone);
                                myRef.child(userID).child("Person").child("4").setValue(Address);
                                myRef.child(userID).child("Person").child("7").setValue(School);
                                myRef.child(userID).child("Person").child("8").setValue(College);
                                myRef.child(userID).child("Person").child("9").setValue(C1);
                                myRef.child(userID).child("Person").child("10").setValue(C2);
                                myRef.child(userID).child("Person").child("11").setValue(C3);
                                myRef.child(userID).child("Person").child("6").setValue(Qualiication);
                                myRef.child(userID).child("Person").child("5").setValue(Objective);
                                myRef.child(userID).child("Person").child("12").setValue(Github);
                                Toast.makeText(FirstTimeActivity.this, "Profile Created Successfully", Toast.LENGTH_LONG).show();
                                //reset the text
                                Intent intent = new Intent(FirstTimeActivity.this, MainActivity.class);
                                startActivity(intent);

                            }
                        }

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //finish();


    }
    public boolean isNetworkAvailable(){
        ConnectivityManager cm=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()){
            return true;
        }else {
            return false;
        }
    }
}





