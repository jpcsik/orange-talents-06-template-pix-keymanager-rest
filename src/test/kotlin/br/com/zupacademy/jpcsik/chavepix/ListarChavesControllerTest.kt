package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.ListaChavesResponse
import br.com.zupacademy.jpcsik.ListarChavesServiceGrpc
import br.com.zupacademy.jpcsik.TipoChave
import br.com.zupacademy.jpcsik.TipoConta
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
internal class ListarChavesControllerTest{

    @field:Inject
    lateinit var grpcClient: ListarChavesServiceGrpc.ListarChavesServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    private val clienteId = UUID.randomUUID()

    private val pixId = UUID.randomUUID()


    @Test
    fun `Deve listar todas chaves pix para o cliente`(){
        //cenario
        val chavesResponse = ListaChavesResponse.newBuilder()
            .setClienteId(clienteId.toString())
            .addAllChaves(listOf(

                ListaChavesResponse.ChavePix.newBuilder()
                .setPixId(pixId.toString())
                .setTipoChave(TipoChave.CPF)
                .setValorChave("12312312312")
                .setCriadaEm(Timestamp.newBuilder()
                    .setSeconds(LocalDateTime.now().second.toLong())
                    .setNanos(LocalDateTime.now().nano)
                    .build())
                .setTipoConta(TipoConta.CONTA_CORRENTE)
                .build(),

                ListaChavesResponse.ChavePix.newBuilder()
                .setPixId(pixId.toString())
                .setTipoChave(TipoChave.EMAIL)
                .setValorChave("email@email.com")
                .setCriadaEm(Timestamp.newBuilder()
                    .setSeconds(LocalDateTime.now().second.toLong())
                    .setNanos(LocalDateTime.now().nano)
                    .build())
                .setTipoConta(TipoConta.CONTA_CORRENTE)
                .build()))

            .build()

        //acao
        Mockito
            .`when`(grpcClient.listarChaves(Mockito.any()))
            .thenReturn(chavesResponse)

        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clienteId/chaves")
        val response = httpClient.toBlocking().exchange(request, List::class.java)

        //validacao
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertTrue(response.body()!!.size == 2)

    }


    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class BuscaStubFactory {
        @Singleton
        internal fun buscaStubMock(): ListarChavesServiceGrpc.ListarChavesServiceBlockingStub {
            return Mockito.mock(ListarChavesServiceGrpc.ListarChavesServiceBlockingStub::class.java)
        }
    }
}