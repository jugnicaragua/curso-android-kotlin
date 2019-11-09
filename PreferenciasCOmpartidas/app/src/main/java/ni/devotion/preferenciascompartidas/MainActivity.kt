package ni.devotion.preferenciascompartidas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = AppPreferences(context = applicationContext)

        btnSavePreferences.setOnClickListener {
            preferences.put(AppPreferences.Key.miNombrePersistente, editNombre.text.toString())
        }
        txtNombre.text = preferences.get(AppPreferences.Key.miNombrePersistente,
            "SOY UN VALOR POR DEFAULT") as String
    }
}
