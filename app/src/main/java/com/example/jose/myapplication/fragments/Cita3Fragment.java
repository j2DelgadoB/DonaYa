package com.example.jose.myapplication.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.jose.myapplication.PrincipalActivity;
import com.example.jose.myapplication.R;
import com.example.jose.myapplication.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 13/03/2015.
 */
public class Cita3Fragment extends Fragment {
    JSONParser jParser = new JSONParser();
    public Cita3Fragment() {
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cita3, container, false);
        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Spinner sp = (Spinner) getActivity().findViewById(R.id.spinnerTipoSangreCita3);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.grupo_sanguineo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        Button btnFinalizar = (Button) getActivity().findViewById(R.id.btnTerminarCita);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCita cita=new crearCita();
                cita.execute();
            }
        });

    }
    private class crearCita extends AsyncTask<Void,Void,String> {
        JSONObject json=null;

        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> par = new ArrayList<NameValuePair>();
            EditText distrito = (EditText) getActivity().findViewById(R.id.cita2_Distrito);//segun distrito mostrar lugares de donacion cercanos
            DatePicker fecha= (DatePicker)getActivity().findViewById(R.id.datePicker_cita2);
            TimePicker hora = (TimePicker)getActivity().findViewById(R.id.timePicker_cita2);

            EditText nombres= (EditText)getActivity().findViewById(R.id.editNombresCita3);
            EditText apellidos = (EditText)getActivity().findViewById(R.id.editApellidosCita3);
            Spinner sp = (Spinner) getActivity().findViewById(R.id.spinnerTipoSangreCita3);
            EditText email = (EditText) getActivity().findViewById(R.id.editemailCita3);
            EditText tel= (EditText)getActivity().findViewById(R.id.editphoneCita3);
            String idUser = getActivity().getIntent().getStringExtra("MyID");

            int   day  = fecha.getDayOfMonth();
            int   month= fecha.getMonth() + 1;
            int   year = fecha.getYear();
            String date=checkDigit(day)+"/"+checkDigit(month+1)+"/"+year;
            int hour = hora.getCurrentHour();
            int minute = hora.getCurrentMinute();
            String time=checkDigit(hour)+":"+checkDigit(minute);
            Log.d("fecha:",date);
            Log.d("tiempo:",time);
            par.add(new BasicNameValuePair("idUser",idUser));
            par.add(new BasicNameValuePair("distrito",distrito.getText().toString()));
            par.add(new BasicNameValuePair("fecha",date));
            par.add(new BasicNameValuePair("hora",time));
            par.add(new BasicNameValuePair("nombres",nombres.getText().toString()));
            par.add(new BasicNameValuePair("apellidos",apellidos.getText().toString()));
            par.add(new BasicNameValuePair("tipoSangre",sp.getSelectedItem().toString()));
            par.add(new BasicNameValuePair("email",email.getText().toString()));
            par.add(new BasicNameValuePair("telefono",tel.getText().toString()));

            try {
                json=jParser.makeHttpRequest("http://10.0.2.2:1000/SoyDonante/crear_cita.php","GET",par);
                //json=jParser.makeHttpRequest("http://isulamotors.com.pe/SoyDonante/crear_cita.php","GET",par);
                Log.d("Mi json 1:", json.toString());
                int success = json.getInt("success");
                if (success==1){
                    Log.d("","se guardo en la base de datos correctamente");
                }//verificar su conexion a internet*/
                Intent i= new Intent(getActivity(), PrincipalActivity.class);
                i.putExtra("MyID",getActivity().getIntent().getStringExtra("MyID"));
                i.putExtra("MyUsername",getActivity().getIntent().getStringExtra("MyUsername"));
                i.putExtra("MyEmail",getActivity().getIntent().getStringExtra("MyEmail"));
                startActivity(i);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
}
