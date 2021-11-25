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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
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
        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    /*declaring required variables for the fragment*/
    private List<Fabric> latestfabricList;
    private List<Fabric> seasonalfabricList;
    private RecyclerView latestfabricRV;
    private FabricLatestAdaptor fabricLatestAdaptor;
    FirebaseDatabase database;
    DatabaseReference referenceUser;
    DatabaseReference referenceFabric;

    ImageView seasonalImage1;
    ImageView seasonalImage2;
    ImageView seasonalImage3;
    ImageView ladies;
    ImageView gents;
    ImageView kids;
    TextView seasonalText1;
    TextView seasonalText2;
    TextView seasonalText3;
    TextView moreFabric;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*getting reference from firebase database*/
        database= FirebaseDatabase.getInstance();
        referenceUser = database.getReference("Customer").child(g.getCustomerID());
        referenceFabric = database.getReference("Fabric");

        latestfabricList=new ArrayList<>();
        seasonalfabricList=new ArrayList<>();
        latestfabricRV=(RecyclerView) view.findViewById(R.id.recycleviewLatestFabrics);
        fabricLatestAdaptor=new FabricLatestAdaptor(latestfabricList,getContext());

        /*attaching backend variables to frontend xml with ids*/
        seasonalImage1=(ImageView) view.findViewById(R.id.imageViewSeasonalItem1);
        seasonalImage2=(ImageView) view.findViewById(R.id.imageViewSeasonalItem2);
        seasonalImage3=(ImageView) view.findViewById(R.id.imageViewSeasonalItem3);
        ladies=(ImageView) view.findViewById(R.id.imageViewLadies);
        gents=(ImageView) view.findViewById(R.id.imageViewGents);
        kids=(ImageView) view.findViewById(R.id.imageViewKids);
        seasonalText1=view.findViewById(R.id.textViewSeasonalItem1);
        seasonalText2=view.findViewById(R.id.textViewSeasonalItem2);
        seasonalText3=view.findViewById(R.id.textViewSeasonalItem3);
        moreFabric=view.findViewById(R.id.textViewMorefabric);

        /*various buttons code for the activity*/
        moreFabric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setCategory("normal");
                Intent intent=new Intent(getActivity(),AllFabric.class);
                startActivity(intent);
                getActivity().finish();
                return;
            }
        });

        seasonalImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setFabric(seasonalfabricList.get(0));
                g.setFabricID(g.getFabric().getFabricID());
                Intent intent=new Intent(getActivity(),FabricDetailsCustomer.class);
                getActivity().startActivity(intent);
                return;
            }
        });
        seasonalImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setFabric(seasonalfabricList.get(1));
                g.setFabricID(g.getFabric().getFabricID());
                Intent intent=new Intent(getActivity(),FabricDetailsCustomer.class);
                getActivity().startActivity(intent);
                return;
            }
        });
        seasonalImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setFabric(seasonalfabricList.get(2));
                g.setFabricID(g.getFabric().getFabricID());
                Intent intent=new Intent(getActivity(),FabricDetailsCustomer.class);
                getActivity().startActivity(intent);
                return;
            }
        });

        seasonalText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setFabric(seasonalfabricList.get(0));
                g.setFabricID(g.getFabric().getFabricID());
                Intent intent=new Intent(getActivity(),FabricDetailsCustomer.class);
                getActivity().startActivity(intent);
                return;
            }
        });
        seasonalText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setFabric(seasonalfabricList.get(1));
                g.setFabricID(g.getFabric().getFabricID());
                Intent intent=new Intent(getActivity(),FabricDetailsCustomer.class);
                getActivity().startActivity(intent);
                return;
            }
        });
        seasonalText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setFabric(seasonalfabricList.get(2));
                g.setFabricID(g.getFabric().getFabricID());
                Intent intent=new Intent(getActivity(),FabricDetailsCustomer.class);
                getActivity().startActivity(intent);
                return;
            }
        });

        ladies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setCategory("ladies");
                Intent intent=new Intent(getActivity(),AllFabric.class);
                getActivity().startActivity(intent);
                return;
            }
        });

        gents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setCategory("gents");
                Intent intent=new Intent(getActivity(),AllFabric.class);
                getActivity().startActivity(intent);
                return;
            }
        });

        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setCategory("kids");
                Intent intent=new Intent(getActivity(),AllFabric.class);
                getActivity().startActivity(intent);
                return;
            }
        });



        /*getting all fabric data from database*/
        database.getReference("Fabric").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Fabric fabricToAdd=dataSnapshot.getValue(Fabric.class);
                if(fabricToAdd.isLatest()){
                    latestfabricList.add(fabricToAdd);
                    fabricLatestAdaptor=new FabricLatestAdaptor(latestfabricList,getActivity());
                    RecyclerView.LayoutManager layout= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                    latestfabricRV.setLayoutManager(layout);
                    latestfabricRV.setAdapter(fabricLatestAdaptor);
                }
                if(fabricToAdd.isSeasonal()){
                    seasonalfabricList.add(fabricToAdd);

                    /*loading rabric image into picaso*/
                    if(seasonalfabricList.size()>2){
                        Picasso.get().load(seasonalfabricList.get(seasonalfabricList.size()-1).getImageURL()).transform(new RoundedCornersTransformation(20,0)).into(seasonalImage1);
                        Picasso.get().load(seasonalfabricList.get(seasonalfabricList.size()-2).getImageURL()).transform(new RoundedCornersTransformation(20,0)).into(seasonalImage2);
                        Picasso.get().load(seasonalfabricList.get(seasonalfabricList.size()-3).getImageURL()).transform(new RoundedCornersTransformation(20,0)).into(seasonalImage3);
                        seasonalText1.setText(seasonalfabricList.get(seasonalfabricList.size()-1).getName());
                        seasonalText2.setText(seasonalfabricList.get(seasonalfabricList.size()-2).getName());
                        seasonalText3.setText(seasonalfabricList.get(seasonalfabricList.size()-3).getName());
                    }
                }
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

        /*redundant timer code, most probably not used, it was going to scroll horizontal rv images*/
        new Timer().schedule(new TimerTask(){
            public void run() {
                //((MainActivity)getActivity()).setSeasonalData(seasonalfabricList);
                /*Picasso.get().load(R.drawable.ladiespic).transform(new RoundedCornersTransformation(20,0)).into(ladies);
                Picasso.get().load(R.drawable.gentspic).transform(new RoundedCornersTransformation(20,0)).into(gents);
                Picasso.get().load(R.drawable.kidspic).transform(new RoundedCornersTransformation(20,0)).into(kids);*/
        }
    }, 3000 );


        /*latestfabricRV.setClickable(false);

        latestfabricRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling up
                } else {
                    // Scrolling down
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });

        new Timer().schedule(new TimerTask(){
            public void run() {
                *//*if(g.getRvCurrentIndex()<g.getRvSize()){
                    latestfabricRV.smoothScrollToPosition(g.getRvCurrentIndex()+1);
                    g.setRvCurrentIndex(g.getRvCurrentIndex()+1);
                }else {
                    latestfabricRV.smoothScrollToPosition(0);
                    g.setRvCurrentIndex(0);
                }*//*

            }
        }, 5000 );*/

    }
}