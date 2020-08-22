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
    0.1.5 (20200822)
        • Moved to package wasys.lib.java_type_util.lib_common from wasys.lib.java_type_util.reflect.type_sig
        • Changed the type as an interface(previously as abstract class)
        • Removed last_call_ex field
        • Removed get_last_call_ex, and was_last_call_success body
        • Updated documentation

    initial version: 0.1(20200701)
*/

package wasys.lib.java_type_util.lib_common;


/**
 * (internal lib use) Exceptional Object.
 * <p>
 * The main usage of this type is holding exceptions instead of throwing them back.
 * </p>
 * <p>
 * Any throwable function/method/op may hold the thrown object somewhere(instead of throwing). And make the {@link Throwable} object accessible using functionalities defined by this interface.
 * </p>
 * @author https://github.com/911992
 */
public interface Exceptional_Object {
    
    /**
     * Returns the last known exception was thrown by last method call.
     * <p>
     * Each new call(state-change) on target type(implements this) should reset the throwable state to clear.
     * </p>
     * <p>
     * If it returns a {@code null} ptr, it means either the last set/get op call was successful, or no any op has been made yet.
     * </p>
     * @return the throwable object was thrown by latest method call
     */
    public Throwable get_last_call_ex();

    /**
     * Tells if the previously called op was successful or not.
     * <p>
     * Default behavior could be checking if the {@code get_last_call_ex} returns {@code null}, then {@code true}.
     * </p>
     * <p>
     * <b>Note:</b> There is no dedicated state checking to see if any throwable function call has been taken or not, so this method would return {@code false} for such situation.
     * </p>
     * @return {@code true} if last op call was success, {@code false} otherwise
     */
    public boolean was_last_call_success();
    
}
