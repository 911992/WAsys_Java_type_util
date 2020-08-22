# Class Diagram Version History
repo: https://github.com/911992/WAsys_Java_type_util    
file: [class_diagram](./class_diagram.svg)  
Author: [911992](https://github.com/911992)  

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