# WAsys_Java_type_util Release Note

repo: https://github.com/911992/WAsys_Java_type_util  
Author: [911992](https://github.com/911992)  
*(NOTE: following list carries mentionable(not all) changes. For detailed changes, check source code(s))*  

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