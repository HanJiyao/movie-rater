package sit164399m.nyp.movierater

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {

    var reviewText = ""
    var reviewStar = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_details)
        movieImage.setImageResource(R.drawable.movie_icon)
        movieNameDisplay.text = intent.getStringExtra("movieName")
        movieDescDisplay.text = intent.getStringExtra("movieDesc")
        movieLanguageDisplay.text = intent.getStringExtra("language")
        releaseDateDisplay.text = intent.getStringExtra("releaseDate")
        notSuitableDisplay.text = intent.getStringExtra("suitable")
        if (notSuitableDisplay.text == "No"){
            val notSuitableText = "No"+" ( "+intent.getStringExtra("reason")+")"
            notSuitableDisplay.text = notSuitableText
        }
        if(intent.getFloatExtra("reviewStar",0f)!=0f){
            reviewStarDisplay.rating = intent.getFloatExtra("reviewStar",0f)
            reviewStarDisplay.visibility = View.VISIBLE
        }
        if(intent.getStringExtra("reviewText").isNotEmpty()){
            reviewDisplay.text = intent.getStringExtra("reviewText")
        }

        val actionbar = supportActionBar
        actionbar!!.title = "MovieRater"
        actionbar.setDisplayHomeAsUpEnabled(true)

        registerForContextMenu(reviewSection)

    }

    override fun onSupportNavigateUp(): Boolean {
        val reviewText:String;
        if(reviewStarDisplay.rating==0f) {
            reviewText = ""
        }else {
            reviewText = reviewDisplay.text.toString()
        }
        System.out.print(reviewDisplay.text)
        setResult(Activity.RESULT_OK, Intent()
                .putExtra("movieName", intent.getStringExtra("movieName"))
                .putExtra("movieDesc", intent.getStringExtra("movieDesc"))
                .putExtra("language", intent.getStringExtra("language"))
                .putExtra("releaseDate", intent.getStringExtra("releaseDate"))
                .putExtra("suitable", intent.getStringExtra("suitable"))
                .putExtra("reason", intent.getStringExtra("reason"))
                .putExtra("reviewText", reviewText)
                .putExtra("reviewStar", reviewStarDisplay.rating)
        )
        finish()
        return true
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        if(v?.id == R.id.reviewSection ){
            if(intent.getStringExtra("reviewText").isNullOrEmpty()&&intent.getFloatExtra("reviewStar",0f)==0f)
                menu?.add(1,1001,1,"Add Review")
            else
                menu?.add(1,1001,1,"Edit Review")
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        startActivityForResult(Intent(this, RateMovie::class.java)
            .putExtra("movieName", intent.getStringExtra("movieName"))
            .putExtra("reviewText", intent.getStringExtra("reviewText"))
            .putExtra("reviewStar", intent.getFloatExtra("reviewStar",0f))
            , 1024)
        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1024) {
            if (resultCode == Activity.RESULT_OK) {
                    reviewStarDisplay.rating = data!!.getFloatExtra("reviewStar", 0f)
                    reviewStarDisplay.visibility = View.VISIBLE
                    reviewDisplay.text = data.getStringExtra("reviewText")

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
