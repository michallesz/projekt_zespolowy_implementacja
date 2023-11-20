package com.example.eisenhower

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.eisenhower.databinding.ActivityAddTaskBinding
import com.example.eisenhower.databinding.ActivitySettingsBinding
import com.example.eisenhower.model.Block
import com.example.eisenhower.viewmodel.BlockViewModel
import com.example.eisenhower.viewmodel.TaskViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var blockViewModel: BlockViewModel
    private lateinit var binding: ActivitySettingsBinding

    private var editDialog: EditBlockFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.taskToolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        blockViewModel = ViewModelProvider(this)[BlockViewModel::class.java]

        blockViewModel.allBlocks.observe(this) { blocks ->
            val block0 = blocks.find { it.priority == 0 }!!
            val block1 = blocks.find { it.priority == 1 }!!
            val block2 = blocks.find { it.priority == 2 }!!
            val block3 = blocks.find { it.priority == 3 }!!

            binding.block0.text = block0.title
            binding.area0.setBackgroundColor(block0.color)
            binding.editTaskButton0.setOnClickListener{ startEditDialog(block0) }

            binding.block1.text = block1.title
            binding.area1.setBackgroundColor(block1.color)
            binding.editTaskButton1.setOnClickListener{ startEditDialog(block1) }

            binding.block2.text = block2.title
            binding.area2.setBackgroundColor(block2.color)
            binding.editTaskButton2.setOnClickListener{ startEditDialog(block2) }

            binding.block3.text = block3.title
            binding.area3.setBackgroundColor(block3.color)
            binding.editTaskButton3.setOnClickListener{ startEditDialog(block3) }
        }
    }
    private fun startEditDialog(block: Block){
        editDialog = EditBlockFragment()
        editDialog!!.setBlock(block)
        editDialog!!.show(supportFragmentManager,"edit block fragment")
    }
}