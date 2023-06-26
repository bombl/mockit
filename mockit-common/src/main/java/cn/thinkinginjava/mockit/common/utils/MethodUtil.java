package cn.thinkinginjava.mockit.common.utils;

import cn.thinkinginjava.mockit.common.model.dto.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class MethodUtil {

    public static List<MethodInfo> convertMethods(Method[] methods) {
        List<MethodInfo> methodInfoList = new ArrayList<>();

        for (Method method : methods) {
            MethodInfo methodInfo = new MethodInfo();
            methodInfo.setAccessModifier(Modifier.toString(method.getModifiers()));
            methodInfo.setReturnType(method.getReturnType().getName());
            methodInfo.setMethodName(method.getName());
            Parameter[] parameters = method.getParameters();
            List<String> parameterTypes = new ArrayList<>();
            for (Parameter parameter : parameters) {
                parameterTypes.add(parameter.getType().getName());
            }
            methodInfo.setParameters(parameterTypes);
            methodInfoList.add(methodInfo);
        }
        return methodInfoList;
    }
}
