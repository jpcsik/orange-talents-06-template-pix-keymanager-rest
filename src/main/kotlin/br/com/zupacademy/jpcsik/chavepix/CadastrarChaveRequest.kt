package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.NovaChavePixRequest
import br.com.zupacademy.jpcsik.TipoChave
import br.com.zupacademy.jpcsik.TipoConta
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ChaveValida
@Introspected
data class CadastrarChaveRequest(
    @field:NotBlank val tipoChave: TipoChave,
    @field:Size(max = 77) val valorChave: String,
    @field:NotBlank val tipoConta: TipoConta
) {

    //Retorna uma request grpc
    fun toGrpcRequest(clienteId: UUID): NovaChavePixRequest? {
        return NovaChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoChave(tipoChave)
            .setValorChave(valorChave)
            .setTipoConta(tipoConta)
            .build()
    }

}
