# Class Diagram Version History
repo: https://github.com/911992/WAsys_Java_type_util    
file: [class_diagram](./class_diagram.svg)  
Author: [911992](https://github.com/911992)  

**v0.2.3** (Nov 8, 2020)  

0. `field_info_user_meta` class
    * Added `field_info_user_meta:String`, and `field_info_user_meta_or_field_name:String` `final` fields
    * Added `get_user_meta_from_field_info_annot(void):String` method
    * Added `get_user_meta_from_field_info_annot_or_name(void):String` method
    * Added `get_field_name(void):String` methods
    * Added dependency to `Field_Info` type

<hr/>

**v0.2.1** (Sept 26, 2020)  

0. Added new `Field_Filter_Entity` entity type
1. `Type_Parser` class
    * Methods `parse_no_filter`, `parse`, and `get_fields` now accept `Generic_Filter<Field_Filter_Entity>` rather `Generic_Filter<Field>`
    * Added `find_getter_method(:Class,:Field,arg_check_as_isAaa:bool):Method` method
2. `Type_Signature_Parse_Policy` class
    * Added `include_skipped_fields:bool` field
3. Added new `annotation` package
4. Added new `Field_Info` annotation on `annotation` package
5. Removed `Skip_This_Field` annotation

<hr/>

**v0.1.7** (Aug 23, 2020)  

(that bad-big O boy!)  (╯°□°)╯︵ ┻━┻  
0. renamed `create_Object()` to `create_object()` in `Object_Factory` interface
1. renamed `create_Object()`s to `create_object()` in `Generic_Object_Factory` class

<hr/>

**v0.1.5** (Aug 22, 2020)  

0. `Exceptional_Object`
    * Moved to package `wasys::lib::java_type_util::lib_common` from `wasys::lib::java_type_util::reflect::type_sig`
    * Changed type to `interface` (previously as `abstract class`)
    * Removed `last_call_ex` field
1. Added `Exceptional_Object_Adapter` adapter class for `Exceptional_Object` in package `wasys::lib::java_type_util::lib_common`
2. Added `Generic_Object_Factory<A>` class to `wasys::lib::java_type_util::reflect::type_sig::impl` package

<hr/>

**v0.1.1** (Jul 16, 2020)  

* `Type_Signature` now is a template/generic type
    * `obj_factory`, and `type` fields now follow the type template-type
    * `create_object` method now returns a type of generic type, instead of `Object`
* `Object_Factory` now is a template/generic type
    * method `create_object` now returns the type template type
* `parse_no_filter`, and `parse` methods of `Type_Parser` now returns templated `Type_Signature` based on given `arg_type` type
* Added missed template/generic type for static `NULL_OBJECT` var in `Generic_Filter`


<hr/>

**v0.1** (Jul 1, 2020)

* Initial release