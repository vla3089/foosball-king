package com.vladds.foosballking.presentation.ranking

import javax.inject.Qualifier

enum class RankingCategory {
    ByGamesPlayed,
    ByGamesWon
}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class RankingCategoryQualifier(val value: RankingCategory)
