package com.kazim.dictionaryapp.MainFragments

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.kazim.dictionaryapp.Login.LoginScreen
import com.kazim.dictionaryapp.MainMenu
import com.kazim.dictionaryapp.databinding.FragmentSettingsBinding
import java.util.*


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signOutButton: Button = binding.btSignOut
        signOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            requireActivity().finish()
            startActivity(Intent(requireContext(), LoginScreen::class.java))
        }

        binding.btNotification.setOnClickListener {
            (activity as MainMenu).setAlarm { timeInMillis ->
                (activity as MainMenu).alarmService.setExactAlarm(timeInMillis)
            }

        }
        binding.btRepetitive.setOnClickListener {
            (activity as MainMenu).setAlarm { (activity as MainMenu).alarmService.setRepetitiveAlarm(it) }
        }

        binding.btCancelNotifications.setOnClickListener {
            (activity as MainMenu).alarmService.cancelAlarm()
            Toast.makeText(requireContext(),"All notifications are cancelled",Toast.LENGTH_LONG).show()
        }
    }




    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}