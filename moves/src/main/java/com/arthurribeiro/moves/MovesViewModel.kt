package com.arthurribeiro.moves

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arthurribeiro.foundation.utils.launchFromNetwork
import com.arthurribeiro.model.model.MoveDetailDTO
import com.arthurribeiro.moves.api.MoveRepositoryImp
import com.arthurribeiro.network.network.Resource

class MovesViewModel(private val repository: MoveRepositoryImp) : ViewModel() {

    private val _moveDetail = MutableLiveData<Resource<MoveDetailDTO>>()
    val moveDetail: LiveData<Resource<MoveDetailDTO>> = _moveDetail

    private var moveUrl: String? = null

    fun setMoveUrl(value: String?){
        moveUrl = value
    }

    fun getMoveUrl() : String? = moveUrl

    fun getMoveDetail(url: String) {
        launchFromNetwork(_moveDetail) {
                repository.getMoveDetail(url)
        }
    }
}