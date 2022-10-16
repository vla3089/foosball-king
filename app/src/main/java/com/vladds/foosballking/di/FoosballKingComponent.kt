package com.vladds.foosballking.di

import androidx.lifecycle.ViewModelProvider
import com.vladds.foosballking.data.FoosballKingDatabase
import dagger.Component

@Component(
    modules = [FoosballKingModule::class],
    dependencies = [FoosballKingDatabase::class]
)
interface FoosballKingComponent {
    val viewModelFactory: ViewModelProvider.Factory
}
