package io.eos.transaction.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.eos.transaction.TestCoroutineRule
import io.eos.transaction.TestCoroutineScopeProvider
import io.eos.transaction.repository.BlockRepository
import io.eos.transaction.model.Block
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException

class BlockDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val executor = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: BlockRepository

    private lateinit var viewModel: BlockDetailViewModel

    @Mock
    lateinit var viewStateObserver: Observer<BlockDetailViewState>

    private lateinit var gson: Gson

    @Before
    fun setup() {

        MockitoAnnotations.openMocks(this)
        gson = GsonBuilder().setPrettyPrinting()
                .create()

        viewModel = BlockDetailViewModel("id", gson, repo, TestCoroutineScopeProvider())
        viewModel.store.observeForTest(viewStateObserver)
    }


    @Test
    fun fetchBlock() = testCoroutineRule.runBlockingTest {


        val mockRes = Block("id", 0, "prev", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)


        whenever(repo.getBlock("id")).thenReturn(mockRes)

        val body = BlockDetailBodyViewModel(mockRes.id, mockRes.producer, mockRes.producerSignature, mockRes.transactions.size.toString(),
                gson.toJson(mockRes), false, null)


        viewModel.load()


        verify(viewStateObserver).onChanged(BlockDetailViewState(null, true, null, false))
        verify(viewStateObserver).onChanged(BlockDetailViewState(body, false, null, false))

    }

    @Test
    fun fetchBlock_error() = testCoroutineRule.runBlockingTest {


        val mockRes = RuntimeException()


        whenever(repo.getBlock("id")).thenThrow(mockRes)

        viewModel.load()


        verify(viewStateObserver).onChanged(BlockDetailViewState(null, true, null, false))
        verify(viewStateObserver).onChanged(BlockDetailViewState(null, false, mockRes, false))
    }


}