package com.vivektripathi.cuseprojects;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationTracker extends AsyncTask<Void, Void, Void> implements LocationListener {
	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	Context mContext;
	Location location;
    LocationManager locationManager; // Variable of "LocationManager" type OR Declaring a Location Manager
	boolean isGPSEnabled=false; //flag for GPS status
	double latitude,longitude;
	//ProgressDialog asyncDialog = new ProgressDialog(mContext); //Takes the Context of MainActivity
	ProgressDialog asyncDialog;
	
	String typeStatus;
	
	//Crating the constructor 
	public LocationTracker(Context context){
		this.mContext = context;
		this.asyncDialog = new ProgressDialog(mContext);
	}
	
	boolean checkgpsisavailable() {
		locationManager= (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//this.locationManager = locationManager; // Better Way than making this.isGPSEnabled in instantiation as well
		return isGPSEnabled;
	}
	
	
	/********************Determining Users locations********************/	
	Location getLocation() {
		try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE); // Acquire a reference to the system Location Manager
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return location;
	}
	

	@Override
	public void onLocationChanged(Location location) {
		latitude= location.getLatitude();
		longitude = location.getLongitude();
		System.out.println(latitude);
		Log.d("grabage","I am being called");
		LatLng latLng = new LatLng(latitude, longitude);	
		new MarkerOptions().position(latLng); //Creating Marker options for User location	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    protected void onPreExecute() {
        asyncDialog.setMessage("Getting Your Location"); //set message of the dialog
        asyncDialog.show(); //show dialog
        super.onPreExecute();
    }
	
	@Override
	protected Void doInBackground(Void... params) {
		getLocation();
		return null;
	}
	
	@Override
    protected void onPostExecute(Void result) {
        asyncDialog.dismiss(); //hide the dialog
        super.onPostExecute(result);
    }	

}
