package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private StoreFragment storeFragment;
    private ordersFragment ordersFragment;
    private profileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_main);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        FullScreencall();

        mainNav= (BottomNavigationView) findViewById(R.id.main_nav);
        mainFrame= (FrameLayout) findViewById(R.id.mainframe);
        storeFragment=new StoreFragment();
        ordersFragment=new ordersFragment();
        profileFragment=new profileFragment();
        setFragment(storeFragment);

        if(g.isIsGoingToCompleted()){
            g.setIsGoingToCompleted(false);
            setFragment(ordersFragment);
        }



        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemStore :
                        setFragment(storeFragment);
                        return true;

                    case R.id.itemOrders :
                        setFragment(ordersFragment);
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

    public void setSeasonalData(List<Fabric> seasonalfabricList){
        ImageView seasonalImage1;
        ImageView seasonalImage2;
        ImageView seasonalImage3;
        TextView seasonalText1;
        TextView seasonalText2;
        TextView seasonalText3;

        seasonalImage1=(ImageView) findViewById(R.id.imageViewLadies);
        seasonalImage2=(ImageView) findViewById(R.id.imageViewGents);
        seasonalImage3=(ImageView) findViewById(R.id.imageViewKids);
        seasonalText1=findViewById(R.id.textViewSeasonalItem1);
        seasonalText2=findViewById(R.id.textViewSeasonalItem2);
        seasonalText3=findViewById(R.id.textViewSeasonalItem3);
        seasonalText1.setText(seasonalfabricList.get(0).getName());
        seasonalText2.setText(seasonalfabricList.get(1).getName());
        seasonalText3.setText(seasonalfabricList.get(2).getName());
        /*Picasso.get().load(seasonalfabricList.get(0).getImageURL()).transform(new RoundedCornersTransformation(20,0)).into(seasonalImage1);
        Picasso.get().load(seasonalfabricList.get(1).getImageURL()).transform(new RoundedCornersTransformation(20,0)).into(seasonalImage2);
        Picasso.get().load(seasonalfabricList.get(2).getImageURL()).transform(new RoundedCornersTransformation(20,0)).into(seasonalImage3);
        *///Glide.with(MainActivity.this).load(seasonalfabricList.get(0).getImageURL()).into(seasonalImage1);
        Glide.with(this).load(seasonalfabricList.get(1).getImageURL()).into(seasonalImage2);
        Glide.with(this).load(seasonalfabricList.get(2).getImageURL()).into(seasonalImage3);
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe,fragment);
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