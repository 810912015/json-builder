package com.pivot.json;

import java.util.Map;

/**
 * A json parser,main function:
 * 1.query value as string by comma seperated key,like "a.b[1].c"
 * 2.generate java source string by json;
 */
public interface JsonBuilder {
    /**
     * generate java source file from json
     * all classes in a file without imports;
     * @param className root class name
     * @return java source class in string format
     */
    String toSrc(String className);

    /**
     * get the json in map
     * @return
     */
    Map<String,Object> getModel();

    /**
     * is the json contains json as string field
     * @return
     */
    boolean isShouldDig();
    /**
     * parse the json contained in fields recursively.
     * As you know,some json contains fields that is a string,
     * but the content alse a json.
     * This method is to parse that kind of string to json.
     */
    void digIn();
    /**
     * query json by key.
     * key format
     * 1.seperated by comma;
     * 2.[] from array index;
     * @param path key
     * @return value,string,will  throw new RuntimeException("illegal formated json",e) when anything wrong
     */
    Object get(String path);

}
