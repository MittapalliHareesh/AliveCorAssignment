package com.example.alivecortask

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.alivecortask.model.UserModel
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_next).setOnClickListener {
            when {
                TextUtils.isEmpty(firstName.editText?.text.toString()) -> showMessage(getString(R.string.first_name_not_empty))
                TextUtils.isEmpty(lastName.editText?.text.toString()) -> showMessage(getString(R.string.last_name_not_empty))
                else -> {
                    (activity as MainActivity).viewModelActivity.setUserData(
                            UserModel(
                                    firstName.editText?.text.toString(),
                                    lastName.editText?.text.toString(),
                                    ageCalculator(datePicker.dayOfMonth, datePicker.month + 1, datePicker.year)
                            )
                    )
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            }
        }
    }

    private fun showMessage(message: String) = Toast.makeText(this.activity, message, Toast.LENGTH_LONG).show()

    private fun ageCalculator(day: Int, month: Int, year: Int): String {
        val today = Calendar.getInstance()
        val birth = Calendar.getInstance()
        birth[year, month] = day
        val temp = Calendar.getInstance()
        temp[year, month] = day
        var totalDays = 0

        var intMonth = 0
        var intDays = 0

        for (iYear in birth[Calendar.YEAR]..today[Calendar.YEAR]) {
            if (iYear == today[Calendar.YEAR] && iYear == birth[Calendar.YEAR]) {
                for (iMonth in birth[Calendar.MONTH]..today[Calendar.MONTH]) {
                    temp[iYear, iMonth] = 1
                    if (iMonth == today[Calendar.MONTH] && iMonth == birth[Calendar.MONTH]) {
                        totalDays += today[Calendar.DAY_OF_MONTH] - birth[Calendar.DAY_OF_MONTH]
                    } else if (iMonth != today[Calendar.MONTH] && iMonth != birth[Calendar.MONTH]
                    ) {
                        totalDays += temp.getActualMaximum(Calendar.DAY_OF_MONTH)
                        intMonth++
                    } else if (iMonth == birth[Calendar.MONTH]) {
                        totalDays += birth.getActualMaximum(Calendar.DAY_OF_MONTH) - birth[Calendar.DAY_OF_MONTH]
                    } else if (iMonth == today[Calendar.MONTH]) {
                        totalDays += today[Calendar.DAY_OF_MONTH]
                        if (birth[Calendar.DAY_OF_MONTH] < today[Calendar.DAY_OF_MONTH]) {
                            intMonth++
                            intDays =
                                today[Calendar.DAY_OF_MONTH] - birth[Calendar.DAY_OF_MONTH]
                        } else {
                            temp[today[Calendar.YEAR], today[Calendar.MONTH] - 1] = 1
                            intDays =
                                temp.getActualMaximum(Calendar.DAY_OF_MONTH) - birth[Calendar.DAY_OF_MONTH] + today[Calendar.DAY_OF_MONTH]
                        }
                    }
                }
            } else if (iYear != today[Calendar.YEAR] && iYear != birth[Calendar.YEAR]) {
                for (iMonth in 0..11) {
                    temp[iYear, iMonth] = 1
                    totalDays += temp.getActualMaximum(Calendar.DAY_OF_MONTH)
                    intMonth++
                }
            } else if (iYear == birth[Calendar.YEAR]) {
                for (iMonth in birth[Calendar.MONTH]..11) {
                    temp[iYear, iMonth] = 1
                    totalDays += if (iMonth == birth[Calendar.MONTH]) {
                        birth.getActualMaximum(Calendar.DAY_OF_MONTH) - birth[Calendar.DAY_OF_MONTH]
                    } else {
                        intMonth++
                        temp.getActualMaximum(Calendar.DAY_OF_MONTH)
                    }
                }
            } else if (iYear == today[Calendar.YEAR]) {
                for (iMonth in 0..today[Calendar.MONTH]) {
                    temp[iYear, iMonth] = 1
                    if (iMonth == today[Calendar.MONTH]) {
                        totalDays += today[Calendar.DAY_OF_MONTH]
                        if (birth[Calendar.DAY_OF_MONTH] < today[Calendar.DAY_OF_MONTH]) {
                            intMonth++
                            intDays =
                                today[Calendar.DAY_OF_MONTH] - birth[Calendar.DAY_OF_MONTH]
                        } else {
                            temp[today[Calendar.YEAR], today[Calendar.MONTH] - 1] = 1
                            intDays =
                                temp.getActualMaximum(Calendar.DAY_OF_MONTH) - birth[Calendar.DAY_OF_MONTH] + today[Calendar.DAY_OF_MONTH]
                        }
                    } else {
                        intMonth++
                        totalDays += temp.getActualMaximum(Calendar.DAY_OF_MONTH)
                    }
                }
            }
        }

        val ageYear = intMonth / 12
        val ageMonth = intMonth % 12
        val ageDays = intDays
        return ageYear.toString() + "Years, " + ageMonth.toString() + "Months, " + ageDays.toString() + "Days"
    }
}