import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/tuition")
public class TuitionServlet extends HttpServlet {

    private FeeManager manager = new FeeManager();

    // doGet - shows the HTML form
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Tuition Tracker</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Segoe UI', sans-serif; background: #f0f4ff; min-height: 100vh; }");
        out.println(".header { background: linear-gradient(135deg, #667eea, #764ba2); color: white; padding: 30px; text-align: center; }");
        out.println(".header h1 { font-size: 2.5rem; letter-spacing: 2px; }");
        out.println(".header p { opacity: 0.8; margin-top: 8px; }");
        out.println(".container { max-width: 900px; margin: 30px auto; padding: 0 20px; }");
        out.println(".grid { display: grid; grid-template-columns: 1fr 1fr; gap: 24px; margin-bottom: 30px; }");
        out.println(".card { background: white; border-radius: 16px; padding: 28px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }");
        out.println(".card h2 { color: #667eea; font-size: 1.2rem; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 2px solid #f0f4ff; }");
        out.println("label { display: block; font-size: 12px; color: #888; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 6px; margin-top: 14px; }");
        out.println("input { width: 100%; padding: 10px 14px; border: 2px solid #e8eaf6; border-radius: 8px; font-size: 14px; outline: none; transition: 0.2s; }");
        out.println("input:focus { border-color: #667eea; }");
        out.println("button { width: 100%; margin-top: 20px; padding: 12px; background: linear-gradient(135deg, #667eea, #764ba2); color: white; border: none; border-radius: 8px; font-size: 15px; font-weight: 600; cursor: pointer; transition: 0.2s; }");
        out.println("button:hover { opacity: 0.9; transform: translateY(-1px); }");
        out.println(".records { background: white; border-radius: 16px; padding: 28px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }");
        out.println(".records h2 { color: #667eea; font-size: 1.2rem; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 2px solid #f0f4ff; }");
        out.println(".student-row { display: flex; justify-content: space-between; align-items: center; padding: 16px 20px; background: #f8f9ff; border-radius: 10px; margin-bottom: 12px; border-left: 4px solid #667eea; }");
        out.println(".student-name { font-weight: 600; color: #333; font-size: 15px; }");
        out.println(".student-num { font-size: 11px; color: #aaa; margin-top: 3px; }");
        out.println(".student-total { background: linear-gradient(135deg, #667eea, #764ba2); color: white; padding: 8px 18px; border-radius: 20px; font-weight: 700; font-size: 15px; }");
        out.println(".empty { text-align: center; color: #bbb; padding: 40px; font-size: 14px; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        // Header
        out.println("<div class='header'>");
        out.println("<h1>📚 Tuition Fee Tracker</h1>");
        out.println("<p>Manage student fees easily</p>");
        out.println("</div>");

        out.println("<div class='container'>");
        out.println("<div class='grid'>");

        // Add Student Form
        out.println("<div class='card'>");
        out.println("<h2>➕ Add Student</h2>");
        out.println("<form method='post' action='tuition'>");
        out.println("<input type='hidden' name='action' value='addStudent'>");
        out.println("<label>How many names?</label>");
        out.println("<input type='number' name='nameCount' min='1' max='50' placeholder='e.g. 3' oninput='generateFields(this.value)'>");
        out.println("<div id='nameFields'></div>");
        out.println("<script>");
        out.println("function generateFields(count) {");
        out.println("  var html = '';");
        out.println("  for(var i=1; i<=count; i++) {");
        out.println("    html += '<label>Name ' + i + '</label>';");
        out.println("    html += '<input type=\"text\" name=\"name' + i + '\" placeholder=\"Enter name ' + i + '\">';");
        out.println("  }");
        out.println("  document.getElementById('nameFields').innerHTML = html;");
        out.println("}");
        out.println("</script>");
        out.println("<button type='submit'>Add Student</button>");
        out.println("</form>");
        out.println("</div>");

        // Add Fee Form
        out.println("<div class='card'>");
        out.println("<h2>💰 Add Fee Payment</h2>");
        out.println("<form method='post' action='tuition'>");
        out.println("<input type='hidden' name='action' value='addFee'>");
        out.println("<label>Student Number</label>");
        out.println("<input type='number' name='index' placeholder='e.g. 1'>");
        out.println("<label>Amount (₹)</label>");
        out.println("<input type='number' name='amount' placeholder='e.g. 2000'>");
        out.println("<button type='submit'>Add Fee</button>");
        out.println("</form>");
        out.println("</div>");

        out.println("</div>");

        // Student Records
        out.println("<div class='records'>");
        out.println("<h2>📋 Student Records</h2>");

        ArrayList<Student> list = manager.getAllStudents();
        if (list.isEmpty()) {
            out.println("<div class='empty'>No students yet. Add your first student above!</div>");
        } else {
            for (int i = 0; i < list.size(); i++) {
                Student s = list.get(i);
                out.println("<div class='student-row'>");
                out.println("<div>");
                out.println("<div class='student-name'>" + s.getNamesAsString() + "</div>");
                out.println("<div class='student-num'>Student #" + (i+1) + "</div>");
                out.println("</div>");
                out.println("<div class='student-total'>₹" + s.getTotal() + "</div>");
                out.println("</div>");
            }
        }

        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    // doPost - handles form submissions
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equals("addStudent")) {
            Student s = new Student();
            int nameCount = Integer.parseInt(request.getParameter("nameCount"));
            for (int i = 1; i <= nameCount; i++) {
                String name = request.getParameter("name" + i);
                if (name != null && !name.isEmpty()) {
                    s.addName(name);
                }
            }

            manager.addStudent(s);
        }

        if (action.equals("addFee")) {
            int index = Integer.parseInt(request.getParameter("index")) - 1;
            double amount = Double.parseDouble(request.getParameter("amount"));
            manager.getStudent(index).addPayment(amount);
        }

        // Redirect back to the page
        response.sendRedirect("tuition");
    }
}
