package com.noice.firststitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class OrderDetails extends AppCompatActivity {

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


    private Button changeState;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*checking and setting theme resource file*/
        if(g.getThemeCode()==0){setTheme(R.style.pinkTheme);}else if(g.getThemeCode()==1){setTheme(R.style.limeTheme);}else if(g.getThemeCode()==2){setTheme(R.style.blackTheme);}else if(g.getThemeCode()==3){setTheme(R.style.pinkThemeDark);}else if(g.getThemeCode()==4){setTheme(R.style.limeThemeDark);}else if(g.getThemeCode()==5){setTheme(R.style.blackThemeDark);}
        setContentView(R.layout.activity_order_details);
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

        changeState=findViewById(R.id.buttonStatusChangeButton);

        //no need we already have objects
        /*database.getReference("Fabric").child(g.getFabricID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                g.setFabric(dataSnapshot.getValue(Fabric.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.getReference("Customer").child(g.getCustomerID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                g.setCustomer(dataSnapshot.getValue(Customer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        /*setting fabric dimensions content/values to resources (images/fields/etc)*/
        fabricImage.setBackgroundResource(R.drawable.framemainbgfill);
        Picasso.get().load(g.getOrder().getFabric().getImageURL()).transform(new RoundedCornersTransformation(30,0)).into(fabricImage);
        name.setText(g.getOrder().getFabric().getName());
        type.setText(g.getOrder().getFabric().getType());
        cost.setText(g.getOrder().getFabric().getCost());

        arm.setText(g.getOrder().getDimensions().getArm());
        shoulder.setText(g.getOrder().getDimensions().getShoulder());
        chest.setText(g.getOrder().getDimensions().getChest());
        shirt.setText(g.getOrder().getDimensions().getShirt());
        waist.setText(g.getOrder().getDimensions().getWaist());
        leg.setText(g.getOrder().getDimensions().getLeg());
        instructions.setText(g.getOrder().getDimensions().getInstructions());

        String tailorString = g.getOrder().getTailor().getUsername()+ "  ("+g.getOrder().getTailor().getPhone()+")";
        String customerString = g.getOrder().getCustomer().getUsername()+ "  ("+g.getOrder().getCustomer().getPhone()+")";
        tailor.setText(tailorString);
        customer.setText(customerString);

        /*disabling field listeners to give user a view only experience*/
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

        /*updating order status resources*/
        if(g.getUserType().compareTo("customer")==0){
            if(g.getOrder().getStatus().compareTo("completed")==0){
                changeState.setVisibility(View.VISIBLE);
                changeState.setText("Accept Order");
            }else{
                changeState.setVisibility(View.GONE);
            }
        }else if(g.getUserType().compareTo("tailor")==0){
            if(g.getOrder().getStatus().compareTo("pending")==0){
                changeState.setVisibility(View.VISIBLE);
                changeState.setText("Complete Order");
            }else{
                changeState.setVisibility(View.GONE);
            }
        }else{
            changeState.setVisibility(View.GONE);
        }

        /*back button code for the activity*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(g.getUserType().compareTo("admin")==0){
                    if(g.getOrderFragmentType().compareTo("pending")==0){
                        g.setIsGoingToPending(true);
                        Intent intent=new Intent(OrderDetails.this,MainActivityAdmin.class);
                        startActivity(intent);
                    }else if(g.getOrderFragmentType().compareTo("completed")==0){
                        g.setIsGoingToCompleted(true);
                        Intent intent=new Intent(OrderDetails.this,MainActivityAdmin.class);
                        startActivity(intent);
                    }
                }else if(g.getUserType().compareTo("tailor")==0){
                    if(g.getOrderFragmentType().compareTo("pending")==0){
                        Intent intent=new Intent(OrderDetails.this,MainActivityTailor.class);
                        startActivity(intent);
                    }else if(g.getOrderFragmentType().compareTo("completed")==0){
                        g.setIsGoingToCompleted(true);
                        Intent intent=new Intent(OrderDetails.this,MainActivityTailor.class);
                        startActivity(intent);
                    }
                }else if(g.getUserType().compareTo("customer")==0){
                    g.setIsGoingToCompleted(true);
                    Intent intent=new Intent(OrderDetails.this,MainActivity.class);
                    startActivity(intent);
                }

                finish();
                return;
            }
        });

        /*change button code for the activity (allowed for tailors & customers)*/
        changeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change order state
                FullScreencall();

                if(g.getUserType().compareTo("customer")==0){
                    if(g.getOrder().getStatus().compareTo("completed")==0){
                        //remove the order
                        reference = database.getReference("Order").child(g.getOrder().getOrderID());

                        /*making and displaying a confirmation dialogue box to the user*/
                        MaterialAlertDialogBuilder builder= new MaterialAlertDialogBuilder(OrderDetails.this);
                        builder.setTitle("Confirm Order Recieval");
                        builder.setMessage("Are you sure you have recieved this order?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reference.removeValue();
                                //Toast.makeText(getApplicationContext(),"Order removed", Toast.LENGTH_SHORT).show();

                                g.setIsGoingToCompleted(true);
                                Intent intent=new Intent(OrderDetails.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                return;
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
                        return;
                    }else{
                        FullScreencall();
                        return;
                    }
                }else if(g.getUserType().compareTo("tailor")==0){
                    if(g.getOrder().getStatus().compareTo("pending")==0){
                        //update the order status to complete
                        reference = database.getReference("Order").child(g.getOrder().getOrderID());

                        /*making and displaying a confirmation dialogue box to the user*/
                        MaterialAlertDialogBuilder builder= new MaterialAlertDialogBuilder(OrderDetails.this);
                        builder.setTitle("Confirm Order Completion");
                        builder.setMessage("Are you sure you have completed this order?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                g.getOrder().setStatus("completed");
                                reference.setValue(g.getOrder());
                                //Toast.makeText(getApplicationContext(),"Order completed", Toast.LENGTH_SHORT).show();

                                g.setIsGoingToPending(true);
                                Intent intent=new Intent(OrderDetails.this,MainActivityTailor.class);
                                startActivity(intent);
                                finish();
                                return;
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
                        return;

                    }else{
                        FullScreencall();
                        return;
                    }
                }else{
                    FullScreencall();
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

    @Override
    protected void onStart() {
        super.onStart();
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