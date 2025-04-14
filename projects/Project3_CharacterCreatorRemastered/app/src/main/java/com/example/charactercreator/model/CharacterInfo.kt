package com.example.charactercreator.model

import androidx.annotation.DrawableRes

data class CharacterInfo(
     val name: String = "",
     val statMap: Map<String, Int> = mapOf()
)
