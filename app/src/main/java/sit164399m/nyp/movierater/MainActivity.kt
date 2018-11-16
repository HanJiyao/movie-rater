package sit164399m.nyp.movierater

import android.app.DatePickerDialog
import android.content.Intent
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
    data class Movie(
        var movieName:String,
        var movieDesc:String,
        var language:String,
        var releaseDate:String,
        var suitable:String,
        var suitable2: String,
        var reason:String)
    var newMovie = Movie(
        "","","","","","","")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        languageEnglish.isChecked = true
        notSuitable.isChecked = false

        val textView: TextView = findViewById(R.id.releaseDate)
        textView.text = SimpleDateFormat("dd-MM-yyyy", Locale.CHINA).format(System.currentTimeMillis())
        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd-MM-yyyy"
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
        updateMovie(v)
        if(movieName.text.isNotEmpty()&&movieDesc.text.isNotEmpty()&&releaseDate.text.isNotEmpty()) {
            Toast.makeText(
                this,
                "Title = " + newMovie.movieName +
                        "\nOverview = " + newMovie.movieDesc +
                        "\nRelease date = " + newMovie.releaseDate +
                        "\nLanguage = " + newMovie.language +
                        "\nSuitable for all ages = " + newMovie.suitable + newMovie.reason, Toast.LENGTH_LONG
            ).show()
        }
    }
    private fun updateMovie(v:View){
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
        val suitable = if(notSuitable.isChecked) {"false\nReason:\n"}else{"true"}
        val suitable2 = if(notSuitable.isChecked) {"No"}else{"Yes"}
        var reason = ""
        if(languageUsed.isChecked){reason += "Language\n"}
        if(violence.isChecked){reason += "Violence\n"}
        newMovie = Movie(
            movieName.text.toString(),
            movieDesc.text.toString(),
            language.text.toString(),
            releaseDate.text.toString(),
            suitable,
            suitable2,
            reason)

    }
    fun suitable(v:View) {
        if (notSuitable.isChecked){
            notSuitableGroup.visibility=View.VISIBLE
        } else {
            violence.isChecked=false
            languageUsed.isChecked=false
            notSuitableGroup.visibility=View.INVISIBLE
        }
    }
    fun viewDetails(v:View){
        updateMovie(v)
        if(movieName.text.isNotEmpty()&&movieDesc.text.isNotEmpty()&&releaseDate.text.isNotEmpty()) {
            startActivity(
                Intent(this, MovieDetails::class.java)
                    .putExtra("movieName", newMovie.movieName)
                    .putExtra("movieDesc", newMovie.movieDesc)
                    .putExtra("language", newMovie.language)
                    .putExtra("releaseDate", newMovie.releaseDate)
                    .putExtra("suitable", newMovie.suitable2)
                    .putExtra("reason", newMovie.reason)
            )
        }
    }
}
