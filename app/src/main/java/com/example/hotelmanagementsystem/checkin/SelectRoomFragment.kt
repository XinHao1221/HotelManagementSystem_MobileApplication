package com.example.hotelmanagementsystem.checkin

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hotelmanagementsystem.R
import com.example.hotelmanagementsystem.databinding.FragmentCheckOutTodayBinding
import com.example.hotelmanagementsystem.databinding.FragmentSelectRoomBinding
import com.example.hotelmanagementsystem.hotelreservation.viewmodel.ReservationDatabaseViewModel
import com.example.hotelmanagementsystem.hotelreservation.viewmodel.ReservationViewModel


class SelectRoomFragment : Fragment() {

    private val sharedViewModel: ReservationViewModel by activityViewModels()
    private lateinit var reservationDatabaseViewModel: ReservationDatabaseViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSelectRoomBinding>(inflater,
            R.layout.fragment_select_room,container,false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Check In"

        reservationDatabaseViewModel = ViewModelProvider(this).get(ReservationDatabaseViewModel::class.java)

        binding.checkInBtn.setOnClickListener{
            checkIsEquipmentCheckListChecked(binding)
        }

        return binding.root
    }

    fun resetCheckBoxBackgroundColor(binding : FragmentSelectRoomBinding){
        binding.checkboxAirCon.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.checkboxHotelHandbook.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.checkboxTvCtrl.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.checkboxRoomAccessCard.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }

    fun checkIsEquipmentCheckListChecked(binding : FragmentSelectRoomBinding){

        resetCheckBoxBackgroundColor(binding)

        var isChecked:Int = 1
        if(!(binding.checkboxAirCon.isChecked)){
            binding.checkboxAirCon.setBackgroundColor(Color.parseColor("#ffdedb"))
            isChecked = 0
        }
        if(!(binding.checkboxHotelHandbook.isChecked)){
            binding.checkboxHotelHandbook.setBackgroundColor(Color.parseColor("#ffdedb"))
            isChecked = 0
        }
        if(!(binding.checkboxTvCtrl.isChecked)){
            binding.checkboxTvCtrl.setBackgroundColor(Color.parseColor("#ffdedb"))
            isChecked = 0
        }
        if(!(binding.checkboxRoomAccessCard.isChecked)){
            binding.checkboxRoomAccessCard.setBackgroundColor(Color.parseColor("#ffdedb"))
            isChecked = 0
        }

        if(isChecked == 0){

            val builder:AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Alert")
            builder.setMessage("Make sure you have checked all item in Equipment Check List")
            builder.setPositiveButton("OK"){_,_ ->}
            builder.show()

        }else{

            var reservationID:Int = sharedViewModel.reservationID.toInt()

            val builder = android.app.AlertDialog.Builder(requireContext())

            builder.setNegativeButton("No"){_, _ ->

            }

            // Confirm delete
            builder.setPositiveButton("Yes"){_, _->
                // Update reservation status to checkIn
                reservationDatabaseViewModel.updateReservationStatus("checkIn", reservationID)

                Toast.makeText(requireContext(), "Check In Successful", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_selectRoomFragment_to_checkInMenuFragment)
            }

            builder.setTitle("Check In?");

            builder.setMessage("Confirm check in " + sharedViewModel.guestName.toString() + "?")
            builder.create().show()






        }
    }

}