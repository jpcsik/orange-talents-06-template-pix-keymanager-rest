package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.RemoverChaveResponse
import br.com.zupacademy.jpcsik.RemoverChaveServiceGrpc
import br.com.zupacademy.jpcsik.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class DeletarChaveControllerTest {

    @field:Inject
    lateinit var grpcClient: RemoverChaveServiceGrpc.RemoverChaveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    private val clienteId = UUID.randomUUID()

    private val pixId = UUID.randomUUID()


    @Test
    fun `Deve deletar uma chave`() {
        //cenario
        val respostaGrpc = RemoverChaveResponse.newBuilder()
            .setMensagem("chave removida com sucesso!")
            .build()

        //acao
        Mockito
            .`when`(grpcClient.removerChave(Mockito.any()))
            .thenReturn(respostaGrpc)

        val request = HttpRequest.DELETE<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
        val response = httpClient.toBlocking().exchange(request, Any::class.java)

        //validacao
        assertEquals(HttpStatus.OK, response.status)

    }


    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class RemoveStubFactory {
        @Singleton
        internal fun removeStubMock(): RemoverChaveServiceGrpc.RemoverChaveServiceBlockingStub {
            return Mockito.mock(RemoverChaveServiceGrpc.RemoverChaveServiceBlockingStub::class.java)
        }
    }
}