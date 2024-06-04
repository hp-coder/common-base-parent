
package com.hp.orm.api;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author hp
 */
public interface BatchUpdaterLoader<AGGREGATE_ROOT, ID extends Serializable> extends BatchLoader<AGGREGATE_ROOT> {

    BatchModifier<AGGREGATE_ROOT> aggregationsById(Collection<ID> ids);

}
