package com.example.resumeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;

    //private ListView list;

    //private ArrayList<String> userDetail=new ArrayList<String>();
    private Uri filePath;
    private Button btnUpload;
    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;
    CircleImageView imageView;

    private String url;


    //TextView pName;
    int flag=0;

    private TextView name,address,college,objective,qualification,eCollege,school,skill1,skill2,skill3,email,github,phone;
    private Button edit,explore;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.add);
        college = (TextView) findViewById(R.id.college);
        objective = (TextView) findViewById(R.id.object);
        qualification = (TextView) findViewById(R.id.qalify);
        eCollege = (TextView) findViewById(R.id.eCollege);
        school = (TextView) findViewById(R.id.school);
        skill1 = (TextView) findViewById(R.id.tskill);
        skill2 = (TextView) findViewById(R.id.tsskill);
        skill3 = (TextView) findViewById(R.id.ttskill);
        github = (TextView) findViewById(R.id.git);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.ph);

        edit = (Button) findViewById(R.id.edit);
        explore=(Button)findViewById(R.id.explore);

        btnUpload = (Button) findViewById(R.id.upload);
        imageView = (CircleImageView) findViewById(R.id.pImage);

        mAuth = FirebaseAuth.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Toast.makeText(MainActivity.this, "onAuthStateChanged:signed_out", Toast.LENGTH_SHORT).show();
                    //toastMessage("Successfully signed out.");
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

                // ...
            }
        };

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();

            }
        });

        final FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();


        myRef = mFirebaseDatabase.getReference().child(userID).child("Person");

        //list=(ListView)findViewById(R.id.list);

        //final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,userDetail);
        //list.setAdapter(arrayAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Name = dataSnapshot.child("1").getValue().toString();
                String Address = dataSnapshot.child("4").getValue().toString();
                String College = dataSnapshot.child("8").getValue().toString();
                String Objective = dataSnapshot.child("5").getValue().toString();
                String Qualification = dataSnapshot.child("6").getValue().toString();
                String School = dataSnapshot.child("7").getValue().toString();
                String Skill1 = dataSnapshot.child("9").getValue().toString();
                String Skill2 = dataSnapshot.child("10").getValue().toString();
                String Skill3 = dataSnapshot.child("11").getValue().toString();
                String Github = dataSnapshot.child("12").getValue().toString();
                String Email = dataSnapshot.child("2").getValue().toString();
                String Phone = dataSnapshot.child("3").getValue().toString();
                name.setText(Name);
                college.setText(College);
                address.setText(Address);
                objective.setText(Objective);
                qualification.setText(Qualification);
                eCollege.setText(College);
                school.setText(School);
                skill1.setText(Skill1);
                skill2.setText(Skill2);
                skill3.setText(Skill3);
                github.setText(Github);
                email.setText(Email);
                phone.setText(Phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("Name", name.getText().toString());
                intent.putExtra("Phone", phone.getText().toString());
                intent.putExtra("Email", email.getText().toString());
                intent.putExtra("Address", address.getText().toString());
                intent.putExtra("College", college.getText().toString());
                intent.putExtra("School", school.getText().toString());
                intent.putExtra("Qualification", qualification.getText().toString());
                intent.putExtra("Skill1", skill1.getText().toString());
                intent.putExtra("Skill2", skill2.getText().toString());
                intent.putExtra("Skill3", skill3.getText().toString());
                intent.putExtra("Objective", objective.getText().toString());
                intent.putExtra("Github", github.getText().toString());
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        storageReference = storage.getReference().child("ProfileImages");
        //storageReference.child("ProfileImages").child(mAuth.getCurrentUser().getUid()).child(System.currentTimeMillis()+"."+getFilleExtension(filePath)).getDownloadUrl();

        //final File localFile = File.createTempFile("images", "jpg");


        //StorageReference pick = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/resumedemo-f1c34.appspot.com/o/ProfileImages%2F%2FProfileImages%2F"+mAuth.getCurrentUser().getUid()+"%2F"+"1562940913969.jpg"+"?alt=media&token=6baef119-44fd-402a-800a-517c156edd78");
        //Task<Uri> downloadUrl = pick.getDownloadUrl();
        //String img=downloadUrl.toString();
        //Toast.makeText(MainActivity.this,img,Toast.LENGTH_LONG).show();
        //Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).into(image );

        url = "https://firebasestorage.googleapis.com/v0/b/resumedemo-f1c34.appspot.com/o/ProfileImages%2FProfileImages%2F"+mAuth.getCurrentUser().getUid()+"%2F"+"image.jpg";

        StringRequest stringRequest=new StringRequest (StringRequest.Method.GET, url, new Response.Listener<String> () {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject parentObject=new JSONObject (response);

                    //Toast.makeText(MainActivity.this,parentObject.getString("downloadTokens"),Toast.LENGTH_LONG).show();

                    Picasso.with(MainActivity.this).load(url+"?alt=media&token="+parentObject.getString("downloadTokens")).fit().centerCrop()
                            .placeholder(R.drawable.ic_account_circle)
                            .into(imageView);


                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }




            }
        },
                new Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });
        RequestQueue requestQueue= Volley.newRequestQueue (MainActivity.this);
        requestQueue.add(stringRequest);

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Wait Some Days...We Start Explore Option from Next Update",Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        uploadImage();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();

        }
    }


    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = storageReference.child("ProfileImages/").child(mAuth.getCurrentUser().getUid()).child("image.jpg");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();


                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody="Download Our Another App, Link Below : https://play.google.com/store/apps/details?id=com.apps.myapp";
                String shareSubject="Students and Competetive Exam Aspirants first choice app 'examChamp', Download Now , Link Below, Helpful in every Exams in India";

                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);

                startActivity(Intent.createChooser(sharingIntent,"Share Using : "));
                break;

            case R.id.feedback:
                Intent intent=new Intent(MainActivity.this,FeedbackActivity.class);
                startActivity(intent);
                break;

            case R.id.rate:
                Intent intent1=new Intent(MainActivity.this,RateUsActivity.class);
                startActivity(intent1);
                break;



            case R.id.action_logout:
                try {
                    mAuth.signOut();
                    Toast.makeText(this, "User Sign out!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }catch (Exception e) {
                    Toast.makeText(MainActivity.this, "onClick: Exception "+e.getMessage(),Toast.LENGTH_SHORT ).show();
                }
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
