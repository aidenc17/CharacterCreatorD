package com.example.charactercreator.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class StatNameIcon(
    @StringRes val name: Int,
    @DrawableRes val icon: Int
)
