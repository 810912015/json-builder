package com.pivot.json;


import com.pivot.json.generated.JSONBaseListener;
import com.pivot.json.generated.JSONParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BuilderListener extends JSONBaseListener {
    ParseTreeProperty<Object> props = new ParseTreeProperty<>();

    public Map<String, Object> getResult(ParseTree root) {
        Object r = props.get(root);
        return (Map<String, Object>) r;
    }

    @Override
    public void exitJson(JSONParser.JsonContext ctx) {
        if (ctx.children.size() == 1) {
            Object cc = props.get(ctx.getChild(0));
            props.put(ctx, cc);
        } else {
            List<Object> r = new ArrayList<>();
            for (ParseTree pt : ctx.children) {
                r.add(props.get(pt));
            }
            props.put(ctx, r);
        }
    }

    @Override
    public void enterObj(JSONParser.ObjContext ctx) {
        props.put(ctx, new HashMap<String, Object>());
    }

    @Override
    public void exitPair(JSONParser.PairContext ctx) {
        ParseTree key = ctx.getChild(0);
        ParseTree value = ctx.getChild(2);
        Map<String, Object> pm = (Map<String, Object>) props.get(ctx.parent);
        String k =pure( key.getText());
        Object v = props.get(value);
        pm.put(k, v);
    }
    String pure(String k){
        String r=k.trim();
        if(r.startsWith("\"")){
            r=r.substring(1);
        }
        if(r.endsWith("\"")){
            r=r.substring(0,r.length()-1);
        }
        return r;
    }
    @Override
    public void exitArray(JSONParser.ArrayContext ctx) {
        List<Object> r = new ArrayList<>();
        for (ParseTree pt : ctx.children) {
            Object tv = props.get(pt);
            if (tv == null) {
                continue;
            }
            r.add(tv);
        }
        props.put(ctx, r);

    }

    @Override
    public void exitValue(JSONParser.ValueContext ctx) {
        Object o = props.get(ctx.getChild(0));
        if (o != null) {
            props.put(ctx, o);
            return;
        }
        String s =pure(ctx.getText());
        Object r=analyze(s);
        props.put(ctx, r);
    }
    Object analyze(String s){
        try {
            Object r =null;
            if("true".equalsIgnoreCase(s)||"false".equalsIgnoreCase(s)) {
                r = anylazeOne(s, Boolean::valueOf);
                if (r != null) {
                    return r;
                }
            }
            r = anylazeOne(s, Integer::valueOf);
            if (r != null) {
                return r;
            }
            r = anylazeOne(s, Long::valueOf);
            if (r != null) {
                return r;
            }
            r= anylazeOne(s, Float::valueOf);
            if (r != null) {
                return r;
            }
            return s;
        }catch (Throwable e){
            return s;
        }
    }
    Object anylazeOne(String s,Function<String,Object> f){
        try{
            return f.apply(s);
        }catch (Throwable e){
            return null;
        }
    }
}
