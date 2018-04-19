package com.example.alexander.tallerinterneti007213;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexander.tallerinterneti007213.Models.User;
import com.example.alexander.tallerinterneti007213.Parser.jsonUser;
import com.example.alexander.tallerinterneti007213.URL.HttpManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //********** Atributos de clase iniciales **************
    ProgressBar progressBar;
    Button button;
    TextView textView;

    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.id_pb_data);
        button = (Button) findViewById(R.id.id_btn_loaddata);
        textView = (TextView) findViewById(R.id.id_tv_data);

    }

    // ********************** Metodo para validar la conexion a internet *************
    public Boolean isOnLine(){
        // Hacer llamado al servicio de conectividad utilizando el ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Obtener el estado de la conexion a internet en el dispositivo
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Validar el estado obtenido de la conexion
        if (networkInfo != null){
            return true;
        }else {
            return false;
        }
    }

    //******************************* Evento del boton **************************************
    public void loadData(View view){
        if (isOnLine()){
            TaskUser taskUser = new TaskUser();
            taskUser.execute("http://pastoral.iucesmag.edu.co/practica/listar.php");
        }else {
            Toast.makeText(this, "Sin conexion", Toast.LENGTH_SHORT).show();
        }
    }

    // *****************************Evento de listado ***************************************

    public void processData(){
        for (User str : userList){
            textView.append("\n");
            textView.append("codigo: " + str.getCodigo() + "\n");
            textView.append("nombre: " + str.getNombre() + "\n");
            textView.append("edad: " +str.getEdad()+ " años " + "\n");
            textView.append("correo: " +str.getCorreo() + "\n");
            textView.append("pass: " +str.getPass() + "\n");
            textView.append("\n");
        }
    }

    //******************* Tarea para traer los datos de los usuarios ******************************
    public class TaskUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = null;
            try {
                content = HttpManager.getDataJson(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                userList = jsonUser.getData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            processData();

            progressBar.setVisibility(View.GONE);
        }
    }


}
