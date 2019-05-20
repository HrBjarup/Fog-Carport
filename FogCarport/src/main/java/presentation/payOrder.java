/*
 *  
 */
package presentation;

import data.exceptions.DataException;
import data.exceptions.UserException;
import data.models.OrderModel;
import javax.servlet.http.HttpServletRequest;
import logic.LogicFacade;

/**
 * Pay for a single order and view the same order after payment
 *
 * @author
 */
public class payOrder extends Command
{

    @Override
    String execute(HttpServletRequest request, LogicFacade logic) throws DataException, UserException 
    {

        Validation validation = new Validation();
        int id = validation.validateInteger(request.getParameter("orderid"), "Order id");
        
        // Pay for order with given id.
        logic.payOrder(id);
        
        //Making the viewOrder-page ready again after the payment update:
        
        // Get an order by id from database.
        OrderModel order = logic.getOrder(id);
        
        // Place values used by viewOrder on request.
        request.setAttribute("order", order);
        request.setAttribute("tile", logic.getMaterial(order.getRoof_tiles_id(), "roof").getDescription());
        request.setAttribute("shedwalls", logic.getMaterial(order.getShed_walls_id(), "shed").getDescription());
        request.setAttribute("shedfloor", logic.getMaterial(order.getShed_floor_id(), "shed").getDescription());
        request.setAttribute("customer", logic.getCustomer(order.getId_customer()));
        request.setAttribute("employee", logic.getEmployee(order.getId_employee()));

        return "viewOrder";
    }

}
