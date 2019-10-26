package ni.devotion.manejolayouts.presentation.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ni.devotion.manejolayouts.R
import ni.devotion.manejolayouts.presentation.base.BaseFragment
import ni.devotion.manejolayouts.presentation.misc.FragmentInteractionListener

class Subfragmento : BaseFragment() {
    private var listener: OnSubFragmentoInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subfragmento, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSubFragmentoInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnSubFragmentoInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnSubFragmentoInteractionListener : FragmentInteractionListener {
        companion object{
            fun newInstance(): Subfragmento {
                val fragmento = Subfragmento()
                return fragmento
            }
        }
    }
}
