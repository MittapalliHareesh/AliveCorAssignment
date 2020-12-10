package com.example.alivecortask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.alivecortask.model.UserModel
import com.example.alivecortask.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).viewModelActivity.getUserData()
            .observe(viewLifecycleOwner, Observer { user ->
                var display: String =
                    getString(R.string.user_firstName) + " : " + user.firstName + "\n \n" + getString(
                        R.string.user_lastName
                    ) + " : " + user.secondName + "\n \n" + getString(R.string.user_age) + " : " + user.dob
                tv_second.text = display
            })
    }
}