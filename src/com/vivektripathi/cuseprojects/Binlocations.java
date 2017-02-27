package com.vivektripathi.cuseprojects;


import com.google.android.gms.maps.model.LatLng;

public class Binlocations {	
	
	//Declare the Array of Wastebins
	public LatLng WB[] =new LatLng[68];

	public void Binarray() {		
		WB[0] = new LatLng(19.135658, 72.907658);
		WB[1] = new LatLng(19.134911, 72.909961);
		WB[2] = new LatLng(19.134972, 72.907206);
		WB[3] = new LatLng(19.133425, 72.911386);
		WB[4] = new LatLng(19.133858, 72.911036);
		WB[5] = new LatLng(19.133003, 72.9174);
		WB[6] = new LatLng(19.135372, 72.909739);
		WB[7] = new LatLng(19.136758, 72.911456);
		WB[8] = new LatLng(19.136447, 72.912214);
		WB[9] = new LatLng(19.136622, 72.913819);
		WB[10] = new LatLng(19.137253, 72.916167);
		WB[11] = new LatLng(19.13545, 72.905564);
		WB[12] = new LatLng(19.135239, 72.907589);
		WB[13] = new LatLng(19.133947, 72.910172);
		WB[14] = new LatLng(19.133533, 72.912008);
		WB[15] = new LatLng(19.133008, 72.913681);
		WB[16] = new LatLng(19.135425, 72.914489);
		WB[17] = new LatLng(19.136125, 72.910469);
		WB[18] = new LatLng(19.136058, 72.912586);
		WB[19] = new LatLng(19.1357995, 72.9138);
		WB[20] = new LatLng(19.1353, 72.913597);
		WB[21] = new LatLng(19.135392, 72.913706);
		WB[22] = new LatLng(19.128844, 72.919122);
		WB[23] = new LatLng(19.129367, 72.919117);
		WB[24] = new LatLng(19.131783, 72.918922);
		WB[25] = new LatLng(19.132575, 72.918947);
		WB[26] = new LatLng(19.132275, 72.918503);
		WB[27] = new LatLng(19.134175, 72.918381);
		WB[28] = new LatLng(19.134989, 72.905614);
		WB[29]= new LatLng(19.135525, 72.907222);
		WB[30] = new LatLng(19.134608, 72.907342);
		WB[31] = new LatLng(19.134486, 72.909919);
		WB[32] = new LatLng(19.136058, 72.910183);
		WB[33] = new LatLng(19.136289, 72.91225);
		WB[34] = new LatLng(19.136058, 72.914031);
		WB[35] = new LatLng(19.137606, 72.915058);
		WB[36] = new LatLng(19.128972, 72.919133);
		WB[37] = new LatLng(19.129128, 72.919453);
		WB[38]= new LatLng(19.135711, 72.9082961);
		WB[39] = new LatLng(19.133506, 72.912089);
		WB[40] = new LatLng(19.136081, 72.909961);
		WB[41] = new LatLng(19.135142, 72.918228);
		WB[42]= new LatLng(19.130599, 72.915577);
		WB[43] = new LatLng(19.134655, 72.907402);
		WB[44]= new LatLng(19.135305, 72.904964);
		WB[45] = new LatLng(19.134734, 72.9049);
		WB[46] = new LatLng(19.135278, 72.903889);
		WB[47] = new LatLng(19.134525, 72.909943);
		WB[48] = new LatLng(19.136449, 72.911564);
		WB[49] = new LatLng(19.135744, 72.91396);
		WB[50] = new LatLng(19.135426, 72.918572);
		WB[51] = new LatLng(19.132441, 72.917793);
		WB[52] = new LatLng(19.133591, 72.911438);
		WB[53] = new LatLng(19.131846, 72.915337);
		WB[54] = new LatLng(19.131683, 72.916023);
		WB[55] = new LatLng(19.12986, 72.915352);
		WB[56] = new LatLng(19.1283, 72.915337);
		WB[57] = new LatLng(19.128672, 72.913803);
		WB[58] = new LatLng(19.126101, 72.916031);
		WB[59] = new LatLng(19.129807, 72.91684);
		WB[60] = new LatLng(19.130058, 72.91712);
		WB[61] = new LatLng(19.131664, 72.916916);
		WB[62] = new LatLng(19.131138, 72.91675);
		WB[63] = new LatLng(19.132311, 72.916195);
		WB[64] = new LatLng(19.132497, 72.916973);
		WB[65] = new LatLng(19.132879, 72.917809);
		WB[66] = new LatLng(19.133938, 72.915412);
		WB[67] = new LatLng(19.13586, 72.909976);
	}

	//Applying Getter to retrieve the array in "MyAcitivity"
	public LatLng[] getWB(){
		Binarray();
		return this.WB;
	}
}
