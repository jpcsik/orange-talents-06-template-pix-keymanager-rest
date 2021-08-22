package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.CadastrarChaveServiceGrpc
import br.com.zupacademy.jpcsik.NovaChavePixResponse
import br.com.zupacademy.jpcsik.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.util.*

@MicronautTest
internal class RegistraChaveControllerTest{

    @field:Inject
    lateinit var grpcClient: CadastrarChaveServiceGrpc.CadastrarChaveServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    private val clienteId = UUID.randomUUID().toString()

    private val pixId = UUID.randomUUID().toString()

    private val respostaGrpc = NovaChavePixResponse.newBuilder()
        .setPixId(pixId)
        .build()


    //Happy Path
    @Test
    fun `Deve registrar uma nova chave pix`(){
        //cenario
        val novaChaveRequest = object {
            val tipoChave = "CPF"
            val valorChave = "12312312312"
            val tipoConta = "CONTA_CORRENTE"
        }

        //acao
        Mockito
            .`when`(grpcClient.cadastrar(Mockito.any()))
            .thenReturn(respostaGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/$clienteId/pix", novaChaveRequest)
        val response = httpClient.toBlocking().exchange(request, CadastrarChaveRequest::class.java)

        //validacao
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(pixId))

    }

    //Alternative Path
    @Test
    fun `Nao deve registrar uma nova chave pix com telefone invalido`(){
        //cenario
        val novaChaveRequest = object {
            val tipoChave = "TELEFONE"
            val valorChave = "invalido"
            val tipoConta = "CONTA_CORRENTE"
        }

        //acao
        Mockito
            .`when`(grpcClient.cadastrar(Mockito.any()))
            .thenReturn(respostaGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/$clienteId/pix", novaChaveRequest)
        val erro =  assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().exchange(request, CadastrarChaveRequest::class.java)
        }

        //validacao
        assertEquals(erro.status.code, HttpStatus.BAD_REQUEST.code)

    }

    @Test
    fun `Nao deve registrar uma nova chave pix com email invalido`(){
        //cenario
        val novaChaveRequest = object {
            val tipoChave = "EMAIL"
            val valorChave = "invalido"
            val tipoConta = "CONTA_CORRENTE"
        }

        //acao
        Mockito
            .`when`(grpcClient.cadastrar(Mockito.any()))
            .thenReturn(respostaGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/$clienteId/pix", novaChaveRequest)
        val erro =  assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().exchange(request, CadastrarChaveRequest::class.java)
        }

        //validacao
        assertEquals(erro.status.code, HttpStatus.BAD_REQUEST.code)

    }

    @Test
    fun `Nao deve registrar uma nova chave pix aleatoria com valor preenchido`(){
        //cenario
        val novaChaveRequest = object {
            val tipoChave = "ALEATORIA"
            val valorChave = "invalido"
            val tipoConta = "CONTA_CORRENTE"
        }

        //acao
        Mockito
            .`when`(grpcClient.cadastrar(Mockito.any()))
            .thenReturn(respostaGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/$clienteId/pix", novaChaveRequest)
        val erro =  assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().exchange(request, CadastrarChaveRequest::class.java)
        }

        //validacao
        assertEquals(erro.status.code, HttpStatus.BAD_REQUEST.code)

    }


    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock(): CadastrarChaveServiceGrpc.CadastrarChaveServiceBlockingStub {
            return Mockito.mock(CadastrarChaveServiceGrpc.CadastrarChaveServiceBlockingStub::class.java)
        }
    }
}