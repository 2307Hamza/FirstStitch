package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private ConstraintLayout registerBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout mHeaderLayout;
    private ImageView mHeaderImage;

    private EditText emails;
    private EditText passwords;
    private EditText emailr;
    private EditText passwordr;
    private EditText passwordrer;
    private Button signin;
    private Button register;
    private RadioButton customer;
    private RadioButton tailor;
    private RadioButton admin;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference referenceUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_sign_in);
        FullScreencall();

        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        emails=findViewById(R.id.emails);
        emailr=findViewById(R.id.emailr);
        passwords=findViewById(R.id.passwords);
        passwordr=findViewById(R.id.passwordr);
        passwordrer=findViewById(R.id.passwordrer);
        signin=findViewById(R.id.buttonSignin);
        register=findViewById(R.id.buttonRegister);
        customer=findViewById(R.id.radiocustomer);
        tailor=findViewById(R.id.radiotailor);
        admin=findViewById(R.id.radioadmin);
        customer.setChecked(true);
        tailor.setChecked(false);
        admin.setChecked(false);


        registerBottomSheet=findViewById(R.id.registerbottomsheet);
        bottomSheetBehavior=BottomSheetBehavior.from(registerBottomSheet);
        mHeaderLayout = findViewById(R.id.header_layout);
        mHeaderImage = findViewById(R.id.header_arrow);
        mHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mHeaderImage.setRotation(slideOffset * 180);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign in code
                if(TextUtils.isEmpty(emails.getText()) || TextUtils.isEmpty(passwords.getText())){
                    Toast.makeText(getApplicationContext(),"Please input all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emails.getText().toString(), passwords.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    g.setUserID(user.getUid().toString());

                                   /* String tmpID = dataSnapshot.getValue(String.class);
                                    if(g.getUserID().toString().compareTo(tmpID)==0){
                                        g.setUserType("customer");
                                        g.setCustomerID(g.getUserID());

                                        Toast.makeText(getApplicationContext(),"Welcome customer.", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(SignIn.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        return;*/

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
                                                Intent intent=new Intent(SignIn.this,MainActivity.class);
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
                                                Intent intent=new Intent(SignIn.this,MainActivityTailor.class);
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
                                                Intent intent=new Intent(SignIn.this,MainActivityAdmin.class);
                                                startActivity(intent);
                                                finish();
                                                return;
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });

                                } else {
                                    Toast.makeText(SignIn.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }

                                // ...
                           }
                        });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register code
                if(TextUtils.isEmpty(emailr.getText()) || TextUtils.isEmpty(passwordr.getText()) || TextUtils.isEmpty(passwordrer.getText())){
                    Toast.makeText(getApplicationContext(),"Please input all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(passwordr.getText().toString().compareTo(passwordrer.getText().toString())!=0){
                    Toast.makeText(getApplicationContext(),"Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailr.getText().toString(), passwordr.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    g.setIsEditing(false);
                                    g.setUserID(user.getUid().toString());
                                    if(customer.isChecked()){
                                        g.setUserType("customer");
                                        g.setCustomerID(g.getUserID());
                                    }else if(tailor.isChecked()){
                                        g.setUserType("tailor");
                                        g.setTailorID(g.getUserID());
                                    }else if(admin.isChecked()){
                                        g.setUserType("admin");
                                        g.setAdminID(g.getUserID());
                                    }

                                    Intent intent=new Intent(SignIn.this,profile.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(SignIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.setChecked(true);
                tailor.setChecked(false);
                admin.setChecked(false);
            }
        });

        tailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.setChecked(false);
                tailor.setChecked(true);
                admin.setChecked(false);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.setChecked(false);
                tailor.setChecked(false);
                admin.setChecked(true);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();

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


                                Toast.makeText(getApplicationContext(),"Welcome customer.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SignIn.this,MainActivity.class);
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
                                Toast.makeText(getApplicationContext(),"Welcome tailor.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SignIn.this,MainActivityTailor.class);
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
                                Toast.makeText(getApplicationContext(),"Welcome admin.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SignIn.this,MainActivityAdmin.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        }*/

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