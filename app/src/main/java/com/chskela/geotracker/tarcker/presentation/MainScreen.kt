package com.chskela.geotracker.tarcker.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chskela.geotracker.R
import com.chskela.geotracker.ui.theme.GeoTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.get_started))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.to_finish_work))
            }
        }
    }
}

@Preview(showBackground = true, name = "Light DetailsTabs", showSystemUi = false, locale = "ru")
@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ru")
@Composable
fun PreviewMainScreen() {
    GeoTrackerTheme {
        MainScreen()
    }
}