package com.vivektripathi.cuseprojects;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyActivity extends FragmentActivity {

	//Class Variables
	static GoogleMap vickymap;
	Button B1,B2;
	Marker markers;
	final static LatLng MAPZOOM= new LatLng(19.1306598, 72.9157809); // final is used to create constant value
	private static Context context;
	final static String SERVER = "http://10.129.23.30:8000";
	protected static final String DEBUG_TAG = null;
	double destlat, destlong;
	LatLng latLngob;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//show error dialog if GoolglePlayServices not available
		if (!isGooglePlayServicesAvailable()) {
			finish();
		}
		setContentView(R.layout.activity_my);
		
		
		
		/**** Setting up Permission to do network operation on same thread ****/
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		
		/**************** Getting the Google Map @ certain Zoom Level **************/
		//Create an instance of SupportMapfragment
		SupportMapFragment supportMapFragment =
				(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
		vickymap = supportMapFragment.getMap();
		
		// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
		CameraPosition cameraPosition = new CameraPosition.Builder()
		    .target(MAPZOOM)      // Sets the center of the map to Mountain View
		    .zoom(18)            // Sets the zoom
		    .bearing(90)        // Sets the orientation of the camera to east
		    .tilt(40)          // Sets the tilt of the camera to 40 degrees
		    .build();         // Creates a CameraPosition from the builder
		
		vickymap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		vickymap.setMyLocationEnabled(true);
		
        
		//Defining Context for my activity. Context represents environment data.
        //It provides access to things such as databases
		MyActivity.context = getApplicationContext();

		
		/*********************Button Operation: B1***************************/
		//Referencing the button from XML to Java
		B1 = (Button) findViewById(R.id.button1);
		B2 = (Button) findViewById(R.id.button2);
		B2.setEnabled(false);
		
		B1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkInternetConnection();
				
				if (checkInternetConnection()==true){
					Log.d("DEBUG_TAG", "I am here before checking GPS");
					LocationTracker lctob = new LocationTracker(MyActivity.this);
						if(lctob.checkgpsisavailable()){
							//Toast.makeText(context, "Happy to know GPS is ON",Toast.LENGTH_SHORT).show();
							try{
								Location mylocation = lctob.getLocation();							
								String Obtaineddata = nearestbin(mylocation,SERVER);
								bindetailstringparser(Obtaineddata);
								B2.setEnabled(true);
							}catch (IOException e) {
								e.printStackTrace();}
				 }else{
						Toast.makeText(context, "Turn on Your GPS",Toast.LENGTH_SHORT).show();}
				}else{
					Toast.makeText(context, "Switch on your Internet",Toast.LENGTH_SHORT).show();}
			}// OnClick Ends Here


			/********* Method to obtain Nearest Bin from Server  ***********************/
			public String nearestbin(Location mylocati,String server) throws IOException{
				double latvalue= mylocati.getLatitude();
				double lngvalue = mylocati.getLongitude();
				Log.d("DEBUG_TAG", "I got latitude " +latvalue+ " and longvale "+lngvalue);
				InputStream in = null;
				String myurl = server+"/bins?lat="+latvalue+"&lng="+lngvalue;
				Log.d("DEBUG_TAG", "The URL prepared is: " + myurl);

				//Calling constructor of URL class with myurl passed as string 
				URL url = new URL(myurl); //Preparing the URL
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();//HTTpURL Object is prepared
				Log.d("DEBUG_TAG", "The connecting object is: " + conn);
				try {
					conn.setRequestMethod("GET"); //Method which will be sent to HTTP Server
					conn.setDoInput(true); //Specifies whether this URLConnection allows receiving data
					conn.connect(); // Connect made connection for getting response
					int response = conn.getResponseCode();
					Log.d("DEBUG_TAG", "The response code is: " + response);
					
					in = new BufferedInputStream(conn.getInputStream()); //In Java Stream is read through InputStream Class
					InputStreamReader is = new InputStreamReader(in); 
					BufferedReader br = new BufferedReader(is); 
					String read = br.readLine(); //Read the Stream

					Log.d("DEBUG_TAG", "The response of first read  is: " + read);
					Log.d("DEBUG_TAG","i think variabletype is " +read.getClass().getName());
					return read; // Now returning data as 1 string from this method
				} 
				finally {//Finally close the connection and/or input stream  
					conn= null;	
				}
			}
			
			/********* Method to Parse JSON Response from Server trating that as String *************/
			private void bindetailstringparser(String Obdata) {
				String str= Obdata; // Odata is original Stream Data
				int start=0;
				int end=0;
				double slat;
				double slng;
				int sdist;
				String sdesc;
				try {
					for(int i = 0; i < str.length(); i++) {
						if(str.charAt(i) == '[') // Looking for '[' position in string
							start = i;
						else if(str.charAt(i) == ']') // Looking for ']' position in  string
							end = i;
					}
					String gotstring1 = str.substring(start+1, end-1);
					Log.d("DEBUG_TAG", "the gotstring1 data is "+gotstring1);
					String delims1 = "[{}]+";
					String[] stringa = gotstring1.split(delims1);

					for (int i = 0; i < stringa.length; i++){
						if(stringa[i].isEmpty() || stringa[i].startsWith(","))
						{
							continue;
						}
						else{
							Log.d("DEBUG_TAG", "I got the stringarray element of index "+i+ " whose value is "+stringa[i]);
							String delims2 = "[:,]+";
							String[] mappairs = stringa[i].split(delims2);
								//Convert all the string to double and int
								slat = Double.parseDouble(mappairs[1]);
								slng = Double.parseDouble(mappairs[3]);
								double double_sdist = Double.parseDouble(mappairs[5]);
								sdist = (int)double_sdist;
								sdesc=mappairs[7];
								Log.d("DEBUG_TAG", "index number is and Latitude is " +slat+ " and longitude is " +slng+ " and distance is " +sdist+ " and Description is " +mappairs[7]); 
								
								differentmarkerputter(slat,slng,sdist,sdesc);
						}//else completes
					}//for completes
				}//try ends here
				catch (Exception e) {e.printStackTrace();}
			}

			
			private void differentmarkerputter(double slat, double slng, int sdist, String sdesc) {
				latLngob = new LatLng(slat, slng);
				if(sdist ==30){
					vickymap.addMarker(new MarkerOptions().position(latLngob).icon(BitmapDescriptorFactory.fromResource(R.drawable.bin30)).title(sdesc));
					vickymap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLngob, 20, 30, 90)));
					destlat= latLngob.latitude;
					destlong= latLngob.longitude;
						
				}else if(sdist ==50){
					vickymap.addMarker(new MarkerOptions().position(latLngob).icon(BitmapDescriptorFactory.fromResource(R.drawable.bin50)).title(sdesc));

				}else if(sdist ==70){
					vickymap.addMarker(new MarkerOptions().position(latLngob).icon(BitmapDescriptorFactory.fromResource(R.drawable.bin70)).title(sdesc));
					
				}else if(sdist ==90){
					vickymap.addMarker(new MarkerOptions().position(latLngob).icon(BitmapDescriptorFactory.fromResource(R.drawable.bin90)).title(sdesc));
				}
				
			}


			/********* Method to Check if Internet Connection is ON or NOT  *************/
			public boolean checkInternetConnection() {
				getBaseContext();
				// get Connectivity Manager object to check connection
				ConnectivityManager vickycm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo[] netInfo=vickycm.getAllNetworkInfo();

				// Check if the network is connected
				for (int i = 0; i<netInfo.length; i++){
					if (netInfo[i].getState() == NetworkInfo.State.CONNECTED){
						return true; 
					}				
				}
				return false;
			}	
		});
	
		
		/*********************Button Operation: B2***************************/	
		B2.setOnClickListener(new View.OnClickListener() 
		{
			String read1,read2;
			@Override
			public void onClick(View v) {
				PathRequest obj1 = new PathRequest();
				LocationTracker obforb2 = new LocationTracker(MyActivity.this);
				double latitudeb2= obforb2.getLocation().getLatitude();
				double longitudeb2= obforb2.getLocation().getLongitude();
				Log.d("DEBUG_TAG", "I got latitude and longitude on B2 Click as "+destlat+" and "+destlong); 
				
				read1 =obj1.makeURL(latitudeb2,longitudeb2,destlat,destlong);
				JSONParser obj2 = new JSONParser();
				read2= obj2.getJSONFromUrl(read1);
				obj2.drawPath(read2);
				B2.setEnabled(false);
			}
		});
		
	}
	
	
	
	/****************** Action Menu Operations **************************/	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu); 
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override // Handling Action Bar Icon Click Events
	public boolean onOptionsItemSelected(MenuItem item) {

		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.satellite:
			vickymap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			return true;

		case R.id.normal:
			vickymap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			return true;

		case R.id.removeallbin:
			vickymap.clear();
			return true;

		case R.id.getallbin:
			BinAdd ob = new BinAdd();
			String respbinadd = null;
			//double slat;
			try {
				respbinadd = ob.binaddingfunction();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ob.binaddparser(respbinadd);
			return true;	

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
	/*** Method to Check if Google Play Service is Available or Not before Getting Map ******/	
	private boolean isGooglePlayServicesAvailable() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS == status) {
			return true;
		} else {
			GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
			return false;
		}
	}
	
}

