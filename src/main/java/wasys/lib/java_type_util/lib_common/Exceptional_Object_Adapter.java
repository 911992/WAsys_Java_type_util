/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Exceptional_Object_Adapter.java
Created on: Aug 22, 2020 10:22:41 AM
    @author https://github.com/911992
 
History:
    initial version: 0.1.5(20200822)
*/

package wasys.lib.java_type_util.lib_common;


/**
 * (internal lib use) Adapter class for {@link Exceptional_Object}.
 * <p>
 * Implements the required functionality of {@link Exceptional_Object}, by holding the last known throwable using {@code last_call_ex} field.
 * </p>
 * @since 0.1.5
 * @author https://github.com/911992
 */
public abstract class Exceptional_Object_Adapter implements Exceptional_Object{
    
    /**
     * Holds the last known exception was thrown by either get/set op calls.
     * <p>
     * <b>Note:</b> For each set/get op call, this field is set to {@code null}, regardless of its value.
     * </p>
     */
    protected Throwable last_call_ex;

    /**
     * {@inheritDoc }
     */
    @Override
    public Throwable get_last_call_ex() {
        return last_call_ex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean was_last_call_success() {
        return get_last_call_ex() == null;
    }
    
}
