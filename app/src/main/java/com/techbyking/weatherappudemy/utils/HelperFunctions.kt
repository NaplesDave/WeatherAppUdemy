package com.techbyking.weatherappudemy.utils

import android.util.Log
import com.techbyking.weatherappudemy.data.CurrentWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Takes the JAVA date and converts it to a string date code from the JASON

// I changed the return type from Int? to Number?
// Number is PARENT TYPE of Int e so it will work

fun getFormattedDate(dt: Number, pattern: String = "dd/MM/yyyy") :
        String {
    return SimpleDateFormat(pattern,Locale.getDefault()).format(Date(dt
        .toLong() * 1000))

}

// Get Weather Icon Graphic and build URL for request

fun getIconUrl(icon: String) : String {
    //Log.d("Icon Number", "$icon")
    return "https://openweathermap.org/img/wn/${icon}@2x.png"
}
