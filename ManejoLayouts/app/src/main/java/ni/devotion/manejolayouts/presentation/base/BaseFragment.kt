package ni.devotion.manejolayouts.presentation.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import ni.devotion.manejolayouts.presentation.misc.FragmentInteractionListener
import ni.devotion.manejolayouts.presentation.misc.HasNavigationManager
import ni.devotion.manejolayouts.presentation.misc.NavigationManager
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

open class BaseFragment : Fragment() {
    private lateinit var navigationManagerInner: NavigationManager
    lateinit var fragmentInteractionInner: FragmentInteractionListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        navigationManagerInner = findNavigationManagerInner()
        if(context is Activity) fragmentInteractionInner = context as FragmentInteractionListener
        else throw RuntimeException("Activity host must implement FragmentInteractionListener")
    }

    fun findNavigationManagerInner(): NavigationManager{
        var parentFragment: Fragment? = BaseFragment@this
        while (true){
            parentFragment = parentFragment?.parentFragment
            parentFragment ?: break
            if (parentFragment is HasNavigationManager){
                return (parentFragment as HasNavigationManager).provideNavigationManager()
            }
        }
        if(context is HasNavigationManager)
            return (context as HasNavigationManager).provideNavigationManager()
        throw IllegalArgumentException("No NavigationManager was found")
    }

    override fun onStart() {
        super.onStart()
        fragmentInteractionInner.setCurrentFragment(this)
    }
}