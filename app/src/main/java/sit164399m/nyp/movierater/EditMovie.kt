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
import kotlinx.android.synthetic.main.activity_add_movie.*
import java.text.SimpleDateFormat
import java.util.*

class EditMovie : AppCompatActivity() {

    class Movie (var movieName:String,var movieDesc:String,var language:String,var releaseDate:String,
                 var suitable:String, var reason:String, var review:String, var star:Float)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        var userData = intent.getBundleExtra("userData")

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
            DatePickerDialog(this@EditMovie, dateSetListener,
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

    var newMovie = Movie("","","","","",
        "","",0f)

    private fun updateMovie(){
        if(movieName.text.isEmpty()){movieName.error = "Field empty"}
        if(movieDesc.text.isEmpty()){movieDesc.error = "Field empty" }
        if(releaseDate.text.isEmpty()){releaseDate.error = "Field empty" }
        val language =  radioGroup.findViewById<View>(radioGroup.checkedRadioButtonId) as RadioButton
        val suitable2 = if(notSuitable.isChecked) {"No"}else{"Yes"}
        var reason2 = ""
        if(languageUsed.isChecked){
            reason2 += "Language Used "
        }
        if(violence.isChecked){
            reason2 += "Violence "
        }
        newMovie = Movie(movieName.text.toString(), movieDesc.text.toString(), language.text.toString(),
            releaseDate.text.toString(), suitable2, reason2,"",0f)

    }

    fun checkSuitable(v: View) {
        if (notSuitable.isChecked){
            notSuitableGroup.visibility=View.VISIBLE
        } else {
            violence.isChecked=false
            languageUsed.isChecked=false
            notSuitableGroup.visibility=View.INVISIBLE
        }
    }

    private fun viewDetails(){
        updateMovie()
        if(movieName.text.isNotEmpty()&&movieDesc.text.isNotEmpty()&&releaseDate.text.isNotEmpty()) {
            startActivity(
                Intent(this, MovieDetails::class.java)
                    .putExtra("movieName", newMovie.movieName)
                    .putExtra("movieDesc", newMovie.movieDesc)
                    .putExtra("language", newMovie.language)
                    .putExtra("releaseDate", newMovie.releaseDate)
                    .putExtra("suitable", newMovie.suitable)
                    .putExtra("reason", newMovie.reason)
                    .putExtra("reviewText", newMovie.review)
                    .putExtra("reviewStar", newMovie.star)
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.miSave){
            viewDetails()
        }
        if(item?.itemId == R.id.miCancel){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
