package com.example.charactercreator.data

import com.example.charactercreator.R
import com.example.charactercreator.model.StatNameIcon

object StatDataSource {
    val statStuffs = listOf(
        StatNameIcon(
            name = R.string.stamina,
            icon = R.drawable.baseline_heart_broken_24),
        StatNameIcon(
            name = R.string.strength,
            icon = R.drawable.baseline_shield_24),
        StatNameIcon(
            name = R.string.agility,
            icon = R.drawable.baseline_electric_bolt_24),
        StatNameIcon(
            name = R.string.intellect,
            icon = R.drawable.baseline_self_improvement_24)
    )
}