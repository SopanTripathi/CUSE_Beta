package com.vivektripathi.cuseprojects;

import android.util.Log;


/*We are trying to implement code given in 
http://stackoverflow.com/questions/14702621/answer-draw-path-between-two-points-using-google-maps-android-api-v2
*/

public class PathRequest {
	
	public String makeURL (double sourcelat, double sourcelong, double destlat, double destlong ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelong));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString( destlong));
        urlString.append("&sensor=false&mode=walking&alternatives=true");
        urlString.append("&key=AIzaSyD9hRrCavGTA1obt-uca32SXCSQRZt9tK8");
        Log.d("DEBUG_TAG", "URL for Path Request is: " +urlString.toString());
        return urlString.toString();     

        //https://maps.googleapis.com/maps/api/directions/json?origin=72.915941,19.130658&destination=72.916242,19.131343&sensor=false&mode=driving&alternatives=true&key=AIzaSyD9hRrCavGTA1obt-uca32SXCSQRZt9tK8
	}

}
