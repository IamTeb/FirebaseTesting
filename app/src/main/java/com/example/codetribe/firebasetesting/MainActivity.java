package com.example.codetribe.firebasetesting;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {
    private ResultsAdapter resultsAdapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    FirebaseDatabase database;

    DatabaseReference myRef ;
    private TextView textV;
    ListView listView;


    private EditText mValueField;
    private Button mAddBtn;
    private  String mUserId;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser() == null)
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        mId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        mValueField = (EditText) findViewById(R.id.testEditText);
        mAddBtn = (Button) findViewById(R.id.send);


        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mValueField.getText().toString();

                String user_id = mFirebaseAuth.getCurrentUser().getUid();

                DatabaseReference current_user_db = myRef.child(user_id);
                current_user_db.child("message").push().setValue(message);

                mUserId = myRef.getRef().child("message").toString();

                mValueField.setText("");

            }

        });



        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String user_id = mFirebaseAuth.getCurrentUser().getUid();
                for (DataSnapshot messageSnapshot :dataSnapshot.getChildren())
                {
                    Query myMessagesQuery = myRef.child("message").child(user_id);
                    myMessagesQuery.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s)
                        {

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s)
                        {


                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot)
                        {


                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s)
                        {


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            myRef.child("users").child(mUserId).child("message").equalTo((String) listView.getItemAtPosition(position))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChildren())
                            {
                                DataSnapshot firstchild = dataSnapshot.getChildren().iterator().next();
                                firstchild.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
           }
       });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_logout)
        {
            mFirebaseAuth.signOut();
            loadLogInView();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void loadLogInView()
    {
        Intent intent =  new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
