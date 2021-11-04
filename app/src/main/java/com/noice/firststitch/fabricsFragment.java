package com.noice.firststitch;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fabricsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fabricsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fabricsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fabricsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static fabricsFragment newInstance(String param1, String param2) {
        fabricsFragment fragment = new fabricsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fabrics, container, false);
    }


    ImageView addFabricFab;

    private List<Fabric> fabricList;
    private RecyclerView fabricRV;
    private FabricWholeAdaptor fabricWholeAdaptor;
    FirebaseDatabase database;
    DatabaseReference referenceUser;
    DatabaseReference referenceFabric;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addFabricFab=view.findViewById(R.id.fabaddfabricadmin);

        database= FirebaseDatabase.getInstance();
        referenceUser = database.getReference("Admin").child(g.getAdminID());
        referenceFabric = database.getReference("Fabric");

        fabricList=new ArrayList<>();
        //fillFabricList();
        //fabricList = ((MainActivityAdmin)getActivity()).fillFabricList();
        fabricRV= (RecyclerView) view.findViewById(R.id.recycleviewfabricsadmin);

        database.getReference("Fabric").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Fabric fabricToAdd=dataSnapshot.getValue(Fabric.class);
                fabricList.add(fabricToAdd);
                fabricWholeAdaptor=new FabricWholeAdaptor(fabricList,getActivity());
                RecyclerView.LayoutManager layout= new GridLayoutManager(getActivity(),3);
                fabricRV.setLayoutManager(layout);
                fabricRV.setAdapter(fabricWholeAdaptor);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*Fabric f=new Fabric();
        f.setName("name 4");
        f.setImageURL("https://firebasestorage.googleapis.com/v0/b/first-stich.appspot.com/o/FabricImage%2F2020-12-27%2012%3A06%3A56?alt=media&token=87d0f85c-47f8-4f01-bf22-c52a3de9401b");
        fabricList.add(f);*/
        fabricWholeAdaptor=new FabricWholeAdaptor(fabricList,getActivity());
        RecyclerView.LayoutManager layout= new GridLayoutManager(getActivity(),3);
        fabricRV.setLayoutManager(layout);
        fabricRV.setAdapter(fabricWholeAdaptor);

        addFabricFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Adding fabric.", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getActivity(),FabricDetails.class);
                startActivity(intent);
                getActivity().finish();
                return;

            }
        });



    }

    /*private void fillFabricList() {
        referenceFabric.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Fabric fabricToAdd=dataSnapshot.getValue(Fabric.class);
                fabricList.add(fabricToAdd);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/
}