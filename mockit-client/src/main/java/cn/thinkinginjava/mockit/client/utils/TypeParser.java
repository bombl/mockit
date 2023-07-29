package cn.thinkinginjava.mockit.client.utils;


import cn.thinkinginjava.mockit.client.model.ReturnType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for parsing and processing complex return types.
 */
public class TypeParser {

    /**
     * Parses the input string and constructs a tree structure representing the return type.
     *
     * @param input The input string representing the return type.
     * @return The root node of the constructed tree representing the return type.
     */
    public static ReturnType parseReturnTypeToTree(String input) {
        return parseReturnType(input);
    }

    /**
     * Recursively parses the input string and constructs a tree structure representing the return type.
     *
     * @param input The input string representing the return type.
     * @return The root node of the constructed tree representing the return type.
     */
    private static ReturnType parseReturnType(String input) {
        String returnType = getReturnType(input);
        String returnTypeString = getReturnTypeString(input);

        ReturnType root = new ReturnType(returnType);

        Class<?> clazz = ClassUtil.getClassByName(returnType);
        if (Map.class.isAssignableFrom(clazz)) {
            root.setIsMap(true);
            int firstCommaIndex = returnTypeString.indexOf(',');
            if (firstCommaIndex != -1) {
                root.setKeyClassName(returnTypeString.substring(0, firstCommaIndex).trim());
                returnTypeString = returnTypeString.substring(firstCommaIndex + 1).trim();
            }
        }

        if (!returnTypeString.isEmpty()) {
            List<String> returnTypes = splitNestedReturnTypes(returnTypeString);
            for (String returnType0 : returnTypes) {
                ReturnType childNode = parseReturnType(returnType0.trim());
                root.addChild(childNode);
            }
        }

        return root;
    }

    /**
     * Extracts the main return type from the input string.
     *
     * @param input The input string representing the return type.
     * @return The main return type as a string.
     */
    private static String getReturnType(String input) {
        int startIndex = input.indexOf('<');
        return startIndex == -1 ? input.trim() : input.substring(0, startIndex).trim();
    }

    /**
     * Extracts the nested return types string from the input string.
     *
     * @param input The input string representing the return type.
     * @return The nested return types as a string.
     */
    private static String getReturnTypeString(String input) {
        int startIndex = input.indexOf('<');
        int endIndex = input.lastIndexOf('>');
        return startIndex == -1 || endIndex == -1 ? "" : input.substring(startIndex + 1, endIndex).trim();
    }

    /**
     * Splits the nested return types string into individual return types.
     *
     * @param input The nested return types as a string.
     * @return A list containing individual return types as strings.
     */
    private static List<String> splitNestedReturnTypes(String input) {
        List<String> returnTypes = new ArrayList<>();

        int level = 0;
        StringBuilder sb = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (ch == '<') {
                level++;
            } else if (ch == '>') {
                level--;
            }

            if (ch == ',' && level == 0) {
                returnTypes.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(ch);
            }
        }

        if (sb.length() > 0) {
            returnTypes.add(sb.toString());
        }

        return returnTypes;
    }
}
