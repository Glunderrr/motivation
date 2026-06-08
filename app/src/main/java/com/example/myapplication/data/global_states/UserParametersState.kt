package com.example.myapplication.data.global_states

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.data.model.Personal
import com.example.myapplication.di.util.BindKey
import com.example.myapplication.di.util.IOScope
import com.example.myapplication.repository.interfaces.PersonalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

@Singleton
class UserParametersState @Inject constructor(
    private val personalRepository: PersonalRepository,
    @IOScope private val scope: CoroutineScope
) {
    init {
        launch()
    }

    var personalDataIsBlank by mutableStateOf(true)
        private set
    private val _state = MutableStateFlow(Personal())
    val personalState: StateFlow<Personal> = _state.asStateFlow()

    // Підписується на зміни особистих даних з репозиторію та оновлює локальний стан;
    // після першого отримання даних скидає прапорець personalDataIsBlank
    private fun launch() {
        scope.launch {
            personalRepository.getPersonal().collect { personal ->
                personal?.let {
                    _state.value = it
                    personalDataIsBlank = false
                }
            }
        }
    }

    // Оновлює окреме поле профілю користувача за рядковим ключем без перестворення всього об'єкта
    fun updateFieldByKey(key: String, value: Any?) {
        val current = _state.value.setFieldByKey(key, value)
        _state.update { current }
    }

    // Зберігає поточний стан особистих даних у базу даних та знімає прапорець незаповненого профілю
    fun savePersonalData() {
        scope.launch {
            personalRepository.upsertPersonal(_state.value)
            personalDataIsBlank = false
        }
    }
}


// Повертає копію об'єкта Personal із оновленим полем, знайденим за анотацією BindKey;
// використовує рефлексію Kotlin для динамічного присвоєння значення без явного when-маппінгу
fun Personal.setFieldByKey(key: String, value: Any?): Personal {
    val kClass = this::class

    val property = kClass.memberProperties.firstOrNull { prop ->
        prop.findAnnotation<BindKey>()?.key == key
    } ?: throw IllegalArgumentException("Field with key '$key' not found")

    val ctor = kClass.primaryConstructor
        ?: throw IllegalStateException("Primary constructor not found")

    val coerced = coerceType(value, property.returnType.jvmErasure)

    val args = ctor.parameters.associateWith { param: KParameter ->
        if (param.name == property.name) {
            coerced
        } else {
            val p = kClass.memberProperties.first { it.name == param.name }
            p.getter.call(this)
        }
    }

    return ctor.callBy(args)
}

// Приводить вхідне значення до типу цільового поля для коректного присвоєння через рефлексію
private fun coerceType(value: Any?, targetClass: kotlin.reflect.KClass<*>): Any? {
    if (value == null) return null
    return when (targetClass) {
        Int::class -> when (value) {
            is Number -> value.toInt()
            is String -> value.toIntOrNull()
            is Boolean -> if (value) 1 else 0
            else -> value
        }

        Boolean::class -> when (value) {
            is Boolean -> value
            is Number -> value.toInt() != 0
            is String -> value.equals("true", true) || value == "1"
            else -> value
        }

        String::class -> value.toString()
        else -> value
    }
}
