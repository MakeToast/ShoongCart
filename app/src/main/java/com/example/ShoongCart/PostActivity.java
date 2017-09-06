package com.example.ShoongCart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by choeyujin on 2017. 8. 25..
 */

public class PostActivity extends BaseActivity {

    ArrayList<PostData> datas = new ArrayList<PostData>();
    ArrayList<PostData> showdata = new ArrayList<PostData>();
    ListView listview;
    Button register;
    Button show;

    EditText name;
    EditText price;
    EditText latitude;
    EditText longitude;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference Item = database.getReference().child("productsss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        register = (Button) findViewById(R.id.register);
        show = (Button) findViewById(R.id.show);

        name = (EditText)findViewById(R.id.name) ;
        price = (EditText)findViewById(R.id.price);
        latitude = (EditText)findViewById(R.id.latitude) ;
        longitude = (EditText)findViewById(R.id.longitude);

        listview = (ListView) findViewById(R.id.listview);
/*
        Item.child("productsss").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                long price = dataSnapshot.child("price").getValue(Long.class);
                double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                double longitude = dataSnapshot.child("longitude").getValue(Double.class);
                if(name != null){
                    datas.add(new PostData(name, price, latitude, longitude));
                         Log.d("aa", " : no name");
                }
                Log.d("aa", " : "+datas.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
*/

        Item.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String name = singleSnapshot.child("name").getValue(String.class);
                    long price = singleSnapshot.child("price").getValue(Long.class);
                    double latitude = singleSnapshot.child("latitude").getValue(Double.class);
                    double longitude = singleSnapshot.child("longitude").getValue(Double.class);
                    if(name != null){
                        datas.add(new PostData(name, price, latitude, longitude));
                             Log.d("aa", " : no name");
                }
                Log.d("aa", " : "+datas.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        Item.child("productsss").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = dataSnapshot.child("name").getValue(String.class);
                long price = dataSnapshot.child("price").getValue(Long.class);
                double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                double longitude = dataSnapshot.child("longitude").getValue(Double.class);
                if(name != null) {
                    datas.add(new PostData(name, price, latitude, longitude));
                    Log.d("aa", " : no name");
                }
                Log.d("aa", " : "+datas.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
*/
        show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //PostAdapter adapter = new PostAdapter(getLayoutInflater(), datas);
                //listview.setAdapter(adapter);
               // PostAdapter adapter = new PostAdapter(getLayoutInflater(), datas);
                //listview.setAdapter(adapter);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                datas.add(new PostData(name.getText().toString(), Long.parseLong(price.getText().toString()), Double.parseDouble(latitude.getText().toString()), Double.parseDouble(longitude.getText().toString())));
                Item.setValue(datas);
                //datas.clear();
            }
        });
    }
}