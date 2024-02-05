package com.testeffectivemobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testeffectivemobile.R
import com.testeffectivemobile.ui.theme.TestEffectiveMobileTheme
import kotlinx.coroutines.flow.MutableStateFlow

fun errorUpdateMocky(mainDialog: MutableStateFlow<@Composable (() -> Unit)?>):(@Composable ()->Unit
        ) {
    return{
    AlertDialog(
        icon = {
            Image(
                modifier = Modifier
                    .aspectRatio(3f)
                    .padding(10.dp),
                painter = painterResource(R.drawable.baseline_error_outline_24),
                contentDescription = ""
            )

        },
        title = {

        },
        text = {
            Text(
                text = stringResource(id = R.string.dialog_error_update_mocky_text))
        },
        onDismissRequest = {

        },
        confirmButton = {

        },
        dismissButton = {
            TextButton(
                onClick = {
                    mainDialog.value = null
                },
            ) {
                Text(
                    text = stringResource(id = android.R.string.cancel))
            }
        }

    )

}
}

fun dialogUnderConstruction(mainDialog: MutableStateFlow<@Composable (() -> Unit)?>):(@Composable ()->Unit
        ){
    return {
        AlertDialog(
            icon = {
                Image(
                    modifier = Modifier
                        .aspectRatio(3f)
                        .padding(10.dp),
                    painter = painterResource(R.drawable.baseline_error_outline_24),
                    contentDescription = ""
                )

            },
            title = {

            },
            text = {
                UnderConstruction()
            },
            onDismissRequest = {

            },
            confirmButton = {

            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mainDialog.value = null
                    },
                ) {
                    Text(
                        text = stringResource(id = android.R.string.cancel)
                    )
                }
            }

        )
    }
}
@Preview(showBackground = true)
@Composable
fun ErrorUpdateMockyPreview() {
    TestEffectiveMobileTheme {
        errorUpdateMocky(MutableStateFlow<(@Composable ()->Unit)?>(null)).invoke()
    }
}
