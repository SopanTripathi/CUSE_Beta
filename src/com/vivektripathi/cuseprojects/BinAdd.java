package com.vivektripathi.cuseprojects;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BinAdd extends MyActivity{
	
	
	
	public String binaddingfunction() throws IOException {
		
		InputStream in = null;
		String myurl = "http://10.129.23.30:8000/all_bins";
		Log.d("DEBUG_TAG", "The URL prepared is: " + myurl);

		//Calling constructor of URL class with myurl passed as string 
		URL url = new URL(myurl); //Preparing the URL
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();//HTTpURL Object is prepared
		Log.d("DEBUG_TAG", "The connecting object is: " + conn);
		try {
			conn.setRequestMethod("GET"); //Method which will be sent to HTTP Server
			conn.setDoInput(true); //Specifies whether this URLConnection allows receiving data
			conn.connect(); // Connect made connection for getting response
			Log.d("DEBUG_TAG","I am here with GET");
			int response = conn.getResponseCode();
			Log.d("DEBUG_TAG", "The response code is: " + response);
			Log.d("DEBUG_TAG","I am here with GET below response code");
			
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

	public void binaddparser(String respbinadd) {
		String str= respbinadd; // Odata is original Stream Data
		int start=0;
		int end=0;
		double slat;
		double slng;
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
						sdesc=mappairs[5];
						Log.d("DEBUG_TAG", "index number is and Latitude is " +slat+ " and longitude is " +slng+ " and Description is " +mappairs[5]); 
						LatLng latLngob = new LatLng(slat, slng); //Creating object of LatLong
						vickymap.addMarker(new MarkerOptions().position(latLngob).icon(BitmapDescriptorFactory.fromResource(R.drawable.bin)).title(sdesc));
				}//else completes
			}//for completes
		}//try ends here
		catch (Exception e) {e.printStackTrace();
		}
	}
	
	

}
