package sit164399m.nyp.movierater

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.util.*

class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        movieImage.setImageResource(R.drawable.movie_icon)
        movieImage.setPadding(0,30,0,15)
        movieNameDisplay.setPadding(0,15,0,15)
        movieNameDisplay.text = intent.getStringExtra("movieName")
        movieDescDisplay.setPadding(0,15,0,15)
        movieDescDisplay.text = intent.getStringExtra("movieDesc")
        movieLanguageDisplay.setPadding(0,15,0,15)
        movieLanguageDisplay.text = intent.getStringExtra("language")
        releaseDateDisplay.setPadding(0,15,0,15)
        releaseDateDisplay.text = intent.getStringExtra("releaseDate")
        notSuitableDisplay.setPadding(0,15,0,15)
        notSuitableDisplay.text = intent.getStringExtra("suitable")
        if (notSuitableDisplay.text == "Yes"){
            reasonTitle.visibility = View.INVISIBLE
            reasonDisplay.visibility = View.INVISIBLE
        } else {
            reasonDisplay.setPadding(0,15,0,15)
            reasonDisplay.text = intent.getStringExtra("reason")
        }
    }

}
