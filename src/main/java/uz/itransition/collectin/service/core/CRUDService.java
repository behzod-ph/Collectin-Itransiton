
package uz.itransition.collectin.service.core;

import uz.itransition.collectin.payload.response.Response;
import uz.itransition.collectin.service.marker.Creationable;
import uz.itransition.collectin.service.marker.Modifiable;

/***
 * @ID -simply id of entities
 * @R - outcome response type which accessible data for usage
 * @C - incoming object that is request for creation
 * @U - incoming object that is request for update
 * */
public interface CRUDService<
        ID,
        R extends Response,
        C extends Creationable,
        U extends Modifiable
        > {

    R create(C c);

    R get(ID identity);

    R modify(ID identity,U u);

    R delete(ID identity);

}
