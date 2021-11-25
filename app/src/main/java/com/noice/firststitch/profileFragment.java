package com.noice.firststitch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    /*declaring required variables for the fragment*/
    private ImageView edit;
    private ImageView logout;
    private TextView logoutText;
    private TextView username;
    private TextView phone;
    private TextView address;
    private TextView speciality;
    private ImageView specialityIcon;
    private RadioButton pink;
    private RadioButton lime;
    private RadioButton black;
    private RadioButton pinkDark;
    private RadioButton limeDark;
    private RadioButton blackDark;

    SharedPreferences sharedPreferences;
    String file="com.noice.firststitch";
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*attaching backend variables to frontend xml with ids*/
        edit=view.findViewById(R.id.imageEditProfile);
        logout=view.findViewById(R.id.imageViewLogo);
        logoutText=view.findViewById(R.id.textLogout);
        username=view.findViewById(R.id.textUsername);
        phone=view.findViewById(R.id.textPhone);
        address=view.findViewById(R.id.textAddress);
        speciality=view.findViewById(R.id.textSpeciality);
        specialityIcon=view.findViewById(R.id.imageViewSpeciality);
        pink=view.findViewById(R.id.themePink);
        lime=view.findViewById(R.id.themeLime);
        black=view.findViewById(R.id.themeBlack);
        pinkDark=view.findViewById(R.id.themePinkDark);
        limeDark=view.findViewById(R.id.themeLimeDark);
        blackDark=view.findViewById(R.id.themeBlackDark);

        /*applying relevant theme resources*/
        sharedPreferences=getActivity().getSharedPreferences(file,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.apply();

        int themeCode = sharedPreferences.getInt("t",0);
        if(themeCode==0){
            pink.setChecked(true);
        }else if(themeCode==1){
            lime.setChecked(true);
        }else if(themeCode==2){
            black.setChecked(true);
        }else if(themeCode==3){
            pinkDark.setChecked(true);
        }else if(themeCode==4){
            limeDark.setChecked(true);
        }else if(themeCode==5){
            blackDark.setChecked(true);
        }

        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("t",0);
                editor.apply();
                Intent intent=new Intent(getContext(), Home.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });
        lime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("t",1);
                editor.apply();
                Intent intent=new Intent(getContext(), Home.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("t",2);
                editor.apply();
                Intent intent=new Intent(getContext(), Home.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });
        pinkDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("t",3);
                editor.apply();
                Intent intent=new Intent(getContext(), Home.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });
        limeDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("t",4);
                editor.apply();
                Intent intent=new Intent(getContext(), Home.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });
        blackDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("t",5);
                editor.apply();
                Intent intent=new Intent(getContext(), Home.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });

        /*setting relevant expertise resources for tailor*/
        if(g.getUserType().compareTo("tailor")==0){
            specialityIcon.setVisibility(View.VISIBLE);
            speciality.setVisibility(View.VISIBLE);

            username.setText(g.getTailor().getUsername());
            phone.setText(g.getTailor().getPhone());
            address.setText(g.getTailor().getAddress());
            int specialityCode = g.getTailor().getSpeciality();

            if(specialityCode==0){
                speciality.setText("none");
            }else if(specialityCode==1){
                speciality.setText("Ladies clothing");
            }else if(specialityCode==2){
                speciality.setText("Gents clothing");
            }else if(specialityCode==3){
                speciality.setText("Children clothing");
            }else if(specialityCode==4){
                speciality.setText("Ladies & Gents clothing");
            }else if(specialityCode==5){
                speciality.setText("Ladies & Children clothing");
            }else if(specialityCode==6){
                speciality.setText("Gents & Children clothing");
            }else if(specialityCode==7){
                speciality.setText("Ladies, Gents & Children clothing");
            }else {
                speciality.setText("unrecognizable code, please contact admin");
            }

        }else if(g.getUserType().compareTo("customer")==0){
            username.setText(g.getCustomer().getUsername());
            phone.setText(g.getCustomer().getPhone());
            address.setText(g.getCustomer().getAddress());
        }else if(g.getUserType().compareTo("admin")==0){
            username.setText(g.getAdmin().getUsername());
            phone.setText(g.getAdmin().getPhone());
            address.setText(g.getAdmin().getAddress());
        }


        /*edit button code for the activity*/
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"edit",Toast.LENGTH_SHORT).show();
                g.setIsEditing(true);
                Intent intent=new Intent(getContext(), profile.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });

        /*logout button code for the activity*/
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                g.setAdmin(null);
                g.setAdminID(null);
                g.setTailor(null);
                g.setTailorID(null);
                g.setCustomer(null);
                g.setCustomerID(null);
                g.setFabric(null);
                g.setFabricID(null);
                g.setUserType(null);
                g.setUserID(null);
                Intent intent=new Intent(getContext(),SignIn.class);
                //((MainActivity)getActivity()).FullScreencall();
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });
        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"logout",Toast.LENGTH_SHORT).show();
            }
        });
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"logout",Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}