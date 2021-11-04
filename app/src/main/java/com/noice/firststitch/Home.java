package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String file="com.noice.firststitch";
    SharedPreferences.Editor editor;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=getSharedPreferences(file,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.apply();
        g.setThemeCode(sharedPreferences.getInt("t",0));
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}

        setContentView(R.layout.activity_home);
        FullScreencall();

        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            g.setUserID(currentUser.getUid().toString());

            String tmp=g.getUserID();
            g.setUserType("");
            database.getReference("Usertype").child(g.getUserID()).
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String userType = dataSnapshot.getValue(String.class);
                            g.setUserType(userType);

                            if(g.getUserType().compareTo("customer")==0){
                                g.setCustomerID(g.getUserID());
                                database.getReference("Customer").child(g.getCustomerID()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        g.setCustomer(dataSnapshot.getValue(Customer.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                //Toast.makeText(getApplicationContext(),"Welcome customer.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Home.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }else if(g.getUserType().compareTo("tailor")==0){
                                g.setTailorID(g.getUserID());
                                database.getReference("Tailor").child(g.getTailorID()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        g.setTailor(dataSnapshot.getValue(Tailor.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                //Toast.makeText(getApplicationContext(),"Welcome tailor.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Home.this,MainActivityTailor.class);
                                startActivity(intent);
                                finish();
                                return;
                            }else if(g.getUserType().compareTo("admin")==0){
                                g.setAdminID(g.getUserID());
                                database.getReference("Admin").child(g.getAdminID()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        g.setAdmin(dataSnapshot.getValue(Admin.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                        Toast.makeText(getApplicationContext(),"error admin.", Toast.LENGTH_SHORT).show();
                                        finish();
                                        return;
                                    }
                                });
                                //Toast.makeText(getApplicationContext(),"Welcome admin.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Home.this,MainActivityAdmin.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        }else{
            //Toast.makeText(getApplicationContext(),"sign in.", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Home.this,SignIn.class);
            startActivity(intent);
            finish();
            return;
        }

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