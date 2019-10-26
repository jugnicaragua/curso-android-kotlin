package ni.devotion.proyectorecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MiAdaptador(list: ArrayList<String>, context: Context, miInterfaz: MiInterfaz) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listaDelAdaptador: ArrayList<String> = list
    var adaptadorContext: Context = context
    var adapterInterfaz: MiInterfaz = miInterfaz

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        adaptadorContext = parent.context

        //MANDAMOS A CREAR UNA VARIALE QUE VA A CONTENER LA VISTA QUE ESTAMOS
        //MANDANDO A CREAR.
        val menuItem = LayoutInflater.from(adaptadorContext).
            inflate(R.layout.recycer_content, parent, false)

        //RETORNAMOS LA VISTA YA CREADA.
        return MiViewHolder(menuItem)
    }

    override fun getItemCount(): Int {
        return listaDelAdaptador.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //CREAMOS UN VIEWHOLDER, CON EL CUAL PODREMOS ACCEDER A LOS ATRIBUTOS
        //DENTRO DE LA CLASE.
        //MiViewHolder.kt
        val mainHolder = holder as MiViewHolder

        //MADAMOS A OBTENER LA POSICION DEL ITEM.
        val menuItem = listaDelAdaptador[position]

        mainHolder.miTextView.text = menuItem

        mainHolder.miTextView.setOnClickListener {
            adapterInterfaz.clickedButton(mainHolder.miTextView.text.toString())
        }
    }

}