<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2022-08-02
  Time: 오후 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>에러 페이지</title>
    <link href="${pageContext.request.contextPath}/template/attend/my/css/bootstrap.min.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="alert alert-danger" role="alert">
    <h4 class="alert-heading">403에러</h4>
    <p>접근 권한이 없습니다.</p>
    <hr>
    <p class="mb-0"><a href="javascript:window.history.back();" class="alert-link">뒤로가기</a></p>
</div>
</body>
</html>