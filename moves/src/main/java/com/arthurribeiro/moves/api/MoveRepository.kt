package com.arthurribeiro.moves.api

import com.arthurribeiro.model.model.MoveDetailDTO

interface MoveRepository {

    suspend fun getMoveDetail(url: String) : MoveDetailDTO
}