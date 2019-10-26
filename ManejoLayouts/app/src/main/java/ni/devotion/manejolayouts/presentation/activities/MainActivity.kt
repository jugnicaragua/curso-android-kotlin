package ni.devotion.manejolayouts.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ni.devotion.manejolayouts.R
import ni.devotion.manejolayouts.presentation.base.BaseFragment
import ni.devotion.manejolayouts.presentation.fragments.PrimerFragmento
import ni.devotion.manejolayouts.presentation.fragments.Subfragmento
import ni.devotion.manejolayouts.presentation.misc.HasNavigationManager
import ni.devotion.manejolayouts.presentation.misc.NavigationManager

class MainActivity : AppCompatActivity(), HasNavigationManager,
                     PrimerFragmento.OnPrimerFragmentoInteractionListener,
                     Subfragmento.OnSubFragmentoInteractionListener{

    lateinit var mNavigationManager: NavigationManager
    var mCurrentFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNavigationManager = NavigationManager(supportFragmentManager,
            R.id.mainContainer)
        savedInstanceState ?: mNavigationManager.
            openAsRoot(PrimerFragmento.OnPrimerFragmentoInteractionListener.
                newInstance())
    }

    override fun provideNavigationManager(): NavigationManager {
        return mNavigationManager
    }

    override fun setCurrentFragment(fragment: BaseFragment) {
        mCurrentFragment = fragment
    }
}
