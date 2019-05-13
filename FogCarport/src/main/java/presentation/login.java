/*
 *  
 */
package presentation;

import data.exceptions.LoginException;
import data.models.CustomerModel;
import javax.servlet.http.HttpServletRequest;
import logic.LogicFacade;

/**
 * Login Method. This is called after you hit "log ind" on the login.jsp. You're
 * then either confirmed as a valid user, or rejected and sent back to the login
 * page.
 *
 * @author
 */
public class login extends Command
{

    public login()
    {
    }

    @Override
    String execute(HttpServletRequest request, LogicFacade logic) throws LoginException
    {
        String email = request.getParameter("email"); // Get the email from the Parameters 
        String password = request.getParameter("password"); // Get the password from the Parameters

        if (email == null || email.isEmpty())
        {
            throw new LoginException("Email field can't be empty.");
        } else if (password == null || password.isEmpty())
        {
            throw new LoginException("Password field can't be empty.");
        } else
        {
            // Check that Customer exists in the Database. If Customer is in Database, return it as a Model.
            CustomerModel customer = logic.login(email, password);

            // Place Customer on the Session.
            request.getSession().setAttribute("customer", customer);

            // Return Customer to the homepage of the website.
            return "homepage";
        }

    }

}
