package io.eos.transaction.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import io.eos.transaction.R
import io.eos.transaction.detail.viewmodel.BlockDetailBodyViewModel
import io.eos.transaction.detail.viewmodel.BlockDetailViewModel
import kotlinx.android.synthetic.main.activity_block.toolbar
import kotlinx.android.synthetic.main.activity_block_detail.flDetail
import kotlinx.android.synthetic.main.activity_block_detail.flPlaceholder
import kotlinx.android.synthetic.main.activity_block_detail.llContent
import kotlinx.android.synthetic.main.activity_block_detail.switchDetail
import kotlinx.android.synthetic.main.activity_block_detail.tvBlockDetail
import kotlinx.android.synthetic.main.activity_block_detail.tvProducer
import kotlinx.android.synthetic.main.activity_block_detail.tvProducerSignature
import kotlinx.android.synthetic.main.activity_block_detail.tvTransactions
import kotlinx.android.synthetic.main.activity_block_detail.vError
import kotlinx.android.synthetic.main.activity_block_detail.vLoading
import javax.inject.Inject

class BlockDetailActivity : DaggerAppCompatActivity() {

    companion object {
        const val KEY_ID = "io.eos.transaction.detail.view.KEY_ID"


        fun makeIntent(activity: Context, id: String): Intent {
            val intent = Intent(activity, BlockDetailActivity::class.java)
            intent.putExtra(KEY_ID, id)
            return intent
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val viewModel: BlockDetailViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_detail)

        bindUi()
        setupListener()

        viewModel.load()
    }

    private fun setupListener() {
        toolbar.setNavigationOnClickListener {
            finish()
        }

        switchDetail.setOnCheckedChangeListener { view, checked ->
            if (view.isPressed) {
                viewModel.toggleDetail(checked, tvBlockDetail)
            }
        }
    }

    private fun bindUi() {
        viewModel.store.observe(this) {
            when {
                it.loading -> {
                    vLoading.visibility = View.VISIBLE
                    llContent.visibility = View.GONE
                    vError.visibility = View.GONE
                }
                it.throwable != null -> {
                    llContent.visibility = View.GONE
                    vLoading.visibility = View.GONE
                    vError.visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tvError).text = it.throwable.message
                }
                it.content != null -> {
                    if (it.renderingText) {
                        flPlaceholder.visibility = View.VISIBLE
                    } else {
                        flPlaceholder.visibility = View.GONE
                    }
                    llContent.visibility = View.VISIBLE
                    vLoading.visibility = View.GONE
                    vError.visibility = View.GONE
                    bindContent(it.content)
                }
            }
        }
    }

    private fun bindContent(viewModel: BlockDetailBodyViewModel) {
        with(viewModel) {
            tvProducer.text = producer
            tvProducerSignature.text = producerSignature
            tvTransactions.text = transactionsCount
            switchDetail.isChecked = detailChecked
            flDetail.visibility = detailVisibility
            if (contentStringPrecomputedText != null && tvBlockDetail.text.isNullOrEmpty()) {

                TextViewCompat.setPrecomputedText(tvBlockDetail, contentStringPrecomputedText)
            }
        }
    }


}