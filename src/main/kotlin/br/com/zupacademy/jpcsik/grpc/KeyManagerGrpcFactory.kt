package br.com.zupacademy.jpcsik.grpc

import br.com.zupacademy.jpcsik.BuscarChaveServiceGrpc
import br.com.zupacademy.jpcsik.CadastrarChaveServiceGrpc
import br.com.zupacademy.jpcsik.ListarChavesServiceGrpc
import br.com.zupacademy.jpcsik.RemoverChaveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
open class KeyManagerGrpcFactory(@GrpcChannel("keyManagerGrpc") val channel: ManagedChannel) {

    @Singleton
    open fun cadastraChave(): CadastrarChaveServiceGrpc.CadastrarChaveServiceBlockingStub {
        return CadastrarChaveServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    open fun removeChave(): RemoverChaveServiceGrpc.RemoverChaveServiceBlockingStub {
        return RemoverChaveServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    open fun buscaChave(): BuscarChaveServiceGrpc.BuscarChaveServiceBlockingStub {
        return BuscarChaveServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    open fun listaChave(): ListarChavesServiceGrpc.ListarChavesServiceBlockingStub {
        return ListarChavesServiceGrpc.newBlockingStub(channel)
    }

}