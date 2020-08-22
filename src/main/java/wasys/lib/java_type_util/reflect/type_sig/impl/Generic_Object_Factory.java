/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Generic_Object_Factory.java
Created on: Aug 22, 2020 4:56:07 AM
    @author https://github.com/911992
 
History:
    initial version: 0.1.5(20200822)
*/

package wasys.lib.java_type_util.reflect.type_sig.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import wasys.lib.java_type_util.reflect.type_sig.Object_Factory;


/**
 * Generic implementation of {@link Object_Factory}.
 * <p>
 * This class could be used for simple POJO types where instantiation is done using default(and public) constructor.
 * </p>
 * <p>
 * Instantiation will be done by reflection, by calling the related{@code <A>} type's default constructor.
 * </p>
 * <p>
 * If instantiation of type is complex, so this is <b>recommended</b> to implement a dedicated-custom {@link Object_Factory} for that.
 * </p>
 * <p>
 * Note again, the typed {@code <A>} should be an instance-able type(not a {@code enum}, {@code interface}, or {@code abstract class}), and <b>must have</b> a {@code public} default constructor.
 * </p>
 * <p>This is an immutable type.</p>
 * @param <A> The type this factory is related to
 * @since 0.1.5
 * @author https://github.com/911992
 */
public class Generic_Object_Factory<A> implements Object_Factory<A>{
    
    /**
     * The constructor related to typed({@code <A>}) class.
     * @since 0.1.5
     */
    final private Constructor<A> def_constructor;
    
    /**
     * Exception-free {@link Generic_Object_Factory} instantiation.
     * <p>
     * It simply calls the constructor, and catches all exceptions for easier syntax/coding by client-side(user).
     * </p>
     * <p>
     * <b>Note: </b> There will be no any way to grab the cause of any exception during instancing, use the constructor instead.
     * </p>
     * @param <C> the type return {@link Generic_Object_Factory} type will be typed
     * @param arg_type The class/type 
     * @return a Generic_Object_Factory&lt;C&gt; instance, or {@code null} if any problem/exception were given during instancing
     * @since 0.1.5
     */
    public static final <C> Generic_Object_Factory<C> new_instance(Class<C> arg_type){
        try {
            return new Generic_Object_Factory<C>(arg_type);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Tries to find the default {@link Constructor} of given type.
     * @param <E> type of actual class the returning {@link Constructor} should be typed
     * @param arg_type the class should be parsed
     * @return default {@link Constructor} of given {@code arg_type} (non-{@code null})
     * @throws IllegalAccessException if given {@code arg_type}(type) is not a instance-able one(enum | interface | abstract-class), or the default constructor is not {@code public}
     * @throws NoSuchMethodException if the given {@code arg_type} has no any explicitly/implicitly default constructor
     * @since 0.1.5
     */
    private static final <E> Constructor<E> get_default_constructor(Class<E> arg_type) throws IllegalAccessException , NoSuchMethodException{
        int _modifiers = arg_type.getModifiers();
        if(Modifier.isAbstract(_modifiers) || arg_type.isInterface() || arg_type.isEnum()){
            throw new IllegalAccessException(String.format("Illegal type(abstract-class | enum | interface) was given. Cannot instantiate type \"%s\"",arg_type.getCanonicalName()));
        }
        Constructor<E> _const = arg_type.getConstructor();
        _modifiers = _const.getModifiers();
        if(!Modifier.isPublic(_modifiers)){
            throw new IllegalAccessException(String.format("Type \"%s\" must have a default public constructor", arg_type.getCanonicalName()));
        }
        _const.setAccessible(true);
        return _const;
        
    }
    
    /**
     * Parse the given {@code arg_type}, and returns a non-{@code null} instance to server as default {@link Object_Factory} of given type.
     * <p>
     * In order to skip exception handling(when you sure there will be no exceptions), calling static {@code new_instance} method could be an alternative.
     * </p>
     * @param arg_type the type should be parsed and used for returning {@link Object_Factory}
     * @throws IllegalAccessException if given {@code arg_type}(type) is not a instance-able one(enum | interface | abstract-class), or the default constructor is not {@code public}
     * @throws NoSuchMethodException has no any explicitly/implicitly default constructor
     * @since 0.1.5
     */
    public Generic_Object_Factory(Class<A> arg_type) throws IllegalAccessException ,NoSuchMethodException {
        Constructor<A> _const = get_default_constructor(arg_type);
        this.def_constructor = _const;
    }
    
    /**
     * Calls the {@code create_Object()} method, but will not throws any exception by it.
     * <p>
     * If grabbing any {@link Throwable} object was thrown by instancing the type is desired, use {@code create_Object()} instead.
     * </p>
     * @param arg_type (will be ignored)the type {@link Object_Factory} may be considered during instancing
     * @return return type of {@code A}, or {@code null} in case of any exception/error
     * @since 0.1.5
     */
    @Override
    public A create_Object(Class arg_type){
        try {
            return create_Object();
        } catch (Throwable wt) {
            return null;
        }
    }
    
    /**
     * Calls the default({@code def_constructor}) constructor with no argument.
     * <p>
     * Considering {@code create_Object(:Class)} method that will not throw any-exception, instead returns a {@code null} ptr
     * </p>
     * @return an instance of typed param @{code A} (result of constructor)
     * @throws ExceptionInInitializerError if there was an problem loading/linking the related class
     * @throws InvocationTargetException if the constructor (type that hosts) throwsn an exception
     * @since 0.1.5
     */
    public A create_Object()throws ExceptionInInitializerError,InvocationTargetException{
        try {
            return def_constructor.newInstance();
        } catch(ExceptionInInitializerError wt){
            throw wt;
        } catch(InvocationTargetException wt){
            throw wt;
        }
        catch (Throwable wt) {wt.printStackTrace();
            return null;
        } 
    }
    
}
