package presentation;

import data.exceptions.LoginException;
import data.models.PartslistModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.LogicFacadeImpl;

public class makeCarportSimple extends presentation.Command
{

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws LoginException
    {
        String height = request.getParameter("height");
        String width  = request.getParameter("width");
        String length = request.getParameter("length");
        String shed   = request.getParameter("shed");
        
        PartslistModel bom = LogicFacadeImpl.getInstance().getSimpleBOM(height, width, length, shed);
        request.getSession().setAttribute("bom", bom);
        
        return "partslist";
    }
    
}