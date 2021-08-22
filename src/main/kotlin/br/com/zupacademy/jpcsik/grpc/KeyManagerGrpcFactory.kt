package br.com.zupacademy.jpcsik.grpc

import br.com.zupacademy.jpcsik.CadastrarChaveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
open class KeyManagerGrpcFactory(@GrpcChannel("keyManagerGrpc") val channel: ManagedChannel) {

    @Singleton
    open fun cadastraChave(): CadastrarChaveServiceGrpc.CadastrarChaveServiceBlockingStub {
        return CadastrarChaveServiceGrpc.newBlockingStub(channel)
    }

}