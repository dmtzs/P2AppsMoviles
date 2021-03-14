package com.example.practica2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
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
        fun onCosaSeleccionada(cosa: Cosa)
    }
    private var callback: Callback? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total de cosas: ${tablaCosasViewModel.inventario.size}")
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
        val vista = inflater.inflate(R.layout.tabla_cosas_fragment, container, false)
        tableRecyclerView = vista.findViewById(R.id.tabla_recycler_view) as RecyclerView
        tableRecyclerView.layoutManager = LinearLayoutManager(context)
        detectaGestosEnTabla()
        pueblaLaTabla()
        return  vista
    }

    private inner class CosaHolder(vista: View): RecyclerView.ViewHolder(vista), View.OnClickListener {
        private lateinit var cosa: Cosa
        val nombreTextView: TextView= itemView.findViewById(R.id.nombre_cosa)
        val precioTextView: TextView= itemView.findViewById(R.id.precio_cosa)
        val serieTextView: TextView= itemView.findViewById(R.id.serie_cosa)

        init{
            itemView.setOnClickListener(this)
        }
        @SuppressLint("SetTextI18n")
        fun holderBinding(cosa: Cosa){
            this.cosa = cosa
            nombreTextView.text= cosa.nombreDeCosa
            precioTextView.text= "$ ${cosa.valorEnPesos}"
            serieTextView.text= "${cosa.numeroDeSerie}"
        }

        override fun onClick(v: View?) {
            //Toast.makeText(context,"El campo de nombre no puede quedar vacío",Toast.LENGTH_SHORT).show()
            callback?.onCosaSeleccionada(cosa)
        }
    }

    private inner class CosaAdapter(var inventario: List<Cosa>): RecyclerView.Adapter<CosaHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CosaHolder {
            val vista = layoutInflater.inflate(R.layout.celda_layout, parent, false)
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
                holder.itemView.setBackgroundColor(Color.rgb(29, 115, 31))
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
            if (cosa.valorEnPesos<= 999 && cosa.valorEnPesos>= 899)
            {
                holder.itemView.setBackgroundColor(Color.rgb(139, 39, 182))
            }
            if (cosa.valorEnPesos>999)
            {
                holder.itemView.setBackgroundColor(Color.rgb(95, 47, 13))
            }
        }

        override fun getItemCount() = inventario.size
    }

    private fun detectaGestosEnTabla()
    {
        val gestosCallback= object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
        {
            override fun onMove(recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                //TODO("Not yet implemented")
                //Esta parte es para el drag aparentemente.
                return false//Se pone porque no se está implementando y así no arroje un error
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int){
                val miAlerta= AlertDialog.Builder(context)
                miAlerta.setTitle("Estar seguro que deseas eliminar este contacto?")
                miAlerta.setPositiveButton("SI") { _, _ ->
                    tablaCosasViewModel.eliminaCosa(viewHolder.adapterPosition)//Le mando el index para borrar el elemento
                    tableRecyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)//Para refrescar la pantalla cuando borras algo
                    Toast.makeText(context, "El registro ha sido eliminado", Toast.LENGTH_SHORT).show()
                }
                miAlerta.setNegativeButton("NO") { _, _ ->
                    tableRecyclerView.adapter?.notifyItemChanged(viewHolder.adapterPosition)
                    Toast.makeText(context, "Tu registro sigue aqui.", Toast.LENGTH_SHORT).show()
                }
                miAlerta.create()
                miAlerta.show()
                //Dejar lo que quite aqui si no srive
            }
        }
        val touchHelper= ItemTouchHelper(gestosCallback)
        touchHelper.attachToRecyclerView(tableRecyclerView)
    }
}