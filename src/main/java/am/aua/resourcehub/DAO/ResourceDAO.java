package am.aua.resourcehub.DAO;

import am.aua.resourcehub.model.Resource;

import java.util.List;

public interface ResourceDAO {

    Resource getResourceById(int id);

    List<Resource> getAllResources();

    List<Resource> searchResourcesByName(String search);
}
