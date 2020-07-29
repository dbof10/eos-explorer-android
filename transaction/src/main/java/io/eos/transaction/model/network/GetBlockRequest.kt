package io.eos.transaction.model.network

import com.google.gson.annotations.SerializedName

data class GetBlockRequest(@SerializedName("block_num_or_id")
                           val blockId: String)