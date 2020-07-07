package controler

import Utilites.BROADCAST_USER_DATA_CHANGE
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.mujtaba.smackthat.R
import kotlinx.android.synthetic.main.activity_creat_user.*
import servises.AuthServies
import servises.userDataService
import kotlin.random.Random

class CreatUserActivity : AppCompatActivity() {

    private var userAvatar = "profileDefault"
    var avatarColor = "[0.5,0.5,0.5,1]"  // RGB RED, GREAN, BLUE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_user)
        creatSpner.visibility=View.INVISIBLE
    }
    fun gneratUserAvatar(view: View){
        val random = java.util.Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        userAvatar = if (color== 0){
            "light$avatar"

        } else {
           "dark$avatar"
        }
        val resoursId = resources.getIdentifier(userAvatar,"drawable",packageName)
        creatAvatarImagView.setImageResource(resoursId)

    }
    fun generatColorClicked(view: View){
        // هنا خليناه يطلع لنا الوان مختلفه نقدر نحطها ف الخلفيه حق الافتار
        val random = java.util.Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
        // هنا مكان الي راح تكون فيه الخلفيات
        creatAvatarImagView.setBackgroundColor(Color.rgb(r,g,b))
        // هنا حولنا الالوان الى ارقام
        val savedR = r.toDouble() / 255
        val saverG = g .toDouble() /255
        val savedB = b.toDouble() / 255
        //هنا حفظنا الون للمستخدم
        avatarColor = "[$savedR, $saverG, $savedB,1]"





    }
    fun createUserClicked(view: View){
        enableSpeaner(true)
        val userName = creatUserNameText.text.toString()
        val email = creatEmailText.text.toString()
        val password = creatPasswordText.text.toString()

        if (userName.isNotEmpty()&&email.isNotEmpty()&&password.isNotEmpty()){
            AuthServies.registerUser(this,email,password){ registerSuccess ->
                if (registerSuccess) {
                    AuthServies.loginUser(this, email,password){ loginSuccess->
                        if (loginSuccess){
                            AuthServies.createUser(this,userName, email,userAvatar,avatarColor){createSuccess ->
                                if (createSuccess){
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpeaner(false)
                                    finish()

                                }else{
                                    errorToast()
                                }

                            }
                        }else{
                            errorToast()
                        }

                    }

                }else{
                    errorToast()
                }


            }
        }else{
            Toast.makeText(this,"Mack sure username, email and password are filled in",Toast.LENGTH_LONG).show()
             enableSpeaner(false)
        }


    }
    fun errorToast(){
        Toast.makeText(this,"someThing went wrong, please try again",Toast.LENGTH_LONG).show()

        enableSpeaner(false)
    }

    fun enableSpeaner (enable:Boolean){
        if (enable){
            creatSpner.visibility=View.VISIBLE
        }else{
            creatSpner.visibility=View.INVISIBLE
        }
        createUserBtn.isEnabled=!enable
        backgroundColorBtn.isEnabled=!enable
        creatAvatarImagView.isEnabled=!enable
    }
}