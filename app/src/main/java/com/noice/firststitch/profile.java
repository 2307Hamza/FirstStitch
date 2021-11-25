package com.noice.firststitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity {
    /*declaring required variables for the activity*/
    private EditText username;
    private EditText phone;
    private EditText address;
    private Button save;
    private RelativeLayout specialitySection;
    private CheckBox ladies;
    private CheckBox gents;
    private CheckBox children;

    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*checking and setting theme resource file*/
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_profile);
        FullScreencall();

        /*attaching backend variables to frontend xml with ids*/
        username=findViewById(R.id.username);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);
        save=findViewById(R.id.save);
        specialitySection=findViewById(R.id.specialitySection);
        ladies=findViewById(R.id.checkboxladies);
        gents=findViewById(R.id.checkboxgents);
        children=findViewById(R.id.checkboxchildren);

        database=FirebaseDatabase.getInstance();

        /*getting user relevant reference from firebase database*/
        if(g.getUserType().compareTo("tailor") == 0){
            specialitySection.setVisibility(View.VISIBLE);
            reference = database.getReference("Tailor").child(g.getTailorID());
        }else if(g.getUserType().compareTo("customer") == 0){
            reference = database.getReference("Customer").child(g.getCustomerID());
        }else if(g.getUserType().compareTo("admin") == 0){
            reference = database.getReference("Admin").child(g.getAdminID());
        }

        /*disabling/enabling fields for relevant users*/
        if(g.isIsEditing()){
            g.setIsEditing(false);
            if(g.getUserType().compareTo("tailor") == 0){
                //bind data to fields
                username.setText(g.getTailor().getUsername());
                phone.setText(g.getTailor().getPhone());
                address.setText(g.getTailor().getAddress());

                int specialityCode = g.getTailor().getSpeciality();
                if(specialityCode==0){
                    ladies.setChecked(false);
                    gents.setChecked(false);
                    children.setChecked(false);
                }else if(specialityCode==1){
                    ladies.setChecked(true);
                    gents.setChecked(false);
                    children.setChecked(false);
                }else if(specialityCode==2){
                    ladies.setChecked(false);
                    gents.setChecked(true);
                    children.setChecked(false);
                }else if(specialityCode==3){
                    ladies.setChecked(false);
                    gents.setChecked(false);
                    children.setChecked(true);
                }else if(specialityCode==4){
                    ladies.setChecked(true);
                    gents.setChecked(true);
                    children.setChecked(false);
                }else if(specialityCode==5){
                    ladies.setChecked(true);
                    gents.setChecked(false);
                    children.setChecked(true);
                }else if(specialityCode==6){
                    ladies.setChecked(false);
                    gents.setChecked(true);
                    children.setChecked(true);
                }else if(specialityCode==7){
                    ladies.setChecked(true);
                    gents.setChecked(true);
                    children.setChecked(true);
                }

            }else if(g.getUserType().compareTo("customer") == 0){
                //bind data to fields
                username.setText(g.getCustomer().getUsername());
                phone.setText(g.getCustomer().getPhone());
                address.setText(g.getCustomer().getAddress());
            }else if(g.getUserType().compareTo("admin") == 0){
                //bind data to fields
                username.setText(g.getAdmin().getUsername());
                phone.setText(g.getAdmin().getPhone());
                address.setText(g.getAdmin().getAddress());
            }
        }

        /*save button code for the activity*/
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*checking if the input fields are empty and warning user about them*/
                if(TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(phone.getText()) || TextUtils.isEmpty(username.getText())){
                    Toast.makeText(getApplicationContext(),"Please input all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(g.getUserType().compareTo("tailor") == 0){

                    Tailor tailor=new Tailor();
                    tailor.setTailorID(g.getTailorID());
                    tailor.setUsername(username.getText().toString());
                    tailor.setPhone(phone.getText().toString());
                    tailor.setAddress(address.getText().toString());

                    if(ladies.isChecked() && !gents.isChecked() && !children.isChecked()){
                        tailor.setSpeciality(1);
                    } else if(!ladies.isChecked() && gents.isChecked() && !children.isChecked()){
                        tailor.setSpeciality(2);
                    } else if(!ladies.isChecked() && !gents.isChecked() && children.isChecked()){
                        tailor.setSpeciality(3);
                    } else if(ladies.isChecked() && gents.isChecked() && !children.isChecked()){
                        tailor.setSpeciality(4);
                    } else if(ladies.isChecked() && !gents.isChecked() && children.isChecked()){
                        tailor.setSpeciality(5);
                    } else if(!ladies.isChecked() && gents.isChecked() && children.isChecked()){
                        tailor.setSpeciality(6);
                    } else if(ladies.isChecked() && gents.isChecked() && children.isChecked()){
                        tailor.setSpeciality(7);
                    }else if(!ladies.isChecked() && !gents.isChecked() && !children.isChecked()){
                        tailor.setSpeciality(0);
                    }

                    reference.setValue(tailor);

                    /*storing tailor data to database*/
                    reference=database.getReference("Usertype").child(g.getTailorID());
                    reference.setValue("tailor");

                    g.setTailor(tailor);

                    //Toast.makeText(getApplicationContext(),"Welcome "+ tailor.getUsername(), Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(profile.this,MainActivityTailor.class);
                    startActivity(intent);
                    finish();
                    return;

                }else  if(g.getUserType().compareTo("customer") == 0){
                    Customer customer=new Customer();
                    customer.setCustomerID(g.getCustomerID());
                    customer.setUsername(username.getText().toString());
                    customer.setPhone(phone.getText().toString());
                    customer.setAddress(address.getText().toString());

                    reference.setValue(customer);

                    /*storing customer data to database*/
                    reference=database.getReference("Usertype").child(g.getCustomerID());
                    reference.setValue("customer");

                    g.setCustomer(customer);

                    //Toast.makeText(getApplicationContext(),"Welcome "+customer.getUsername(), Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(profile.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }else  if(g.getUserType().compareTo("admin") == 0){
                    Admin admin=new Admin();
                    admin.setAdminID(g.getCustomerID());
                    admin.setUsername(username.getText().toString());
                    admin.setPhone(phone.getText().toString());
                    admin.setAddress(address.getText().toString());

                    reference.setValue(admin);

                    /*storing admin data to database*/
                    reference=database.getReference("Usertype").child(g.getAdminID());
                    reference.setValue("admin");

                    g.setAdmin(admin);

                    //Toast.makeText(getApplicationContext(),"Welcome "+admin.getUsername(), Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(profile.this,MainActivityAdmin.class);
                    startActivity(intent);
                    finish();
                    return;
                }

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