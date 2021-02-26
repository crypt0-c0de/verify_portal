package com.example.verifyportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView pName,pEmail, pPhone;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pName = findViewById(R.id.profileFullName);
        pEmail = findViewById(R.id.profileEmail);
        pPhone = findViewById(R.id.profilePhone);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        DocumentReference docRef = fStore.collection("users").document(fAuth.getCurrentUser().getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String fullName = documentSnapshot.getString("firstName") + " " + documentSnapshot.getString("lastName");

                   pName.setText(fullName);
                    pEmail.setText(documentSnapshot.getString("emailAddress"));
                    pPhone.setText(fAuth.getCurrentUser().getPhoneNumber());
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu ){
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.logout_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean OnOptionsItemSelected(@NonNull MenuItem item){
//
//
//        return super.onOptionsItemSelected(item);
//    }
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == R.id.logout){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish();
    }
    return(super.onOptionsItemSelected(item));
}
}


/* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if(item.getItemId() == R.id.logout){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish();
    }
     return(super.onOptionsItemSelected(item));
    }

 */