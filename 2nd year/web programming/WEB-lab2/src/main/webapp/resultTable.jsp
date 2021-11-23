<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ServletContext context = request.getServletContext();
    ArrayList<String> resultList = (ArrayList<String>) context.getAttribute("resultList");
%>



<table class="result-table" id = "result_table">
    <thead>
    <tr class="result-table-header">
        <th class="result-table-th">Значение Х</th>
        <th class="result-table-th">Значение Y</th>
        <th class="result-table-th">R</th>
        <th class="result-table-th">Время запроса</th>
        <th class="result-table-th" id="result_table_result_sell">Результат</th>
    </tr>
    </thead>
    <tbody class="result-table-body">
    <% for (String responseInfo: resultList){
        out.println(responseInfo);
        }%>
    </tbody>
</table>
