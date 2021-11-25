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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Dimensions extends AppCompatActivity {

    /*declaring required variables for the activity*/
    private EditText arm;
    private EditText shoulder;
    private EditText chest;
    private EditText shirt;
    private EditText waist;
    private EditText leg;
    private EditText instructions;
    private RadioButton ladies;
    private RadioButton gents;
    private RadioButton kids;
    private CheckBox pocket;
    private CheckBox vneck;
    private Button confirm;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*checking and setting theme resource file*/
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_dimensions);
        FullScreencall();

        /*attaching backend variables to frontend xml with ids*/
        arm=findViewById(R.id.armlength);
        shoulder=findViewById(R.id.shoulderlength);
        chest=findViewById(R.id.chest);
        shirt=findViewById(R.id.shirt);
        waist=findViewById(R.id.waist);
        leg=findViewById(R.id.leg);
        instructions=findViewById(R.id.extrainstructions);
        ladies=findViewById(R.id.forladies);
        gents=findViewById(R.id.forgents);
        kids=findViewById(R.id.forkids);
        pocket=findViewById(R.id.checkboxpockets);
        vneck=findViewById(R.id.checkboxvneck);
        confirm=findViewById(R.id.buttonConfirmDimensions);
        back=findViewById(R.id.backButton);

        suiteDimensions suiteDimensions=new suiteDimensions();

        /*code to enable relative radio and disable others*/
        ladies.setChecked(true);
        ladies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ladies.setChecked(true);
                gents.setChecked(false);
                kids.setChecked(false);
            }
        });
        gents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ladies.setChecked(false);
                gents.setChecked(true);
                kids.setChecked(false);
            }
        });
        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ladies.setChecked(false);
                gents.setChecked(false);
                kids.setChecked(true);
            }
        });

        /*back button code for the activity*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dimensions.this,FabricDetailsCustomer.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        /*confirm button code for the activity*/
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*checking if the input fields are empty and warning user about them*/
                if(TextUtils.isEmpty(arm.getText()) || TextUtils.isEmpty(shoulder.getText()) || TextUtils.isEmpty(chest.getText()) ||
                        TextUtils.isEmpty(shirt.getText()) || TextUtils.isEmpty(waist.getText()) || TextUtils.isEmpty(leg.getText())
                || TextUtils.isEmpty(instructions.getText())){
                    Toast.makeText(getApplicationContext(),"Please inputs all fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                /*setting content/values of dimensions to front end resources (images/fields/etc)*/
                suiteDimensions suiteDimensions=new suiteDimensions();
                suiteDimensions.setArm(arm.getText().toString());
                suiteDimensions.setChest(chest.getText().toString());
                suiteDimensions.setShoulder(shoulder.getText().toString());
                suiteDimensions.setShirt(shirt.getText().toString());
                suiteDimensions.setWaist(waist.getText().toString());
                suiteDimensions.setLeg(leg.getText().toString());
                suiteDimensions.setInstructions(instructions.getText().toString());
                if(ladies.isChecked()){
                    suiteDimensions.setType("ladies");
                }else if(gents.isChecked()){
                    suiteDimensions.setType("gents");
                }else if(kids.isChecked()){
                    suiteDimensions.setType("kids");
                }

                if(pocket.isChecked()){
                    suiteDimensions.setPocket(true);
                }else {
                    suiteDimensions.setPocket(false);
                }
                if(vneck.isChecked()){
                    suiteDimensions.setVneck(true);
                }else {
                    suiteDimensions.setVneck(false);
                }

                g.setSuiteDimensions(suiteDimensions);

                //Toast.makeText(getApplicationContext(),"dimensions entered",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Dimensions.this,chooseTailor.class);
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