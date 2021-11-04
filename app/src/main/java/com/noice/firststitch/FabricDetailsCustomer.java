package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class FabricDetailsCustomer extends AppCompatActivity {

    private ImageView fabricImage;
    private EditText name;
    private EditText type;
    private EditText color;
    private EditText cost;
    private EditText description;
    private String urlFabric = null;
    private Button select;
    private ImageView back;

    private StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_fabric_details_customer);
        FullScreencall();

        fabricImage=findViewById(R.id.imageViewFabric);
        name=findViewById(R.id.fabricname);
        type=findViewById(R.id.fabrictype);
        color=findViewById(R.id.fabriccolor);
        cost=findViewById(R.id.fabriccost);
        description=findViewById(R.id.fabricdesc);
        select=findViewById(R.id.buttonSelectFabric);
        back=findViewById(R.id.backButton);

        storageReference = FirebaseStorage.getInstance().getReference().child("FabricImage");
        database=FirebaseDatabase.getInstance();
        reference = database.getReference("Fabric");

        database.getReference("Fabric").child(g.getFabricID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                g.setFabric(dataSnapshot.getValue(Fabric.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fabricImage.setBackgroundResource(R.drawable.framemainbgfill);
        Picasso.get().load(g.getFabric().getImageURL()).transform(new RoundedCornersTransformation(30,0)).into(fabricImage);
        name.setText(g.getFabric().getName());
        type.setText(g.getFabric().getType());
        color.setText(g.getFabric().getColor());
        cost.setText(g.getFabric().getCost());
        description.setText(g.getFabric().getDescription());

        fabricImage.setEnabled(false);
        name.setKeyListener(null);
        cost.setKeyListener(null);
        type.setKeyListener(null);
        color.setKeyListener(null);
        description.setKeyListener(null);
        name.setFocusable(false);
        cost.setFocusable(false);
        color.setFocusable(false);
        type.setFocusable(false);
        description.setFocusable(false);
        /*name.requestFocus();
        cost.setHintTextColor(name.getCurrentHintTextColor());
        type.setHintTextColor(name.getCurrentHintTextColor());
        color.setHintTextColor(name.getCurrentHintTextColor());
        description.setHintTextColor(name.getCurrentHintTextColor());
        name.setHintTextColor(name.getCurrentHintTextColor());*/

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"fabric selected",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(FabricDetailsCustomer.this,Dimensions.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(g.isIsComingFromAllFabric()){
                    g.setIsComingFromAllFabric(false);
                    Intent intent=new Intent(FabricDetailsCustomer.this,AllFabric.class);
                    startActivity(intent);
                    finish();
                    return;
                }else {
                    Intent intent=new Intent(FabricDetailsCustomer.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
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