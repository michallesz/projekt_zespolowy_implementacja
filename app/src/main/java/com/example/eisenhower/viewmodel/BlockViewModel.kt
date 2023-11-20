package com.example.eisenhower.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.eisenhower.model.Block
import com.example.eisenhower.repository.BlockRepository
import androidx.lifecycle.Transformations



class BlockViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: BlockRepository
    private val mAllBlocks: LiveData<List<Block>>

    init {
        mRepository = BlockRepository(application)
        mAllBlocks = mRepository.allBlocks
    }

    val allBlocks: LiveData<List<Block>>
        get() = mAllBlocks

    fun update(block: Block) {
        mRepository.update(block)
    }
}

