/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Type_Signature.java
Created on: Jul 3, 2020 12:40:47 PM
    @author https://github.com/911992
 
History:
    initial version: 0.1(20200701)
*/

package wasys.lib.java_type_util.reflect.type_sig;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * Represents a signature of a type.
 * <p>
 * Mostly holds the list of fields signature({@link Type_Field_Signature}), the type this signature related to, and parsing policy.
 * </p>
 * <p>
 * This type is almost immutable, except the user-customable {@code meta_name} that would be used to hold a user-defined meta name/data.
 * </p>
 * @author https://github.com/911992
 */
public final class Type_Signature extends Exceptional_Object{
    /**
     * Type this signature related to.
     * <p>This is non-{@code null}</p>
     * <p>This field is immutable.</p>
     */
    private final Class type;
    
    /**
     * list of proceed fields signature.
     * <p>
     * This is non-{@code null}(but could be empty). None of the indices are {@code null}.
     * </p>
     * <p>This field is immutable.</p>
     */
    private final ArrayList<Type_Field_Signature> proceed_fields;
    
    /**
     * The policy were used for parsing the related type.
     * <p>
     * This is a non-{@code null} var.
     * </p>
     * <p>This field is immutable.</p>
     */
    private final Type_Signature_Parse_Policy parse_policy;
    
    /**
     * User space for keeping a meta name/data related to this type.
     */
    private String meta_name;
    
    /**
     * Holds the user-defined object factory(if any).
     * <p>
     * This is used by {@code create_object} method, when new instance of relate {@code type} is appreciated.
     * </p>
     * <p>
     * This is {@code null} by default, that will force the {@code create_object} method to use {@code type}'s default constructor for instancing a object.
     * </p>
     */
    private Object_Factory obj_factory;

    /**
     * Default constructor.
     * @param type the type of parsing process
     * @param proceed_fields proceed fields of given type
     * @param parse_policy the policy were used for parsing the type
     */
    public Type_Signature(Class type, ArrayList<Type_Field_Signature> proceed_fields, Type_Signature_Parse_Policy parse_policy) {
        this.type = type;
        this.proceed_fields = proceed_fields;
        this.parse_policy = parse_policy;
    }

    /**
     * Returns the type this signature related to.
     * @return the {@code type} var
     */
    public Class getType() {
        return type;
    }

    /**
     * Returns the proceed fields for this type signature.
     * @return the {@code proceed_fields} var.
     */
    public ArrayList<Type_Field_Signature> getProceed_fields() {
        return proceed_fields;
    }

    /**
     * Returns the policy were used for parsing this type signature.
     * @return the {@code parse_policy} var
     */
    public Type_Signature_Parse_Policy getParse_policy() {
        return parse_policy;
    }
    
    /**
     * Returns the user meta name/data.
     * @return the {@code meta_name} var
     */
    public String getMeta_name() {
        return meta_name;
    }

    /**
     * Sets the user meta name/data from given arg.
     * @param meta_name the val to be set to {@code meta_name} var
     */
    public void setMeta_name(String meta_name) {
        this.meta_name = meta_name;
    }

    /**
     * Returns the user-defined object factory.
     * @return the {@code obj_factory} var
     */
    public Object_Factory getObj_factory() {
        return obj_factory;
    }

    /**
     * Sets the (user-defined) object factory.
     * @param obj_factory the concreted (or {@code null)} object factory
     */
    public void setObj_factory(Object_Factory obj_factory) {
        this.obj_factory = obj_factory;
    }
    
    
    /**
     * Filters proceed field signatures based on given types.
     * <p>
     * Search over all proceed fields, and will return a list of ones come with one of given filtered types({@code arg_types}).
     * </p>
     * <p>
     * it calls {@code filter} method, with an anon implementation of {@link Generic_Filter} as filter.
     * </p>
     * @param arg_ctx {@code null}able list that filtered fields will be saved to
     * @param arg_types list of types need to be used for filtering
     * @return list of filtered field signatures
     */
    public ArrayList<Type_Field_Signature> filter_type(ArrayList<Type_Field_Signature> arg_ctx,final Class...arg_types){
        Generic_Filter<Type_Field_Signature> _filter = new Generic_Filter<Type_Field_Signature>() {
            Class types[]=arg_types;
            @Override
            public boolean consider(Type_Field_Signature arg_field) {
                for(int a=0;a<types.length;a++){
                    if(arg_field.getField().getType().equals(types[a])){
                        return true;
                    }
                }
                return false;
            }
        };
        
        return filter(arg_ctx, _filter);
    }
    
    /**
     * Filters proceed field signatures based on field names.
     * <p>
     * Search over all proceed fields, and will return a list of ones come with one of given filtered names({@code arg_types}).
     * </p>
     * <p>
     * it calls {@code filter} method, with an anon implementation of {@link Generic_Filter} as filter.
     * </p>
     * @param arg_ctx {@code null}able list that filtered fields will be saved to
     * @param arg_field_names list of names need to be used for filtering
     * @return list of filtered field signatures
     */
    public ArrayList<Type_Field_Signature> filter_type(ArrayList<Type_Field_Signature> arg_ctx,final String...arg_field_names){
        Generic_Filter<Type_Field_Signature> _filter = new Generic_Filter<Type_Field_Signature>() {
            String[] names=arg_field_names;
            @Override
            public boolean consider(Type_Field_Signature arg_field) {
                for(int a=0;a<names.length;a++){
                    if(arg_field.getField().getName().equals(names[a])){
                        return true;
                    }
                }
                return false;
            }
        };
        return filter(arg_ctx, _filter);
    }
    
    /**
     * Filter the {@code proceed_fields} based on given filter.
     * <p>
     * If the given {@code arg_ctx} is {@code null}, then new {@link ArrayList} will be created, and returned, otherwise the same given list will be used for persisting results.
     * </p>
     * @param arg_ctx {@code null}able list that filtered fields will be saved to
     * @param arg_filter filter will be used for
     * @return list of filtered field signatures, if {@code arg_ctx} is not {@code null}, then {@code arg_ctx}, otherwise a new instance.
     */
    public ArrayList<Type_Field_Signature> filter(ArrayList<Type_Field_Signature> arg_ctx,Generic_Filter<Type_Field_Signature> arg_filter){
        ArrayList<Type_Field_Signature> _res = arg_ctx;
        if(_res==null){
            _res = new ArrayList<Type_Field_Signature>(proceed_fields.size());
        }
        Type_Field_Signature _tfs;
        for(int a=0;a<proceed_fields.size();a++){
            _tfs = proceed_fields.get(a);
            if(arg_filter.consider(_tfs)){
                _res.add(_tfs);
            }
        }
        return _res;
    }
    
    /**
     * Search over the {@code proceed_fields} to find the method named as given {@code arg_field_name}.
     * <p>
     * <b>Note:</b> The given {@code arg_field_name} may be present on target type({@code type}), however the(this) signature cache may not have the field available due to policy/filter during parsing process.
     * </p>
     * @param arg_field_name the field name should be searched on local field siganture cache
     * @return a field signature that equals to given {@code arg_field_name}, or {@code null} if not found
     */
    public Type_Field_Signature get_field_by_name(String arg_field_name){
        Type_Field_Signature _res;
        for(int a=0;a<proceed_fields.size();a++){
            _res = proceed_fields.get(a);
            if(_res.getField().getName().equals(arg_field_name)){
                return _res;
            }
        }
        return null;
    }
    
    /**
     * Creates an instance of related {@code type}.
     * <p>
     * It first checks if the given {@code obj_factory} is not {@code null}, then it goes for the associated {@link Object_Factory}.
     * </p>
     * <p>
     * Else, it will tries to instance an object from default constructor.
     * </p>
     * <p>
     * <b>Important Note:</b> Any exception were thrown by {@code obj_factory} <b>will</b> be thrown to invoker, but if {@code obj_factory} is {@code null}, any exception will be saved and could be grabbed by {@code get_last_call_ex} call.
     * </p>
     * <p>
     * <b>Note:</b> if it returns a {@code null}, this could be because {@code obj_factory} returns it, or an exception were thrown by object instancing by default constructor.
     * </p>
     * @return an object of {@code type}
     */
    public Object create_object(){
        Object _res;
        if(obj_factory!=null){
            _res = obj_factory.create_Object(type);
        }else{
            try {
                Constructor _cons = type.getDeclaredConstructor();
                if(_cons!=null){
                    _cons.setAccessible(true);
                    _res = _cons.newInstance();
                }else{
                    throw new NullPointerException(String.format("Type %s has no default constructor", type.getName()));
                }
            } catch (Throwable wtf) {
                last_call_ex = wtf;
                _res=null;
            }
        }
        return _res;
    }
    
}
