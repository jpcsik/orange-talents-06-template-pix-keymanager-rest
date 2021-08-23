package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.ListaChavesRequest
import br.com.zupacademy.jpcsik.ListarChavesServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class ListarChavesController(private val listarChaveClient: ListarChavesServiceGrpc.ListarChavesServiceBlockingStub) {

    @Get("/chaves")
    fun listar(clienteId: UUID): HttpResponse<Any> {

        val request = ListaChavesRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .build()

        //Busca a lista de chaves por id do cliente no servidor grpc
        val response = listarChaveClient.listarChaves(request)

                                    //Mapeia cada item da lista de chaves para um objeto ChavesResponse
        return HttpResponse.ok(response.chavesList.map { ChavesResponse(it) })
    }

}