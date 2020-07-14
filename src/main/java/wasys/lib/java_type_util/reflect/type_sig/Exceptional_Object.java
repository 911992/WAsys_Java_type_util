/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Exceptional_Object.java
Created on: Jul 14, 2020 6:35:49 PM
    @author https://github.com/911992
 
History:
    initial version: 0.1(20200701)
*/

package wasys.lib.java_type_util.reflect.type_sig;


/**
 * (internal lib use) Exceptional Object.
 * <p>
 * The main usage of this type is holding exceptions instead of throwing them back.
 * </p>
 * @author https://github.com/911992
 */
public abstract class Exceptional_Object {
    /**
     * Holds the last known exception was thrown by either get/set op calls.
     * <p>
     * <b>Note:</b> For each set/get op call, this field is set to {@code null}, regardless of its value.
     * </p>
     */
    protected Throwable last_call_ex;
    
    /**
     * Returns the last known exception by set/get ops.
     * <p>
     * If it returns a {@code null} ptr, it means either the last set/get op call was successful, or no any op has been made yet.
     * </p>
     * @return the {@code last_call_ex} var
     */
    public Throwable get_last_call_ex() {
        return last_call_ex;
    }

    /**
     * Tells if the previously called set/get op was successful or not.
     * <p>
     * It simply checks if the {@code last_call_ex} is {@code null}, then {@code true}.
     * </p>
     * <p>
     * <b>Note:</b> There is no dedicated state checking to see if any set/get op has been taken or not, so this method would return {@code false} for such situation.
     * </p>
     * @return {@code true} if last get/set op call was success, {@code false} otherwise
     */
    public boolean was_last_call_success() {
        return last_call_ex == null;
    }
    
}
