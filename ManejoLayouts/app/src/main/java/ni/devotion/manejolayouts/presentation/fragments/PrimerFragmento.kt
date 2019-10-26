package ni.devotion.manejolayouts.presentation.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ni.devotion.manejolayouts.R
import ni.devotion.manejolayouts.presentation.base.BaseFragment
import ni.devotion.manejolayouts.presentation.misc.FragmentInteractionListener
import ni.devotion.manejolayouts.presentation.misc.HasNavigationManager
import ni.devotion.manejolayouts.presentation.misc.NavigationManager
import java.lang.RuntimeException

/**
 * A simple [Fragment] subclass.
 */
class PrimerFragmento : BaseFragment(), HasNavigationManager,
                        Subfragmento.OnSubFragmentoInteractionListener{
    var mCurrentFragment: BaseFragment?=null
    lateinit var mListener: OnPrimerFragmentoInteractionListener
    lateinit var mNavigationManager: NavigationManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnPrimerFragmentoInteractionListener){
            mListener = context
        }else{
            throw RuntimeException("$context must implement OnPrimerFragmentoInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_primer_fragmento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavigationManager = NavigationManager(childFragmentManager, R.id.subContainer)
        mNavigationManager.openAsRoot(Subfragmento.OnSubFragmentoInteractionListener.newInstance())
    }

    override fun provideNavigationManager(): NavigationManager {
        return mNavigationManager
    }

    override fun setCurrentFragment(fragment: BaseFragment) {
        mCurrentFragment = fragment
    }

    interface OnPrimerFragmentoInteractionListener : FragmentInteractionListener {
        companion object{
            fun newInstance(): PrimerFragmento {
                val fragmento = PrimerFragmento()
                return fragmento
            }
        }
    }

}
