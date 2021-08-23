package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.BuscarChaveResponse
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Introspected
class DetalheChaveResponse(response: BuscarChaveResponse) {

    val pixId = response.pixId
    val clienteId = response.clientId
    val tipo = response.chave.tipo.name
    val chave = response.chave.chave

    //Converte Timestamp em LocalDateTime
    val criadaEm = LocalDateTime.ofEpochSecond(
        response.chave.criadaEm.seconds,
        response.chave.criadaEm.nanos,
        ZoneOffset.UTC
    ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy-hh/ss/nn"))

    //Mapeia os dados da conta para uma variavel da chave
    val conta = mapOf(
        Pair("tipo", response.chave.conta.tipo.name),
        Pair("instituicao", response.chave.conta.instituicao),
        Pair("nomeDoTitular", response.chave.conta.nomeTitular),
        Pair("cpfDoTitular", response.chave.conta.cpfDoTitular),
        Pair("agencia", response.chave.conta.agencia),
        Pair("numero", response.chave.conta.numero)
    )

}
