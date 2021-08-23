package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.RemoverChaveRequest
import br.com.zupacademy.jpcsik.RemoverChaveServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import java.util.*

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class DeletarChaveController(private val removeChaveClient: RemoverChaveServiceGrpc.RemoverChaveServiceBlockingStub) {

    @Delete("/pix/{pixId}")
    fun deletar(clienteId: UUID, pixId: UUID): HttpResponse<Any> {

        val request = RemoverChaveRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()

        //Faz a requisicao para o servidor grpc deletar a chave
        removeChaveClient.removerChave(request)

        return HttpResponse.ok()

    }

}