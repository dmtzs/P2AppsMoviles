package com.example.practica2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
//import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "TablaCosasFragment"
class TablaCosasFragment : Fragment(){
    private lateinit var tableRecyclerView: RecyclerView
    private var adapter: CosaAdapter? = null
    private val tablaCosasViewModel: TablaCosaViewModel by lazy{
        ViewModelProvider(this).get(TablaCosaViewModel::class.java)
    }
    interface Callback{
        fun onCosaSeleccionada(cosa:Cosa)
    }
    private var callback: Callback? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"Total de cosas: ${tablaCosasViewModel.inventario.size}")
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        callback = context as Callback?
    }

    companion object{
        fun nuevaInstancia(): TablaCosasFragment
        {
            return TablaCosasFragment()
        }

    }
    private fun pueblaLaTabla()
    {
        adapter = CosaAdapter(tablaCosasViewModel.inventario)
        tableRecyclerView.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.tabla_cosas_fragment,container, false)
        tableRecyclerView = vista.findViewById(R.id.tabla_recycler_view) as RecyclerView
        tableRecyclerView.layoutManager = LinearLayoutManager(context)
        pueblaLaTabla()
        return  vista
    }

    private inner class CosaHolder(vista: View): RecyclerView.ViewHolder(vista), View.OnClickListener {
        private lateinit var cosa: Cosa
        val nombreTextView: TextView = itemView.findViewById(R.id.nombre_cosa)
        val precioTextView: TextView = itemView.findViewById(R.id.precio_cosa)

        init{
            itemView.setOnClickListener(this)
        }
        @SuppressLint("SetTextI18n")
        fun holderBinding (cosa: Cosa){
            this.cosa = cosa
            nombreTextView.text = cosa.nombreDeCosa
            precioTextView.text = "$ ${cosa.valorEnPesos}"
        }

        override fun onClick(v: View?) {
            //Toast.makeText(context,"El campo de nombre no puede quedar vacío",Toast.LENGTH_SHORT).show()
            callback?.onCosaSeleccionada(cosa)
        }
    }

    private inner class CosaAdapter(var inventario: List<Cosa>): RecyclerView.Adapter<CosaHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CosaHolder {
            val vista = layoutInflater.inflate(R.layout.celda_layout,parent,false)
            return CosaHolder(vista)
        }
        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: CosaHolder, position: Int) {
            val cosa = inventario[position]
            /*holder.apply {
                nombreTextView.text = cosa.nombreDeCosa
                precioTextView.text = "$ ${cosa.valorEnPesos}"
            }*/

            holder.holderBinding(cosa)
            //Validaciones para los colores de los intervalos.
            if(cosa.valorEnPesos<= 99)
            {
                holder.itemView.setBackgroundColor(Color.RED)
            }
            if(cosa.valorEnPesos<= 199 && cosa.valorEnPesos> 99)
            {
                holder.itemView.setBackgroundColor(Color.BLUE)
            }
            if (cosa.valorEnPesos<= 299 && cosa.valorEnPesos> 199)
            {
                holder.itemView.setBackgroundColor(Color.GREEN)
            }
            if (cosa.valorEnPesos<= 399 && cosa.valorEnPesos> 299)
            {
                holder.itemView.setBackgroundColor(Color.DKGRAY)
            }
            if (cosa.valorEnPesos<= 499 && cosa.valorEnPesos> 399)
            {
                holder.itemView.setBackgroundColor(Color.MAGENTA)
            }
            if (cosa.valorEnPesos<= 599 && cosa.valorEnPesos> 499)
            {
                holder.itemView.setBackgroundColor(Color.rgb(205, 164, 52))//rgb(237, 248, 51)
            }
            if (cosa.valorEnPesos<= 699 && cosa.valorEnPesos> 599)
            {
                holder.itemView.setBackgroundColor(Color.rgb(47, 179, 174))//rgb(47, 179, 174)
            }
            if (cosa.valorEnPesos<= 799 && cosa.valorEnPesos> 699)
            {
                holder.itemView.setBackgroundColor(Color.LTGRAY)
            }
            if (cosa.valorEnPesos<= 899 && cosa.valorEnPesos>= 799)
            {
                holder.itemView.setBackgroundColor(Color.BLACK)
            }
        }

        override fun getItemCount() = inventario.size
    }
}