package hu.tarsas.totalkaosz

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import hu.tarsas.totalkaosz.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var timer: CountDownTimer
    private var blueTime = 20000
    private var yellowTime = 0
    private var isBlue = true
    public var isStarted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        timer = object : CountDownTimer(Long.MAX_VALUE, 100) {
            override fun onTick(millisUntilFinished: Long) {
                if (isBlue) {
                    if (blueTime <= 0) {
                        timer.cancel()
                    } else {
                        blueTime -= 100
                        yellowTime += 100
                        binding.timerBtn.text = DecimalFormat("00.0").format((blueTime / 1000.0))
                    }
                } else {
                    if (yellowTime <= 0) {
                        timer.cancel()
                    } else {
                        blueTime += 100
                        yellowTime -= 100
                        binding.timerBtn.text = DecimalFormat("00.0").format((yellowTime / 1000.0))
                    }
                }
            }

            override fun onFinish() {
                binding.timerBtn.text = "A játék idő lejárt, indítsd újra az appot ):"
            }
        }

        binding.timerBtn.setOnClickListener {
            if (!isBlue) {
                binding.timerBtn.setBackgroundColor(Color.parseColor("#03A9F4"))
                isBlue = true
            } else {
                binding.timerBtn.setBackgroundColor(Color.YELLOW)
                isBlue = false
            }
        }
        binding.PauseBtn.setOnClickListener {
            if (!isStarted) {
                binding.PauseBtn.setBackgroundColor(Color.RED)
                binding.PauseBtn.text = "Szünet"
                isStarted = true
                timer.start()
            } else {
                binding.PauseBtn.setBackgroundColor(Color.GREEN)
                binding.PauseBtn.text = "Start"
                isStarted = false
                timer.cancel()
            }
        }
        setContentView(binding.root)
    }

    override fun onStop() {
        binding.PauseBtn.setBackgroundColor(Color.GREEN)
        binding.PauseBtn.text = "Start"
        isStarted = false
        timer.cancel()
        super.onStop()
    }
}
