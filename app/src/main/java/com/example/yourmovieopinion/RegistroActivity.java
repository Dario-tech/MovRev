package com.example.yourmovieopinion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputVerifyPassword;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textInputEmail = findViewById(R.id.til_email);
        textInputUsername = findViewById(R.id.til_nombre);
        textInputPassword = findViewById(R.id.til_password);
        textInputVerifyPassword = findViewById(R.id.til_verifyPassword);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //Referencia al Bot??n
        Button botonAceptar = (Button) findViewById(R.id.button);
        botonAceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                validarDatos();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.foto_perfil);
        View view = MenuItemCompat.getActionView(menuItem);
        ShapeableImageView fotoPerfil = view.findViewById(R.id.toolbar_fotoperfil);

        fotoPerfil.setOnClickListener(view1 -> {
            drawerLayout.openDrawer(Gravity.RIGHT);
            navigationView.setNavigationItemSelectedListener(item ->{
                return onOptionsItemSelected(item);

            });
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_peliculas:
                Intent intent = new Intent(this,ListaPeliculasFav.class);
                startActivity(intent);
                break;
            case R.id.nav_reviews:
                Intent intent2 = new Intent(this,MostrarReviews.class);
                startActivity(intent2);
                break;
            case R.id.nav_profile:

                break;
            case R.id.nav_cerrarSesion:

                break;
        }
        return true;
    }
    private void validarDatos() {
        String nombre = textInputUsername.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();
        String correo = textInputEmail.getEditText().getText().toString();
        String verifypassword = textInputVerifyPassword.getEditText().getText().toString();

        boolean a = esNombreValido(nombre);
        boolean b = false;
        if(password.equals(verifypassword)) {
            b = validatePassword(password);
        }else{
            //Las contrasenias no coinciden
            textInputPassword.setError("Las contrase??as no coinciden");
            textInputVerifyPassword.setError("Las contrase??as no coinciden");
            //Toast.makeText(this,"Las contrase??as no coinciden",Toast.LENGTH_LONG).show();
        }
        boolean c = esCorreoValido(correo);

        if (a && b && c) {
            // OK, se pasa a la siguiente acci??n
            Toast.makeText(this, "Se guarda el registro", Toast.LENGTH_LONG).show();
        }

    }

    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            textInputUsername.setError("Nombre inv??lido");
            return false;
        } else {
            textInputUsername.setError(null);
        }

        return true;
    }

    private boolean validatePassword(String passwordInput) {


        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean esCorreoValido(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            textInputEmail.setError("Correo electr??nico inv??lido");
            return false;
        } else {
            textInputEmail.setError(null);
        }

        return true;
    }


}