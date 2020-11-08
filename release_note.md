# WAsys_Java_type_util Release Note

repo: https://github.com/911992/WAsys_Java_type_util  
Author: [911992](https://github.com/911992)  
*(NOTE: following list carries mentionable(not all) changes. For detailed changes, check source code(s))*  

**0.2.3** (Nov 8, 2020)

0. User-defined `meta_name` value from a annotated field(using `Field_Info`) now is stored(cached) in `Type_Field_Signature`
1. `Source_Code::Type_Field_Signature`
    * Added `field_info_user_meta:String`, and `field_info_user_meta_or_field_name fields:String`
    * constructor now caches(filling `field_info_user_meta_or_field_name`) the either user-defined name from `Field_Info`, or the field
    * Added `get_user_meta_from_field_info_annot(void):String` method
    * Added `get_user_meta_from_field_info_annot_or_name(void):String` method
    * Added `get_field_name(void):String` method
2. Diagrams
    * Updated [class diagram](./_docs/_diagrams/class_diagram.svg) (changes could be found [here](./_docs/_diagrams/class_diagram_version_history.md))
3. Repo
    * update `pom.xml` file
        * Updated artifact to `0.2.3`
    * Updated `README.md` file


<hr/>

**0.2.1** (Sept 26, 2020)

0. Adding support for custom getter and setter methods related to a field
1. getter/setter method now could be grabbed(searched) from super types too
2. Dedicated `isAaa` getter method naming for `boolean` and `Boolean` types where `getAaa` fails/misses
3. `Source_Code::Field_Filter_Entity`
    * Added to be used as typ-arg as field filter instead of plain `Field` type.
4. `Source_Code::Type_Parser`
    * Using `Field_Filter_Entity` as type-arg instead of `Field` as field filter(`Generic_Filter`)
    * `find_getter_method` method now tries for `isAaa` when field type is `boolean`, and `getAaa` is missing
    * `find_method` now searches over inherited methods too, rather only declared ones
    * `find_setter_method` and `find_getter_method` now check if given field is annotated to search for a custom getter/setter methods
    * Added `find_getter_method(:Class,:Field,arg_check_as_isAaa:bool):Method` method
    * Minor doc updates/fixes
5. `Source_Code::Type_Signature_Parse_Policy`
    * Added field `include_skipped_fields:bool` and its getter method
    * Updated the default constructor to include the `include_skipped_fields` field 
6. `Source_Code::Field_Info`
    * Added, primary usage is about specifying customized info of a field for parsing, such as getter/setter methods, and marked as ignore.
7. Added new package `wasys.lib.java_type_util.reflect.type_sig.annotation` and its doc file `package-info`
8. Removed <del>`Skip_This_Field`</del> annotation
9. Diagrams
    * Updated [class diagram](./_docs/_diagrams/class_diagram.svg) (changes could be found [here](./_docs/_diagrams/class_diagram_version_history.md))
10. Repo
    * update `pom.xml` file
        * Updated artifact to `0.2.1`
    * Updated `README.md` file
        * Added more examples
            * *Custom Getter And Setter Methods And Skipping Fields For Parsing*
            * *Dedicated `isAaa` For Boolean Types*


<hr/>

**0.1.9** (Sept 9, 2020)

**Module::Type Signature**

(that stupid `==` instead of `!=`)  `(╯°□°)╯︵ ┻━┻`  

0. `Source_Code::Type_Field_Signature`
    * Fixed the very stupid bug, where `==` must be `=!` about field getter method `null`ify check (oh god, this is maximum stupidity lvl) `(╯°□°)╯︵ ┻━┻`
1. Repo
    * update `pom.xml` file
        * Updated artifact to `0.1.9`
        * Added copyright literal for generated javadoc(plugin)

<hr/>

**0.1.7** (Aug 23, 2020)

**Module::Type Signature**

(that bad-big O boy!)  (╯°□°)╯︵ ┻━┻  

0. `Source_Code::Object_Factory`
    * renamed `create_Object()` to `create_object()`
    * Updated documentation of `create_object()` method
1. `Source_Code::Generic_Object_Factory`
    * renamed `create_Object()` to `create_object()`
    * Updated documentation of `create_object(:Class)` method
2. `Source_Code::Type_Signature`
    * Calling renamed `create_object()` of Object_Factory
3. Diagrams
    * Updated [class diagram](./_docs/_diagrams/class_diagram.svg) (changes could be found [here](./_docs/_diagrams/class_diagram_version_history.md))
4. Repo
    * update `pom.xml` file
        * Updated artifact to `0.1.7`
    * Updated `README.md` file
        * Fixed the `create_Object` to `create_object` in *Using `Generic_Object_Factory`*  example usage

<hr/>


**0.1.5** (Aug 22, 2020)

**Module::Type Signature**

0. Added new package `wasys.lib.java_type_util.lib_common` to place internal-lib common types
1. `Source_Code::Exceptional_Object`
    * Moved to package `wasys.lib.java_type_util.lib_common`
    * Changed the type as an `interface`(previously as `abstract class`)
    * Removed `last_call_ex` field
    * Removed `get_last_call_ex`, and `was_last_call_success` body
    * Updated documentation
2. Added `Exceptional_Object_Adapter` class as adapter class for `Exceptional_Object` interface
3. `Source_Code::Type_Field_Signature`
    * Extending from `Exceptional_Object_Adapter`, since `Exceptional_Object` is now an interface
4. `Source_Code::Type_Signature`
    * Extending from `Exceptional_Object_Adapter`, since `Exceptional_Object` is now an interface
5. Added `Generic_Object_Factory` class as a generic implementation of `Object_Factory`
6. Diagrams
    * Updated [class diagram](./_docs/_diagrams/class_diagram.svg) (changes could be found [here](./_docs/_diagrams/class_diagram_version_history.md))
7. Repo
    * Ant repo(`build.xml`) source/bin changed from `1.7` to `1.6`
    * update `pom.xml` file
        * Updated artifact to `0.1.5`
    * Updated `README.md` file
        * Added example related to `Generic_Object_Factory` (section *Using `Generic_Object_Factory`*)

<hr/>

**0.1.1** (Jul 16, 2020)

**Module::Type Signature**

0. `Source_Code::Object_Factory`
    * Type now is generic/templated
    * `create_Object` method now returns the template`<A>` type
1. `Source_Code::Type_Parser`
    * `parse_no_filter`, and `parse` methods now return templated `Type_Signatur`e based on given `arg_type` type
2. `Source_Code::Type_Signature`
    * Type now is templated/generic based on `type` this signature related to
    * `type`, and `obj_factory` fields(also setter, and getter funcs) now follow the class template-type
    * `create_object` returns type of generic type instead of `Object`
3. Diagrams
    * Updated [class diagram](./_docs/_diagrams/class_diagram.svg) (changes could be found [here](./_docs/_diagrams/class_diagram_version_history.md))
4. Repo
    * update `pom.xml` file
        * Updated artifact to `0.1.1`
    * Updated `README.md` file
        * Updated example usages based on new templated-types/changes
        * Added note about `ant`, and `maven` output source/binary format
        * 
<hr/>

**Initial Release 0.1** (Jul 1, 2020)