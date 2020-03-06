// The MIT License (MIT)
//
// Copyright (c) 2019 Hellobike Group
//
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the "Software"),
// to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included
// in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
// OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
// THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.

package io.flutter.embedding.android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.hellobike.flutter.thrio.OnActionListener
import com.hellobike.flutter.thrio.navigator.NavigatorChannelCache
import com.hellobike.flutter.thrio.navigator.NavigatorController
import com.hellobike.flutter.thrio.OnNotifyListener
import com.hellobike.flutter.thrio.Result
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.BinaryMessenger

open class ThrioActivity : FlutterActivity(), OnActionListener, OnNotifyListener {

    private val channel by lazy {
        val messenger: BinaryMessenger = flutterEngine.run {
            requireNotNull(this) { "flutterEngine does not exist" }
            dartExecutor
        }
        NavigatorChannelCache.get(messenger.hashCode())
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.extras?.let { this.intent.putExtras(it) }
    }

    @SuppressLint("VisibleForTests")
    override fun finish() {
        super.finish()
        super.delegate.onStop()
        super.delegate.onDetach()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        NavigatorController.pop(context, true) {}
    }

    override fun onPush(url: String, index: Int, params: Map<String, Any>, animated: Boolean, result: Result) {
        channel.onPush(url, index, params, animated, result)
    }

    override fun onPop(url: String, index: Int, animated: Boolean, result: Result) {
        channel.onPop(url, index, animated, result)
    }

    override fun onRemove(url: String, index: Int, animated: Boolean, result: Result) {
        channel.onRemove(url, index, animated, result)
    }

    override fun onPopTo(url: String, index: Int, animated: Boolean, result: Result) {
        channel.onPopTo(url, index, animated, result)
    }

    override fun onNotify(url: String, index: Int, name: String, params: Map<String, Any>) {
        channel.onNotify(url, index, name, params)
    }

}