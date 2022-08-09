package com.arthurribeiro.moves.api

import com.arthurribeiro.model.model.MoveDetailDTO
import com.arthurribeiro.network.network.Network

class MoveRepositoryImp(private val service: MovesService, private val network: Network) : MoveRepository {
    override suspend fun getMoveDetail(url: String): MoveDetailDTO {
        return network.doRequest { service.getMoveDetail(url) }
    }
}