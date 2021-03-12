package com.example.practica2

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*

private const val TAG = "cosaFragment"

class cosaFragment: Fragment() {
    private lateinit var cosa:Cosa
    private lateinit var  campoDeNombre: EditText
    private lateinit var campoDePrecio: EditText
    private lateinit var campoDeSerie: EditText
    private lateinit var labelDeFecha: TextView
    private lateinit var contexto :Context
    private lateinit var boton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cosa = Cosa()
        cosa = arguments?.getParcelable("COSA_ENVIADA")!!
        //Log.d(TAG,"Se recibió ${cosa.nombreDeCosa} $ ${cosa.valorEnPesos}")

    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.cosa_fragment, container, false)
        val calendarioini = Calendar.getInstance()
        val anioini = calendarioini.get(Calendar.YEAR)
        var mesini = calendarioini.get(Calendar.MONTH)
        mesini+= 1
        val diaini = calendarioini.get(Calendar.DAY_OF_MONTH)
        campoDeNombre = vista.findViewById(R.id.campoNombre)
        campoDePrecio = vista.findViewById(R.id.campoPrecio)
        campoDeSerie = vista.findViewById(R.id.campoSerie)
        labelDeFecha = vista.findViewById(R.id.labelFecha)
        campoDeNombre.setText(cosa.nombreDeCosa)
        campoDePrecio.setText(cosa.valorEnPesos.toString())
        labelDeFecha.text = "$diaini/$mesini/$anioini"
        campoDeSerie.setText(cosa.numeroDeSerie.toString())
        boton = vista.findViewById(R.id.button)
        return  vista

    }

    /*  fun cosaAMostrar(cosa: Cosa){
          Log.d(TAG, "Me pasaron un ${cosa.nombreDeCosa} de $ ${cosa.valorEnPesos}")
          this.cosa = cosa
      }*/
    @SuppressLint("SetTextI18n")
    override fun onStart(){
        super.onStart()
        val observador = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.hashCode() == campoDeNombre.text.hashCode()) {
                    if (s!= null)
                    {
                        if (s.isEmpty())
                        {
                            Toast.makeText(context,"El campo de nombre no puede quedar vacío", Toast.LENGTH_SHORT).show()
                        }
                    }
                    cosa.nombreDeCosa = s.toString()
                }
                if (s.hashCode()==campoDePrecio.text.hashCode()){
                    if (s!= null)
                    {
                        if (s.isEmpty()){
                            cosa.valorEnPesos= 0
                        } else{
                            cosa.valorEnPesos= s.toString().toInt()
                        }
                    }
                    //cosa.valorEnPesos = s.toString().toInt()
                }
                if(s.hashCode()==campoDeSerie.text.hashCode()){
                    cosa.numeroDeSerie = UUID.fromString(s.toString())
                }
            }


            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "El nuevo nombre de la cosas es ${cosa.nombreDeCosa}")
            }
        }
        campoDeNombre.addTextChangedListener(observador)
        campoDePrecio.addTextChangedListener(observador)
        campoDeSerie.addTextChangedListener(observador)
        //-------------------DatePicker-------------------------------------------------------------
        val calendario = Calendar.getInstance()
        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        /*boton.setOnClickListener {

        }*/
        boton.setOnClickListener {
            val fn= DatePickerDialog(contexto  , { view, year, monthOfYear, dayOfMonth ->
                val cMes= (monthOfYear + 1)
                val meses= cMes.toString()

                labelDeFecha.text = "$dayOfMonth/$meses/$year"
            }, anio, mes, dia)
            fn.getDatePicker().setMaxDate(System.currentTimeMillis())
            fn.show()

        }
        //------------------------------------------------------------------------------------------

    }
    companion object{
        fun nuevaInstancia(cosa: Cosa) : cosaFragment{
            val argumentos = Bundle().apply {
                putParcelable("COSA_ENVIADA", cosa)
            }
            return cosaFragment().apply {
                arguments = argumentos
            }
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        contexto = context


    }
}