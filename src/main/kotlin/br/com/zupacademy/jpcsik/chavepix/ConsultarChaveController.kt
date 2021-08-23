package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.BuscarChaveRequest
import br.com.zupacademy.jpcsik.BuscarChaveServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class ConsultarChaveController(private val buscaChaveClient: BuscarChaveServiceGrpc.BuscarChaveServiceBlockingStub) {

    @Get("pix/{pixId}")
    fun consultar(clienteId: UUID, pixId: UUID): HttpResponse<Any> {

        val request = BuscarChaveRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()

        val response = buscaChaveClient.buscarChave(request)

        return HttpResponse.ok(DetalheChaveResponse(response))

    }

}