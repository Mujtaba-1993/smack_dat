package controler

import android.graphics.Color
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
        val userName = creatUserNameText.text.toString()
        val email = creatEmailText.text.toString()
        val password = creatPasswordText.text.toString()

        AuthServies.registerUser(this,email,password){ registerSuccess ->
            if (registerSuccess) {
                AuthServies.loginUser(this, email,password){ loginSuccess->
                    if (loginSuccess){
                       AuthServies.createUser(this,userName, email,userAvatar,avatarColor){createSuccess ->
                           if (createSuccess){
                               println(userDataService.avatarName)
                               println(userDataService.name)
                               println(userDataService.avatarColor)
                               finish()

                           }

                       }
                    }

                }

            }


        }

    }
}