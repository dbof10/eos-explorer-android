package io.eos.transaction.repository

import androidx.collection.LruCache
import dagger.Lazy
import io.eos.recyclerview.ReachedEndException
import io.eos.transaction.model.Block
import io.eos.transaction.model.network.GetBlockRequest
import timber.log.Timber
import java.util.LinkedList


open class BlockRepository(private val api: Lazy<EOSApi>) {

    private val memCache: LruCache<String, Block> by lazy {
        LruCache<String, Block>(4 * 1024 * 1024)// 4MiB
    }

    open suspend fun fetch(limit: Int): List<Block> {

        val info = api.get().fetchInfo()

        val headId = info.lastBlock

        val headBlock = api.get().fetchBlock(GetBlockRequest(headId.toString()))


        return fetch(limit, headBlock)
    }

    private suspend fun fetch(limit: Int, headBlock: Block): List<Block> {
        val queue = LinkedList<Block>()
        val blocks = ArrayList<Block>()

        queue.push(headBlock)
        blocks.add(headBlock)

        var i = 1
        while (queue.isNotEmpty() && i < limit) {

            val current = queue.pop()
            val prevBlock = api.get().fetchBlock(GetBlockRequest(current.previous))

            memCache.put(current.id, current)
            memCache.put(prevBlock.id, prevBlock)

            blocks.add(prevBlock)
            queue.push(prevBlock)
            i++
        }
        return blocks
    }

    open suspend fun fetchNext(limit: Int, prevBlockId: String?): List<Block> {

        if (prevBlockId == null) {
            throw ReachedEndException()
        }
        val headBlock = api.get().fetchBlock(GetBlockRequest(prevBlockId))

        return fetch(limit, headBlock)
    }

    open suspend fun getBlock(id: String): Block {
        val cachedBlock = memCache.get(id)
        return if (cachedBlock != null) {
            Timber.d("Cache hit block %s", id)
            cachedBlock
        } else {
            api.get().fetchBlock(GetBlockRequest(id))
        }
    }
}