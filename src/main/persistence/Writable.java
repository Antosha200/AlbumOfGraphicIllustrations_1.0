package persistence;

import org.json.JSONObject;

/**
 * Interface Writable
 *
 * @author Naumov A.M
 * @version 1.0
 */
public interface Writable {

    /**
     * Returns this as JSON object
     */
    JSONObject toJson();
}
