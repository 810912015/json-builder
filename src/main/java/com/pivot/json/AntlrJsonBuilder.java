package com.pivot.json;

import com.pivot.json.generated.JSONLexer;
import com.pivot.json.generated.JSONParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class AntlrJsonBuilder implements JsonBuilder {
    private String json;
    private Map<String, Object> model;
    private SrcMaker maker;

    @Override
    public boolean isShouldDig() {
        return shouldDig;
    }

    private boolean shouldDig;

    private AntlrJsonBuilder(String s,boolean shouldDig) {
        this.json = s;
        maker = new SrcMaker();
        this.model=parse(this.json);
        this.shouldDig=shouldDig;
        if(shouldDig){
            this.digIn();
        }
    }
    private AntlrJsonBuilder(String s){
        this(s,true);
    }
    /**
     * jsonBuilder factory
     * @param json source json string
     * @return
     */
    public static JsonBuilder build(String json) {
        return new AntlrJsonBuilder(json);
    }

    /**
     * jsonBuilder factory
     * @param json source json string
     * @param shouldDig  is the json string contains json as string field
     * @return
     */
    public static JsonBuilder build(String json,boolean shouldDig) {
        return new AntlrJsonBuilder(json,shouldDig);
    }
    @Override
    public String toSrc(String className) {
        try {
            return maker.toSrc(model, className);
        } catch (Exception e) {
            throw new RuntimeException("illegal formated json", e);
        }
    }

    @Override
    public Map<String, Object> getModel() {
        return this.model;
    }

    @Override
    public Object get(String path) {
        try {
            String[] sa = path.split("\\.");
            Map<String, Object> m = this.model;
            for (int i = 0; i < sa.length; i++) {
                String k = sa[i];
                String ks = k;
                int ind = -1;
                boolean isArray = k.contains("[");
                boolean last = i == sa.length - 1;
                if (isArray) {
                    String index = k.substring(k.indexOf("[") + 1, k.indexOf("]"));
                    ind = Integer.valueOf(index);
                    ks = k.substring(0, k.indexOf("["));
                }
                Object o = m.get(ks);
                if (o == null) {
                    return null;
                }
                if (!isArray) {
                    if (last) {
                        return o;
                    } else {
                        m = (Map<String, Object>) o;
                    }
                } else {
                    List<Object> l = (List<Object>) o;
                    if (last) {
                        return (String) (l.get(ind));
                    } else {
                        m = (Map<String, Object>) (l.get(ind));
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("illegal formated json", e);
        }
    }

    /**
     * 有些json的字符字段本身有包含了一个json对象,此方法的目的是
     * 递归解析字符字段中的json对象
     */
    @Override
    public void digIn() {
        if(!this.shouldDig){
            return;
        }
        dig(this.model);
    }

    void dig(Map<String, Object> m) {
        for (String k : m.keySet()) {
            Object v = m.get(k);
            if (v instanceof String) {
                Map<String, Object> m1 = null;
                try {
                    String uv = StringEscapeUtils.unescapeJava((String) v);
                    m1 = parse(uv);
                } catch (Exception e) {
                    continue;
                }
                if (m1 == null) {
                    continue;
                }
                dig(m1);
                m.put(k, m1);
            } else if (v instanceof Map<?, ?>) {
                dig((Map<String, Object>) v);
            }
        }
    }
    public Map<String, Object> parse(String json) {
        try {
            ParseTree pt;
            pt = makeByExp(json);
            ParseTreeWalker walker = new ParseTreeWalker();
            BuilderListener listener = new BuilderListener();
            walker.walk(listener, pt);
            Map<String, Object> r = listener.getResult(pt);
            return r;
        } catch (Exception e) {
            throw new RuntimeException("illegal formated json", e);
        }
    }

    public ParseTree makeByExp(String exp) {
        InputStream stream = new ByteArrayInputStream(exp.getBytes(StandardCharsets.UTF_8));
        CharStream cs = null;
        try {
            cs = CharStreams.fromStream(stream);
        } catch (IOException e) {
            return null;
        }
        JSONLexer lexer = new JSONLexer(cs);
        CommonTokenStream ctx = new CommonTokenStream(lexer);

        JSONParser parser = new JSONParser(ctx);
        parser.removeErrorListeners();
        lexer.removeErrorListeners();
        parser.setBuildParseTree(true);
        ParseTree pt = parser.json();
        return pt;
    }
}
