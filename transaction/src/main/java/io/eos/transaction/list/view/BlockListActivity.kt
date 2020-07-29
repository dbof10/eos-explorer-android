package io.eos.transaction.list.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import dagger.android.support.DaggerAppCompatActivity
import io.eos.recyclerview.InfiniteScrollListener
import io.eos.recyclerview.OnItemClick
import io.eos.recyclerview.ReachedEndException
import io.eos.transaction.R
import io.eos.transaction.list.viewmodel.BlockListViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_block.ivLoad
import kotlinx.android.synthetic.main.activity_block.rvBlocks
import kotlinx.android.synthetic.main.activity_block.toolbar
import kotlinx.android.synthetic.main.activity_block.vError
import kotlinx.android.synthetic.main.activity_block.vLoading
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class BlockListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val viewModel: BlockListViewModel by viewModels {
        viewModelFactory
    }


    @Inject
    lateinit var idiomAdapter: BlockAdapter

    @Inject
    lateinit var loadingAdapter: BlockFooterLoadingAdapter

    @Inject
    lateinit var errorAdapter: BlockFooterErrorAdapter

    companion object {
        private const val DURATION_SLIDE_UP = 500L
    }

    private lateinit var mergeAdapter: MergeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)

        setupRecyclerView()
        bindUi()
        setupListener()
    }

    private fun setupListener() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        findViewById<View>(R.id.btRetry).setOnClickListener {
            viewModel.load()
        }

        ivLoad.setOnClickListener {
            viewModel.load()
        }

    }

    private fun setupRecyclerView() {

        errorAdapter.listener = OnItemClick { _, _ ->
            viewModel.loadMore()
        }

        idiomAdapter.listener = OnItemClick { _, item ->
            viewModel.onClicked(item)
        }
        mergeAdapter = MergeAdapter(idiomAdapter)
        val lm = LinearLayoutManager(this@BlockListActivity)
        rvBlocks.run {
            layoutManager = lm
            adapter = mergeAdapter
            addOnScrollListener(InfiniteScrollListener(lm, 5) {
                viewModel.loadMore()
            })
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = DURATION_SLIDE_UP
            }
        }
    }

    private fun bindUi() {
        viewModel.store.observe(this) {
            when {
                it.loading -> {
                    vLoading.visibility = View.VISIBLE
                    rvBlocks.visibility = View.GONE
                    vError.visibility = View.GONE
                }
                it.throwable != null -> {
                    rvBlocks.visibility = View.GONE
                    vLoading.visibility = View.GONE
                    vError.visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tvError).text = it.throwable.message
                }
                it.loadingMore -> {
                    mergeAdapter.removeAdapter(errorAdapter)
                    mergeAdapter.addAdapter(1, loadingAdapter)
                }
                it.throwableMore != null -> {
                    mergeAdapter.removeAdapter(loadingAdapter)
                    if (it.throwableMore !is ReachedEndException) {
                        mergeAdapter.addAdapter(1, errorAdapter)
                    }
                }
                it.content != null -> {
                    rvBlocks.visibility = View.VISIBLE
                    vLoading.visibility = View.GONE
                    vError.visibility = View.GONE

                    lifecycleScope.launch {
                        delay(100) //wait for loading view to disappear
                        idiomAdapter.submitList(it.content)
                    }
                }

            }


        }
    }

}