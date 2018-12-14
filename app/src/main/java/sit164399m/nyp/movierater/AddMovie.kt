package sit164399m.nyp.movierater

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_movie.*
import java.text.SimpleDateFormat
import java.util.*

class AddMovie : AppCompatActivity() {

    class Movie (var movieName:String,
                 var movieDesc:String,
                 var language:String,
                 var releaseDate:String,
                 private var suitable:String,
                 var suitable2:String,
                 var reason:String,
                 var reason2:String,
                 var review:String,
                 var star:Float){
        fun displayToast():String{
            return  "Title = " + this.movieName +
                    "\nOverview = " + this.movieDesc +
                    "\nRelease date = " + this.releaseDate +
                    "\nLanguage = " + this.language +
                    "\nSuitable for all ages = " + this.suitable + this.reason
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

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
            DatePickerDialog(this@AddMovie, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val actionbar = supportActionBar
        actionbar!!.title = "MovieRater"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private var newMovie = Movie("","","","","","",
        "","","",0f)

    private fun updateMovie(){
        if(movieName.text.isEmpty()){movieName.error = "Field empty"}
        if(movieDesc.text.isEmpty()){movieDesc.error = "Field empty" }
        if(releaseDate.text.isEmpty()){releaseDate.error = "Field empty" }
        val language =  radioGroup.findViewById<View>(radioGroup.checkedRadioButtonId) as RadioButton
        val suitable = if(notSuitable.isChecked) {"false\nReason:\n"}else{"true"}
        val suitable2 = if(notSuitable.isChecked) {"No"}else{"Yes"}
        var reason = ""
        var reason2 = "No Reason Specified "
        if(languageUsed.isChecked){reason += "Language\n" }
        if(violence.isChecked){ reason += "Violence\n" }
        if(languageUsed.isChecked&&!violence.isChecked){
            reason2 = "Language Used "
        } else if(violence.isChecked&&!languageUsed.isChecked){
            reason2 = "Violence "
        } else if(violence.isChecked&&languageUsed.isChecked){
            reason2 = "Language Used and Violence "
        }
        newMovie = Movie(movieName.text.toString(), movieDesc.text.toString(), language.text.toString(),
            releaseDate.text.toString(), suitable, suitable2, reason, reason2,"",0f)

    }

    fun checkSuitable(v: View) {
        v.display
        if (notSuitable.isChecked){
            notSuitableGroup.visibility=View.VISIBLE
        } else {
            violence.isChecked=false
            languageUsed.isChecked=false
            notSuitableGroup.visibility=View.INVISIBLE
        }
    }

    private fun clearScreen(){
        movieName.text.clear()
        movieDesc.text.clear()
        releaseDate.text.clear()
        languageEnglish.isChecked = true
        notSuitable.isChecked = false
        notSuitableGroup.visibility=View.INVISIBLE
        violence.isChecked=false
        languageUsed.isChecked=false
    }

    private fun addMovie(){
        updateMovie()
        if(movieName.text.isNotEmpty()&&movieDesc.text.isNotEmpty()&&releaseDate.text.isNotEmpty()) {
            Toast.makeText(
                this, newMovie.displayToast()
                , Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun viewDetails(){
        updateMovie()
        if(movieName.text.isNotEmpty()&&movieDesc.text.isNotEmpty()&&releaseDate.text.isNotEmpty()) {
            movieIndex = 0
            startActivity(
                Intent(this, MovieDetails::class.java)
                    .putExtra("movieName", newMovie.movieName)
                    .putExtra("movieDesc", newMovie.movieDesc)
                    .putExtra("language", newMovie.language)
                    .putExtra("releaseDate", newMovie.releaseDate)
                    .putExtra("suitable", newMovie.suitable2)
                    .putExtra("reason", newMovie.reason2)
                    .putExtra("reviewText", newMovie.review)
                    .putExtra("reviewStar", newMovie.star)
                    .addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            )
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.miAdd){
            addMovie()
            viewDetails()
        }
        if(item?.itemId == R.id.miClear){
            clearScreen()
        }
        return super.onOptionsItemSelected(item)
    }
}
