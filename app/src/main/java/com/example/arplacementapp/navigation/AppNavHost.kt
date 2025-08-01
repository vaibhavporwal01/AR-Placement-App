package com.example.arplacementapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.arplacementapp.data.mockDrills
import com.example.arplacementapp.data.mockDrillsMap
import com.example.arplacementapp.ui.DrillDetailScreen
import com.example.arplacementapp.ui.DrillSelectionScreen

sealed class Screen(val route: String) {
    object DrillSelection : Screen("drill_selection")
    object DrillDetail : Screen("drill_detail/{drillId}") {
        fun createRoute(drillId: String) = "drill_detail/$drillId"
    }
}


@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.DrillSelection.route,
    onStartArActivity: (String) -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.DrillSelection.route) {
            DrillSelectionScreen(
                drills = mockDrills,
                onDrillSelected = { drill ->
                    navController.navigate(Screen.DrillDetail.createRoute(drill.id))
                }
            )
        }
        composable(Screen.DrillDetail.route) { backStackEntry ->
            val drillId = backStackEntry.arguments?.getString("drillId")
            Log.d("AppNavHost", "DrillDetail: Received drillId: $drillId")

            val startTime = System.currentTimeMillis()
            Log.d("AppNavHost", "DrillDetail: Looking up drill in mockDrillsMap...")
            val drill = if (drillId != null) mockDrillsMap[drillId] else null
            val endTime = System.currentTimeMillis()
            Log.d("AppNavHost", "DrillDetail: Found drill: $drill. Lookup took ${endTime - startTime}ms")
            if (drill != null) {
                DrillDetailScreen(
                    drill = drill,
                    onStartArClick = { selectedDrill ->
                        onStartArActivity(selectedDrill.id)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            } else {
                Log.e("AppNavHost", "DrillDetail: Drill with ID $drillId not found!")
                navController.popBackStack()
            }
        }
    }
}