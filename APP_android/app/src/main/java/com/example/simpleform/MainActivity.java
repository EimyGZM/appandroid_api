package com.example.simpleform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText txt_nom;
    private EditText txt_email;
    private EditText txt_tel;
    private EditText txt_pass;

    DatabaseHelper db = new DatabaseHelper(this);

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
           /* for (int i = start; i < end; ++i)
            {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz]*").matcher(String.valueOf(source.charAt(i))).matches())
                {
                    return "";
                }
            }*/

            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_nom = findViewById(R.id.txt_nom);
        txt_email = findViewById(R.id.txt_email);
        txt_tel = findViewById(R.id.txt_telefono);
        txt_pass = findViewById(R.id.txt_pass);


    }

    public void clickBtn_Guardar(View view) {
        String url = "http://192.168.163.1/appuser/insertar.php";
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest resultadoPost = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Usuario insertado exitosamente", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("nombre", txt_nom.getText().toString());
                parametros.put("email", txt_email.getText().toString());
                parametros.put("telefono", txt_tel.getText().toString());
                parametros.put("pass", txt_pass.getText().toString());
                return parametros;
            }
        };
        queue.add(resultadoPost);
    }


    public void addUser(User newUser) {
        boolean insertUser = db.addUser(newUser);
        if(insertUser)
            toastMessage("User added successfully");
        else
            toastMessage("Something went wrong");
    }


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

