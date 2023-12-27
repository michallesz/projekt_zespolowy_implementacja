package com.example.eisenhower

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.eisenhower.databinding.FragmentEditBlockBinding
import com.example.eisenhower.model.Block
import com.example.eisenhower.viewmodel.BlockViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditBlockFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditBlockBinding? = null
    private val binding get() = _binding!!
    private var block: Block? = null
    private var selectedColor: Int? = null
    private lateinit var viewModel: BlockViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBlockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BlockViewModel::class.java]

        binding.titleEditText.setText(block!!.title)
        setColor(block!!.color)

        val buttons = listOf(
            binding.color1,
            binding.color2,
            binding.color3,
            binding.color4,
            binding.color5,
            binding.color6,
            binding.color7,
            binding.color8
        )

        for (button in buttons) {
            button.setOnClickListener {
                val color = getColorFromButton(button)
                if (color != null) {
                    setColor(color)
                }
            }
        }
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            if (title.isNotBlank()) {
                val newBlock = block!!.copy(
                    title = title,
                    color = selectedColor!!
                )
                viewModel.update(newBlock)
                dismiss()
            }
        }
    }

    fun setBlock(block: Block) {
        this.block = block
    }

    private fun setColor(newColor: Int) {
        this.selectedColor = newColor

        val buttons = listOf(
            binding.color1,
            binding.color2,
            binding.color3,
            binding.color4,
            binding.color5,
            binding.color6,
            binding.color7,
            binding.color8
        )

        for (button in buttons) {
            button.setImageResource(0)
        }

        val foundButton = buttons.find { button ->
            val colorFromTag = getColorFromButton(button)
            colorFromTag == newColor
        }!!
        foundButton.setImageResource(R.drawable.ic_done)
    }

    private fun getColorFromButton(button: FloatingActionButton): Int? {
        val buttonTag = button.tag as? String
        if (buttonTag == null) {
            return null
        }
        val colorFromTag = Color.parseColor(buttonTag)
        return colorFromTag
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}