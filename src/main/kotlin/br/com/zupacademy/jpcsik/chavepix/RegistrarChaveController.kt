package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.CadastrarChaveServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RegistrarChaveController(private val cadastraChaveClient: CadastrarChaveServiceGrpc.CadastrarChaveServiceBlockingStub) {

    @Post("/pix")
    fun cadastrar(clienteId: UUID, @Valid @Body request: CadastrarChaveRequest): HttpResponse<Any> {

        //Faz a requisicao para o servidor grpc cadastrar a nova chave
        val response = cadastraChaveClient.cadastrar(request.toGrpcRequest(clienteId))

        //Cria a uri de resposta
        val uri = HttpResponse.uri("/api/v1/clientes/$clienteId/pix/${response.pixId}")

        return HttpResponse.created(uri)

    }

}