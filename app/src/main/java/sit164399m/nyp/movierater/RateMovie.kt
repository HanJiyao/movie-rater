package sit164399m.nyp.movierater

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_rate_movie.*

class RateMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_movie)

        val actionbar = supportActionBar
        actionbar!!.title = "MovieRater"
        actionbar.setDisplayHomeAsUpEnabled(true)

        rateMovie.text = "Enter your review for the movie: "+intent.getStringExtra("movieName")
        reviewStar.rating = intent.getFloatExtra("reviewStar",0f)
        reviewText.setText(intent.getStringExtra("reviewText"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.rate,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.miSubmit){
            startActivity(
                Intent(this, MovieDetails::class.java)
                    .putExtra("movieName", intent.getStringExtra("movieName"))
                    .putExtra("movieDesc", intent.getStringExtra("movieDesc"))
                    .putExtra("language", intent.getStringExtra("language"))
                    .putExtra("releaseDate", intent.getStringExtra("releaseDate"))
                    .putExtra("suitable", intent.getStringExtra("suitable"))
                    .putExtra("reason", intent.getStringExtra("reason"))
                    .putExtra("reviewText", reviewText.text.toString())
                    .putExtra("reviewStar", reviewStar.rating)
            )
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
