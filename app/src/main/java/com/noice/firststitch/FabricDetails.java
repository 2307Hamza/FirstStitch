package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.printservice.CustomPrinterIconCallback;
import android.renderscript.Script;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static java.text.DateFormat.getDateTimeInstance;

public class FabricDetails extends AppCompatActivity {

    private ImageView fabricImage;
    private EditText name;
    private EditText type;
    private EditText color;
    private EditText cost;
    private EditText description;
    private CheckBox isSeasonal;
    private CheckBox isLatest;
    private Uri uriFabric = null;
    private String urlFabric = null;
    private Button save;
    private ImageView back;

    private StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_fabric_details);
        FullScreencall();

        fabricImage=findViewById(R.id.imageViewFabric);
        name=findViewById(R.id.fabricname);
        type=findViewById(R.id.fabrictype);
        color=findViewById(R.id.fabriccolor);
        cost=findViewById(R.id.fabriccost);
        description=findViewById(R.id.fabricdesc);
        isSeasonal=findViewById(R.id.checkboxseasonal);
        isLatest=findViewById(R.id.checkboxlatest);
        save=findViewById(R.id.buttonSaveFabricDetails);
        back=findViewById(R.id.backButton);
        storageReference = FirebaseStorage.getInstance().getReference().child("FabricImage");
        database=FirebaseDatabase.getInstance();
        reference = database.getReference("Fabric");


        if(g.isIsEditing()){
            g.setIsEditing(false);
            fabricImage.setBackgroundResource(R.drawable.framemainbgfill);
            Picasso.get().load(g.getFabric().getImageURL()).transform(new RoundedCornersTransformation(30,0)).into(fabricImage);
            name.setText(g.getFabric().getName());
            type.setText(g.getFabric().getType());
            color.setText(g.getFabric().getColor());
            cost.setText(g.getFabric().getCost());
            description.setText(g.getFabric().getDescription());
            if(g.getFabric().isSeasonal()){
                isSeasonal.setChecked(true);
            }else{
                isSeasonal.setChecked(false);
            }
            if(g.getFabric().isLatest()){
                isLatest.setChecked(true);
            }else{
                isLatest.setChecked(false);
            }
            save.setText("Update");
        }

        fabricImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FabricDetails.this,MainActivityAdmin.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FullScreencall();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(uriFabric==null || TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(type.getText()) || TextUtils.isEmpty(color.getText())
                        || TextUtils.isEmpty(cost.getText()) || TextUtils.isEmpty(description.getText()) ){
                    Toast.makeText(getApplicationContext(),"Please input all the fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                final Fabric fabric=new Fabric();
                fabric.setName(name.getText().toString());
                fabric.setType(type.getText().toString());
                fabric.setColor(color.getText().toString());
                fabric.setCost(cost.getText().toString());
                fabric.setDescription(description.getText().toString());
                if(isSeasonal.isChecked()){
                    fabric.setSeasonal(true);
                }else{
                    fabric.setSeasonal(false);
                }
                if(isLatest.isChecked()){
                    fabric.setLatest(true);
                }else{
                    fabric.setLatest(false);
                }

                MaterialAlertDialogBuilder builder= new MaterialAlertDialogBuilder(FabricDetails.this);
                builder.setTitle("Confirm Changes");
                builder.setMessage("Are you sure you want to add/update this fabric?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        if(g.isIsEditing()){
                            storageReference.child(currentTime).putFile(uriFabric).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            urlFabric=uri.toString();
                                            fabric.setImageURL(urlFabric);

                                            fabric.setFabricID(g.getFabric().getFabricID());

                                            reference.child(fabric.getFabricID()).setValue(fabric);
                                            Toast.makeText(getApplicationContext(),"Fabric details updated",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(FabricDetails.this,MainActivityAdmin.class);
                                            startActivity(intent);
                                            finish();
                                            return;
                                        }
                                    });
                                }
                            });
                        }else {
                            storageReference.child(currentTime).putFile(uriFabric).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            urlFabric=uri.toString();
                                            fabric.setImageURL(urlFabric);

                                            fabric.setFabricID(currentTime);

                                            reference.child(fabric.getFabricID()).setValue(fabric);
                                            Toast.makeText(getApplicationContext(),"Fabric details added",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(FabricDetails.this,MainActivityAdmin.class);
                                            startActivity(intent);
                                            finish();
                                            return;

                                        }
                                    });
                                }
                            });
                        }
                        g.setIsEditing(false);
                    }
                });
                builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"cacncell.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

                FullScreencall();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data != null && data.getData()!=null){
            uriFabric=data.getData();
            fabricImage.setBackgroundResource(R.drawable.framemainbgfill);
            Picasso.get().load(uriFabric).transform(new RoundedCornersTransformation(30,0)).into(fabricImage);
            //Glide.with(this).load(uriFabric).into(fabricImage);
            //Toast.makeText(getApplicationContext(),imguri.toString(),Toast.LENGTH_SHORT).show();
        }
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

    public void logoutFromApp() {
        //Toast.makeText(getApplicationContext(),"logging out",Toast.LENGTH_SHORT).show();
    }


}