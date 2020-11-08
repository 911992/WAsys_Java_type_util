# WAsys_Java_type_util
Common type-level utils for Java  

## Revision History
Latest: v0.2.3 (Nov 8, 2020)  
Please refer to [release_note.md](./release_note.md) file  

## Requirements
* Java 1.5 or later

## Overview
This lib has module(s) about java type level funtionalities.  
Each modules would cover a specific requirements/functionalities.

## Installation
Artifacts could be grabed from central maven(or build by maven), and or ant.

**Maven** *(source/bin format: 1.5)*  
```xml
<dependency>
    <groupId>com.github.911992</groupId>
    <artifactId>WAsys_Java_type_util</artifactId>
    <version>0.2.3</version>
</dependency>
```
Or you would clone the repo, and build it as following:
```
mvn clean package
```
Artifact under `target` folder


**Ant** *(source/bin format: jdk 1.6)*  
Clone this repo, and build it by `ant` as following
```
ant clean jar
```
Artifact under `dist` folder

## Module::Type Signature
![Type Signature Module Media Img](./_docs/_diagrams/media_cover_type_signature.svg)  
*Diagram 0: Type Signature Module Media Img*  

### Abstract
This module is about parsing and generate a siganture of a type. The signature would have list of fields signature that includes the `Field` ptr, and its setter/getter methods.

### Use Case
Serializing POJO were the main reason behind this. A requirement about auto data binding(read/write) like JDBC(thinking an ORM lib), or JSON.

### Class Diagram
![Type Signature Module Class Diagram](./_docs/_diagrams/class_diagram_partial.svg)  
*Diagram 1: Type Signature Module [Class Diagram](./_docs/_diagrams/class_diagram.svg)*  

### Sample Usage
Considering following sample usages. Since using this lib is pretty simple and straight forward, so having a dedicated repo may not be very logical.

#### Simple Field Setting(no setter method)
```java
class Entity0{
int model;
}
/*...*/
public static void main(String args[]) {
    //or Type_Signature<Entity0> _ts0=...
    Type_Signature _ts0=Type_Parser.parse_no_filter(Entity0.class, Type_Signature_Parse_Policy.DEFAULT_POLICY);
    Entity0 _e0=new Entity0();
    Type_Field_Signature _fage= _ts0.get_field_by_name("model");
    _fage.set(_e0, 911);
    System.out.printf("Model: %d\n",_e0.model);//911
}
```

#### Simple Field Setting(using setter method)
```java
package java_type_util_test;
class Entity1{
public String name;
    public String getName() {
        return name;
    }
    public void setName(String arg_name) {
        System.out.printf("Setting name to:%s\n",arg_name);
        this.name = arg_name;
    }
}
/*...*/
public static void main(String args[]) {
    //or Type_Signature<?> _ts0=...
    Type_Signature _ts0=Type_Parser.parse_no_filter(Class.forName("java_type_util_test.Entity1"), new Type_Signature_Parse_Policy(Type_Signature_Parse_Policy.ACCESS_SPECIFIER_ALL, false/*include transient*/, false/*include static*/));
    Type_Field_Signature _fname = _ts0.get_field_by_name("name");
    /*Going to create a new object by default constructor, since there is no Object_Factory has associated*/
    /*Using a Object_Factory is HIGHLY recomended over default constructor!*/
    Object _obj0=_ts0.create_object();
     _fname.set(_obj0, "Porsche");//will call the setName()
}
```

#### List Of Scrabed Fields
```java
class Entity2_P{
static String TYPE_VER;
String parent_name;
int parent_id;
}
class Entity2_C extends Entity2_P{
String child_name;
int child_id;
}
/*...*/
public static void main(String argsp[]) {
    //or Type_Signature<?> _ts =...
    Type_Signature<Entity2_C> _ts = Type_Parser.parse_no_filter(Entity2_C.class, new Type_Signature_Parse_Policy(Type_Signature_Parse_Policy.ACCESS_SPECIFIER_ALL, false/*excluding transient fields*/, true/*including static fields*/, Field_Definition_Order.From_Parent_To_Child/*scrap parent fields too*/));
    for(Type_Field_Signature _tfs : _ts.getProceed_fields()){
        System.out.printf("Field name:%s\n",_tfs.getField().getName());
    }
    /*out:
    Field name:TYPE_VER
    Field name:parent_name
    Field name:parent_id
    Field name:child_name
    Field name:child_id
    */
}
```

#### Using `Generic_Object_Factory` 
```java
class Plain_POJO3{
    public void hi(){
        System.out.println("Hello! Nothing especial here...");
    }
    public Plain_POJO3() {
        System.out.println("Instancing...");
    }
}
 /*...*/
 public static void main(String[] args) {
    try {
        //or Generic_Object_Factory<Plain_POJO3> _entity_factory = Generic_Object_Factory.new_instance(Plain_POJO3.class);
        Generic_Object_Factory<Plain_POJO3> _entity_factory = new Generic_Object_Factory<>(Plain_POJO3.class);
        Plain_POJO3 _smp = _entity_factory.create_object();//"Instancing..."
        _smp.hi();//"Hello! Nothing especial here..."
    } catch (Exception wth) {
        wth.printStackTrace();
    }   
}
```

#### Custom Getter And Setter Methods And Skipping Fields For Parsing
Since version `0.2.1`, specifying a custom getter/setter method for a field is possible by annotating the target field using `Field_Info` annotation.

Annotation <del>`Skip_This_Field`</del> was removed on version `0.2.1`, and now `Field_Info.skip_this_field` could be used instead. Any field marked as `skip_this_field` will be skipped in type processing stage.

```java
class Student{
    
    @Field_Info(skip_this_field = true)
    private int not_a_field;
    
    @Field_Info(getter_method = "get_the_name",setter_method = "set_name")
    private String name;
    
    @Field_Info(setter_method = "set_age_please")
    private int age;

    public String get_the_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void set_age_please(int age) {
        this.age = age;
    }
}
/*....*/
public static void main(String[] args) {        
    Type_Signature ts = Type_Parser.parse_no_filter(Student.class, Type_Signature_Parse_Policy.DEFAULT_POLICY);
    printout_fields(ts);
}
public static void printout_fields(Type_Signature<?> arg_typ_sig){
    for(Type_Field_Signature _tfs_ins : arg_typ_sig.getProceed_fields()){
        System.out.printf("field: %s , getter:%s, setter:%s\n",_tfs_ins.getField().getName(),_tfs_ins.getGetter_func(),_tfs_ins.getSetter_func());
    }
}
/*
Result:
field: name , getter:public java.lang.String type_util_lib_test.Student.get_the_name(), setter:public void type_util_lib_test.Student.set_name(java.lang.String)
field: age , getter:public int type_util_lib_test.Student.getAge(), setter:public void type_util_lib_test.Student.set_age_please(int)
*/
```

#### Dedicated `isAaa` For Boolean Types
Since version `0.2.1`, `boolean`(also `Boolean`) types could cause the default parser to search for `isAaa` getetr method scheme instead of `getAaa` when it's absent.

```java
class My_Entity{
    private boolean valid_state;

    public boolean isValid_state() {
        return valid_state;
    }

    public void setValid_state(boolean valid_state) {
        this.valid_state = valid_state;
    }
}
/*...*/
public static void main(String[] args) {
    Type_Signature<My_Entity> _tsig = Type_Parser.parse_no_filter(My_Entity.class, Type_Signature_Parse_Policy.DEFAULT_POLICY);
    for(Type_Field_Signature _tfs_ins : _tsig.getProceed_fields()){
        System.out.printf("field: %s , getter:%s, setter:%s\n",_tfs_ins.getField().getName(),_tfs_ins.getGetter_func(),_tfs_ins.getSetter_func());
    } 
}
/*
Result:
field: valid_state , getter:public boolean type_util_lib_test.My_Entity.isValid_state(), setter:public void type_util_lib_test.My_Entity.setValid_state(boolean)
*/
```
