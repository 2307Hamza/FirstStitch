package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ConfirmOrder extends AppCompatActivity {

    /*declaring required variables for the activity*/
    private ImageView back;

    private ImageView fabricImage;
    private EditText name;
    private EditText type;
    private EditText cost;
    private String urlFabric = null;

    private EditText arm;
    private EditText shoulder;
    private EditText chest;
    private EditText shirt;
    private EditText waist;
    private EditText leg;
    private EditText instructions;

    private EditText tailor;
    private EditText customer;


    private Button confirmOrder;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*checking and setting theme resource file*/
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_confirm_order);
        FullScreencall();

        /*getting reference from firebase database*/
        database= FirebaseDatabase.getInstance();
        reference = database.getReference("Order");

        /*attaching backend variables to frontend xml with ids*/
        fabricImage=findViewById(R.id.imageViewFabric);
        name=findViewById(R.id.fabricname);
        type=findViewById(R.id.fabrictype);
        cost=findViewById(R.id.fabriccost);

        back=findViewById(R.id.backButton);

        arm=findViewById(R.id.armlength);
        shoulder=findViewById(R.id.shoulderlength);
        chest=findViewById(R.id.chest);
        shirt=findViewById(R.id.shirt);
        waist=findViewById(R.id.waist);
        leg=findViewById(R.id.leg);
        instructions=findViewById(R.id.extrainstructions);

        tailor=findViewById(R.id.tailordetails);
        customer=findViewById(R.id.customerdetails);

        confirmOrder=findViewById(R.id.buttonPlaceOrder);

        //no need we already have objects
        database.getReference("Fabric").child(g.getFabricID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                g.setFabric(dataSnapshot.getValue(Fabric.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*getting all customer data from database*/
        database.getReference("Customer").child(g.getCustomerID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                g.setCustomer(dataSnapshot.getValue(Customer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*setting content/values to resources (images/fields/etc)*/
        fabricImage.setBackgroundResource(R.drawable.framemainbgfill);
        Picasso.get().load(g.getFabric().getImageURL()).transform(new RoundedCornersTransformation(30,0)).into(fabricImage);
        name.setText(g.getFabric().getName());
        type.setText(g.getFabric().getType());
        cost.setText(g.getFabric().getCost());

        arm.setText(g.getSuiteDimensions().getArm());
        shoulder.setText(g.getSuiteDimensions().getShoulder());
        chest.setText(g.getSuiteDimensions().getChest());
        shirt.setText(g.getSuiteDimensions().getShirt());
        waist.setText(g.getSuiteDimensions().getWaist());
        leg.setText(g.getSuiteDimensions().getLeg());
        instructions.setText(g.getSuiteDimensions().getInstructions());

        String tailorString = g.getTailor().getUsername()+ "  ("+g.getTailor().getPhone()+")";
        String customerString = g.getCustomer().getUsername()+ "  ("+g.getCustomer().getPhone()+")";
        tailor.setText(tailorString);
        customer.setText(customerString);

        /*disabling radio and check buttons and fields so customer can only view them and not edit them*/
        fabricImage.setEnabled(false);
        name.setKeyListener(null);
        cost.setKeyListener(null);
        type.setKeyListener(null);
        arm.setKeyListener(null);
        shoulder.setKeyListener(null);
        chest.setKeyListener(null);
        shirt.setKeyListener(null);
        waist.setKeyListener(null);
        leg.setKeyListener(null);
        instructions.setKeyListener(null);
        tailor.setKeyListener(null);
        customer.setKeyListener(null);
        name.setFocusable(false);
        cost.setFocusable(false);
        type.setFocusable(false);
        arm.setFocusable(false);
        shoulder.setFocusable(false);
        chest.setFocusable(false);
        shirt.setFocusable(false);
        waist.setFocusable(false);
        leg.setFocusable(false);
        instructions.setFocusable(false);
        tailor.setFocusable(false);
        customer.setFocusable(false);

        /*back button code for the activity*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConfirmOrder.this,chooseTailor.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        /*confirm button code for the activity*/
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //place order online

                /*getting reference from firebase database*/
                reference = database.getReference("Order");

                /*setting content/values to resources (images/fields/etc)*/
                final Order order=new Order();
                order.setCost(cost.getText().toString());
                order.setCustomer(g.getCustomer());
                order.setDimensions(g.getSuiteDimensions());
                order.setFabric(g.getFabric());
                final String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                order.setOrderID(currentTime);
                order.setStatus("pending");
                order.setTailor(g.getTailor());

                /*Snackbar snackBar = Snackbar .make(v, "Are you sure?", Snackbar.LENGTH_LONG);
                snackBar.setActionTextColor(getResources().getColor(R.color.cmain));
                View snackBarView = snackBar.getView();*/

                /*making and displaying a confirmation dialogue box to the user*/
                MaterialAlertDialogBuilder builder= new MaterialAlertDialogBuilder(ConfirmOrder.this);
                builder.setTitle("Confirm Order");
                builder.setMessage("Are you sure you want to place this order?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"success.", Toast.LENGTH_SHORT).show();
                        reference.child(order.getOrderID()).setValue(order);
                        //Toast.makeText(getApplicationContext(),"order placed", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ConfirmOrder.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"cacncell.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


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