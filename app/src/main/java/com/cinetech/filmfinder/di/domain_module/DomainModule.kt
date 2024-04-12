package com.cinetech.filmfinder.di.domain_module

import dagger.Module

@Module(includes = [MovieModule::class, CommentModule::class])
class DomainModule