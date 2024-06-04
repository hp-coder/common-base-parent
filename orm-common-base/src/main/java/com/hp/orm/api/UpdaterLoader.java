
package com.hp.orm.api;

import java.io.Serializable;

/**
 * @author hp
 */
public interface UpdaterLoader<AGGREGATE_ROOT, ID extends Serializable> extends Loader<AGGREGATE_ROOT> {

    Modifier<AGGREGATE_ROOT> aggregationById(ID id);

}
