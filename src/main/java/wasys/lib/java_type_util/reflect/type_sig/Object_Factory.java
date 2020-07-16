/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Object_Factory.java
Created on: Jul 14, 2020 3:34:07 PM
    @author https://github.com/911992
 
History:
    initial version: 0.1(20200701)

    0.1.1(20200716)
        • Type now is generic/templated
        • create_Object method now returns the template<A> type
*/

package wasys.lib.java_type_util.reflect.type_sig;


/**
 * An object factory skeleton.
 * <p>
 * When creating an instance of a type should be done by user-part, so this interface may help.
 * </p>
 * @param <A> The type this object is explicitly related to
 * @author https://github.com/911992
 */
public interface Object_Factory<A> {
    /**
     * Should return a non-{@code null} expected object which is assignable to given {@code arg_type}.
     * @param arg_type the type returning object should be assignable or equal
     * @return an object of related template type
     */
    public A create_Object(Class arg_type);
}
