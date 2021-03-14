package com.example.practica2

import androidx.lifecycle.ViewModel
import java.util.*

class TablaCosaViewModel :ViewModel(){
    val inventario: MutableList<Cosa> = mutableListOf<Cosa>()
    val nombres: Array<String> = arrayOf("Alcohol","Audifono","Telefono")
    val adjetivos: Array<String> = arrayOf("feo","grande","negro")
    init{
        for(i in 0 until 100){
            val cosa = Cosa()
            val nombreAlAzar = nombres.random()
            val adjetivoAlAzar = adjetivos.random()
            val precioAlAzar = Random().nextInt(1000)
            cosa.nombreDeCosa = "$nombreAlAzar $adjetivoAlAzar"
            cosa.valorEnPesos = precioAlAzar
            inventario += cosa
        }
    }
    fun eliminaCosa(dePosicion: Int)
    {
        inventario.removeAt(dePosicion)
    }
}