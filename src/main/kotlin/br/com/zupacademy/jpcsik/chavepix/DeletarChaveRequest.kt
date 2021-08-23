package br.com.zupacademy.jpcsik.chavepix

import br.com.zupacademy.jpcsik.RemoverChaveRequest
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank

@Introspected
data class DeletarChaveRequest(
    @field:NotBlank val pixId: UUID
) {

    fun toGrpcRequest(clienteId: UUID): RemoverChaveRequest? {
        return RemoverChaveRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build()
    }

}
