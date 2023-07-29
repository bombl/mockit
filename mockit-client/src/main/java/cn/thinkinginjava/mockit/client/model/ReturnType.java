package cn.thinkinginjava.mockit.client.model;

/**
 * The ReturnType class represents the structure of a return type in the context of data generation.
 * It is used to store information about the return type of a method, including whether it represents
 * a simple class, a Map, or a List, and any nested structures within it.
 */
public class ReturnType {

    private String className;
    private Boolean isMap;
    private String keyClassName;
    private ReturnType children;
    private Object data;

    /**
     * Constructor to create a ReturnType object with the given class name.
     *
     * @param className The class name for the ReturnType.
     */
    public ReturnType(String className) {
        this.className = className;
        this.isMap = false;
        this.keyClassName = null;
        this.children = null;
    }

    /**
     * Sets the key class name for the ReturnType in case of Map.
     *
     * @param keyClassName The key class name to be set.
     */
    public void setKeyClassName(String keyClassName) {
        this.keyClassName = keyClassName;
    }

    /**
     * Sets whether the ReturnType represents a Map or not.
     *
     * @param isMap true if the ReturnType is a Map, false otherwise.
     */
    public void setIsMap(Boolean isMap) {
        this.isMap = isMap;
    }

    /**
     * Adds a child ReturnType to the current ReturnType, representing nested structure.
     *
     * @param child The child ReturnType to be added.
     */
    public void addChild(ReturnType child) {
        this.children = child;
    }

    /**
     * Returns the class name of the ReturnType.
     *
     * @return The class name of the ReturnType.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Checks if the ReturnType represents a Map.
     *
     * @return true if the ReturnType is a Map, false otherwise.
     */
    public Boolean isMap() {
        return isMap;
    }

    /**
     * Returns the key class name in case of Map.
     *
     * @return The key class name for the ReturnType if it's a Map, null otherwise.
     */
    public String getKeyClassName() {
        return keyClassName;
    }

    /**
     * Returns the child ReturnType representing nested structure.
     *
     * @return The child ReturnType if it exists, null otherwise.
     */
    public ReturnType getChildren() {
        return children;
    }

    /**
     * Returns additional data associated with the ReturnType.
     *
     * @return The additional data associated with the ReturnType.
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets additional data for the ReturnType.
     *
     * @param data The additional data to be set.
     */
    public void setData(Object data) {
        this.data = data;
    }
}
