package org.example.MainLocalizacion;

import android.os.Bundle;
import android.app.Activity;
import android.content.*;
import android.view.Menu;
import java.util.*;

public class Pantalla_Inicio extends Activity {

	 private long Tiempo_Ini = 6000; //6 segundos

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_pantalla__inicio);

	    TimerTask task = new TimerTask() {
	      @Override
	      public void run() {
	        Intent mainIntent = new Intent().setClass(Pantalla_Inicio.this, RegistroBus.class);
	        startActivity(mainIntent);
	        finish();//Destruimos esta activity para prevenir que el usuario retorne aqui presionando el boton Atras.
	      }
	    };

	    Timer timer = new Timer();
	    timer.schedule(task, Tiempo_Ini);//Pasado los 6 segundos dispara la tarea
	  }

}
