package br.com.zupacademy.jpcsik.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

//Trata as exceptions que podem ser lancadas pelo servidor grpc em qualquer ponto da aplicacao
@Singleton
class ControllerExceptionHandler: ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {

        val statusCode = exception.status.code

        val statusDescription = exception.status.description

        //Mapeia os erros do servidor grpc para um status http
        val (httpStatus, message) = when (statusCode) {
            Status.INVALID_ARGUMENT.code -> Pair(HttpStatus.BAD_REQUEST, statusDescription)
            Status.NOT_FOUND.code -> Pair(HttpStatus.NOT_FOUND, statusDescription)
            Status.ALREADY_EXISTS.code -> Pair(HttpStatus.UNPROCESSABLE_ENTITY, statusDescription)
            Status.INTERNAL.code -> Pair(HttpStatus.SERVICE_UNAVAILABLE, statusDescription)
            else -> Pair(HttpStatus.INTERNAL_SERVER_ERROR, statusDescription)

        }

        /*
        Retorna uma resposta para o usuario com o status adequado
        e com a mensagem de erro definida pelo servidor grpc
         */
        return HttpResponse
            .status<JsonError>(httpStatus)
            .body(JsonError(message))

    }

}