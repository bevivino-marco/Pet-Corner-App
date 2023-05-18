package com.petcorner.petcorner.view

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SitterCheckBox() {

    val contextForToast = LocalContext.current.applicationContext

    var checked by remember {
        mutableStateOf(false)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked_ ->
                checked = checked_
                if (checked == true)
                    Toast.makeText(
                        contextForToast,
                        "Sarai registrato come Sitter",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    Toast.makeText(
                        contextForToast,
                        "Non sarai registrato come Sitter",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        )

        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = "Sitter"
        )
    }
}