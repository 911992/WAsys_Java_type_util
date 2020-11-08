/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

 /*
WAsys_Java_type_util
File: Type_Field_Signature.java
Created on: Jul 2, 2020 9:24:15 PM
    @author https://github.com/911992
 
History:
    0.2.3(20201108)
        • Added field_info_user_meta:String, and field_info_user_meta_or_field_name fields:String
        • constructor now caches(filling field_info_user_meta_or_field_name) the either user-defined name from Field_Info, or the field name
        • Added get_user_meta_from_field_info_annot(void):String method
        • Added get_user_meta_from_field_info_annot_or_name(void):String method
        • Added get_field_name(void):String method

    0.1.9(20200909)
        • Fixed the very stupid bug, where == should be supposed =! about field getter method (oh god) (╯°□°)╯︵ ┻━┻
        • Minor doc update

    0.1.5(20200822)
        • Extending from Exceptional_Object_Adapter, since Exceptional_Object is now an interface

    initial version: 0.1(20200701)
 */
package wasys.lib.java_type_util.reflect.type_sig;

import wasys.lib.java_type_util.lib_common.Exceptional_Object_Adapter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import wasys.lib.java_type_util.reflect.type_sig.annotation.Field_Info;

/**
 * Represents a type's field's signature.
 * <p>
 * This type holds related non-{@code null} field {@link Field} pointer, and related/linked {@code null}able getter and method {@link Method}s.
 * </p>
 * <p>
 * It also keeps a redundant {@link String} field as {@code meta_name} that <i>may</i> be useful for user to keep some meta info about the field.
 * </p>
 * <p>
 * This type is immutable(except the {@code meta-name}, and {@code last_call_ex} fields).
 * </p>
 * <p>
 * By default, using getter and setter methods for set/get ops are considered, but if they are {@code null}, then setting and getting will be done explicitly to the field.
 * </p>
 * <p>
 * <b>Note:</b> All set and get ops for the related fields are exception free. Any exception during get/set calls will be saved on {@code last_call_ex} which is accessible by method {@code get_last_call_ex}.
 * </p>
 * <p>
 * <b>Note:</b> Any set/get call will clear the previously exception which is hold by {@code last_call_ex} method.
 * </p>
 * <p>
 * <b>Note:</b> set and get ops <b>are NOT</b> thread-safe.
 * </p>
 * @author https://github.com/911992
 */
public class Type_Field_Signature extends Exceptional_Object_Adapter{

    /**
     * Pointer to related field.
     * <p>This is non-{@code null}.</p>
     */
    private final Field field;
    
    /**
     * Pointer to related getter method.
     * <p>This could be {@code null}</p>
     */
    private final Method getter_func;
    
    /**
     * Pointer to related setter method.
     * <p>This could be {@code null}.</p>
     */
    private final Method setter_func;
    
    /**
     * User space for keeping a meta name/data related to this field.
     */
    private String meta_name;
    
    /**
     * User meta string is defined using {@link Field_Info} for the related(this) field.
     * @since 0.2.3
     */
    private final String field_info_user_meta;
    
    /**
     * Holds the safe non-{@code null} field name.
     * <p>
     * If the associated {@link Field_Info#user_meta() } is defined(no default), then
     * it points to that value, otherwise the related {@link Field#getName()}
     * </p>
     * @since 0.2.3
     */
    private final String field_info_user_meta_or_field_name;

    /**
     * Default constructor.
     * @param field The non-{@code null} pointer to the field.
     * @param getter_func the {@code null}-able method pointer for getter method.
     * @param setter_func the {@code null}-able method pointer for setter method.
     */
    public Type_Field_Signature(Field field, Method getter_func, Method setter_func) {
        this.field = field;
        this.getter_func = getter_func;
        this.setter_func = setter_func;
        Field_Info _fi=(Field_Info)field.getAnnotation(Field_Info.class);
        if(_fi != null){
            if((this.field_info_user_meta = _fi.user_meta()).length()==0){
                field_info_user_meta_or_field_name = field.getName();
            }else{
                field_info_user_meta_or_field_name = field_info_user_meta;
            }
        }else{
            this.field_info_user_meta = null;
            field_info_user_meta_or_field_name = field.getName();
        }
    }

    /**
     * Returns the associated non-{@code null} field pointer.
     * @return the {@code field} var
     */
    public Field getField() {
        return field;
    }

    /**
     * Returns the associated {@code null}able getter method.
     * @return the {@code getter_func} var
     */
    public Method getGetter_func() {
        return getter_func;
    }

    /**
     * Returns the associated {@code null}able setter method.
     * @return the {@code setter_func} var
     */
    public Method getSetter_func() {
        return setter_func;
    }

    /**
     * Returns the user meta name/data.
     * @return the {@code meta_name} var
     */
    public String getMeta_name() {
        return meta_name;
    }

    /**
     * Sets the user meta name/data.
     * @param meta_name the value to be set for {@code meta_name} var
     */
    public void setMeta_name(String meta_name) {
        this.meta_name = meta_name;
    }

    /**
     * Reads the field's value of given {@code arg_ins} instance.
     * <p>
     * It first try to read the value by getter method <i>if present</i>, otherwise it will tries read the value explicitly from field.
     * </p>
     * <p>
     * If the field is a {@code static} one, then given {@code arg_ins} could be {@code null}.
     * </p>
     * <p>
     * <b>Note:</b> it <b>does not</b> check arguments and field state before attempt for getting the value.
     * </p>
     * <p>
     * <b>Note:</b> If {@code null} is returned, then check for possible exceptions using {@code get_last_call_ex} method.
     * </p>
     * @param arg_ins the working instance field should consider for reading
     * @return value of the field
     */
    public Object get(Object arg_ins) {
        Object _res;
        try {
            if (this.getGetter_func() != null) {
                _res = getGetter_func().invoke(arg_ins);
            }else{
                _res = getField().get(arg_ins);
            }
            last_call_ex = null;
            return _res;
        } catch (Throwable wtf) {
            last_call_ex = wtf;
            _res = null;
        }
        return _res;
    }
    
    /**
     * Attempts to read the field as a static one, by passing {@code null} as instance.
     * <p>
     * It simple calls the {@code get(null)} method.
     * </p>
     * @return value of the field
     */
    public Object get_static(){
        return get(null);
    }
    
    /**
     * Sets the field's value for given {@code arg_ins} instance to {@code arg_value}.
     * <p>
     * It first tries to sets the value using setter method <i>if present</i>, otherwise setting value explicitly using field will applied.
     * </p>
     * <p>
     * If the field is a {@code static} one, then given {@code arg_ins} could be {@code null}.
     * </p>
     * <p>
     * If this returns {@code false}, it means setting was not successful and an exception/throwable was thrown. To grab the thrown exception, call for {@code get_last_call_ex} method.
     * </p>
     * @param arg_ins the working instance field should consider for setting
     * @param arg_value the value should be set to the field
     * @return {@code true} if the setting was success, {@code false} otherwise(check {@code get_last_call_ex} method)s
     */
    public boolean set(Object arg_ins,Object arg_value){
        try {
            if(this.getSetter_func()!=null){
                this.getSetter_func().invoke(arg_ins, arg_value);
            }else{
                this.getField().set(arg_ins, arg_value);
            }
            last_call_ex = null;
        } catch (Throwable wtf) {
            last_call_ex = wtf;
        }
        return last_call_ex==null;
    }
    
    /**
     * Attempts to set the field as a static one, by passing {@code null} as instance.
     * <p>
     * It simply calls the {@code set(null,arg_val)} function.
     * </p>
     * @param arg_val the value should be set to the {@code static} field
     * @return {@code true} if the setting was success, {@code false} otherwise
     */
    public boolean set_static(Object arg_val){
        return set(null, arg_val);
    }
    
    /**
     * Returns the user specified {@link Field_Info#user_meta()} value.
     * <p>
     * If the related field has not annotated using the {@link Field_Info}, then it returns {@code null}.
     * </p>
     * <p>
     * It's {@code null} when the field is not annotated, and it's an empty {@code String}, when
     * {@link Field_Info#user_meta() } has not set yet(the default value)
     * </p>
     * @return the cached user {@link Field_Info#user_meta()} value of related field
     * @since 0.2.3
     */
    public String get_user_meta_from_field_info_annot(){
        return field_info_user_meta;
    }
    
    /**
     * Returns the user specified {@link Field_Info#user_meta()} value, or the associated
     * field name.
     * <p>
     * If the associated {@link Field} is annotated by {@link Field_Info}, and it has a <b>not-empty</b> 
     * {@link Field_Info#user_meta()}, then it's returned, otherwise, the real
     * field name is returned.
     * </p>
     * @return result of {@link #get_user_meta_from_field_info_annot() }(if not {@code null}/empty), or associated {@link Field} name
     * @since 0.2.3
     */
    public String get_user_meta_from_field_info_annot_or_name(){
        return field_info_user_meta_or_field_name;
    }
    
    /**
     * Returns the associated {@link Field} name
     * @return field name by calling {@link Field#getName()}
     * @since 0.2.3
     */
    public String get_field_name(){
        return this.field.getName();
    }
    
}
