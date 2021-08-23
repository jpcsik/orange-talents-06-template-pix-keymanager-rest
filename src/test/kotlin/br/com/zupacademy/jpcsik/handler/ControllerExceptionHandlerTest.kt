package br.com.zupacademy.jpcsik.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ControllerExceptionHandlerTest {

    private val request = HttpRequest.GET<Any>("/")

    @Test
    internal fun `Deve retornar 400 quando quando servidor grpc lancar INVALID_ARGUMENT`(){
        //cenario
        val mensagem = "dados invalidos"
        val exception = StatusRuntimeException(Status.INVALID_ARGUMENT .withDescription(mensagem))

        //acao
        val resposta = ControllerExceptionHandler().handle(request, exception)

        //validacao
        assertEquals(HttpStatus.BAD_REQUEST, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)

    }

    @Test
    internal fun `Deve retornar 404 quando quando servidor grpc lancar NOT_FOUND`(){
        //cenario
        val mensagem = "nao encontrado"
        val exception = StatusRuntimeException(Status.NOT_FOUND .withDescription(mensagem))

        //acao
        val resposta = ControllerExceptionHandler().handle(request, exception)

        //validacao
        assertEquals(HttpStatus.NOT_FOUND, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)

    }

    @Test
    internal fun `Deve retornar 422 quando quando servidor grpc lancar ALREADY_EXISTS`(){
        //cenario
        val mensagem = "ja existe"
        val exception = StatusRuntimeException(Status.ALREADY_EXISTS .withDescription(mensagem))

        //acao
        val resposta = ControllerExceptionHandler().handle(request, exception)

        //validacao
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)

    }

    @Test
    internal fun `Deve retornar 503 quando quando servidor grpc lancar INTERNAL`(){
        //cenario
        val mensagem = "servico indisponivel"
        val exception = StatusRuntimeException(Status.INTERNAL .withDescription(mensagem))

        //acao
        val resposta = ControllerExceptionHandler().handle(request, exception)

        //validacao
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)

    }

    @Test
    internal fun `Deve retornar 500 quando quando servidor grpc lancar alguma outra exception`(){
        //cenario
        val mensagem = "erro interno"
        val exception = StatusRuntimeException(Status.UNKNOWN .withDescription(mensagem))

        //acao
        val resposta = ControllerExceptionHandler().handle(request, exception)

        //validacao
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)

    }

}