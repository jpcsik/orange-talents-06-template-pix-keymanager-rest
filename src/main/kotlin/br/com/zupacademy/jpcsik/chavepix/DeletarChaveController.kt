package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.RemoverChaveServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class DeletarChaveController(private val removeChaveClient: RemoverChaveServiceGrpc.RemoverChaveServiceBlockingStub) {

    @Delete("/pix")
    fun deletar(clienteId: UUID, @Body @Valid request: DeletarChaveRequest): HttpResponse<Any> {

        removeChaveClient.removerChave(request.toGrpcRequest(clienteId))

        return HttpResponse.ok(object {
            val chave = request.pixId
            val mensagem = "chave deletada com sucesso!"
        })

    }

}