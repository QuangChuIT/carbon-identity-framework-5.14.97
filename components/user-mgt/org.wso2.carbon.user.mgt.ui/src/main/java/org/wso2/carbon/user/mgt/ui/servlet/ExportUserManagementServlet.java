package org.wso2.carbon.user.mgt.ui.servlet;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.identity.claim.metadata.mgt.ClaimMetadataManagementAdminService;
import org.wso2.carbon.identity.claim.metadata.mgt.dto.ClaimPropertyDTO;
import org.wso2.carbon.identity.claim.metadata.mgt.dto.LocalClaimDTO;
import org.wso2.carbon.identity.claim.metadata.mgt.util.ClaimConstants;
import org.wso2.carbon.ui.CarbonUIUtil;
import org.wso2.carbon.user.mgt.stub.types.carbon.UserProfileClient;
import org.wso2.carbon.user.mgt.ui.UserAdminClient;
import org.wso2.carbon.user.mgt.ui.UserAdminUIConstants;
import org.wso2.carbon.utils.ServerConstants;

import javax.naming.directory.Attributes;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ExportUserManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        log.info("Login Context Servlet doGet got Hit !");
        HttpSession session = request.getSession();
        UserProfileClient[] userProfiles = null;
        //  search filter
        String selectedDomain = request.getParameter("domain");
        String returnedAtts = request.getParameter("attributes");
        String displayNames = request.getParameter("displayNames");
        if (StringUtils.isBlank(selectedDomain)) {
            selectedDomain = UserAdminUIConstants.ALL_DOMAINS;
        }

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

            userProfiles = client.exportUsers(modifiedFilter, -1, returnedAtts);

            if (userProfiles != null && userProfiles.length > 0) {
                String[] atts = returnedAtts.split(",");
                String[] names = displayNames.split(",");

                HSSFWorkbook wb2007 = new HSSFWorkbook( );
                Sheet sheet = wb2007.createSheet("Users");
                CellStyle styleHeader = wb2007.createCellStyle();

                int rownum = 0;
                Cell cell;
                Row row;
                row = sheet.createRow(rownum);
                for(int i = 0; i < names.length; i ++) {
                    // Column Name
                    cell = row.createCell(i);
                    cell.setCellValue(names[i]);
                    cell.setCellStyle(styleHeader);
                }
                // Data
                for (int i = 0; i < userProfiles.length; i++) {
                    rownum++;
                    row = sheet.createRow(rownum);
                    String[] userPropertiesValue = userProfiles[i].getUserPropertiesValue();
                    for(int j = 0; j < userPropertiesValue.length; j++) {
                        // row data
                        cell = row.createCell(j);
                        cell.setCellValue(userPropertiesValue[j]);
                    }
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
            } else {
                JOptionPane.showMessageDialog(null, "No data to export",
                        "Alert", JOptionPane.WARNING_MESSAGE);
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
