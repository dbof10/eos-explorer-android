package io.eos.transaction

import io.eos.arch.CoroutineScopeProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TestCoroutineScopeProvider : CoroutineScopeProvider {
    override val io: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val main: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val async: CoroutineScope
        get() = CoroutineScope(Dispatchers.Unconfined)
}