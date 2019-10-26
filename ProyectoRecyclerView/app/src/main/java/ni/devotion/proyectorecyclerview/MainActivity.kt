package ni.devotion.proyectorecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MiInterfaz {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MiAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lista = mutableListOf("Hola",
            "SOy",
            "Un",
            "Componente",
            "En RecyclerVIew")

        recyclerView = findViewById(R.id.rvCurso)
        adapter = MiAdaptador(ArrayList(lista), applicationContext, this)

        inicializarRV()

    }

    fun inicializarRV(){
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun clickedButton(miTexto: String) {
        Toast.makeText(applicationContext, miTexto, Toast.LENGTH_LONG).show()
    }
}
