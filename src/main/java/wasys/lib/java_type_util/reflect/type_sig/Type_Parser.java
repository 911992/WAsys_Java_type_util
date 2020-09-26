/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Type_Parser.java
Created on: Jul 3, 2020 3:37:19 PM
    @author https://github.com/911992
 
History:
    0.2.1(20200926)
        • Using Field_Filter_Entity as type-arg instead of Field as field filter(Generic_Filter)
        • find_getter_method method now tries for isAaa when field type is boolean, and getAaa is missing
        • find_method now searches over inherited methods too, rather only declared ones
        • find_setter_method and find_getter_method now check if given field is annotated to search for a custom getter/setter methods
        • Added find_getter_method(:Class,:Field,arg_check_as_isAaa:bool):Method method
        • Minor doc updates/fixes

    0.1.1(20200716)
        • parse_no_filter, and parse methods now return templated Type_Signature based on given arg_type type

    initial version: 0.1(20200701)
*/

package wasys.lib.java_type_util.reflect.type_sig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import wasys.lib.java_type_util.reflect.type_sig.annotation.Field_Info;


/**
 * This type is a generic/default type/class parser.
 * <p>
 * The main responsibility is preparing signature of a type and its field members.
 * </p>
 * <p>
 * <b>Note:</b> Beside any kind of type could be parsed, but mind parsing POJO types are most appreciated way.
 * </p>
 * <p>
 * Signatures of the type relay on fields, as methods are ignored. Besides each field would have its setter and getter method that will be identified and kept by signature.
 * </p>
 * @author https://github.com/911992
 */
public class Type_Parser {
    
    /**
     * Mask for access specifiers, for omitting other parts.
     */
    private static final int ACC_SPEC_MASK = (Modifier.PUBLIC | Modifier.PRIVATE | Modifier.PROTECTED);
    
    /**
     * (internal lib use)Specifies a virtual bit mask for {@code package} level access specifier.
     */
    static final int PACKAGE_ACCESS_SPECIFIER = ACC_SPEC_MASK + 1;

    /**
     * Default private constructor, to mark instance of this type redundant.
     * <p>
     * All available functionalities should be accessed by {@code static} methods.
     * </p>
     */
    private Type_Parser() {
    }
    
    /**
     * Parses the given type without any field filter.
     * <p>
     * It simply calls the {@code parse(arg_type,arg_pol,null)} function, please read the {@code parse} method doc.
     * </p>
     * @param <A> the type of signature, follows the {@code arg_type}
     * @param arg_type the type should be parsed (must <i>not</i> be {@code null})
     * @param arg_pol the policy should be considered (must <i>not</i> be {@code null})
     * @return A non-{@code null} type signature
     */
    public static <A> Type_Signature<A> parse_no_filter(Class<A> arg_type,Type_Signature_Parse_Policy arg_pol){
        return parse(arg_type, arg_pol, null);
    }
    
    /**
     * Parses the given type, based on given policy and field filter.
     * <p>
     * Parsing process is done by grabbing the list of declared fields, and once they are filtered(based on policy and filter), setter and getter methods will be tried to be found.
     * </p>
     * <p>
     * A field may or may not have getter and/or setter method.
     * </p>
     * <p>
     * Finding getter/setter method is done based on method name that follows the java default camelCase format as setAaa/getAaa.<br>
     * <b>Note:</b> Please mind, the return type of setter and getter methods are checked too, as a setter method should be {@code void}, and a getter method should return a type of related field's type.
     * </p>
     * <p>
     * <b>Note:</b> It first checks and validate a field based on given policy({@code arg_pol}), and then asks the given filter({@code arg_filter}) to check if the field should considered or not.
     * </p>
     * <p>
     * <b>Note:</b> Once a field is filtered based on given policy, it won't be sent/check by the given policy.
     * </p>
     * <p>
     * <b>Note:</b> Getter/setter methods' access specifiers are <b>not</b> checked by given policy.
     * </p>
     * <p>
     * If scraping parent type(s) is appreciated, so scraping will be go to {@link Object} type level inheritance.
     * </p>
     * <p>
     * Since version 0.2.1, the given {@code arg_filter} should be type-arg of {@link Field_Filter_Entity}.
     * </p>
     * @param <A> the type of signature, follows the {@code arg_type}
     * @param arg_type the type should be parsed (must <i>not</i> be {@code null})
     * @param arg_pol the policy should be considered (must <i>not</i> be {@code null})
     * @param arg_filter the field filter
     * @return A non-{@code null} type signature
     */
    public static <A> Type_Signature<A> parse(Class<A> arg_type,Type_Signature_Parse_Policy arg_pol,Generic_Filter<Field_Filter_Entity> arg_filter){
        if(arg_filter == null){
            arg_filter = Generic_Filter.NULL_OBJECT;
        }
        ArrayList<Field> _fields = get_fields(arg_type, arg_pol, arg_filter);
        Field _field;
        ArrayList<Type_Field_Signature> _field_sigs = new ArrayList<Type_Field_Signature>(_fields.size());
        for(int a=0;a<_fields.size();a++){
            _field = _fields.get(a);
            Method _getter_method = find_getter_method(arg_type, _field);
            Method _setter_method = find_setter_method(arg_type, _field);
            Type_Field_Signature _f_sig=new Type_Field_Signature(_field, _getter_method, _setter_method);
            _field_sigs.add(_f_sig);
        }
        Type_Signature _res = new Type_Signature(arg_type, _field_sigs, arg_pol);
        return _res;
    }
    
    /**
     * (internal lib use) Finds fields of a type, based on given policy and filter.
     * @param arg_type the type should scraped
     * @param arg_pol the policy field should be check against
     * @param arg_filter the filter fields should be considered
     * @return a list of fields that follows the desired given policy and filter
     */
    private static ArrayList<Field> get_fields(Class arg_type,Type_Signature_Parse_Policy arg_pol,Generic_Filter<Field_Filter_Entity> arg_filter){
        Class _tp=arg_type;
        ArrayList<Field> _res = new ArrayList<Field>(13);
        Field _field;
        int _mod;
        int _access_mode;
        int _filter_access_mode;
        int _add_idx;
        Field_Filter_Entity _working_ffe = new Field_Filter_Entity(); 
        Field_Info _fi_ins;
        while(_tp != Object.class){
            Field[] _fields = _tp.getDeclaredFields();
            _add_idx = (arg_pol.getField_order()==Field_Definition_Order.From_Parent_To_Child)?0:_res.size();
            for(int a=0;a<_fields.length;a++){
                _field = _fields[a];
                _field.setAccessible(true);
                _working_ffe.setField(_field);
                _working_ffe.setField_info(_fi_ins=_field.getAnnotation(Field_Info.class));
                if(_fi_ins!=null && _fi_ins.skip_this_field() && arg_pol.isInclude_skipped_fields()==false){
                    continue;
                }
                _mod=_field.getModifiers();
                if(arg_pol.isInclude_static()==false && Modifier.isStatic(_mod)){
                    continue;
                }
                if(arg_pol.isInclude_transient()==false && Modifier.isTransient(_mod)){
                    continue;
                }
                _access_mode = _mod & ACC_SPEC_MASK;
                if(_access_mode == 0){/*when access spec is zero, so it means package, blame sun/oracle*/
                    _access_mode = PACKAGE_ACCESS_SPECIFIER;
                }
                _filter_access_mode = arg_pol.getAccess_specifier();
                if((_filter_access_mode & _access_mode) == 0){
                    continue;
                }
                if(arg_filter.consider(_working_ffe)==false){
                    continue;
                }
                _res.add(_add_idx, _field);
                _add_idx++;
            }
            if(arg_pol.isInclude_parent_fields()==false){
                break;
            }
            _tp = _tp.getSuperclass();
        }
        
        return _res;
    }
    
    /**
     * (internal lib use) Tries to find a setter method for given field of given type.
     * <p>
     * The setter method must be void, and accepts only one argument with type of given type.
     * </p>
     * <p>
     * Since version 0.2.1, the given {@code arg_field} is checked if is annotated using {@link Field_Info} or not, to grab the possible custom setter method name.
     * </p>
     * @param arg_type the class method/field should be scraped
     * @param arg_field the field type should be considered for finding 
     * @return a method that follows as a setter method for given field, or {@code null} if not found
     */
    private static Method find_setter_method(Class arg_type,Field arg_field){
        Field_Info _fi = arg_field.getAnnotation(Field_Info.class);
        String _method_name;
        if(_fi==null || _fi.setter_method().length()==0){
            _method_name = aaaMethod_name("set", arg_field.getName());
        }else{
            _method_name = _fi.setter_method();
        }
        return find_method(arg_type,void.class, _method_name, arg_field.getType());
    }
    
    /**
     * (internal lib use) Calls find_getter_method(:Class,:Field,:bool) to perform real method scraping.
     * @param arg_type the class method/field should be scraped
     * @param arg_field the field type should be considered for finding 
     * @return an instance that works as getter method for given field from type, or {@code null} if not found
     */
    private static Method find_getter_method(Class arg_type,Field arg_field){
        return find_getter_method(arg_type, arg_field, true);
    }
    
    /**
     * (internal lib use) Tries to find a getter method for given field of given type.
     * <p>
     * The getter method should return a type of field's type, and accepts nothing(void).
     * </p>
     * <p>
     * Since version 0.2.1, another call for finding {@code isAaa} as getter method is called for boolean types, where {@code getAaa} fails.<br>
     * Also, the given {@code arg_field} is checked if is annotated using {@link Field_Info} or not, to grab the possible custom getter method name.
     * </p>
     * @param arg_type the class method/field should be scraped
     * @param arg_field the field type should be considered for finding 
     * @param arg_can_check_as_isAaa tells if anotehr check/(recursive-call) should be applied where there is no such getAaa for a boolean type field
     * @return a method that follows as a getter method for given field, or {@code null} if not found
     * @since 0.2.1
     */
    private static Method find_getter_method(Class arg_type,Field arg_field,boolean arg_can_check_as_isAaa){
        Field_Info _fi = arg_field.getAnnotation(Field_Info.class);
        String _method_name;
        if(_fi==null || _fi.getter_method().length()==0){
            _method_name = aaaMethod_name((arg_can_check_as_isAaa?"get":"is"), arg_field.getName());
        }else{
            _method_name = _fi.getter_method();
            arg_can_check_as_isAaa = false;
        }
        Method _res = find_method(arg_type,arg_field.getType(), _method_name, (Class[])null);
        if(arg_can_check_as_isAaa && _res == null && (arg_field.getType() == boolean.class || arg_field.getType() == Boolean.class)){
            _res = find_getter_method(arg_type, arg_field, false);
        }
        return _res;
    }
    
    /**
     * (internal lib use) Generates a method name that follows Java default setAaa/getAaa camelCase format.
     * @param arg_prefix the prefix for the method(probably either 'get', or 'set')
     * @param arg_aaa_name name of the field, as suffix for target method name
     * @return a name that follows aaaName format
     */
    private static String aaaMethod_name(String arg_prefix,String arg_aaa_name){
        char _first_char = arg_aaa_name.charAt(0);
        String _res = String.format("%s%c%s", arg_prefix,Character.toUpperCase(_first_char),arg_aaa_name.substring(1));
        return _res;
    }
    
    /**
     * (internal lib use) Finds a method based on given return type, name and input args from given type.
     * <p>
     * Since version 0.2.1, inherited methods are also searched for requested method.
     * </p>
     * @param arg_type the type method should be checked(looked up)
     * @param arg_return_type the return type of the method
     * @param arg_method_name name of the method
     * @param arg_args input argument types
     * @return a method ptr that follows all requirements, or {@code null} if not found
     */
    private static Method find_method(Class arg_type,Class arg_return_type,String arg_method_name,Class ... arg_args){
        Class _typ = arg_type;
        while(_typ!=null){
            try {
                Method _res = _typ.getDeclaredMethod(arg_method_name, arg_args);
                if(_res.getReturnType()!=arg_return_type){
                    return null;
                }
                _res.setAccessible(true);
                return _res;
            }catch(NoSuchMethodException e){
            }catch (Throwable wtf) {
                wtf.printStackTrace();
                return null;
            }
            _typ = _typ.getSuperclass();
        }
        return null;
    }
    
}
