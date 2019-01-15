package com.pivot.json;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xianfengzhang
 * @date 2019/1/14
 */

class AntlrJsonBuilderTest {
    public static class Address {
            private String streetAddress;

            public String getStreetAddress() {
                return this.streetAddress;
            }

            public void setStreetAddress(String streetAddress) {
                this.streetAddress = streetAddress;
            }

            private String city;

            public String getCity() {
                return this.city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            private Integer postalCode;

            public Integer getPostalCode() {
                return this.postalCode;
            }

            public void setPostalCode(Integer postalCode) {
                this.postalCode = postalCode;
            }

            private String state;

            public String getState() {
                return this.state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    public static class PhoneNumbers {
            private String number;

            public String getNumber() {
                return this.number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            private String type;

            public String getType() {
                return this.type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    public static class Addr {
        private String firstName;

        public String getFirstName() {
            return this.firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        private String lastName;

        public String getLastName() {
            return this.lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        private Address address;

        public Address getAddress() {
            return this.address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        private String gender;

        public String getGender() {
            return this.gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        private Integer age;

        public Integer getAge() {
            return this.age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        private List<PhoneNumbers> phoneNumbers;

        public List<PhoneNumbers> getPhoneNumbers() {
            return this.phoneNumbers;
        }

        public void setPhoneNumbers(List<PhoneNumbers> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }
    }
    @Test
    void compare() throws IOException {
      long s=System.currentTimeMillis();
      ObjectMapper om =new ObjectMapper();
      Addr ad=om.readValue(Jsons.addr,Addr.class);
      System.out.println(ad.phoneNumbers.get(0).number);
      long d=System.currentTimeMillis()-s;
        System.out.println(d);
      long s1=System.currentTimeMillis();
      JsonBuilder jb=AntlrJsonBuilder.build(Jsons.addr,false);
      System.out.println(jb.get("phoneNumbers[0].number"));
      long d1=System.currentTimeMillis()-s1;
      System.out.println(d1);

    }

    @Test
    void get() {
        JsonBuilder jb = AntlrJsonBuilder.build(Jsons.sj);
        Object s1 = jb.get("menu.id");
        Object s2 = jb.get("menu.popup.menuitem[1].onclick");
        assertEquals("file", s1);
        assertEquals("OpenDoc()", s2);
        String src = jb.toSrc("MENU");
        System.out.println(src);
        assertNotNull(src);
    }

    static class Jsons {
            private static final String addr="{\n" +
                    "    \"firstName\": \"John\",\n" +
                    "    \"lastName\": \"Smith\",\n" +
                    "    \"gender\": \"man\",\n" +
                    "    \"age\": 32,\n" +
                    "    \"address\": {\n" +
                    "        \"streetAddress\": \"21 2nd Street\",\n" +
                    "        \"city\": \"New York\",\n" +
                    "        \"state\": \"NY\",\n" +
                    "        \"postalCode\": \"10021\"\n" +
                    "    },\n" +
                    "    \"phoneNumbers\": [\n" +
                    "        { \"type\": \"home\", \"number\": \"212 555-1234\" },\n" +
                    "        { \"type\": \"fax\", \"number\": \"646 555-4567\" }\n" +
                    "    ]\n" +
                    "}";
        private static final String s = "{\"im\":{\"closeMode\":\"AGENTCLOSE\",\"closeTime\":1547189986781,\"customerSource\":\"ANDROID-99999999-8.0.2_1.3.0\",\"dataChangeLastTime\":1547189986783,\"firstMessageTime\":1547188912788,\"gid\":\"1113464568820006928\",\"inQueueTime\":1547189213044,\"inServiceTime\":1547189213348,\"lastMessageTime\":1547189986775,\"requestTime\":1547189212790,\"sessionId\":\"600000000402195\"},\"phone\":{\"agentPhoneNum\":\"\",\"answerTime\":0,\"callId\":\"\",\"callType\":\"\",\"customerPhoneNum\":\"\",\"dataChangeLastTime\":0,\"direction\":\"\",\"hangupTime\":0,\"isAnswered\":false,\"receivingTime\":0},\"workSheet\":{\"agentUid\":\"_IMKF2987465259\",\"categories\":\"WS003469\",\"categoriesDetail\":[{\"categoryDesc\":\"订单查询修改>咨询>查询客规、行李、登机流程\",\"code\":\"WS003469\",\"gradeCode\":\"WS003461_WS003462_WS003469\"}],\"classificationParams\":\"{\\\"desc\\\":\\\"2019年01月16日 1人\\\",\\\"pagefrom\\\":\\\"10320608519\\\",\\\"appversion\\\":\\\"8.0.2\\\",\\\"ctype\\\":\\\"ORD\\\",\\\"sensitivewords\\\":\\\"{\\\\\\\"reason\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"mid\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"1113465195818123280\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"oid\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"8305464368\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"r\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"default\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"rt\\\\\\\\\\\\\\\":10,\\\\\\\\\\\\\\\"tag\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"ctrip/flight/national/client\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"v\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"A\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"way\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"MATCH\\\\\\\\\\\\\\\"}\\\\\\\",\\\\\\\"words_type\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"sent_words\\\\\\\":\\\\\\\"\\\\\\\"}\\\",\\\"cid\\\":\\\"8305464368\\\",\\\"bu\\\":\\\"FLT\\\",\\\"currency\\\":\\\"CNY\\\",\\\"amount\\\":\\\"910\\\",\\\"title\\\":\\\"【单程】 ZH9451  广州-昆明\\\",\\\"switchhumanreason\\\":\\\"{\\\\\\\"mid\\\\\\\":\\\\\\\"1113465195818123280\\\\\\\",\\\\\\\"oid\\\\\\\":\\\\\\\"8305464368\\\\\\\",\\\\\\\"r\\\\\\\":\\\\\\\"default\\\\\\\",\\\\\\\"rt\\\\\\\":10,\\\\\\\"tag\\\\\\\":\\\\\\\"ctrip/flight/national/client\\\\\\\",\\\\\\\"v\\\\\\\":\\\\\\\"A\\\\\\\",\\\\\\\"way\\\\\\\":\\\\\\\"MATCH\\\\\\\"}\\\",\\\"orderid\\\":\\\"8305464368\\\",\\\"channel\\\":\\\"FLT\\\",\\\"vtoken\\\":\\\"\\\"}\",\"customerUid\":\"_WeChat2392842733\",\"dataChangeLastTime\":1547189986781,\"direction\":0,\"empCode\":\"N28548\",\"id\":2110103,\"initiatorRole\":\"CUSTOMER\",\"initiatorUid\":\"_WeChat2392842733\",\"reason\":\"{\\\"mid\\\":\\\"1113465195818123280\\\",\\\"oid\\\":\\\"8305464368\\\",\\\"r\\\":\\\"default\\\",\\\"rt\\\":10,\\\"tag\\\":\\\"ctrip/flight/national/client\\\",\\\"v\\\":\\\"A\\\",\\\"way\\\":\\\"MATCH\\\"}\",\"serviceType\":\"1325\",\"skillGroupCode\":\"G260019\",\"status\":\"CLOSE\",\"summary\":\"\",\"type\":\"IM\"}}";

        private static final String sj = "{\"menu\": {\n" +
                "  \"id\": \"file\",\n" +
                "  \"value\": \"File\",\n" +
                "  \"popup\": {\n" +
                "    \"menuitem\": [\n" +
                "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
                "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
                "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
                "    ]\n" +
                "  }\n" +
                "}}";
        private static final String json = "{\n" +
                "  \"messageId\": \"190111.145947.10.25.185.123.34.36675\",\n" +
                "  \"subject\": \"basebiz.implus.distribution.common\",\n" +
                "  \"durable\": true,\n" +
                "  \"storeAtFailed\": false,\n" +
                "  \"attrs\": {\n" +
                "    \"qmq_expireTime\": 1547190887059,\n" +
                "    \"qmq_maxRetryNum\": 32,\n" +
                "    \"qmq_appCode\": \"100006480\",\n" +
                "    \"qmq_createTIme\": \"1547189987059\",\n" +
                "    \"qmq_consumerOffset\": -1,\n" +
                "    \"qmq_traceId\": \"100006480-0a19b97b-429774-1772088\",\n" +
                "    \"qmq_reliabilityLevel\": \"High\",\n" +
                "    \"qmq_prefix\": \"basebiz.implus.distribution.common\",\n" +
                "    \"body\": \"{\\\"im\\\":{\\\"closeMode\\\":\\\"AGENTCLOSE\\\",\\\"closeTime\\\":1547189986781,\\\"customerSource\\\":\\\"ANDROID-99999999-8.0.2_1.3.0\\\",\\\"dataChangeLastTime\\\":1547189986783,\\\"firstMessageTime\\\":1547188912788,\\\"gid\\\":\\\"1113464568820006928\\\",\\\"inQueueTime\\\":1547189213044,\\\"inServiceTime\\\":1547189213348,\\\"lastMessageTime\\\":1547189986775,\\\"requestTime\\\":1547189212790,\\\"sessionId\\\":\\\"600000000402195\\\"},\\\"phone\\\":{\\\"agentPhoneNum\\\":\\\"\\\",\\\"answerTime\\\":0,\\\"callId\\\":\\\"\\\",\\\"callType\\\":\\\"\\\",\\\"customerPhoneNum\\\":\\\"\\\",\\\"dataChangeLastTime\\\":0,\\\"direction\\\":\\\"\\\",\\\"hangupTime\\\":0,\\\"isAnswered\\\":false,\\\"receivingTime\\\":0},\\\"workSheet\\\":{\\\"agentUid\\\":\\\"_IMKF2987465259\\\",\\\"categories\\\":\\\"WS003469\\\",\\\"categoriesDetail\\\":[{\\\"categoryDesc\\\":\\\"订单查询修改>咨询>查询客规、行李、登机流程\\\",\\\"code\\\":\\\"WS003469\\\",\\\"gradeCode\\\":\\\"WS003461_WS003462_WS003469\\\"}],\\\"classificationParams\\\":\\\"{\\\\\\\"desc\\\\\\\":\\\\\\\"2019年01月16日 1人\\\\\\\",\\\\\\\"pagefrom\\\\\\\":\\\\\\\"10320608519\\\\\\\",\\\\\\\"appversion\\\\\\\":\\\\\\\"8.0.2\\\\\\\",\\\\\\\"ctype\\\\\\\":\\\\\\\"ORD\\\\\\\",\\\\\\\"sensitivewords\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"reason\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"{\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"mid\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"1113465195818123280\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"oid\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"8305464368\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"r\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"default\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"rt\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":10,\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"tag\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"ctrip/flight/national/client\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"v\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"A\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"way\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"MATCH\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"}\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"words_type\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"sent_words\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"\\\\\\\\\\\\\\\"}\\\\\\\",\\\\\\\"cid\\\\\\\":\\\\\\\"8305464368\\\\\\\",\\\\\\\"bu\\\\\\\":\\\\\\\"FLT\\\\\\\",\\\\\\\"currency\\\\\\\":\\\\\\\"CNY\\\\\\\",\\\\\\\"amount\\\\\\\":\\\\\\\"910\\\\\\\",\\\\\\\"title\\\\\\\":\\\\\\\"【单程】 ZH9451  广州-昆明\\\\\\\",\\\\\\\"switchhumanreason\\\\\\\":\\\\\\\"{\\\\\\\\\\\\\\\"mid\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"1113465195818123280\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"oid\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"8305464368\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"r\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"default\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"rt\\\\\\\\\\\\\\\":10,\\\\\\\\\\\\\\\"tag\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"ctrip/flight/national/client\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"v\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"A\\\\\\\\\\\\\\\",\\\\\\\\\\\\\\\"way\\\\\\\\\\\\\\\":\\\\\\\\\\\\\\\"MATCH\\\\\\\\\\\\\\\"}\\\\\\\",\\\\\\\"orderid\\\\\\\":\\\\\\\"8305464368\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"FLT\\\\\\\",\\\\\\\"vtoken\\\\\\\":\\\\\\\"\\\\\\\"}\\\",\\\"customerUid\\\":\\\"_WeChat2392842733\\\",\\\"dataChangeLastTime\\\":1547189986781,\\\"direction\\\":0,\\\"empCode\\\":\\\"N28548\\\",\\\"id\\\":2110103,\\\"initiatorRole\\\":\\\"CUSTOMER\\\",\\\"initiatorUid\\\":\\\"_WeChat2392842733\\\",\\\"reason\\\":\\\"{\\\\\\\"mid\\\\\\\":\\\\\\\"1113465195818123280\\\\\\\",\\\\\\\"oid\\\\\\\":\\\\\\\"8305464368\\\\\\\",\\\\\\\"r\\\\\\\":\\\\\\\"default\\\\\\\",\\\\\\\"rt\\\\\\\":10,\\\\\\\"tag\\\\\\\":\\\\\\\"ctrip/flight/national/client\\\\\\\",\\\\\\\"v\\\\\\\":\\\\\\\"A\\\\\\\",\\\\\\\"way\\\\\\\":\\\\\\\"MATCH\\\\\\\"}\\\",\\\"serviceType\\\":\\\"1325\\\",\\\"skillGroupCode\\\":\\\"G260019\\\",\\\"status\\\":\\\"CLOSE\\\",\\\"summary\\\":\\\"\\\",\\\"type\\\":\\\"IM\\\"}}\",\n" +
                "    \"envelope\": \"{\\\"params\\\":{\\\"worksheetId\\\":2110103},\\\"resourceType\\\":\\\"WORKSHEET\\\"}\",\n" +
                "    \"qmq_consumerGroupName\": \"ctrip.flight.butler.agent\",\n" +
                "    \"qmq_spanId\": \"100006480-0a19b97b-429774-1772098\",\n" +
                "    \"qmq_isnewqmq\": \"true\",\n" +
                "    \"qmq_pullOffset\": 294049\n" +
                "  },\n" +
                "  \"bigMessage\": false,\n" +
                "  \"maxRetryNum\": 32,\n" +
                "  \"newqmq\": true\n" +
                "}";
    }

    @Test
    void get2() {
        long ts=System.currentTimeMillis();
        JsonBuilder jb = AntlrJsonBuilder.build(Jsons.json);
        Object s1 = jb.get("attrs.envelope");
        long d=System.currentTimeMillis()-ts;
        System.out.println(d);
        assertNotNull(s1);
        String s2 = jb.toSrc("ImplusMsg");
        assertNotNull(s2);
        String s = StringEscapeUtils.unescapeJava(jb.get("attrs.body").toString());
        JsonBuilder jb1 = AntlrJsonBuilder.build(s);
        String ss1 = jb1.toSrc("Body");
        assertNotNull(ss1);

        jb.digIn();
        String fs = jb.toSrc("Full");
        assertNotNull(fs);
    }
    @Test
    void get3(){
        long ts=System.currentTimeMillis();
        JsonBuilder ajb=AntlrJsonBuilder.build(Jsons.json);
        ajb.digIn();
        long d=System.currentTimeMillis()-ts;
        System.out.println(d);
        Object s=ajb.get("attrs.body.workSheet.categoriesDetail[0].categoryDesc");
        assertEquals("订单查询修改>咨询>查询客规、行李、登机流程",s);
    }

    @Test
    void parse() {
        Map<String, Object> r = AntlrJsonBuilder.build(Jsons.s).getModel();
        assertNotNull(r);
    }

    @Test
    void analyze(){
        Boolean r=Boolean.valueOf("false");
        assertTrue(!r);
    }
}