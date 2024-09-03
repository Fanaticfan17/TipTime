package medina.diego.tiptime

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val costOfServiceEditText: EditText = findViewById(R.id.cost_of_service)
        val tipOptionsRadioGroup: RadioGroup = findViewById(R.id.tip_options)
        val roundUpSwitch: Switch = findViewById(R.id.round_up_switch)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val tipResultTextView: TextView = findViewById(R.id.tip_result)

        calculateButton.setOnClickListener {
            val cost = costOfServiceEditText.text.toString().toDoubleOrNull()
            if (cost == null || cost == 0.0) {
                tipResultTextView.text = ""
                return@setOnClickListener
            }

            val tipPercentage = when (tipOptionsRadioGroup.checkedRadioButtonId) {
                R.id.option_twenty_percent -> 0.20
                R.id.option_eighteen_percent -> 0.18
                R.id.option_fifteen_percent -> 0.15
                else -> 0.10
            }

            var tip = cost * tipPercentage

            if (roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }

            tipResultTextView.text = getString(R.string.tip_amount, tip)
        }
    }
}
