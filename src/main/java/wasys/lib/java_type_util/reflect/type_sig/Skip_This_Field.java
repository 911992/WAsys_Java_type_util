/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Skip_This_Field.java
Created on: Jul 12, 2020 11:38:13 PM
    @author https://github.com/911992
 
History:
    initial version: 0.1(20200701)
*/

package wasys.lib.java_type_util.reflect.type_sig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Tells the type parse, to ignore(skip) the related type field has annotated.
 * <p>
 * By default, all fields will be considered by given {@link Generic_Filter}, unless the field is annotated by this annotation.
 * </p>
 * <p>
 * This could be an replacement(alternative way) when implementing a {@link Generic_Filter} is not appreciated, and skipping a field should be done explicitly without complex checks.
 * </p>
 * @author https://github.com/911992
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Skip_This_Field {

}
