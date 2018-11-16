package sit164399m.nyp.movierater

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        languageEnglish.isChecked = true
        notSuitable.isChecked = false

        val textView: TextView = findViewById(R.id.releaseDate)
        textView.text = SimpleDateFormat("dd-MM-yyyy", Locale.CHINA).format(System.currentTimeMillis())

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd-MM-yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.text = sdf.format(cal.time)

        }

        textView.setOnClickListener {
            DatePickerDialog(this@MainActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    fun addMovie(v:View){
        if(movieName.text.isEmpty()){
            movieName.error = "Field empty"
        }
        if(movieDesc.text.isEmpty()){
            movieDesc.error = "Field empty"
        }
        if(releaseDate.text.isEmpty()){
            releaseDate.error = "Field empty"
        }
        val language =  radioGroup.findViewById<View>(radioGroup.checkedRadioButtonId) as RadioButton
        val suitable = if(notSuitable.isChecked) {"false\nReason:"}else{"true"}
        var reason = ""
        if(languageUsed.isChecked){reason += "\nLanguage"}
        if(violence.isChecked){reason += "\nViolence"}
        if(movieName.text.isNotEmpty()&&movieDesc.text.isNotEmpty()&&releaseDate.text.isNotEmpty()){
            Toast.makeText(this,
                "Title = "+movieName.text+
                    "\nOverview = "+movieDesc.text+
                    "\nRelease date = "+releaseDate.text+
                    "\nLanguage = "+language.text+
                    "\nSuitable for all ages = "+suitable+
                    reason,Toast.LENGTH_LONG).show()
        }
    }

    fun suitable(v:View){
        if (notSuitable.isChecked){
            notSuitableGroup.visibility=View.VISIBLE
        } else {
            violence.isChecked=false
            languageUsed.isChecked=false
            notSuitableGroup.visibility=View.INVISIBLE
        }
    }
}
