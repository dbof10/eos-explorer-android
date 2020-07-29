package io.eos.transaction.model

import com.google.gson.annotations.SerializedName


data class ChainInfo(@SerializedName("last_irreversible_block_num") val lastBlock: Int)

data class Block(val id: String,
                 @SerializedName("block_num")
                 val blockNum: Int,
                 val previous: String,
                 val producer: String,
                 @SerializedName("producer_signature")
                 val producerSignature: String,
                 val transactions: List<Any>,
                 val timestamp: String,
                 val confirm: Int,
                 @SerializedName("transaction_mroot")
                 val transactionMRoot: String,
                 @SerializedName("action_mroot")
                 val actionMRoot: String,
                 @SerializedName("ref_block_prefix")
                 val refBlockPrefix: Long,
                 @SerializedName("new_producers")
                 val newProducers: String?)

