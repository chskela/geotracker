package com.chskela.geotracker.tracker.permissions

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import com.chskela.geotracker.tracker.utils.checkIfPermissionGranted
import com.chskela.geotracker.tracker.utils.shouldShowPermissionRationale

private const val TAG = "PermissionUI"

@Composable
fun PermissionsUI(
    context: Context,
    permission: String,
    permissionRationale: String,
    scaffoldState: ScaffoldState,
    permissionAction: (PermissionAction) -> Unit
) {
    val permissionGranted = checkIfPermissionGranted(context, permission)

    if (permissionGranted) {
        Log.d(TAG, "Permission already granted, exiting..")
        permissionAction(PermissionAction.OnPermissionGranted)
        return
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.i(TAG, "PermissionsUI: $isGranted")
        if (isGranted) {
            // Permission Accepted
            permissionAction(PermissionAction.OnPermissionGranted)
        } else {
            // Permission Denied
            permissionAction(PermissionAction.OnPermissionDenied)
           }
    }

    val showPermissionRationale = shouldShowPermissionRationale(
        context,
        permission
    )
    if (showPermissionRationale) {

        LaunchedEffect(showPermissionRationale) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = permissionRationale,
                actionLabel = "Grant Access",
                duration = SnackbarDuration.Long
            )

            when (snackbarResult) {
                SnackbarResult.Dismissed -> {
                    //User denied the permission, do nothing
                    permissionAction(PermissionAction.OnPermissionDenied)
                }
                SnackbarResult.ActionPerformed -> {
                    launcher.launch(permission)
                }
            }
        }
    } else {
        //Request permissions again
        Log.d(TAG, "Requesting permission for $permission again")
        SideEffect {
            launcher.launch(permission)
        }
    }
}
