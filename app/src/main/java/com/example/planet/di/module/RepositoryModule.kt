package com.example.planet.di.module

import com.example.planet.data.repository.ImageRepositoryImpl
import com.example.planet.data.repository.UserRepositoryImpl
import com.example.planet.domain.repository.ImageRepository
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
    abstract fun ImageRepository(
        ImageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}