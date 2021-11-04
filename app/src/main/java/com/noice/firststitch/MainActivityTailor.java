package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityTailor extends AppCompatActivity {

    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private pendingordersFragment pendingordersFragment;
    private completedordersFragment completedordersFragment;
    private profileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_main_tailor);

        FullScreencall();

        mainNav= (BottomNavigationView) findViewById(R.id.main_navtailor);
        mainFrame= (FrameLayout) findViewById(R.id.mainframetailor);
        pendingordersFragment=new pendingordersFragment();
        completedordersFragment=new completedordersFragment();
        profileFragment=new profileFragment();
        setFragment(pendingordersFragment);
        if(g.isIsGoingToCompleted()){
            g.setIsGoingToCompleted(false);
            setFragment(completedordersFragment);
        }else if(g.isIsGoingToPending()){
            g.setIsGoingToPending(false);
            setFragment(pendingordersFragment);
        }

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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
        });mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframetailor,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FullScreencall();
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


}