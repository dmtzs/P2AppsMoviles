package com.example.practica2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


private const val TAG="MainActivity"
class MainActivity : AppCompatActivity(), TablaCosasFragment.Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentoActual = supportFragmentManager.findFragmentById(R.id.contenedor_fragment)
        if(fragmentoActual == null){
            val fragment = TablaCosasFragment.nuevaInstancia()
            supportFragmentManager.beginTransaction().add(R.id.contenedor_fragment,fragment).commit()
        }
    }

    override fun onCosaSeleccionada(cosa: Cosa) {
        Log.d(TAG, "MainActivity.onCosaSleccionada recibio ${cosa.nombreDeCosa} ${cosa.valorEnPesos}")
        val fragment = cosaFragment.nuevaInstancia(cosa)
        //fragment.cosaAMostrar(cosa)
        supportFragmentManager.beginTransaction().replace(R.id.contenedor_fragment, fragment).addToBackStack(null).commit()
    }
}