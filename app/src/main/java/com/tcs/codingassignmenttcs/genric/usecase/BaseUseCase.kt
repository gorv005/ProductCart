package com.tcs.codingassignmenttcs.genric.usecase

interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}