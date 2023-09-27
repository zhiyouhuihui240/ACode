package cn.hx.plugin.junkcode.utils

import com.squareup.javapoet.MethodSpec

import javax.lang.model.element.Modifier


class MethodsUtil {






    static generateRandomMethods(MethodSpec.Builder methodBuilder){
        methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(int.class, "year")
                .addParameter(int.class, "month")
                .addParameter(int.class, "day")
                .returns(Date.class)
                .addStatement("return new \$T(year - 1900, month - 1, day)", Date.class)
    }
}