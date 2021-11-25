package com.noice.firststitch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pendingordersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pendingordersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pendingordersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pendingordersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static pendingordersFragment newInstance(String param1, String param2) {
        pendingordersFragment fragment = new pendingordersFragment();
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
        return inflater.inflate(R.layout.fragment_pendingorders, container, false);
    }

    /*declaring required variables for the activity*/
    private List<Order> orderList;
    /*private List<Customer> customerList;
    private List<Tailor> tailorList;
    private List<Fabric> fabricList;*/

    private RecyclerView ordersRV;
    private OrderlistAdaptor orderlistAdaptor;
    private FirebaseDatabase database;
    private DatabaseReference referenceUser;
    private DatabaseReference referenceFabric;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        g.setOrderFragmentType("pending");

        orderList=new ArrayList<>();
        /*customerList=new ArrayList<>();
        tailorList=new ArrayList<>();
        fabricList=new ArrayList<>();*/

        database= FirebaseDatabase.getInstance();
        //referenceUser = database.getReference("Admin").child(g.getAdminID());
        //referenceFabric = database.getReference("Fabric");

        /*attaching backend variables to frontend xml with ids*/
        ordersRV= (RecyclerView) view.findViewById(R.id.recycleviewpendingorders);
        ordersRV.setHasFixedSize(false);

        /*intializing recycler view*/
        orderlistAdaptor=new OrderlistAdaptor(orderList,getActivity());
        RecyclerView.LayoutManager layout= new LinearLayoutManager(getActivity());
        ordersRV.setLayoutManager(layout);
        ordersRV.setAdapter(orderlistAdaptor);

        /*getting all orders data from database*/
        database.getReference("Order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order=dataSnapshot.getValue(Order.class);
                orderList.add(order);

                /*database.getReference("Customer").child(order.getCustomerID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Customer customer=dataSnapshot.getValue(Customer.class);
                        customerList.add(customer);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/



                //while (orderList.size()<1 || tailorList.size()<1 || customerList.size()<1 || fabricList.size()<1);
                /*updating recycler view*/
                orderlistAdaptor=new OrderlistAdaptor(orderList,getActivity());
                orderlistAdaptor.notifyDataSetChanged();
                ordersRV.setAdapter(orderlistAdaptor);
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


    }
}