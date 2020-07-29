package io.eos.transaction.repository

import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import io.eos.recyclerview.ReachedEndException
import io.eos.transaction.TestCoroutineRule
import io.eos.transaction.model.Block
import io.eos.transaction.model.ChainInfo
import io.eos.transaction.model.network.GetBlockRequest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class BlockRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var api: EOSApi

    private lateinit var repository: BlockRepository

    @Before
    fun setup() {

        MockitoAnnotations.openMocks(this)
        repository = BlockRepository(Lazy {
            api
        })
    }

    @Test
    fun fetch() = testCoroutineRule.runBlockingTest {


        val mockRes = Block("id", 0, "prev", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)

        val mockRes2 = Block("id2", 0, "prev2", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)

        whenever(api.fetchInfo()).thenReturn(ChainInfo(101))

        whenever(api.fetchBlock(GetBlockRequest("101"))).thenReturn(mockRes)
        whenever(api.fetchBlock(GetBlockRequest("prev"))).thenReturn(mockRes2)

        val blocks = repository.fetch(2)

        assertEquals(listOf(mockRes, mockRes2), blocks)
    }


    @Test
    fun fetchNext() = testCoroutineRule.runBlockingTest {


        val mockRes = Block("id2", 0, "prev2", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)

        val mockRes2 = Block("id3", 0, "prev3", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)

        whenever(api.fetchBlock(GetBlockRequest("prev"))).thenReturn(mockRes)
        whenever(api.fetchBlock(GetBlockRequest("prev2"))).thenReturn(mockRes2)

        val blocks = repository.fetchNext(2, "prev")

        assertEquals(listOf(mockRes, mockRes2), blocks)
    }


    @Test(expected = ReachedEndException::class)
    fun fetchNext_IfReachedEnd() = testCoroutineRule.runBlockingTest {


        val blocks = repository.fetchNext(2, null)

        assertEquals(emptyList<Block>(), blocks)
    }


    @Test
    fun getBlock_notInCache() = testCoroutineRule.runBlockingTest {
        val mockRes = Block("id2", 0, "prev2", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)
        whenever(api.fetchBlock(GetBlockRequest("prev"))).thenReturn(mockRes)


        val block = repository.getBlock("prev")

        assertEquals(mockRes, block)
    }

    @Test
    fun getBlock_cacheHit() = testCoroutineRule.runBlockingTest {
        val mockRes = Block("id2", 0, "prev2", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)

        val mockRes2 = Block("id3", 0, "prev3", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)

        whenever(api.fetchBlock(GetBlockRequest("prev"))).thenReturn(mockRes)
        whenever(api.fetchBlock(GetBlockRequest("prev2"))).thenReturn(mockRes2)

        repository.fetchNext(2, "prev")

        val block = repository.getBlock("id2")

        assertEquals(mockRes, block)

    }
}