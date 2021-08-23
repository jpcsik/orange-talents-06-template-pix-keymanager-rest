package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.*
import br.com.zupacademy.jpcsik.grpc.KeyManagerGrpcFactory
import com.google.protobuf.Timestamp
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
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultarChaveControllerTest{

    @field:Inject
    lateinit var grpcClient: BuscarChaveServiceGrpc.BuscarChaveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    private val clienteId = UUID.randomUUID().toString()

    private val pixId = UUID.randomUUID().toString()


    @Test
    fun `Deve consultar os detalhes de uma chave`(){
        //cenario
        val buscarChaveResponse = BuscarChaveResponse.newBuilder()
            .setClientId(clienteId)
            .setPixId(pixId)
            .setChave(BuscarChaveResponse.ChavePix.newBuilder()
            .setChave("12312312312")
            .setTipo(TipoChave.CPF)
            .setCriadaEm(Timestamp.newBuilder()
                .setSeconds(LocalDateTime.now().second.toLong())
                .setNanos(LocalDateTime.now().nano)
                .build())
            .setConta(BuscarChaveResponse.DadosConta.newBuilder()
                .setTipo(TipoConta.CONTA_CORRENTE)
                .setInstituicao("Banco")
                .setAgencia("00001")
                .setNumero("12345")
                .setNomeTitular("Titular")
                .setCpfDoTitular("12312312312")
                .build())
            )
            .build()

        //acao
        Mockito
            .`when`(grpcClient.buscarChave(Mockito.any()))
            .thenReturn(buscarChaveResponse)

        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
        val response = httpClient.toBlocking().exchange(request, Any::class.java)

        //validacao
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())

    }


    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class BuscaStubFactory {
        @Singleton
        internal fun buscaStubMock(): BuscarChaveServiceGrpc.BuscarChaveServiceBlockingStub {
            return Mockito.mock(BuscarChaveServiceGrpc.BuscarChaveServiceBlockingStub::class.java)
        }
    }
}