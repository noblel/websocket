package cn.noblel.websocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import okhttp3.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "WebSocket"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.tv_send)
        textView.setOnClickListener {
            val editText = findViewById<EditText>(R.id.et_server)
            val text = editText.editableText.toString()
            Thread() {
                val okHttpClient = OkHttpClient.Builder()
                    .build()
                val request = Request.Builder()
                    .url(text)
                    .build()
                okHttpClient.newWebSocket(request, object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        super.onOpen(webSocket, response)
                        Log.d(TAG, "open")
                        webSocket.send("hello server")
                        runOnUiThread{
                            textView.append("\n")
                            textView.append("Server: $response")
                        }
                    }

                    override fun onMessage(webSocket: WebSocket, text: String) {
                        super.onMessage(webSocket, text)
                        Log.d(TAG, "onMessage: $text")
                        runOnUiThread{
                            textView.append("\n")
                            textView.append("onMessage: $text")
                        }
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosed(webSocket, code, reason)
                        Log.d(TAG, "onClosed: $code reason: $reason")
                        runOnUiThread{
                            textView.append("\n")
                            textView.append("onClosed: $code reason: $reason")
                        }
                    }

                    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosing(webSocket, code, reason)
                        Log.d(TAG, "onClosing: $code reason: $reason")
                        runOnUiThread{
                            textView.append("\n")
                            textView.append("onClosing: $code reason: $reason")
                        }
                    }

                    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                        super.onFailure(webSocket, t, response)
                        Log.e(TAG, "onFailure: $t")
                        runOnUiThread{
                            textView.append("\n")
                            textView.append("onFailure: $t")
                        }
                    }
                })
            }.start()
        }
    }
}