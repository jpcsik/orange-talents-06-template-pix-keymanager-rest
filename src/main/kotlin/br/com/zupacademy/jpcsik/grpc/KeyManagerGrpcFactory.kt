package br.com.zupacademy.jpcsik.grpc

import br.com.zupacademy.jpcsik.CadastrarChaveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManagerGrpc") val channel: ManagedChannel) {

    @Singleton
    fun cadastraChave(): CadastrarChaveServiceGrpc.CadastrarChaveServiceBlockingStub {
        return CadastrarChaveServiceGrpc.newBlockingStub(channel)
    }

}