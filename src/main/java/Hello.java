// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Hello extends HttpServlet {

   private String message;

   public void init() throws ServletException {
      // Vulnerability: Hardcoded password (should not store passwords like this)
      String password = "admin123";  // Hardcoded password
      message = "Hello World: src/main/java/Hello.java";
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      // Vulnerability: Missing input validation (allowing unchecked user input)
      String userInput = request.getParameter("userInput");  // User input without validation
      if (userInput == null) {
         userInput = "default";
      }

      // Vulnerability: Potential Cross-Site Scripting (XSS) by directly printing user input
      PrintWriter out = response.getWriter();
      response.setContentType("text/html");

      // Vulnerability: Potential XSS if unescaped input is printed directly
      out.println("<h1>Hello, " + userInput + "</h1>");  // User input displayed without sanitization

      // Vulnerability: Lack of secure cookie handling (should use HttpOnly and Secure flags)
      Cookie cookie = new Cookie("session_id", "123456");
      cookie.setMaxAge(60 * 60); // Set cookie expiry to 1 hour
      response.addCookie(cookie);  // Vulnerable cookie handling
   }
}
