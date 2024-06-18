package com.example.planet.di.module

import com.example.planet.data.repository.ChatRepositoryImpl
import com.example.planet.data.repository.ImageRepositoryImpl
import com.example.planet.data.repository.LoginRepositoryImpl
import com.example.planet.data.repository.MainRepositoryImpl
import com.example.planet.data.repository.PloggingRepositoryImpl
import com.example.planet.data.repository.PostingRepositoryImpl
import com.example.planet.data.repository.RankRepositoryImpl
import com.example.planet.data.repository.SearchRepositoryImpl
import com.example.planet.data.repository.StatisticsRepositoryImpl
import com.example.planet.data.repository.UserRepositoryImpl
import com.example.planet.domain.repository.ChatRepository
import com.example.planet.domain.repository.ImageRepository
import com.example.planet.domain.repository.LoginRepository
import com.example.planet.domain.repository.MainRepository
import com.example.planet.domain.repository.PloggingRepository
import com.example.planet.domain.repository.PostingRepository
import com.example.planet.domain.repository.RankRepository
import com.example.planet.domain.repository.SearchRepository
import com.example.planet.domain.repository.StatisticsRepository
import com.example.planet.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun userRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun imageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    abstract fun loginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun rankRepository(
        rankRepositoryImpl: RankRepositoryImpl
    ): RankRepository

    @Binds
    abstract fun mainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository

    @Binds
    abstract fun ploggingRepository(
        ploggingRepositoryImpl: PloggingRepositoryImpl
    ): PloggingRepository

    @Binds
    abstract fun postingRepository(
        postingRepositoryImpl: PostingRepositoryImpl
    ): PostingRepository

    @Binds
    abstract fun chatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    abstract fun searchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    abstract fun statisticsRepository(
        statisticsRepositoryImpl: StatisticsRepositoryImpl
    ): StatisticsRepository
}
