package am.aua.resourcehub.DAO;

import am.aua.resourcehub.model.Resource;

import java.util.List;

/**
 * Interface that represents the DAO layer for manipulating resources in the databse
 */
public interface ResourceDAO {

    /**
     * Get all the resources from the database
     * @return a List of Resource objects
     */
    List<Resource> getAllResources(int limit, int offset);


    /**
     * Get all the resources which match the search and filtering input of the user
     * @return a List of Resource objects
     */
    List<Resource> search(String query,
                          List<String> types, //can be null
                          List<String> targets, //can be null
                          List<String> regions, //can be null
                          List<String> domains, //can be null
                          List<String> languages, //can be null
                          int limit, int offset);

    /**
     * Get the number of resources that match the search
     * @return the resource count as int
     */
    int getFoundResourceCount();

    /**
     * Inserts the given list of resources into the database
     */
    void insertResources(List<Resource> resources);

    /**
     * Removes the resource with the given id from the database
     * @param id of the resource to be removed
     * @return a massage String to tell user how insertion went
     */
    String removeResourceWithId(int id);

}
