package com.example.yourmovieopinion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class MostrarReviews extends AppCompatActivity {
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputVerifyPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        textInputEmail = findViewById(R.id.til_email);
        textInputUsername = findViewById(R.id.til_nombre);
        textInputPassword = findViewById(R.id.til_password);
        textInputVerifyPassword = findViewById(R.id.til_verifyPassword);

        //Referencia al Botón
        Button botonAceptar = (Button) findViewById(R.id.button);
        botonAceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //validarDatos();
            }
        });
    }
}
