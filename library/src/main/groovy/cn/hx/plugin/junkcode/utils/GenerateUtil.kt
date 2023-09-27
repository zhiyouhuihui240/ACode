package cn.hx.plugin.junkcode.utils

import com.squareup.javapoet.MethodSpec
import java.util.Date

import javax.lang.model.element.Modifier


object GenerateUtil {

    @JvmName("generateRandomMethods")
    @JvmStatic
    fun generateRandomMethods( methodBuilder: MethodSpec.Builder){
        methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .addParameter(Int::class.java, "year")
            .addParameter(Int::class.java, "month")
            .addParameter(Int::class.java, "day")
            .returns(
                Date::class.java)
                .addStatement("return new \$T(year - 1900, month - 1, day)", Date::class.java)
    }

}