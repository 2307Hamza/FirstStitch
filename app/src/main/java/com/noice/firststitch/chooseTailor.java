package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class chooseTailor extends AppCompatActivity {

    /*declaring required variables for the activity*/
    private ImageView back;

    private RecyclerView tailorsRv;
    private List<Tailor> tailorList;
    private TailorlistAdaptor tailorlistAdaptor;
    FirebaseDatabase database;
    DatabaseReference referenceUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*checking and setting theme resource file*/
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_choose_tailor);
        FullScreencall();

        /*getting reference from firebase database*/
        database= FirebaseDatabase.getInstance();
        referenceUser = database.getReference("Customer").child(g.getCustomerID());

        tailorList=new ArrayList<>();

        tailorsRv=(RecyclerView) findViewById(R.id.recycleviewchoosetailors);

        /*fetching tailors data from database*/
        database.getReference("Tailor").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Tailor tailor= dataSnapshot.getValue(Tailor.class);
                tailorList.add(tailor);
                tailorlistAdaptor=new TailorlistAdaptor(tailorList,chooseTailor.this);
                RecyclerView.LayoutManager layout= new LinearLayoutManager(chooseTailor.this);
                tailorsRv.setLayoutManager(layout);
                tailorsRv.setAdapter(tailorlistAdaptor);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*back button code*/
        back=findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chooseTailor.this,Dimensions.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreencall();
    }

    /*function to fullscreen activity*/
    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}