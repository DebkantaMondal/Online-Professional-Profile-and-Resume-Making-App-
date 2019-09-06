package com.example.resumeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    EditText name, school, college, email, phone, qualification, address, objective, c1, c2, c3, github;
    Button submit;


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    //private FirebaseAuth firebaseAuth;

    //User user;


    //String Name,School,College,Email,Phone,Qualification,Address,Objective,C1,C2,C3,Github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_profile);

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
                    Toast.makeText(ProfileActivity.this, "Successfully signed in with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(ProfileActivity.this, "Successfully signed out.", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
        FirebaseUser user = mAuth.getCurrentUser();


        Bundle extras=(Bundle)getIntent().getExtras();
        String put_name=extras.getString("Name");
        String put_phone=extras.getString("Phone");
        String put_email=extras.getString("Email");
        String put_address=extras.getString("Address");
        String put_college=extras.getString("College");
        String put_school=extras.getString("School");
        String put_qualify=extras.getString("Qualification");
        String put_skill1=extras.getString("Skill1");
        String put_skill2=extras.getString("Skill2");
        String put_skill3=extras.getString("Skill3");
        String put_obj=extras.getString("Objective");
        String put_github=extras.getString("Github");

        name.setText(put_name);
        phone.setText(put_phone);
        email.setText(put_email);
        address.setText(put_address);
        college.setText(put_college);
        school.setText(put_school);
        qualification.setText(put_qualify);
        c1.setText(put_skill1);
        c2.setText(put_skill2);
        c3.setText(put_skill3);
        objective.setText(put_obj);
        github.setText(put_github);

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
                    Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                    //reset the text

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profilemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Download Our Another App, Link Below : https://play.google.com/store/apps/details?id=com.apps.myapp";
                String shareSubject = "Students and Competetive Exam Aspirants first choice app 'examChamp', Download Now , Link Below, Helpful in every Exams in India";

                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

                startActivity(Intent.createChooser(sharingIntent, "Share Using : "));
                break;


            case R.id.action_logout:
                mAuth.signOut();
                Toast.makeText(ProfileActivity.this,"You're Signed Out!",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
