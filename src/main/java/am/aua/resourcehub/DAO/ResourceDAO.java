package am.aua.resourcehub.DAO;

import am.aua.resourcehub.model.Resource;

import java.util.List;

public interface ResourceDAO {

    Resource getResourceById(int id);

    List<Resource> getAllResources(int limit, int offset);

    List<Resource> searchResourcesByName(String search);

    List<Resource> searchResourcesByKeyword(String keyword);

    List<Resource> search(String query,
                          List<String> types, //can be null
                          List<String> targets, //can be null
                          List<String> regions, //can be null
                          List<String> domains, //can be null
                          List<String> languages, //can be null
                          int limit, int offset);


}
