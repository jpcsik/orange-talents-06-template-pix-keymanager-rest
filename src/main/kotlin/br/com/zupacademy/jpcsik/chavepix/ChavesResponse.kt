package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.ListaChavesResponse
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ChavesResponse(response: ListaChavesResponse.ChavePix){

    val pixId = response.pixId
    val chave = response.valorChave
    val tipo = response.tipoChave.name
    val conta = response.tipoConta.name

    val criadaEm = LocalDateTime.ofEpochSecond(
        response.criadaEm.seconds,
        response.criadaEm.nanos,
        ZoneOffset.UTC
    ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy-hh/ss/nn"))

}
