package com.noice.firststitch;

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
import android.widget.Toast;

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
 * Use the {@link usersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class usersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public usersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment usersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static usersFragment newInstance(String param1, String param2) {
        usersFragment fragment = new usersFragment();
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
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    private List<User> userList;
    private RecyclerView userRV;
    private UserlistAdaptor userlistAdaptor;
    FirebaseDatabase database;
    DatabaseReference referenceUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRV= (RecyclerView) view.findViewById(R.id.recycleviewusersadmin);
        database= FirebaseDatabase.getInstance();
        referenceUser = database.getReference("Admin").child(g.getAdminID());

        userList=new ArrayList<>();

        database.getReference("Customer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Customer customer= dataSnapshot.getValue(Customer.class);
                userList.add(new User(customer.getCustomerID(),customer.getUsername(),customer.getPhone(),"customer"));
                userlistAdaptor=new UserlistAdaptor(userList);
                RecyclerView.LayoutManager layout= new LinearLayoutManager(getActivity());
                userRV.setLayoutManager(layout);
                userRV.setAdapter(userlistAdaptor);
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

        database.getReference("Tailor").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Tailor tailor= dataSnapshot.getValue(Tailor.class);
                userList.add(new User(tailor.getTailorID(),tailor.getUsername(),tailor.getPhone(),"tailor"));
                userlistAdaptor=new UserlistAdaptor(userList);
                RecyclerView.LayoutManager layout= new LinearLayoutManager(getActivity());
                userRV.setLayoutManager(layout);
                userRV.setAdapter(userlistAdaptor);
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

        database.getReference("Admin").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Admin admin= dataSnapshot.getValue(Admin.class);
                userList.add(new User(admin.getAdminID(),admin.getUsername(),admin.getPhone(),"admin"));
                userlistAdaptor=new UserlistAdaptor(userList);
                RecyclerView.LayoutManager layout= new LinearLayoutManager(getActivity());
                userRV.setLayoutManager(layout);
                userRV.setAdapter(userlistAdaptor);
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

        /*database.getReference("Usertype").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String userType = dataSnapshot.getValue(String.class);
                String userID = dataSnapshot.getKey();

                if(userType.compareTo("customer")==0){
                    database.getReference("Customer").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Customer customer= dataSnapshot.getValue(Customer.class);
                            userList.add(new User(customer.getCustomerID(),customer.getUsername(),customer.getPhone(),"customer"));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(),"error finding customer.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(userType.compareTo("tailor")==0){
                    database.getReference("Tailor").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Tailor tailor= dataSnapshot.getValue(Tailor.class);
                            userList.add(new User(tailor.getTailorID(),tailor.getUsername(),tailor.getPhone(),"tailor"));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(),"error finding tailor.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else if(userType.compareTo("admin")==0){
                    database.getReference("Admin").child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Admin admin= dataSnapshot.getValue(Admin.class);
                            userList.add(new User(admin.getAdminID(),admin.getUsername(),admin.getPhone(),"admin"));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(),"error finding admin.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                userlistAdaptor=new UserlistAdaptor(userList);
                RecyclerView.LayoutManager layout= new LinearLayoutManager(getActivity());
                userRV.setLayoutManager(layout);
                userRV.setAdapter(userlistAdaptor);
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
        });*/




    }
}