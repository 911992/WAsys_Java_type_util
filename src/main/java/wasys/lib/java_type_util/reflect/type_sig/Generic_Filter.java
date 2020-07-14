/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Generic_Filter.java
Created on: Jul 3, 2020 3:47:33 PM
    @author https://github.com/911992
 
History:
    initial version: 0.1(20200701)
*/

package wasys.lib.java_type_util.reflect.type_sig;


/**
 * A generic object filter, for marking an object should be skipped or not.
 * @param <A> Type of the object should be considered
 * @author https://github.com/911992
 */
public interface Generic_Filter <A> {
    /**
     * Null Object implementation of hosted type.
     * <p>
     * This implementation will consider(no skip) any given input arg to functions.
     * </p>
     */
    public static Generic_Filter NULL_OBJECT = new Generic_Filter() {
        @Override
        public boolean consider(Object arg_field) {
            return true;
        }        
    };
    
    /**
     * Indicates if the given {@code arg_obj} should be considered, or not(get skipped).
     * @param arg_obj the object should be considered
     * @return {@code true} if the object should be considered, {@code false} otherwise(get skipped)
     */
    boolean consider(A arg_obj);
    
}
