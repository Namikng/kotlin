package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0 // 시간 변수
    private var isRunning = false
    private var timerTask: Timer? = null // null도 허용함
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }

        lapButton.setOnClickListener {
            recordLapTime()
        }

        resetFab.setOnClickListener {
            reset()
        }
    }

    // 시작 버튼 클릭 시
    private fun start() {
        // 타이머 시작 시 일시정지 이미지로 변경
        fab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100

            // timer는 워커스레드에서 동작하여 UI 조작이 불가 -> runOnUiThread 사용
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    // 일시정지 클릭 시
    private fun pause() {
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()  // 실행중인 타이머가 있다면 취소
    }

    // 랩 타임 버튼 클릭 시
    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAB : ${lapTime / 100}.${lapTime % 100}"

        // 맨 위에(0의 위치에) 랩타임 추가
        lapLayout.addView(textView, 0)
        lap++
    }

    // 초기화 버튼 클릭 시
    private fun reset() {
        // 실행중인 타이머가 있다면 취소
        timerTask?.cancel()

        // 모든 변수 초기화
        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        // 모든 랩타임 제거
        lapLayout.removeAllViews()
        lap = 1
    }
}
