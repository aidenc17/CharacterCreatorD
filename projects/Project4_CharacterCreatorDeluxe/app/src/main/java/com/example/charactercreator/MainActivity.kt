package com.example.charactercreator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.charactercreator.data.DataSource
import com.example.charactercreator.data.StatDataSource
import com.example.charactercreator.model.CharacterCreatorViewModel
import com.example.charactercreator.model.CharacterInfo
import com.example.charactercreator.model.StatNameIcon
import com.example.charactercreator.ui.theme.CharacterCreatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharacterCreatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharacterCreationApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterCreationApp(
    modifier: Modifier = Modifier,
    characterCreatorViewModel: CharacterCreatorViewModel = viewModel()
) {
    val characterCreatorUiState by characterCreatorViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {

        // radio buttons for selecting the character
        SelectCharacter(
            charInfo = characterCreatorUiState.characterInfo,
            options = DataSource.characterNames,
            onSelectionChanged = {charName -> characterCreatorViewModel.setCharacterName(charName)},
            onCancelClicked = {characterCreatorViewModel.resetCharacter()}
        )

        Spacer(Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))

        // sets of buttons for each stat
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp)
        ) {
            items(count = 4, key = {it}) { index ->
                StatButtons(
                    statStuff = StatDataSource.statStuffs[index],
                    value = "${characterCreatorUiState.stats[index]}",
                    onPlusClick = { characterCreatorViewModel.incrementStat(index) },
                    onMinusClick = { characterCreatorViewModel.decrementStat(index) }
                )
            }
        }

        Spacer(Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))

        CharacterAttributes(characterCreatorViewModel)

    }
}


/**
 * Composable that displays the list of items as [RadioButton] options,
 * [onSelectionChanged] lambda that notifies the parent composable when a new value is selected,
 * [onCancelButtonClicked] lambda that cancels the order when user clicks cancel and
 * [onNextButtonClicked] lambda that triggers the navigation to next screen
 */
@Composable
fun SelectCharacter(
    charInfo: CharacterInfo,
    options: Array<String>,
    onSelectionChanged: (String) -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer),

        verticalArrangement = Arrangement.Top

    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            options.forEach { item ->
                Row(
                    modifier = Modifier.selectable(
                        selected = selectedValue == item,
                        onClick = {
                            selectedValue = item
                            onSelectionChanged(item)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedValue == item,
                        onClick = {
                            selectedValue = item
                            onSelectionChanged(item)
                        }
                    )

                    Text(item, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium)),
                thickness = dimensionResource(R.dimen.thickness_divider)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onCancelClicked()
                    selectedValue = ""}
                ) {
                    Text(text = "Clear")
                }
                Text(
                    text = charInfo.name,
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                )
            }
        }

    }
}

/**
 * Prints the character attributes as well as the remaining points
 *
 * @param characterCreatorViewModel
 * @param modifier
 */
@Composable
fun CharacterAttributes(
    characterCreatorViewModel: CharacterCreatorViewModel,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
    ) {
        // first column prints the name of the attribute
        Column() {
            DataSource.characterAttributes.forEach { charValue ->
                Text(charValue)
                if (charValue == DataSource.characterAttributes[0]) {
                    Spacer(Modifier.padding(10.dp))
                }
            }
        }

        Spacer(Modifier.padding(10.dp))

        // second column prints the attribute
        Column() {
            DataSource.characterAttributes.forEach { charValue ->
                Text(characterCreatorViewModel.getCharacterAttribute(charValue).toString())
                if (charValue == DataSource.characterAttributes[0]) {
                    Spacer(Modifier.padding(10.dp))
                }
            }
        }
    }
}

/**
 * A set of two buttons for increasing and decreasing a value, shows the value, the stat name, and an icon
 *
 * @param statStuff
 * @param value
 * @param onPlusClick
 * @param onMinusClick
 * @param modifier
 * @receiver
 * @receiver
 */
@Composable
fun StatButtons(
    statStuff: StatNameIcon,
    value: String,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(
            onClick = onPlusClick,
        ) {
            Icon(painter = painterResource(id = R.drawable.baseline_keyboard_arrow_up_24), contentDescription = "Up button")
        }
        Row() {

            Icon(painterResource(statStuff.icon), null)
            Text(
                text = stringResource(statStuff.name)
            )
        }
        Text(
            text = value
        )
        Button(onClick = onMinusClick) {
            Icon(painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24), contentDescription = "Down button")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterCreationPreview() {
    CharacterCreatorTheme {
        CharacterCreationApp(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}