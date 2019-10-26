package ni.devotion.manejolayouts.presentation.misc

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class NavigationManager(private val mFragmentManager: FragmentManager, private val container: Int) {
    init {
        mFragmentManager.addOnBackStackChangedListener {
            navigationListener?.invoke()
        }
    }

    val isRootFragmentVisible: Boolean
        get() = mFragmentManager.backStackEntryCount <= 1

    var navigationListener: (() -> Unit)? = null

    fun open(fragment: Fragment) = openFragment(fragment, addToBackStack = true, isRoot = false)

    fun openFragment(fragment: Fragment, addToBackStack: Boolean, isRoot: Boolean){
        val fragmentTransaction = mFragmentManager.beginTransaction()
        if (isRoot) fragmentTransaction.replace(container, fragment, "ROOT")
        else fragmentTransaction.replace(container, fragment)

        if(addToBackStack) fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.commit()
    }

    fun openAsRoot(fragment: Fragment){
        popEveryFragment()
        openFragment(fragment, addToBackStack = false, isRoot = true)
    }

    fun popEveryFragment() = mFragmentManager.popBackStack("ROOT", FragmentManager.POP_BACK_STACK_INCLUSIVE)
}