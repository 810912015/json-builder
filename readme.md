#命令举例
----------
D:\proj\tools\target>java -jar json-1.0.0.jar
----------
  请输入json串(^ 回车结束):
>{
    >"firstName": "John",
    >"lastName": "Smith",
    >"gender": "man",
    >"age": 32,
    >"address": {
        >"streetAddress": "21 2nd Street",
        >"city": "New York",
        >"state": "NY",
        >"postalCode": "10021"
    >},
    >"phoneNumbers": [
        >{ "type": "home", "number": "212 555-1234" },
        >{ "type": "fax", "number": "646 555-4567" }
    >]
>}
  ^
-----------
请输入要执行的命令:i->查询,g 类名->生成代码并显示,f 文件名->生成代码并写入文件,^->退出
----------
  i
  -----------
请输入查询的键(q-退出查询):
----------
  age
  ----------
  age==32
  -----------
请输入查询的键(q-退出查询):
----------
  q
  -----------
请输入要执行的命令:i->查询,g 类名->生成代码并显示,f 文件名->生成代码并写入文件,^->退出
----------
  g msg
  -----------
输入json的java类:
-----------
>public class msg{
public static class Address{
private String streetAddress ;
public String getStreetAddress(){
return this.streetAddress;
}
public void setStreetAddress(String streetAddress){
this.streetAddress=streetAddress;
}
private String city ;
public String getCity(){
return this.city;
}
public void setCity(String city){
this.city=city;
}
private Integer postalCode ;
public Integer getPostalCode(){
return this.postalCode;
}
public void setPostalCode(Integer postalCode){
this.postalCode=postalCode;
}
private String state ;
public String getState(){
return this.state;
}
public void setState(String state){
this.state=state;
}
}
public static class PhoneNumbers{
private String number ;
public String getNumber(){
return this.number;
}
public void setNumber(String number){
this.number=number;
}
private String type ;
public String getType(){
return this.type;
}
public void setType(String type){
this.type=type;
}
}
private String firstName ;
public String getFirstName(){
return this.firstName;
}
public void setFirstName(String firstName){
this.firstName=firstName;
}
private String lastName ;
public String getLastName(){
return this.lastName;
}
public void setLastName(String lastName){
this.lastName=lastName;
}
private Address address;
public Address getAddress(){
return this.address;
}
public void setAddress(Address address){
this.address=address;
}
private String gender ;
public String getGender(){
return this.gender;
}
public void setGender(String gender){
this.gender=gender;
}
private Integer age ;
public Integer getAge(){
return this.age;
}
public void setAge(Integer age){
this.age=age;
}
private List<PhoneNumbers> phoneNumbers;
public List<PhoneNumbers> getPhoneNumbers(){
return this.phoneNumbers;
}
public void setPhoneNumbers(List<PhoneNumbers> phoneNumbers){
this.phoneNumbers=phoneNumbers;
}
>}
-----------
请输入要执行的命令:i->查询,g 类名->生成代码并显示,f 文件名->生成代码并写入文件,^->退出
  ----------
  f msg2
  -----------
写入成功:msg2.java
  ----------
请输入要执行的命令:i->查询,g 类名->生成代码并显示,f 文件名->生成代码并写入文件,^->退出
  ----------
^
  -----------
Disconnected from the target VM, address: '127.0.0.1:62476', transport: 'socket'
----------
Process finished with exit code 0
