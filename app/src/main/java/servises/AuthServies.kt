package servises

import Utilites.URL_CREATE_USER
import Utilites.URL_LOGIN
import Utilites.URL_REGISTER
import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import servises.AuthServies.authToken

object AuthServies {
    var isLoggedIn = false
    var userEmail = ""
    var authToken = ""

    //هنا تسجيل كمستخدم جديد
    fun registerUser(
        context: Context,
        email: String,
        password: String,
        complete: (Boolean) -> Unit
    ) {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val registerRequest =
            object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response ->
                complete(true)
            }, Response.ErrorListener { error ->
                Log.d("ERROR", "could not register user: $error")
                complete(false)

            }) {
                override fun getBodyContentType(): String {
                    return "application/json;charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()
                }
            }
        Volley.newRequestQueue(context).add(registerRequest)


    }

    // هنا تسجيل الدخول كمستخدم قديم
    fun loginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val loginRequest =
            object : JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener { response ->

                println(response)
                try {
                    userEmail = response.getString("user")
                    authToken = response.getString("token")
                    isLoggedIn = true
                    complete(true)

                } catch (e: JSONException) {
                    Log.d("JSON", "EXC:" + e.localizedMessage)
                    complete(false)

                }

            }, Response.ErrorListener { error ->
                Log.d("ERROR", "could not register user: $error")
                complete(false)

            }) {
                override fun getBodyContentType(): String {
                    return "application/json;charset=utf-8"
                }

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()

                }

            }
        Volley.newRequestQueue(context).add(loginRequest)

    }

    fun createUser(
        context: Context,
        name: String,
        email: String,
        avatarName: String,
        avatarColor: String,
        complete: (Boolean) -> Unit
    ) {
        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarName", avatarName)
        jsonBody.put("avatarColor", avatarColor)
        val requestBody = jsonBody.toString()

        val creatrRequest = object :
            JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { response ->

                try {
                    userDataService.name = response.getString("name")
                    userDataService.email = response.getString("email")
                    userDataService.avatarName = response.getString("avatarName")
                    userDataService.avatarColor = response.getString("avatarColor")
                    userDataService.id = response.getString("_id")
                    complete(true)

                } catch (e: JSONException) {

                    Log.d("Json", "EXC:" + e.localizedMessage)
                    complete(false)

                }


            }, Response.ErrorListener { error ->
                Log.d("ERROR", "could not add user: $error")
                complete(false)

            }) {
            override fun getBodyContentType(): String {
                return "application/json;charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()

            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $authToken")
                return headers
            }




        }
        Volley.newRequestQueue(context).add(creatrRequest)
    }

}



