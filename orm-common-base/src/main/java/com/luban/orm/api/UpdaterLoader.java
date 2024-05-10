
package com.luban.orm.api;

import java.io.Serializable;

/**
 * @author hp
 */
public interface UpdaterLoader<AGGREGATION, ID extends Serializable> extends Loader<AGGREGATION> {

    Modifier<AGGREGATION> aggregationById(ID id);

}
