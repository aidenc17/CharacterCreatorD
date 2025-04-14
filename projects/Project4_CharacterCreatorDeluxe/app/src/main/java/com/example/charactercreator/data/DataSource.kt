package com.example.charactercreator.data

import com.example.charactercreator.model.CharacterInfo

object DataSource {
    val MAX_STATS = 25

    val characterAttributes = listOf(
        "Points remaining:",
        "Hit points:",
        "Health regeneration:",
        "Physical Damage:",
        "Magic Damage:"
    )

    val characterNames = arrayOf(
        "Warrior",
        "Rogue",
        "Paladin",
        "Mage"
    )

    val statNames = arrayOf(
        "stamina",
        "strength",
        "agility",
        "intellect"
    )
    val statValues = arrayOf(
        arrayOf(8, 7, 3, 2),
        arrayOf(3, 3, 8, 6),
        arrayOf(5, 6, 4, 5),
        arrayOf(2, 3, 4, 11)
    )


    fun loadCharacters(): Map<String, CharacterInfo> {
        var result = HashMap<String, CharacterInfo>()

        // charNameStats.first = name, .second = array of stat values
        for (charNameStats in characterNames.zip(statValues)) {
            var charMap = HashMap<String, Int>()
            // stats is a pairing of the stat name with the value from statValues array
            for (stats in statNames.zip(charNameStats.second)) {
                charMap += stats
            }
            // map the character name to their info of name and stats map
            result[charNameStats.first] =
                CharacterInfo(name = charNameStats.first, statMap = charMap)
        }
        return result
    }

}