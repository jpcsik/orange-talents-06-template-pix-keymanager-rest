package br.com.zupacademy.jpcsik.chavepix

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import jakarta.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@MustBeDocumented
@Target(CLASS, TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = [ChaveValidaValidator::class])
annotation class ChaveValida(
    val message: String = "Chave Pix com valor inválido!",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ChaveValidaValidator : ConstraintValidator<ChaveValida, CadastrarChaveRequest> {
    override fun isValid(
        value: CadastrarChaveRequest?,
        annotationMetadata: AnnotationValue<ChaveValida>,
        context: ConstraintValidatorContext
    ): Boolean {

        value ?: return false

        val valido: Boolean

        when (value.tipoChave.number) {
            1 -> {
                valido = value.valorChave.matches("^[0-9]{11}$".toRegex())
                context.messageTemplate("CPF inválido!")
            }
            2 -> {
                valido = value.valorChave.matches("^+[1-9][0-9]\\d{1,14}\$".toRegex())
                context.messageTemplate("Telefone inválido!")
            }
            3 -> {
                valido =
                    value.valorChave.matches("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex())
                context.messageTemplate("Email inválido!")
            }
            4 -> {
                valido = value.valorChave.isBlank()
                context.messageTemplate("Valor de chave aleatoria não deve ser preenchido!")
            }

            else -> valido = false

        }

        return valido

    }

}