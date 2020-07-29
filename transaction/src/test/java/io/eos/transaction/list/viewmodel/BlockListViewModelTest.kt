package io.eos.transaction.list.viewmodel

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.eos.transaction.TestCoroutineRule
import io.eos.transaction.TestCoroutineScopeProvider
import io.eos.transaction.repository.BlockRepository
import io.eos.transaction.model.Block
import io.eos.transaction.model.PagingKey
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BlockListViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val executor = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: BlockRepository

    @Mock
    lateinit var activity: Activity

    lateinit var viewModel: BlockListViewModel

    @Mock
    lateinit var viewStateObserver: Observer<BlockListViewState>

    @Before
    fun setup() {

        MockitoAnnotations.openMocks(this)
        viewModel = BlockListViewModel(activity, repo, TestCoroutineScopeProvider())
        viewModel.store.observeForTest(viewStateObserver)
    }


    @Test
    fun fetchBlock() = testCoroutineRule.runBlockingTest {

        val mockRes = listOf(Block("id", 0, "prev", "producer",
                "signature", emptyList(), "timeStamp", 0, "", "", 0, null)
        )

        val vms = mockRes.map {
            BlockListViewModel.toViewModel(it)
        }
        whenever(repo.fetch(20)).thenReturn(mockRes)
        viewModel.load()

        verify(viewStateObserver).onChanged(BlockListViewState(null, null, true, null))
        verify(viewStateObserver).onChanged(BlockListViewState(vms, PagingKey("id", "prev"), false, null,
                false, null))

    }

    @Test
    fun fetchBlock_error() = testCoroutineRule.runBlockingTest {


        val exception = RuntimeException()
        whenever(repo.fetch(20)).thenThrow(exception)
        viewModel.load()

        verify(viewStateObserver).onChanged(BlockListViewState(null, null, true, null))
        verify(viewStateObserver).onChanged(BlockListViewState(null, null, false, exception,
                false, null))

    }

    @Test
    fun fetchBlockMore() = testCoroutineRule.runBlockingTest {

        val mockRes = listOf(Block("id", 0, "prev", "producer",
                "signature", emptyList(), "timeStamp", 0,
                "", "", 0, null))


        whenever(repo.fetch(20)).thenReturn(mockRes)
        viewModel.load()


        val mockRes2 = listOf(Block("id2", 0, "prev2", "producer",
                "signature2", emptyList(), "timeStamp", 0, "",
                "", 0, null))

        whenever(repo.fetchNext(20, "prev")).thenReturn(mockRes2)

        val state = viewModel.store.state

        val totalVM = (mockRes + mockRes2).map {
            BlockListViewModel.toViewModel(it)
        }

        viewModel.loadMore()


        verify(viewStateObserver).onChanged(state.copy(loadingMore = true))
        verify(viewStateObserver).onChanged(BlockListViewState(totalVM, PagingKey("id2", "prev2"), false, null, false, null))

    }

    @Test
    fun fetchBlockMore_Error() = testCoroutineRule.runBlockingTest {

        val mockRes = listOf(Block("id", 0, "prev", "producer",
                "signature", emptyList(), "timeStamp", 0,
                "", "", 0, null))
        val exception = RuntimeException()


        whenever(repo.fetch(20)).thenReturn(mockRes)
        viewModel.load()

        whenever(repo.fetchNext(20, "prev")).thenThrow(exception)

        val state = viewModel.store.state

        viewModel.loadMore()


        verify(viewStateObserver).onChanged(state.copy(loadingMore = true))
        verify(viewStateObserver).onChanged(BlockListViewState(state.content!!, PagingKey("id", "prev"), false, null, false, exception))

    }

}