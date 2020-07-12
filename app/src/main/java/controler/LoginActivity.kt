package controler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mujtaba.smackthat.R
import kotlinx.android.synthetic.main.nav_header_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }
    fun loginLoginBtnClicked(view: View){

    }
    fun loginUserBtnClicked(view: View){
        val createUserIntent = Intent(this, CreatUserActivity::class.java)
        startActivity(createUserIntent)

    }
}