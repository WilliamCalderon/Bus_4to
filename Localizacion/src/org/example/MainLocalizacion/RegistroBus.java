package org.example.MainLocalizacion;


import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;


public class RegistroBus extends Activity implements OnItemSelectedListener{
    private String Ruta;
    private String Placas;
    private String Nombre;
    private String Apellido;
	private Button btFinalizar; 
    private Spinner spSentido;
    private EditText etPlacas, etNombre, etApellido;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_bus);
		
		btFinalizar = (Button)findViewById(R.id.button1);
		etPlacas = (EditText) findViewById(R.id.etPlacas);
		etNombre = (EditText) findViewById(R.id.etNombreConductor);
		etApellido = (EditText) findViewById(R.id.etApellidos);
		this.spSentido = (Spinner) findViewById(R.id.spinner1);
		
		Cargar_Sentido();
		
		
		
		
		//Finalizar registro
		btFinalizar.setOnClickListener(new OnClickListener() { 
			ArrayList<String> datos = new ArrayList<String>();
			 @Override 
			 public void onClick(View v) {
				 
				 //datos.add(spSentido.getSelectedItem().toString());
				 //datos.add(etPlacas.getText().toString());
				 
				 Ruta=spSentido.getSelectedItem().toString();
				 Placas = etPlacas.getText().toString();
				 Nombre = etNombre.getText().toString();
				 Apellido = etApellido.getText().toString();
				 
				 datos.add(Ruta);
				 datos.add(Placas);
				 datos.add(Nombre);
				 datos.add(Apellido);
				 
				 UtilitComuncServer llamada= new UtilitComuncServer();
				 String r= getString(R.string.message).toString()+Ruta+" "+Placas+" "+Nombre+" "+Apellido;
				 Toast.makeText(
							getApplicationContext(),
							r,
							Toast.LENGTH_LONG).show();
				 	Intent mainIntent = new Intent().setClass(RegistroBus.this, MainLocalizacion.class);
			        startActivity(mainIntent);
			        
			        llamada.execute(datos); //Llamamos al doinBackGround
					//UtilitComuncServer.enviaRegistroBus(Ruta, Placas, Nombre, Apell
			        finish();//Destruimos esta activity para prevenit que el usuario retorne aqui presionando el boton Atras.
			 } 
			 }); 
	}
	
	public void Cargar_Sentido(){
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.Sentido, android.R.layout.simple_spinner_item);            		 // Create an ArrayAdapter using the string array and a default spinner layout
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  // Specify the layout to use when the list of choices appears
		this.spSentido.setAdapter(adapter);													 // Apply the adapter to the spinner
		this.spSentido.setOnItemSelectedListener(this);
		
	}
	
	public void Habilitar(){
		btFinalizar.setEnabled(true);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro_bus, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
