package io.eos.transaction.repository

import io.eos.transaction.model.Block
import io.eos.transaction.model.ChainInfo
import io.eos.transaction.model.network.GetBlockRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface EOSApi {


    @POST("chain/get_info")
    suspend fun fetchInfo(): ChainInfo

    @POST("chain/get_block")
    suspend fun fetchBlock(@Body request: GetBlockRequest): Block
}