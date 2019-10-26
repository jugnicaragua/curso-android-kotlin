package ni.devotion.proyectorecyclerview

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MiViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var miCardView = itemView.findViewById(R.id.cardViewPrincipal) as CardView
    var miTextView = itemView.findViewById(R.id.textViewPrincipal) as AppCompatTextView
}