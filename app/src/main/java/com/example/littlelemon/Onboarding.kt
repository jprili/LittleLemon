package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Onboarding(context: Context, navController: NavController) {
    val txt = "Let's get to know you"
    val sharedPrefs : SharedPreferences = context.getSharedPreferences(
        "littleLemon", Context.MODE_PRIVATE
    )

    var firstName: String by remember {
        mutableStateOf("")
    }

    var lastName: String by remember {
        mutableStateOf("")
    }

    var email: String by remember {
        mutableStateOf("")
    }


    Column {
        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxWidth()
                .size(90.dp)
                .padding(20.dp)
        )

        Text(
            text = txt,
            fontSize = 28.sp,
            color = Color(0xFFEDEFEE),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(start = 30.dp, end = 30.dp, top = 60.dp, bottom = 60.dp)
        )

        Text(
            text = "Personal Information",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 60.dp, bottom = 60.dp)
        )

        Column { // Form
            FormItem(
                txt = "First Name",
                value = firstName,
                onValueChange = { firstName = it }
            )

            FormItem(
                txt = "Last Name",
                value = lastName,
                onValueChange = { lastName = it }
            )

            FormItem(
                txt = "Email",
                value = email,
                onValueChange = { email = it }
            )
        }
        
        Button(
            onClick = {
                      if (isFormValid(firstName, lastName, email)) {
                          sharedPrefs
                              .edit()
                              .putBoolean("isRegistered", true)
                              .putString("firstName", firstName)
                              .putString("lastName", lastName)
                              .putString("email", email)
                              .apply()

                          Toast.makeText(
                              context,
                              "Registration Successful!",
                              Toast.LENGTH_SHORT
                          ).show()

                          navController.navigate(Home.route) {
                              popUpTo(Onboarding.route) {
                                  inclusive = true
                              }
                              launchSingleTop = true
                          }
                      } else {
                          Toast.makeText(
                              context,
                              "Invalid Details, please try again.",
                              Toast.LENGTH_SHORT
                          ).show()
                      }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFF4C314)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 40.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Register",
                color = Color(0xFF333333)
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormItem(txt: String, value: String, onValueChange: (String) -> Unit) {
    Column (
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
        Text(
            text = txt,
            fontSize = 14.sp
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.padding(bottom = 20.dp)
                .fillMaxWidth()
        )
    }
}

fun isFormValid(firstName: String, lastName: String, email: String): Boolean {
    val formData: List<String> = listOf(firstName, lastName, email)
    return !( formData.any { it.isEmpty() } ) // return true if any of the list is not empty
}


@Composable
@Preview
fun OnboardingPreview() {
}