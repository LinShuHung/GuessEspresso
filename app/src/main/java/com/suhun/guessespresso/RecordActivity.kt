package com.suhun.guessespresso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.suhun.guessespresso.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var countScort:Int = intent.getIntExtra("COUNT", -1)
        binding.saveButton.setOnClickListener { view->
            var name = binding.nameInputTextView.text.toString()
            val scoreInfo = "$name \t $countScort"
            val temp = getSharedPreferences("record_scort", MODE_PRIVATE).getString("SCORT", null)
            var tempScort = ""
            if(temp != null){
                tempScort = "$scoreInfo \t $temp"

            }else{
                tempScort = scoreInfo
            }
            getSharedPreferences("record_scort", MODE_PRIVATE)
                .edit()
                .putString("SCORT", tempScort)
                .apply()
            setResult(RESULT_OK)
            finish()
        }
    }

}