package sit164399m.nyp.movierater

import android.app.Activity
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
        val ratingText = "Enter your review for the movie: "+intent.getStringExtra("movieName")
        rateMovie.text = ratingText
        reviewStar.rating = intent.getFloatExtra("reviewStar",0f)
        reviewText.setText(intent.getStringExtra("reviewText"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.rate,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.miSubmit){
            if (reviewStar.rating != 0f && reviewText.text.isNotEmpty() ) {
                setResult(Activity.RESULT_OK, Intent()
                    .putExtra("reviewText", reviewText.text.toString())
                    .putExtra("reviewStar", reviewStar.rating))
            } else {
                setResult(Activity.RESULT_CANCELED)
            }
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
