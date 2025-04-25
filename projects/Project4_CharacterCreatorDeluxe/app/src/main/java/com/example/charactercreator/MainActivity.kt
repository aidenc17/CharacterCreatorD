package com.example.charactercreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.charactercreator.model.CharacterCreatorViewModel
import com.example.charactercreator.ui.CharacterCardScreen
import com.example.charactercreator.ui.SelectClassScreen
import com.example.charactercreator.ui.SpendTalentsScreen
import com.example.charactercreator.ui.theme.CharacterCreatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharacterCreatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharacterCreatorApp()
                }
            }
        }
    }
}

enum class CharacterCreatorScreen {
    SelectClass,
    SpendTalents,
    CharacterCard
}

@Composable
fun CharacterCreatorApp(
    viewModel: CharacterCreatorViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CharacterCreatorScreen.SelectClass.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = CharacterCreatorScreen.SelectClass.name) {
                SelectClassScreen(
                    onNextButtonClicked = {
                        navController.navigate(CharacterCreatorScreen.SpendTalents.name)
                    },
                    onCancelClicked = { viewModel.resetCharacter() },
                    characterCreatorViewModel = viewModel
                )
            }

            composable(route = CharacterCreatorScreen.SpendTalents.name) {
                SpendTalentsScreen(
                    onNextButtonClicked = {
                        navController.navigate(CharacterCreatorScreen.CharacterCard.name)
                    },
                    onBackButtonClicked = {
                        navController.popBackStack()
                    },
                    characterCreatorViewModel = viewModel
                )
            }

            composable(route = CharacterCreatorScreen.CharacterCard.name) {
                CharacterCardScreen(
                    onDoneButtonClicked = {
                        navController.popBackStack(CharacterCreatorScreen.SelectClass.name, false)
                    },
                    onBackButtonClicked = {
                        navController.popBackStack()
                    },
                    characterCreatorViewModel = viewModel
                )
            }
        }
    }
}
