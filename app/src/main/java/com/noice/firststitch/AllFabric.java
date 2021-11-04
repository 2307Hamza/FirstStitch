package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class AllFabric extends AppCompatActivity {

    private List<Fabric> fabricList;
    private RecyclerView fabricRV;
    private FabricWholeAdaptor fabricWholeAdaptor;
    FirebaseDatabase database;
    DatabaseReference referenceUser;
    DatabaseReference referenceFabric;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_all_fabric);
        FullScreencall();

        database= FirebaseDatabase.getInstance();
        referenceUser = database.getReference("Customer").child(g.getCustomerID());
        referenceFabric = database.getReference("Fabric");

        fabricList=new ArrayList<>();
        //fillFabricList();
        //fabricList = ((MainActivityAdmin)getActivity()).fillFabricList();
        fabricRV= (RecyclerView) findViewById(R.id.recycleviewfabricscustomerall);

        back=findViewById(R.id.backButton);

        String category= "";
        category=g.getCategory();
        final String finalCategory = category;
        database.getReference("Fabric").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Fabric fabricToAdd=dataSnapshot.getValue(Fabric.class);
                /*fabricList.add(fabricToAdd);
                fabricWholeAdaptor=new FabricWholeAdaptor(fabricList,AllFabric.this);
                RecyclerView.LayoutManager layout= new GridLayoutManager(AllFabric.this,3);
                fabricRV.setLayoutManager(layout);
                fabricRV.setAdapter(fabricWholeAdaptor);*/

                if(finalCategory.compareTo("ladies")==0){
                    if(fabricToAdd.getDescription().toLowerCase().contains("lady") || fabricToAdd.getDescription().toLowerCase().contains("ladies")
                            || fabricToAdd.getDescription().toLowerCase().contains("girl") || fabricToAdd.getDescription().toLowerCase().contains("woman")
                            || fabricToAdd.getDescription().toLowerCase().contains("women") ){
                        fabricList.add(fabricToAdd);
                        fabricWholeAdaptor=new FabricWholeAdaptor(fabricList,AllFabric.this);
                        RecyclerView.LayoutManager layout= new GridLayoutManager(AllFabric.this,3);
                        fabricRV.setLayoutManager(layout);
                        fabricRV.setAdapter(fabricWholeAdaptor);
                    }
                }else if(finalCategory.compareTo("gents")==0){
                    if(fabricToAdd.getDescription().toLowerCase().contains("man") || fabricToAdd.getDescription().toLowerCase().contains("men")
                            || fabricToAdd.getDescription().toLowerCase().contains("boy") || fabricToAdd.getDescription().toLowerCase().contains("gent") ){
                        fabricList.add(fabricToAdd);
                        fabricWholeAdaptor=new FabricWholeAdaptor(fabricList,AllFabric.this);
                        RecyclerView.LayoutManager layout= new GridLayoutManager(AllFabric.this,3);
                        fabricRV.setLayoutManager(layout);
                        fabricRV.setAdapter(fabricWholeAdaptor);
                    }
                }else if(finalCategory.compareTo("kids")==0){
                    if(fabricToAdd.getDescription().toLowerCase().contains("kid") || fabricToAdd.getDescription().toLowerCase().contains("child")){
                        fabricList.add(fabricToAdd);
                        fabricWholeAdaptor=new FabricWholeAdaptor(fabricList,AllFabric.this);
                        RecyclerView.LayoutManager layout= new GridLayoutManager(AllFabric.this,3);
                        fabricRV.setLayoutManager(layout);
                        fabricRV.setAdapter(fabricWholeAdaptor);
                    }
                }else if(finalCategory.compareTo("normal")==0){
                    if(fabricToAdd.getDescription().toLowerCase().contains(" ")){
                        fabricList.add(fabricToAdd);
                        fabricWholeAdaptor=new FabricWholeAdaptor(fabricList,AllFabric.this);
                        RecyclerView.LayoutManager layout= new GridLayoutManager(AllFabric.this,3);
                        fabricRV.setLayoutManager(layout);
                        fabricRV.setAdapter(fabricWholeAdaptor);
                    }
                }
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AllFabric.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


    }

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

    @Override
    protected void onStart() {
        super.onStart();
        FullScreencall();
    }
}