<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript">
			function addOrder(){
				window.location.href = "${pageContext.request.contextPath}/adminOrder_addPage.action";
			}
			function delete_confirm()
			{
				if(confirm("确定删除？"))
				{
					return true;
				}
				else {
					return false;
				}
			}
			
			//展示商品信息的方法
			function showDetail(oid){
				//获得按钮的对象
				var but = document.getElementById("but"+oid);
				//获得div对象
				var div1 = document.getElementById("div"+oid);
				if(but.value=="订单详细"){
					//使用ajax
					//1 创建异步加载对象
					var xhr = createXmlHttp();
					//2 设置监听
					xhr.onreadystatechange = function(){
						if(xhr.readyState==4){
							if(xhr.status==200){

								div1.innerHTML = xhr.responseText;
							}
						}
					}
					//3 打开链接
					xhr.open("GET","${pageContext.request.contextPath}/adminOrder_findOrderItem.action?time="+new Date().getTime()+"&oid="+oid,true);
					//4 发送
					xhr.send(null);
					but.value = "close";
				}else{
					but.value = "订单详细";
					div1.innerHTML = "";
				}
				
			}
			function createXmlHttp(){
				   var xmlHttp;
				   try{ // Firefox, Opera 8.0+, Safari
				        xmlHttp=new XMLHttpRequest();
				    }
				    catch (e){
					   try{// Internet Explorer
					         xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
					      }
					    catch (e){
					      try{
					         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
					      }
					      catch (e){}
					      }
				    }

					return xmlHttp;
				 }
		</script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</td>
					</tr>

					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="1%">
										序号
									</td>
									<td align="center" width="1%">
										订单编号
									</td>
									<td align="center" width="1%">
										总金额
									</td>
									<td align="center" width="1%">
										收货人
									</td>
									<td align="center" width="1%">
										订单状态
									</td>
									<td width="*" align="center">
										订单详细
									</td>

								</tr>
								<s:iterator var="order" value="pageBean.list" status="status">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												<s:property value="#status.count"/>
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												<s:property value="#order.oid"/>
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												<s:property value="#order.total"/>
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												<s:property value="#order.name"/>
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												<s:if test="#order.state==1">未付款</s:if>
												<s:if test="#order.state==2"><a href="${pageContext.request.contextPath }/adminOrder_updateState.action?oid=<s:property value="#order.oid" />"><font color="blue">发货</font></a></s:if>
												<s:if test="#order.state==3">买家未确认收货</s:if>
												<s:if test="#order.state==4">交易完成</s:if>
											</td>
											<td align="center" style="HEIGHT: 22px">
												<input id="but<s:property value="#order.oid" />" type="button" value="订单详细" onclick="showDetail(<s:property value="#order.oid" />)"/>
												<div id="div<s:property value="#order.oid" />"></div>
											</td>

										</tr>
									</s:iterator>	
							</table>
						</td>
					</tr>
					<tr align="center">
						<td class="ta_01" align="center" bgColor="#afd1f3">
							第<s:property value="pageBean.page"/>页/共<s:property value="pageBean.totalPage"/>页&nbsp;&nbsp;&nbsp;&nbsp;
							<s:if test="pageBean.page!=1">
								<a href="${pageContext.request.contextPath }/adminOrder_findAll.action?page=1">首页|&nbsp;</a>
								<a href="${pageContext.request.contextPath }/adminOrder_findAll.action?page=<s:property value="pageBean.page-1"/>" >上页</a>
							</s:if>
							<s:if test="pageBean.page!=pageBean.totalPage">
								<a href="${pageContext.request.contextPath }/adminOrder_findAll.action?page=<s:property value="pageBean.page+1"/>" >下页</a>
								<a href="${pageContext.request.contextPath }/adminOrder_findAll.action?page=<s:property value="pageBean.totalPage"/>" >&nbsp;|尾页</a>
							</s:if>
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
	</body>
</HTML>

