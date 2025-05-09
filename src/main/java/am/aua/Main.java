package am.aua;

import am.aua.resourcehub.DAO.ResourceDAOImpl;
import am.aua.resourcehub.model.Resource;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        /*//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }*/


        ResourceDAOImpl dao = new ResourceDAOImpl();
        List<Resource> resources = dao.search(null, null, null, null, null, null, 20, 0);


        for (Resource resource : resources) {
            System.out.println(resource.getId() + " | " + resource.getTitle() + " | " + resource.getTarget() + " | " + resource.getDomain() + " | " + resource.getType());
        }
    }
}