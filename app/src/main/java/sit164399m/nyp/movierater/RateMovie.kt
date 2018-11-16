package sit164399m.nyp.movierater

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_rate_movie.*

class RateMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_movie)

        rateMovie.text = "Enter your review for the movie: "+intent.getStringExtra("movieName")
        star1.setPadding(10,80,10,0)
        star2.setPadding(10,80,10,0)
        star3.setPadding(10,80,10,0)
        star4.setPadding(10,80,10,0)
        star5.setPadding(10,80,10,0)
    }
}
