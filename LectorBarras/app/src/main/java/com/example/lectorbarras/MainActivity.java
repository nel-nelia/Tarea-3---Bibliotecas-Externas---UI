package com.example.lectorbarras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    configurarLector();
  }

  private void configurarLector(){
    final ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton);
    imageButton.setOnClickListener(new View.OnClickListener() {
      // lo que va hacer es integrar a nuestra nueva identidad
      @Override
      public void onClick(View view) {
        new IntentIntegrator(MainActivity.this).initiateScan();
      }
    });
  }

  private void actualizarUITextViews(String resultadoScaneo, String formatoResultado){
    ((TextView)findViewById(R.id.tvFormat)).setText(formatoResultado);
    ((TextView)findViewById(R.id.tvResult)).setText(resultadoScaneo);
  }

  private void manipularResultado(IntentResult intentResult){
    if(intentResult != null){
      actualizarUITextViews(intentResult.getContents(),intentResult.getFormatName());
    }
    else{
      Toast.makeText(getApplicationContext(),"No se leyó nada",Toast.LENGTH_SHORT).show();
    }
  }

  // Recoger la respuesta de la actividad, y va a tener tres parametros, todos son númericos
  //resultcode si está realizado de manera correcta
  //intent si el resultado propio de la actividad

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent){
    final IntentResult intentResult =
        IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
    manipularResultado(intentResult);
  }

}
