package com.example.zenithrajbhandari_assign1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zenithrajbhandari_assign1.ui.theme.Zenithrajbhandari_assign1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Zenithrajbhandari_assign1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val userNameValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val emailValue = remember {
        mutableStateOf(TextFieldValue())
    }

    val studentIDValue = remember {
        val defaultStudentID = getId()
        mutableStateOf(TextFieldValue(text = defaultStudentID))
    }

    val store = DataStoreManager(context)

    Column(
        modifier = Modifier.clickable { keyboardController?.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Assignment 1", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = userNameValue.value,
            onValueChange = { userNameValue.value = it },
            label = {Text("Username")},
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = emailValue.value,
            onValueChange = { emailValue.value = it },
            label = { Text("Email") },
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = studentIDValue.value,
            onValueChange = { studentIDValue.value = it },
            label = { Text("ID") },
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column {
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val loadedUserName = store.getUsername.first()
                        val loadedEmail = store.getEmail.first()
                        val loadedStudentId = store.getStudentID.first()

                        userNameValue.value = TextFieldValue(loadedUserName)
                        emailValue.value = TextFieldValue(loadedEmail)
                        studentIDValue.value = TextFieldValue(loadedStudentId)
                    }
                }
            ) {
                Text(text = "Load")
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        store.saveData(userNameValue.value.text, emailValue.value.text, studentIDValue.value.text)
                    }
                }
            ) {
                Text(text = "Save")
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        store.clearData()
                    }
                }
            ) {
                Text(text = "Clear")
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

        Divider()
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Zenith Rajbhandari", fontWeight = FontWeight.Bold)
        Text(text = "310373152", fontWeight = FontWeight.Bold)

    }

}
private fun getId(): String{
    val studentID = "310373152"
    return studentID.takeLast(3)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Zenithrajbhandari_assign1Theme {
        MainScreen()
    }
}