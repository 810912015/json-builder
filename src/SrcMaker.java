package com.pivot.json;

import java.util.Map;

/**
 * @author xianfengzhang
 * @date 2019/1/16
 */
public interface SrcMaker {
    String getLang();
    String toSrc(Map<String, Object> r, String className);
}
