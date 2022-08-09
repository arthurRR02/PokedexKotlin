package com.arthurribeiro.moves.api

import com.arthurribeiro.model.model.MoveDetailDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MovesService {
    @GET
    suspend fun getMoveDetail(@Url url: String) : Response<MoveDetailDTO>

}