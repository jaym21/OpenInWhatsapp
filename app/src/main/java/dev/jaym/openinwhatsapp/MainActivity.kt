package dev.jaym.openinwhatsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {

    private lateinit var number: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //checking if the action of intent received is PROCESS_TEXT
        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            //saving the text received from intent
            number = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        }

        //checking if the text has numbers only
        if (number.isDigitsOnly()) {
            openWhatsApp(number)
        }else {
            Toast.makeText(this, "Please check selected text, it should be a number", Toast.LENGTH_LONG).show()
        }
    }

    private fun openWhatsApp(number: String) {
        //making an intent to open whatsapp
        val intentWhatsApp = Intent(Intent.ACTION_VIEW)
        //opening using whatsapp api
        intentWhatsApp.setPackage("com.whatsapp")
        //as per whatsapp api the number should of format 919087654321(i.e country code and number only)
        //first checking if the selected number has a + in it
        if (number[0] == '+') {
            //if there is + then removing it by taking a substring from 1 to last character
            number.substring(1)
        }else if (number.length == 10) {
            //if the number length is 10 that means the selected text only has number and country code is not present
            //adding the country code to the number
            "91$number"
        }

        //setting the data for intent
        //making use of whatsapp api format
        intentWhatsApp.data = Uri.parse("https://wa.me/$number")

        //checking if whatsapp is available on device
        if (packageManager.resolveActivity(intentWhatsApp, 0) != null) {
            //if it is present then starting the intent to opening number in whatsapp
            startActivity(intentWhatsApp)
        }else {
            //if it is not present on device
            Toast.makeText(this, "WhatsApp not available on device", Toast.LENGTH_LONG).show()
        }
    }
}