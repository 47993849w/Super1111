package com.example.tomecabello.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.Map;


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

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

    Location location4 = null;

    static String lon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        View view2 = inflater.inflate(R.layout.layout, container, false);
        imageView = (ImageView) view2.findViewById(R.id.imageVie);
        final ListView lvParkings = (ListView) view.findViewById(R.id.lvCosas);
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
//                System.out.println("her"+location4.getAltitude());

                write(v);
            }
        });
        Firebase ref = new Firebase("https://adri.firebaseio.com");
        final Firebase parkings = ref;




        parkings.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(final DataSnapshot snapshot) {
             //   System.out.println("556555" + snapshot.getValue());  //prints "Do you have data? You'll love Firebase."

                adapter = new FirebaseListAdapter<notitas>(getActivity(), notitas.class, R.layout.layout, parkings) {
                    @Override
                    protected void populateView(View view, notitas parking, int position) {

                    //    System.out.println(adapter.getRef(1).getKey());



                        //System.out.println();
                      //  id = parking.
                        System.out.println("4544"+position);

                        TextView tvName = (TextView) view.findViewById(R.id.title);
                        ImageView imageView = (ImageView) view.findViewById(R.id.imageVie);

                        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
                        TextView textView3 = (TextView) view.findViewById(R.id.textView3);

                        textView2.setText(parking.getLo());
                        textView3.setText(parking.getLat());

                        lon = (textView2.getText().toString());

                        la = textView3.getText().toString();
                        File file = new File("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/IEEE_1394_Firewire_PCI_Expansion_Card_Digon3.jpg/220px-IEEE_1394_Firewire_PCI_Expansion_Card_Digon3.jpg");
                        Picasso.with(getContext()).load(parking.getUr()).
                                resize(270,556).
                                into(imageView);


                        tvName.setText(parking.getTitulo().toUpperCase());
                        TextView tvDes = (TextView) view.findViewById(R.id.textView);
                        tvDes.setText("\n"+parking.getMensa());
                    }
                };
                lvParkings.setAdapter(adapter);


            }

            @Override
            public void onCancelled(FirebaseError error) {
            }

        });



        lvParkings.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textView3 = (TextView) view.findViewById(R.id.textView3);
                System.out.println(textView3.getText()+"3");
                MapitaFragment.la = Double.parseDouble(textView3.getText().toString());
                TextView textView2 = (TextView) view.findViewById(R.id.textView2);
                System.out.println(textView2.getText()+"3");
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


    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public static double lat;
    public static double lo;
    public void write(View view){






       // MapitaFragment fragmentB = (MapitaFragment)getActivity()
         //       .getSupportFragmentManager()
         //       .findFragmentByTag("Mapa");


       Intent intent = new Intent(getActivity(), com.example.tomecabello.myapplication.activities.MainActivity.class);
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
