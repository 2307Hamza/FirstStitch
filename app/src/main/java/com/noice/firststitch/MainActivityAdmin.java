package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdmin extends AppCompatActivity {

    /*declaring required variables for the activity*/
    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private fabricsFragment fabricsFragment;
    private usersFragment usersFragment;
    private pendingordersFragment pendingordersFragment;
    private completedordersFragment completedordersFragment;
    private profileFragment profileFragment;

    private List<Fabric> fabricList;
    /*private RecyclerView fabricRV;
    private FabricWholeAdaptor fabricWholeAdaptor;*/
    FirebaseDatabase database;
    DatabaseReference referenceUser;
    DatabaseReference referenceFabric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*checking and setting theme resource file*/
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_main_admin);
        FullScreencall();

        /*attaching backend variables to frontend xml with ids*/
        mainNav= (BottomNavigationView) findViewById(R.id.main_navadmin);
        mainFrame= (FrameLayout) findViewById(R.id.mainframeadmin);
        fabricsFragment=new fabricsFragment();
        usersFragment=new usersFragment();
        pendingordersFragment=new pendingordersFragment();
        completedordersFragment=new completedordersFragment();
        profileFragment=new profileFragment();
        setFragment(fabricsFragment);

        /*navigating to different order type activities*/
        if(g.isIsGoingToCompleted()){
            g.setIsGoingToCompleted(false);
            setFragment(completedordersFragment);
        }else if(g.isIsGoingToPending()){
            g.setIsGoingToPending(false);
            setFragment(pendingordersFragment);
        }

        /*getting reference from firebase database*/
        database=FirebaseDatabase.getInstance();
        referenceUser = database.getReference("Admin").child(g.getAdminID());
        referenceFabric = database.getReference("Fabric");

        fabricList=new ArrayList<>();
        fillFabricList();
        /*fabricRV= (RecyclerView) findViewById(R.id.recycleviewfabricsadmin);
        fabricWholeAdaptor=new FabricWholeAdaptor(this,fabricList);
        RecyclerView.LayoutManager layout= new LinearLayoutManager(MainActivityAdmin.this);
        fabricRV.setLayoutManager(layout);
        fabricRV.setAdapter(fabricWholeAdaptor);*/


        /*navigation buttons listener code for the activity*/
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemFabric :
                        setFragment(fabricsFragment);
                        return true;

                    case R.id.itemUsers :
                        setFragment(usersFragment);
                        return true;

                    case R.id.itemPendingOrders :
                        setFragment(pendingordersFragment);
                        return true;

                    case R.id.itemCompletedOrders :
                        setFragment(completedordersFragment);
                        return true;

                    case R.id.itemMe :
                        setFragment(profileFragment);
                        return true;

                    default:
                        return false;

                }
            }
        });

    }

    /*getting all fabrics data from database*/
    public List<Fabric> fillFabricList() {
        referenceFabric.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Fabric fabricToAdd=dataSnapshot.getValue(Fabric.class);
                fabricList.add(fabricToAdd);
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
        return fabricList;
    }


    /*setting fragment for the parent activity*/
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframeadmin,fragment);
        fragmentTransaction.commit();
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