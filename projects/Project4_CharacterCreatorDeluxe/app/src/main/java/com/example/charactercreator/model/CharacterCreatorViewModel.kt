package com.example.charactercreator.model

import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.lifecycle.ViewModel
import com.example.charactercreator.data.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterCreatorViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(CharacterCreatorUiState())
    val uiState = _uiState.asStateFlow()


    fun setCharacterName(newName: String) {
        _uiState.update { currentState ->
            val characterInfo = DataSource.loadCharacters()[newName] ?: CharacterInfo()
            currentState.copy(characterInfo = characterInfo,
                stats = DataSource.statValues[DataSource.characterNames.indexOf(newName)])

        }
    }

    fun resetCharacter() {
        _uiState.update {  CharacterCreatorUiState() }
    }

    private fun statSum(): Int {
        return uiState.value.stats.sum()
    }

    fun getPointsRemaining(): Int {
        return _uiState.value.maxPoints - statSum()
    }

    private fun compStatsMaxPoints(): Boolean {
        return statSum() < _uiState.value.maxPoints
    }

    fun incrementStat(stat: String) {
//        if (compStatsMaxPoints()) {
//            _uiState.update { currentState ->
//                when (stat) {
//                    //"stamina" -> currentState.copy(s = currentState.stamina + 1)
//                    "strength" -> currentState.copy(strength = currentState.strength + 1)
//                    "agility" -> currentState.copy(agility = currentState.agility + 1)
//                    "intellect" -> currentState.copy(intellect = currentState.intellect + 1)
//                    else -> currentState
//                }
//            }
//        }
    }

    fun decrementStat(stat: String) {
//        _uiState.update { currentState ->
//            when (stat) {
//                //"stamina" -> currentState.copy(stamina = if (currentState.stamina - 1 > 0) currentState.stamina - 1 else 0)
//                "strength" -> currentState.copy(strength = if (currentState.strength - 1 > 0) currentState.strength - 1 else 0)
//                "agility" -> currentState.copy(agility = if (currentState.agility - 1 > 0) currentState.agility - 1 else 0)
//                "intellect" -> currentState.copy(intellect = if (currentState.intellect - 1 > 0) currentState.intellect - 1 else 0)
//                else -> currentState
//            }
//        }
    }

    fun incrementStat(statIdx: Int) {
        if (compStatsMaxPoints()) {
            _uiState.update { currentState ->
                val tempStats = currentState.stats.clone()
                tempStats[statIdx]++
                currentState.copy(stats = tempStats)
            }
        }
    }

    fun decrementStat(statIdx: Int) {
        val minStatValue = DataSource.statValues.getOrNull(DataSource.characterNames.indexOf(_uiState.value.characterInfo.name))?.getOrElse(index = statIdx, defaultValue = {0}) ?: 0
        if (_uiState.value.stats[statIdx] > minStatValue ) {
            _uiState.update { currentState ->
                val tempStats = currentState.stats.clone()
                tempStats[statIdx]--
                currentState.copy(stats = tempStats)
            }
        }
    }

    fun getHitPoints() : Int {
        // 2 * stamina + strength + agility
        return 2 * uiState.value.stats[0] + uiState.value.stats[1] + uiState.value.stats[2]
    }

    fun getHealthRegen() : Int {
        // 2 * stamina + agility + intellect
        return 2 * uiState.value.stats[0] + uiState.value.stats[2] + uiState.value.stats[3]
    }

    fun getPhysicalDmg() : Int {
        // 2 * strength + agility
        return 2 * uiState.value.stats[1] + uiState.value.stats[2]
    }

    fun getMagicDmg() : Int {
        // 2 * intellect + agility
        return 2 * uiState.value.stats[3] + uiState.value.stats[2]
    }

    fun getCharacterAttribute(charVal: String): Int {
        return when (charVal) {
            "Points remaining:" -> getPointsRemaining()
            "Hit points:" -> getHitPoints()
            "Health regeneration:" -> getHealthRegen()
            "Physical Damage:" -> getPhysicalDmg()
            "Magic Damage:" -> getMagicDmg()
            else -> -1
        }
    }

    init {
        resetCharacter()
    }
}