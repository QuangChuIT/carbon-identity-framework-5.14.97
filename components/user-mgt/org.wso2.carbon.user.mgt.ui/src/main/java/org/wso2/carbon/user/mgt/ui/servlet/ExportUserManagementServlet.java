package org.wso2.carbon.user.mgt.ui.servlet;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.ui.CarbonUIUtil;
import org.wso2.carbon.user.mgt.stub.types.carbon.UserProfileClient;
import org.wso2.carbon.user.mgt.ui.UserAdminClient;
import org.wso2.carbon.user.mgt.ui.UserAdminUIConstants;
import org.wso2.carbon.utils.ServerConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportUserManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        log.info("Login Context Servlet doGet got Hit !");
        HttpSession session = request.getSession();
        UserProfileClient[] datas = null;
        //  search filter
        String selectedDomain = request.getParameter("domain");
        if (StringUtils.isBlank(selectedDomain)) {
            selectedDomain = UserAdminUIConstants.ALL_DOMAINS;
        }

//        String filter = request.getParameter("filter");
//        if (StringUtils.isBlank(filter)) {
//            if (StringUtils.isBlank(filter)) {
//                filter = "*";
//            }
//        }
        String filter = "*";

        String modifiedFilter = filter.trim();
        if (!UserAdminUIConstants.ALL_DOMAINS.equalsIgnoreCase(selectedDomain)) {
            modifiedFilter = selectedDomain + UserAdminUIConstants.DOMAIN_SEPARATOR + filter;
            modifiedFilter = modifiedFilter.trim();
        }

        try {
            String cookie = (String) session
                    .getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
            String backendServerURL =
                    CarbonUIUtil.getServerURL(getServletConfig().getServletContext(),
                            session);
            ConfigurationContext configContext = (ConfigurationContext) getServletConfig()
                    .getServletContext()
                    .getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
            UserAdminClient client = new UserAdminClient(cookie, backendServerURL, configContext);

            if (filter.length() > 0) {
                datas = client.exportUsers(modifiedFilter, -1);
                if (datas != null) {
                    HSSFWorkbook wb2007 = new HSSFWorkbook( );
                    Sheet sheet = wb2007.createSheet("Users");
                    sheet.setColumnWidth(0, 12*256);
                    sheet.setColumnWidth(1, 12*256);
                    sheet.setColumnWidth(2, 25*256);
                    sheet.setColumnWidth(3, 25*256);
                    sheet.setColumnWidth(4, 15*256);
                    sheet.setColumnWidth(5, 12*256);
                    sheet.setColumnWidth(6, 15*256);
                    CellStyle styleHeader = wb2007.createCellStyle();

                    int rownum = 0;
                    Cell cell;
                    Row row;
                    row = sheet.createRow(rownum);
                    // First Name
                    cell = row.createCell(0);
                    cell.setCellValue("First Name");
                    cell.setCellStyle(styleHeader);
                    // Last Name
                    cell = row.createCell(1);
                    cell.setCellValue("Last Name");
                    cell.setCellStyle(styleHeader);
                    // Full Name
                    cell = row.createCell(2);
                    cell.setCellValue("Full Name");
                    cell.setCellStyle(styleHeader);
                    // Email
                    cell = row.createCell(3);
                    cell.setCellValue("Email");
                    cell.setCellStyle(styleHeader);
                    // Organization
                    cell = row.createCell(4);
                    cell.setCellValue("Organization");
                    cell.setCellStyle(styleHeader);
                    // Country
                    cell = row.createCell(5);
                    cell.setCellValue("Country");
                    cell.setCellStyle(styleHeader);
                    // Mobile Phone
                    cell = row.createCell(6);
                    cell.setCellValue("Mobile Phone");
                    cell.setCellStyle(styleHeader);

                    //Data
                    for (int i = 0; i < datas.length; i++) {
                        rownum++;
                        row = sheet.createRow(rownum);
                        // First Name
                        cell = row.createCell(0);
                        cell.setCellValue(datas[i].getFirstName());
                        // Last Name
                        cell = row.createCell(1);
                        cell.setCellValue(datas[i].getLastName());
                        // Full Name
                        cell = row.createCell(2);
                        cell.setCellValue(datas[i].getFullName());
                        // Email
                        cell = row.createCell(3);
                        cell.setCellValue(datas[i].getEmail());
                        // Organization
                        cell = row.createCell(4);
                        cell.setCellValue(datas[i].getOrganization());
                        // Country
                        cell = row.createCell(5);
                        cell.setCellValue(datas[i].getCountry());
                        // Mobile Phone
                        cell = row.createCell(6);
                        cell.setCellValue(datas[i].getMobile());
                    }
                    String fileName = "User_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xlsx";

                    // write it as an excel attachment
                    ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
                    wb2007.write(outByteStream);
                    byte [] outArray = outByteStream.toByteArray();
                    response.setContentType("application/vnd.ms-excel");
                    response.setContentLength(outArray.length);
                    response.setHeader("Expires:", "0"); // eliminates browser caching
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                    OutputStream outStream = response.getOutputStream();
                    outStream.write(outArray);
                    outStream.flush();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in Export. " + e.getMessage(),
                    "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("You are inside the servlet !");
    }
}
