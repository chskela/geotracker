package com.chskela.geotracker.tracker.presentation

import android.Manifest
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chskela.geotracker.R
import com.chskela.geotracker.tracker.permissions.PermissionAction
import com.chskela.geotracker.tracker.permissions.PermissionsUI
import com.chskela.geotracker.tracker.service.LocationService
import kotlinx.coroutines.launch

private const val TAG = "RESULT"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel) {

    val scaffoldState = rememberScaffoldState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }) { padding ->

        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val performLocationAction by mainScreenViewModel.performLocationAction.collectAsState()

        if (performLocationAction) {
            Log.d(TAG, "Invoking Permission UI")
            PermissionsUI(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                stringResource(id = R.string.permission_location_rationale),
                scaffoldState
            ) { permissionAction ->
                when (permissionAction) {
                    is PermissionAction.OnPermissionGranted -> {
                        mainScreenViewModel.setPerformLocationAction(false)

                        Log.d(TAG, "Location has been granted")
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Location permission granted!")
                        }
                    }
                    is PermissionAction.OnPermissionDenied -> {
                        mainScreenViewModel.setPerformLocationAction(false)
                    }
                }
            }

        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                mainScreenViewModel.setPerformLocationAction(true)
                Intent(context, LocationService::class.java).apply {
                    action = LocationService.ACTION_START
                    context.startService(this)

                }
            }) {
                Text(text = stringResource(id = R.string.get_started))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                Intent(context, LocationService::class.java).apply {
                    action = LocationService.ACTION_STOP
                    context.startService(this)
                }
            }) {
                Text(text = stringResource(id = R.string.to_finish_work))
            }
        }
    }
}