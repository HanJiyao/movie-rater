package sit164399m.nyp.movierater

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

class LandingPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        if(!intent?.getStringExtra("movieName").isNullOrEmpty()){
            if(intent?.getIntExtra("movieIndex",0)==0){
                userData.add(Movie(
                    intent?.getStringExtra("movieName"),
                    intent?.getStringExtra("movieDesc"),
                    intent?.getStringExtra("language"),
                    intent?.getStringExtra("releaseDate"),
                    intent?.getStringExtra("suitable"),
                    intent?.getStringExtra("reason"),
                    intent?.getStringExtra("reviewText"),
                    intent?.getFloatExtra("reviewStar",0f)))
            } else {
                userData[intent.getIntExtra("movieIndex", 0)-1] = Movie(
                    intent.getStringExtra("movieName"),
                    intent.getStringExtra("movieDesc"),
                    intent.getStringExtra("language"),
                    intent.getStringExtra("releaseDate"),
                    intent.getStringExtra("suitable"),
                    intent.getStringExtra("reason"),
                    intent.getStringExtra("reviewText"),
                    intent.getFloatExtra("reviewStar", 0f)
                )
            }

        }
        if (userData.size!=0){
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
                    startActivity(
                        Intent(this, MovieDetails::class.java)
                            .putExtra("movieIndex",i+1)
                            .putExtra("movieName", userData[i].movieName)
                            .putExtra("movieDesc", userData[i].movieDesc)
                            .putExtra("language", userData[i].language)
                            .putExtra("releaseDate", userData[i].releaseDate)
                            .putExtra("suitable", userData[i].suitable)
                            .putExtra("reason", userData[i].reason)
                            .putExtra("reviewText", userData[i].review)
                            .putExtra("reviewStar", userData[i].star)
                    )
                }
                // Create an array of the attributes we want to resolve
                // using values from a theme
                // android.R.attr.selectableItemBackground requires API LEVEL 11
                val attrs = intArrayOf(android.R.attr.selectableItemBackground /* index 0 */)

                // Obtain the styled attributes. 'themedContext' is a context with a
                // theme, typically the current Activity (i.e. 'this')
                val ta = obtainStyledAttributes(attrs)

                // Now get the value of the 'listItemBackground' attribute that was
                // set in the theme used in 'themedContext'. The parameter is the index
                // of the attribute in the 'attrs' array. The returned Drawable
                // is what you are after
                val drawableFromTheme = ta.getDrawable(0 /* index */)

                // Finally free resources used by TypedArray
                ta.recycle()

                // setBackground(Drawable) requires API LEVEL 16,
                // otherwise you have to use deprecated setBackgroundDrawable(Drawable) method.
                movieListItem.background = drawableFromTheme
                // imageButton.setBackgroundDrawable(drawableFromTheme);
                movieListItem.isClickable = true
                registerForContextMenu(movieListItem)
                movieList.addView(movieListItem)
                movieList.addView(spaceLine)
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
        for(i in 0 until userData.size){
            if(v?.id == i)
                menu?.add(1,1000+i,1,"Edit")
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        for(i in 0 until userData.size){
            if(item?.itemId == 1000+i)
                startActivity(
                    Intent(this, EditMovie::class.java)
                        .putExtra("movieIndex",i+1)
                        .putExtra("movieName", userData[i].movieName)
                        .putExtra("movieDesc", userData[i].movieDesc)
                        .putExtra("language", userData[i].language)
                        .putExtra("releaseDate", userData[i].releaseDate)
                        .putExtra("suitable", userData[i].suitable)
                        .putExtra("reason", userData[i].reason)
                        .putExtra("reviewText", userData[i].review)
                        .putExtra("reviewStar", userData[i].star)
                )
        }
        return super.onContextItemSelected(item)
    }

}
