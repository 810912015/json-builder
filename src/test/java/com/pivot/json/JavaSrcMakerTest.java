package com.pivot.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author xianfengzhang
 * @date 2019/1/16
 */
class JavaSrcMakerTest {
    @Test
    void makeOne() {
        Map<String,Object> m=new HashMap<>();
        m.put("id",1L);
        doNothing().when(tt).makeSimple(any(),any(),any());
        doNothing().when(tt).deep(any(),any(),any(),any(),any(),any(),any());
        tt.makeOne(m,null,sb,"id");
        verify(tt,times(1))
                .makeSimple(eq(sb),eq("id"),eq("Long"));
        m.put("ids", Arrays.asList(Collections.singletonMap("abc",123)));
        tt.makeOne(m,null,sb,"ids");
        verify(tt,times(1))
                .deep(any(),any(),any(),any(),any(),eq("List<"),eq(">"));
    }

    @Test
    void makeSimple() {
       doNothing().when(tt).makeSetProperty(any(),any(),any());
       doNothing().when(tt).makeGetProperty(any(),any(),any());
       tt.makeSimple(sb,"id","Long");
       assertEquals("private Long id ;\n",cleanString());
    }

    @Test
    void deep() {
        doNothing().when(tt).toClass(any(),any(),any());
        doNothing().when(tt).makeSetProperty(any(),any(),any());
        doNothing().when(tt).makeGetProperty(any(),any(),any());
        tt.deep(null,sb,"ids","Interger",null,"List<",">");
        assertEquals("private List<Interger> ids ;\n",cleanString());
    }

    String cleanString(){
        return sb.toString().replace("\r","");
    }
    @Test
    void makeGetProperty() {
        t.makeGetProperty(sb,"Long","id");
        String r="public Long getId(){\n" +
                "return this.id;\n" +
                "}\n";
        assertEquals(r,cleanString());
    }

    @Test
    void makeSetProperty() {
        t.makeSetProperty(sb,"Long","id");
        String r="public void setId(Long id){\n" +
                "this.id=id;\n" +
                "}\n";
        // lineseperator \n\r->\n
        String rs=sb.toString().replace("\r","");
        assertEquals(r,rs);
    }
    JavaSrcMaker t;
    JavaSrcMaker tt;
    StringBuilder sb;
    @BeforeEach
    void init(){
        sb=new StringBuilder();
        t=new JavaSrcMaker();
        tt=spy(t);
    }
}