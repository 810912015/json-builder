package com.pivot.json;

import java.util.List;
import java.util.Map;

/**
 * generate java source class string by Map<String,Object>
 *     the Object here might be:string,array,map<String,Object>
 * @author xianfengzhang
 * @date 2019/1/14
 */
public class SrcMaker {
    public String toSrc(Map<String, Object> r, String className) {
        StringBuilder sb = new StringBuilder();
        toClass(r, sb, className);
        return sb.toString();
    }

    private int level = 0;

    public void toClass(Map<String, Object> map, StringBuilder outer, String className) {
        StringBuilder sb = new StringBuilder();
        boolean level1 = level++ == 0;
        StringBuilder csb = level1 ? outer : sb;
        if (level1) {
            csb.append("public class ");
        } else {
            csb.append("public static class ");
        }
        csb.append(className);
        csb.append("{");
        csb.append(System.lineSeparator());
        for (String k : map.keySet()) {
            makeOne(map, outer, sb, k);
        }
        sb.append("}");
        sb.append(System.lineSeparator());
        outer.append(sb.toString());
    }

    String makeClassName(String k) {
        return k.substring(0, 1).toUpperCase() + k.substring(1);
    }

    private void makeOne(Map<String, Object> map, StringBuilder outer, StringBuilder sb, String k) {
        Object o = map.get(k);
        String cn = makeClassName(k);
        if (o instanceof String) {
            makeSimple(sb, k,"String");
        }else if(o instanceof Boolean){
            makeSimple(sb,k,"Boolean");
        }else if(o instanceof Integer){
            makeSimple(sb,k,"Integer");
        }else if(o instanceof Long){
            makeSimple(sb,k,"Long");
        }else if(o instanceof Float){
            makeSimple(sb,k,"Float");
        }else if (o instanceof Map<?, ?>) {
            deep(outer, sb, k, cn, (Map<String, Object>) o, "", "");
        } else {
            List<Object> l = (List<Object>) map.get(k);
            Object o1 = l.get(0);
            if (o1 instanceof Map<?, ?>) {
                deep(outer, sb, k, cn, (Map<String, Object>) o1, "List<", ">");
            }
        }
    }

    private void makeSimple(StringBuilder sb, String k,String type) {
        sb.append(String.format("private %s %s ;",type,k));
        sb.append(System.lineSeparator());
        makeGetProperty(sb, type, k);
        makeSetProperty(sb, type, k);
    }

    private void deep(StringBuilder outer, StringBuilder sb, String k,
                      String cn, Map<String, Object> o2, String startField, String endField) {
        toClass(o2, outer, cn);
        String fcn = String.format("%s%s%s", startField, cn, endField);
        sb.append(String.format("private %s %s;", fcn, k));
        sb.append(System.lineSeparator());
        makeGetProperty(sb, fcn, k);
        makeSetProperty(sb, fcn, k);
    }

    void makeGetProperty(StringBuilder sb, String cn, String field) {
        sb.append(String.format("public %s get%s(){", cn, makeClassName(field)));
        sb.append(System.lineSeparator());
        sb.append(String.format("return this.%s;", field));
        sb.append(System.lineSeparator());
        sb.append("}");
        sb.append(System.lineSeparator());
    }

    void makeSetProperty(StringBuilder sb, String cn, String field) {
        sb.append(String.format("public void set%s(%s %s){", makeClassName(field), cn, field));
        sb.append(System.lineSeparator());
        sb.append(String.format("this.%s=%s;", field, field));
        sb.append(System.lineSeparator());
        sb.append("}");
        sb.append(System.lineSeparator());
    }
}
