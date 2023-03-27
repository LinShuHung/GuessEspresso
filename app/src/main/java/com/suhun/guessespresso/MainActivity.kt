package com.suhun.guessespresso

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.suhun.guessespresso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val tag:String = MainActivity::class.java.simpleName
    val secretNumber:SecretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.reset_game))
                .setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(R.string.ok), {dialog, which->
                    guessResetProcess()
                })
                .setNeutralButton(getString(R.string.cancel), null)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun guessResetProcess(){
        secretNumber.resetAll()
        binding.contentLayout.userInputEditText.text = null
        binding.contentLayout.counterTextView.text = "0"
    }

    fun guessVerify(view:View){
        var userInput:Int = binding.contentLayout.userInputEditText.text.toString().toInt()
        val message:String = secretNumber.verifyResult(resources, userInput)
        var bingo:Boolean = if(secretNumber.verify(userInput)==0) true else false

        binding.contentLayout.counterTextView.text = secretNumber.guessCounter.toString()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.guess_result))
            .setMessage(message).setPositiveButton(getString(R.string.ok),{dialog, which->
                if(bingo){
                    //design latter
                    var intent:Intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNT", secretNumber.guessCounter)
                    startActivity(intent)
                }else{
                    binding.contentLayout.userInputEditText.text = null
                }
            })
            .show()
    }
}