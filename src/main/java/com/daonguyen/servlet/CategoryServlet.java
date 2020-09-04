package com.daonguyen.servlet;

import com.daonguyen.entity.Category;
import com.daonguyen.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "categoryServlet", urlPatterns = {"/category", "/category/add", "/category/edit", "/category/remove"})
public class CategoryServlet extends HttpServlet {

    private List<Category> categories = null;

    @Override
    public void init() throws ServletException {
        categories = new ArrayList<Category>();
        categories.add(new Category("mobile", "Mobile", "Smart Mobile"));
        categories.add(new Category("laptop", "Laptop", "Powerful Laptop"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getServletPath();
        switch (action) {
            case "/category":
                if (!categories.isEmpty()) {
                    req.setAttribute("categories", categories);
                }
                req.getRequestDispatcher("/WEB-INF/view/category/index.jsp").forward(req, resp);
                break;
            case "/category/add":
                req.getRequestDispatcher("/WEB-INF/view/category/add.jsp").forward(req, resp);
                break;
            case "/category/edit":
                String editingCode = req.getParameter("code");

                for (Category category : categories) {
                    if (category.getCode().equals(editingCode)) {
                        req.setAttribute("category", category);
                        break;
                    }
                }

                req.getRequestDispatcher("/WEB-INF/view/category/edit.jsp").forward(req, resp);
                break;
            case "/category/remove":
                String removingCode = req.getParameter("code");

                for (int i = 0; i < categories.size(); i++) {
                    if(categories.get(i).getCode().equals(removingCode)) {
                        categories.remove(i);
                        break;
                    }
                }

                resp.sendRedirect(req.getContextPath() + "/category");
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getServletPath();
        System.out.println(action);

        switch (action) {
            case "/category/add":
                Category newCategory = new Category();
                newCategory.setCode(req.getParameter("code"));
                newCategory.setName(req.getParameter("name"));
                newCategory.setDescription(req.getParameter("desc"));

                categories.add(newCategory);
                break;
            case "/category/edit":
                String editingCode = req.getParameter("code");

                for (Category category : categories) {
                    if(category.getCode().equals(editingCode)) {
                        category.setName(req.getParameter("name"));
                        category.setDescription(req.getParameter("desc"));
                    }
                }

                break;
            default:
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/category");
    }
}
