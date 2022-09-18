package com.arthurribeiro.abilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arthurribeiro.abilities.api.AbilitiesRepositoryImp
import com.arthurribeiro.foundation.utils.launchFromNetwork
import com.arthurribeiro.model.model.AbilityDetailDTO
import com.arthurribeiro.network.network.Resource

class AbilitiesViewModel(private val repository: AbilitiesRepositoryImp) :  ViewModel() {

    private val _abilitiesDetail = MutableLiveData<Resource<AbilityDetailDTO>>()
    val abilitiesDetail: LiveData<Resource<AbilityDetailDTO>> = _abilitiesDetail

    private var abilityUrl: String? = null

    fun setAbilityUrl(url: String?) {
        abilityUrl = url
    }

    fun getAbilityUrl() : String? = abilityUrl

    fun getAbilitiesDetail(url: String){
        launchFromNetwork(_abilitiesDetail){
            repository.getAbilitiesDetail(url)
        }
    }

}