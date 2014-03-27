package org.example.MainLocalizacion;

import java.io.IOException;
import java.util.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity; 
import android.content.Context; 
import android.location.Location; 
import android.location.LocationListener; 
import android.location.LocationManager; 
import android.os.Bundle; 
import android.util.Log; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.TextView; 
import org.example.MainLocalizacion.RegistroBus;

public class MainLocalizacion extends Activity { 

private Button btnActualizar; 
//private Button btnDesactivar; 
private TextView lblLatitud; 
private TextView lblLongitud; 
private TextView lblPrecision; 
private TextView lblEstado; 
String l = RegistroBus.getPlacas();
private LocationManager locManager; 
private LocationListener locListener; 
private long Tiempo_Ini = 40000; //6 segundos

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnActualizar = (Button)findViewById(R.id.BtnActualizar); 	
		lblLatitud = (TextView)findViewById(R.id.LblPosLatitud); 
		lblLongitud = (TextView)findViewById(R.id.LblPosLongitud); 
		lblPrecision = (TextView)findViewById(R.id.LblPosPrecision); 
		lblEstado = (TextView)findViewById(R.id.LblEstado); 
		 
		btnActualizar.setOnClickListener(new OnClickListener() { 
			 @Override 
			 public void onClick(View v) {
				  
			      comenzarLocalizacion(); 
			 } 
			 }); 
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				btnActualizar.setOnClickListener(new OnClickListener() { 
					 @Override 
					 public void onClick(View v) {
						  
					      comenzarLocalizacion(); 
					 } 
					 });
				
			}
			
		 };
		Timer timer = new Timer();
	    timer.schedule(task, Tiempo_Ini);//Pasado los 6 segundos dispara la tarea
	}
	
	private void comenzarLocalizacion() 
	 { 
	 //Obtenemos una referencia al LocationManager 
	 locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
	 
	 //Obtenemos la �ltima posici�n conocida 
	 Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); 
	 
	 //Mostramos la �ltima posici�n conocida 
	 mostrarPosicion(loc); 
	 
	 //Nos registramos para recibir actualizaciones de la posici�n 
	 
	 	locListener = new LocationListener() { 
	 		public void onLocationChanged(Location location) { 
	 				mostrarPosicion(location); 
	 		}
		 
	 		public void onProviderDisabled(String provider){ 
	 				lblEstado.setText("Provider OFF"); 
	 		} 
	 		public void onProviderEnabled(String provider){ 
			 		lblEstado.setText("Provider ON "); 
	 		} 
	 		public void onStatusChanged(String provider, int status, Bundle extras){ 
	 				Log.i("", "Provider Status: " + status); 
	 				lblEstado.setText("Provider Status: " + status); 
	 		} 
	 	}; 	
	 
	 	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locListener); 
	 	
	 } 

	private void mostrarPosicion(Location loc) { 
		String latitud=null; 
		String longitud=null;
		UtilitComuncServer llamada = new UtilitComuncServer();
		ArrayList<String> datos = new ArrayList<String>();
		if(loc != null) 
		 { 
			 latitud=String.valueOf(loc.getLatitude());
			 longitud=String.valueOf(loc.getLongitude());
			 
			 lblLatitud.setText("Latitud: " + latitud); 
			 lblLongitud.setText("Longitud: " + longitud); 
			 lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()) + "      Placas: "+RegistroBus.getPlacas()); 
			 Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude()))); 
			 
			 datos.add(latitud);
			 datos.add(longitud);
			 //
			 datos.add(RegistroBus.getPlacas());
			 llamada.execute(datos);
			 //try {
				//	UtilitComuncServer.enviaLocalizacion(latitud, longitud);
				//} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
			 
			 
		 } 
		 else 
		 { 
			 latitud="(sin_datos)";
			 longitud="(sin_datos)";
			 lblLatitud.setText("Latitud:" + latitud); 
			 lblLongitud.setText("Longitud: "+longitud); 
			 lblPrecision.setText("Precision: (sin_datos)"+ "      Placas: "+RegistroBus.getPlacas()); 
		 } 
		/*try {
			UtilitComuncServer.enviaLocalizacion(latitud, longitud);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	 } 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_localizacion, menu);
		return true;
	}

}
