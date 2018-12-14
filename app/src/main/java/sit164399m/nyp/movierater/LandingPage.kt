package sit164399m.nyp.movierater

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_landing_page.*
import java.io.Serializable
import kotlin.collections.ArrayList


class Movie (var movieName:String?,var movieDesc:String?,var language:String?,var releaseDate:String?,var suitable:String?,
             var reason:String?, var review:String?, var star:Float?):Serializable

var userData:ArrayList<Movie> = arrayListOf()
var movieIndex = 0

class LandingPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.landing,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.miAdd){
            startActivityForResult(
                Intent(this, AddMovie::class.java)
                    .putExtra("userData", userData),2048
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2048) {
            if (resultCode == Activity.RESULT_OK) {
                if(movieIndex==0){
                    userData.add(Movie(
                        data!!.getStringExtra("movieName"),
                        data.getStringExtra("movieDesc"),
                        data.getStringExtra("language"),
                        data.getStringExtra("releaseDate"),
                        data.getStringExtra("suitable"),
                        data.getStringExtra("reason"),
                        data.getStringExtra("reviewText"),
                        data.getFloatExtra("reviewStar",0f)))
                } else {
                    userData[movieIndex-1] = Movie(
                        data!!.getStringExtra("movieName"),
                        data.getStringExtra("movieDesc"),
                        data.getStringExtra("language"),
                        data.getStringExtra("releaseDate"),
                        data.getStringExtra("suitable"),
                        data.getStringExtra("reason"),
                        data.getStringExtra("reviewText"),
                        data.getFloatExtra("reviewStar", 0f)
                    )
                }
                if (userData.size!=0){
                    movieList.removeAllViews()
                    for(i in 0 until userData.size){
                        val movieListItem = LinearLayout(this)
                        val imageView = ImageView(this)
                        imageView.setImageResource(R.drawable.movie_icon)
                        imageView.layoutParams = LinearLayout.LayoutParams(180,180)
                        val movieText = TextView(this)
                        movieText.text = userData[i].movieName
                        movieText.textSize = 18f
                        movieText.gravity = Gravity.CENTER
                        movieText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT)
                        val spaceLine = View(this)
                        spaceLine.layoutParams = ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,5)
                        spaceLine.setBackgroundColor(Color.parseColor("#e0e0e0"))
                        movieListItem.addView(imageView)
                        movieListItem.addView(movieText)
                        movieListItem.id=i
                        movieListItem.setOnClickListener {
                            movieIndex = i+1
                            startActivityForResult(
                                Intent(this, MovieDetails::class.java)
                                    .putExtra("movieName", userData[i].movieName)
                                    .putExtra("movieDesc", userData[i].movieDesc)
                                    .putExtra("language", userData[i].language)
                                    .putExtra("releaseDate", userData[i].releaseDate)
                                    .putExtra("suitable", userData[i].suitable)
                                    .putExtra("reason", userData[i].reason)
                                    .putExtra("reviewText", userData[i].review)
                                    .putExtra("reviewStar", userData[i].star),2048
                            )
                        }
                        val attrs = intArrayOf(android.R.attr.selectableItemBackground)
                        val ta = obtainStyledAttributes(attrs)
                        val drawableFromTheme = ta.getDrawable(0)
                        ta.recycle()
                        movieListItem.background = drawableFromTheme
                        movieListItem.isClickable = true
                        registerForContextMenu(movieListItem)
                        movieList.addView(movieListItem)
                        movieList.addView(spaceLine)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        for(i in 0 until userData.size){
            if(v?.id == i)
                menu?.add(1,1000+i,1,"Edit")
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        for(i:Int in 0 until userData.size){
            if(item?.itemId == 1000+i) {
                movieIndex = i+1
                startActivityForResult(
                    Intent(this, EditMovie::class.java)
                        .putExtra("movieName", userData[i].movieName)
                        .putExtra("movieDesc", userData[i].movieDesc)
                        .putExtra("language", userData[i].language)
                        .putExtra("releaseDate", userData[i].releaseDate)
                        .putExtra("suitable", userData[i].suitable)
                        .putExtra("reason", userData[i].reason)
                        .putExtra("reviewText", userData[i].review)
                        .putExtra("reviewStar", userData[i].star), 2048
                )
            }
        }
        return super.onContextItemSelected(item)
    }

}
