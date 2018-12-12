package sit164399m.nyp.movierater

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_landing_page.*
import java.util.*
import android.graphics.drawable.GradientDrawable
import android.os.Parcelable
import java.io.Serializable
import kotlin.collections.ArrayList

class Movie (var movieName:String,var movieDesc:String,var language:String,var releaseDate:String,var suitable:String,
             var reason:String, var review:String, var star:Float):Serializable

public var userData:ArrayList<Movie> = arrayListOf()

class LandingPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        if(!intent?.getStringExtra("movieName").isNullOrEmpty()){
            userData.add(Movie(
                intent.getStringExtra("movieName"),
                intent.getStringExtra("movieDesc"),
                intent.getStringExtra("language"),
                intent.getStringExtra("releaseDate"),
                intent.getStringExtra("suitable"),
                intent.getStringExtra("reason"),
                intent.getStringExtra("reviewText"),
                intent.getFloatExtra("reviewStar",0f)))
            for(i in 0 until userData.size){
                val movieListItem = LinearLayout(this)
                val imageView = ImageView(this)
                imageView.setImageResource(R.drawable.movie_icon)
                imageView.layoutParams = LinearLayout.LayoutParams(180,180)
                val movieItem = TextView(this)
                movieItem.text = userData[i].movieName
                movieItem.textSize = 18f
                movieItem.gravity = Gravity.CENTER
                movieItem.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
                val gd = GradientDrawable()
                gd.cornerRadius = 0f
                gd.setStroke(1, -0x555555)
                movieListItem.background = gd
                movieListItem.addView(imageView)
                movieListItem.addView(movieItem)
                movieListItem.id=View.generateViewId()
                movieList.addView(movieListItem)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.landing,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.miAdd){
            startActivity(
                Intent(this, AddMovie::class.java)
                    .putExtra("userData", userData)
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(1,1,1,"Edit")

    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == 1)
        startActivity(
            Intent(this, AddMovie::class.java)
        )
        return super.onContextItemSelected(item)
    }

}
