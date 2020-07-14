/*
 * Copyright (c) 2020, https://github.com/911992 All rights reserved.
 * License BSD 3-Clause (https://opensource.org/licenses/BSD-3-Clause)
 */

/*
WAsys_Java_type_util
File: Field_Definition_Order.java
Created on: Jul 3, 2020 3:10:07 PM
    @author https://github.com/911992
 
History:
    initial version: 0.1(20200701)
*/

package wasys.lib.java_type_util.reflect.type_sig;



/**
 * Specifies the field order for a type signature.
 * <p>
 * The order of fields needed to be considered, when scraping parent(s)'s types are considered too.
 * </p>
 * @author https://github.com/911992
 */
public enum Field_Definition_Order {
    /**
     * Top-level parent's field first, and at the end the child(actual) type's field.
     * <p>
     * Example, for scraping type {@code C} as {@code A -&gt; B -&gt; C}, where A is the top-level parent(assuming {@code Object}).<br>
     * Field scraping will be as parent-to-child order as fields of {@code A}, then {@code B}, and finally {@code C}.
     * </p>
     */
    From_Parent_To_Child,
    
    /**
     * Low-level(current) child type's field first, then very first parent, til top-level one.
     * <p>
     * Example, for scraping type {@code C} as {@code A -&gt; B -&gt; C}, where A is the top-level parent(assuming {@code Object}).<br>
     * Field scraping will be as parent-to-child order as fields of {@code C}, then {@code B}, and finally {@code A}.
     * </p>
     */
    From_Child_To_Parent
}
