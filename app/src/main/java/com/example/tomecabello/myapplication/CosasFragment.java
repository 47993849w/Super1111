package com.example.tomecabello.myapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;


/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * interface.
 */
public class CosasFragment extends Fragment implements LocationListener {

    private FirebaseListAdapter<notitas> adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public static double lat;
    public static double lo;
    private String mParam1;
    private String mParam2;
    static String lon;


    /**

     */


    /**
     *
     */

    static String la;
    // TODO: Rename and change types of parameters
    public static CosasFragment newInstance(String param1, String param2) {
        CosasFragment fragment = new CosasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CosasFragment() {
    }

    ImageView imageView;
    LocationManager locationManager;

    //Location location4 = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        View view2 = inflater.inflate(R.layout.layout, container, false);
        imageView = (ImageView) view2.findViewById(R.id.imageVie);
        final ListView lvCosas = (ListView) view.findViewById(R.id.lvCosas);
        try{
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);}catch (SecurityException e){
            System.out.println("basuieieieiiee" + e);
        }


      //  MyApp app = (MyApp) getActivity().getApplication();


        System.out.println("coloooooeeeeddddddddddddddddddddddddddddddddddd");
        final ImageButton button = (ImageButton) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                write(v);
            }
        });
        Firebase ref = new Firebase("https://adri.firebaseio.com");
        final Firebase re1 = ref;




        re1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(final DataSnapshot snapshot) {

                adapter = new FirebaseListAdapter<notitas>(getActivity(), notitas.class, R.layout.layout, re1) {
                    @Override
                    protected void populateView(View view, notitas nota, int position) {



                        //System.out.println();
                        System.out.println("4544" + position);

                        TextView tvName = (TextView) view.findViewById(R.id.title);
                        ImageView imageView = (ImageView) view.findViewById(R.id.imageVie);

                        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
                        TextView textView3 = (TextView) view.findViewById(R.id.textView3);

                        textView2.setText(nota.getLo());
                        textView3.setText(nota.getLat());

                        lon = (textView2.getText().toString());

                        la = textView3.getText().toString();
                      //  File file = new File("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/IEEE_1394_Firewire_PCI_Expansion_Card_Digon3.jpg/220px-IEEE_1394_Firewire_PCI_Expansion_Card_Digon3.jpg");
                        Picasso.with(getContext()).load(nota.getUr()).
                                resize(99, 165).
                                into(imageView);


                        tvName.setText(nota.getTitulo().toUpperCase());
                        TextView tvDes = (TextView) view.findViewById(R.id.textView);
                        tvDes.setText("\n" + nota.getMensa());
                    }
                };
                lvCosas.setAdapter(adapter);


            }

            @Override
            public void onCancelled(FirebaseError error) {
            }

        });



        lvCosas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textView3 = (TextView) view.findViewById(R.id.textView3);
                System.out.println(textView3.getText() + "3");
                MapitaFragment.la = Double.parseDouble(textView3.getText().toString());
                TextView textView2 = (TextView) view.findViewById(R.id.textView2);
                System.out.println(textView2.getText() + "3");
                MapitaFragment.lo = Double.parseDouble(textView2.getText().toString());
                MapitaFragment fragment1 = new MapitaFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(android.R.id.content, fragment1);
                fragmentTransaction.commit();
            }

        });





        return view;

    }



    public void write(View view){







       Intent intent = new Intent(getActivity(), com.example.tomecabello.myapplication.imgur.activities.MainActivity.class);
       /// intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


    @Override
    public void onLocationChanged(Location location) {

        try{
        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
lat=location.getLatitude();
            lo=location.getLongitude();}

        catch (SecurityException e){
            System.out.println("w66"+e);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
