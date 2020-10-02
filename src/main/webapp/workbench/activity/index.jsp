<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

	<script type="text/javascript">

	$(function(){

		pageList(1,2);

		//日历插件
		$(".time").datetimepicker({
			minView:"month",
			language:'zh-CN',
			format:"yyyy-mm-dd",
			autoclose:true,
			todayBtn:true,
			startDate:new Date(),
			pickerPosition:"bottom-left"
		});


		//全选操作
		$("#qx").click(function (){
			$("input[name=fxk]").prop("checked",this.checked);
		})
		//动态生成元素的绑定事件
		$("#activityBody").on("click",$("input[name=fxk]"),function (){
			$("#qx").prop("checked",$("input[name=fxk]").length == $("input[name=fxk]:checked").length);
		})

		//查询按钮事件
		$("#searchBtn").click(function (){
			//alert("查询事件")
			/*
			* 利用隐藏域保存要查询的值
			*
			* */
			$("#hidden_name").val($.trim($("#search_name").val()));
			$("#hidden_owner").val($.trim($("#search_owner").val()));
			$("#hidden_startDate").val($.trim($("#search_startTime").val()));
			$("#hidden_endDate").val($.trim($("#search_endTime").val()));

			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		})

		//创建按钮单击事件（给下拉框铺数据）
		$("#addBtn").click(function (){
			/*
			* 操作模态窗口
			* 找到jQuery对象，调用model方法，传入show hide参数
			* */
			//alert(123)
			$.ajax({
				url:"Activity/getUserList.do",
				type:"get",
				dataType:"json",
				success:function (resp){

					$("#create-marketActivityOwner").empty();
					$("#create-marketActivityOwner").append("<option></option>")

					$.each(resp,function (i,n){

						$("#create-marketActivityOwner").append("<option value='"+n.id+"'>"+n.name+"</option>");

					})

					var id = "${user.id}"
					$("#create-marketActivityOwner").val(id)
					$("#createActivityModal").modal("show")
				}
			})
		})

		//修改Activity，修改按钮单击事件
		$("#editBtn").click(function (){

			var $fxk = $("input[name=fxk]:checked");
			if ($fxk.length == 0){
				alert("请选择要修改的记录")
			}else if ($fxk.length > 1){
				alert("一次只能修改一条记录")
			}else {
				//修改记录
				var id = $fxk.val();
				//根据id查找Activity信息，查找userList，局部刷新
				$.ajax({
					url:"Activity/getUserListAndActivity.do",
					data:{
						"id":id
					},
					type:"get",
					dataType:"json",
					success:function (resp){
						//铺数据：所有者下拉框的user数据
						var html = "<option value=''></option>";
						$.each(resp.userList,function (i,n){
							html += "<option value='"+n.id+"'>"+n.name+"</option>";
						})
						$("#edit-marketActivityOwner").html(html);
						//Activity数据
						//Id放在隐藏域中
						$("#edit-marketActivityId").val(resp.activity.id);

						$("#edit-marketActivityOwner").val(resp.activity.owner);
						$("#edit-marketActivityName").val(resp.activity.name);
						$("#edit-startTime").val(resp.activity.startDate);
						$("#edit-endTime").val(resp.activity.endDate);
						$("#edit-cost").val(resp.activity.cost);
						$("#edit-describe").val(resp.activity.description);
						//显示模态窗口中的数据
						$("#editActivityModal").modal("show");
					}
				})
			}

			//模态窗口中修改按钮的单价事件
			$("#updateBtn").click(function (){
				$.ajax({
					url:"Activity/updateActivity.do",
					data:{
						"id":$.trim($("#edit-marketActivityId").val()),
						"owner":$.trim($("#edit-marketActivityOwner").val()),
						"name":$.trim($("#edit-marketActivityName").val()),
						"startDate":$.trim($("#edit-startTime").val()),
						"endDate":$.trim($("#edit-endTime").val()),
						"cost":$.trim($("#edit-cost").val()),
						"description":$.trim($("#edit-describe").val())
					},
					type:"post",
					dataType: "json",
					success:function (resp){
						if (resp.success){
							//添加成功
							alert("市场活动修改成功");
							//局部刷新市场活动信息
							pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
									,$("#activityPage").bs_pagination('getOption', 'rowsPerPage')
							);
							//清空模态窗口数据,使用dom对象的reset方法
							//$("#createActivityForm")[0].reset();
							//关闭模态窗口
							$("#editActivityModal").modal("hide");
						}
						else {
							//添加失败
							alert("市场活动修改失败");
						}
					}
				})
			})


		})


		//删除Activity 删除按钮的单击事件
		$("#deleteBtn").click(function (){

			//找到选中的复选框
			var $fxk = $("input[name=fxk]:checked");
			if ($fxk.length==0){
				alert("请选择要删除的记录")
			} else {

				if (confirm("确定要删除所选的记录吗？")){
					//alert("123")
					//拼接url带参数
					var param = "";
					for (var i = 0; i < $fxk.length; i++){
						param += "id="+$($fxk[i]).val();
						if (i < $fxk.length-1){
							param += "&";
						}
					}
					//alert(param)
					$.ajax({
						url:"Activity/deleteActivity.do",
						data:param,
						type:"get",
						dataType:"json",
						success:function (resp){
							if (resp.success){
								//删除成功
								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else {
								//删除失败
								alert("删除失败")
							}
						}
					})
				}
			}


		})

		//添加Activity数据，保存按钮单击事件
        $("#saveBtn").click(function (){
            $.ajax({
                url:"Activity/saveActivity.do",
                data:{
                    "owner":$.trim($("#create-marketActivityOwner").val()),
                    "name":$.trim($("#create-marketActivityName").val()),
                    "startDate":$.trim($("#create-startTime").val()),
                    "endDate":$.trim($("#create-endTime").val()),
                    "cost":$.trim($("#create-cost").val()),
                    "description":$.trim($("#create-describe").val())
                },
                type:"post",
                dataType: "json",
                success:function (resp){
                    if (resp.success){
                        //添加成功
                        //alert("市场活动添加成功");
                        //局部刷新市场活动信息
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
								,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

						//清空模态窗口数据,使用dom对象的reset方法
						$("#createActivityForm")[0].reset();
                        //关闭模态窗口
                        $("#createActivityModal").modal("hide");
                    }
                    else {
                        //添加失败
                        alert("市场活动添加失败");
                    }
                }
            })
        })
	});

	//局部刷新Activity列表
	function pageList(pageNo,pageSize){

		//将全选的复选框取消
		$("#qx").prop("checked",false);
		//隐藏域中取值
		$("#search_name").val($.trim($("#hidden_name").val()));
		$("#search_owner").val($.trim($("#hidden_owner").val()));
		$("#search_startDate").val($.trim($("#hidden_startTime").val()));
		$("#search_endDate").val($.trim($("#hidden_endTime").val()));

		$.ajax({
			url:"Activity/pageList.do",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search_name").val()),
				"owner":$.trim($("#search_owner").val()),
				"startDate":$.trim($("#search_startDate").val()),
				"endDate":$.trim($("#search_endDate").val())
				/*
                * name,owner,startDate,endDate使用动态sql查询
                * */
			},
			type:"get",
			dataType:"json",
			success:function (resp){
				/*
                * resp中的数据有：ActivityList，total（记录的总条数）
                * */
				var html = "";
				$.each(resp.dataList,function (i,n){

					html += '<tr class="active">';
					html += '<td><input type="checkbox" name="fxk" value="' + n.id + '"/></td>';
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'Activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
					html += '<td>'+n.owner+'</td>';
					html += '<td>'+n.startDate+'</td>';
					html += '<td>'+n.endDate+'</td>';
					html += '</tr>';

					$("#activityBody").html(html);

					var totalPages = resp.total%pageSize==0?resp.total/pageSize:parseInt(resp.total/pageSize)+1;
					//分页插件
					$("#activityPage").bs_pagination({
						currentPage: pageNo, // 页码
						rowsPerPage: pageSize, // 每页显示的记录条数
						maxRowsPerPage: 20, // 每页最多显示的记录条数
						totalPages: totalPages, // 总页数
						totalRows: resp.total, // 总记录条数

						visiblePageLinks: 3, // 显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						onChangePage : function(event, data){
							pageList(data.currentPage , data.rowsPerPage);
						}
					});

				})
			}
		})

	}
</script>
</head>
<body>

 	<%--隐藏域--%>
	<input type="hidden" id="edit-marketActivityId">
	<input type="hidden" id="hidden_name">
	<input type="hidden" id="hidden_owner">
	<input type="hidden" id="hidden_startDate">
	<input type="hidden" id="hidden_endDate">

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" id="createActivityForm" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label ">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label ">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search_name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search_owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search_startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search_endTime">
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
					<%--
					data-toggle="modal"表示触发按钮打开一个模态窗口
					data-target="#createActivityModal"表示打开模态窗口的名字
					这两个实现在js代码中
					--%>
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">

				<div id="activityPage">

				</div>
				<%--<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>--%>

			</div>
			
		</div>
		
	</div>
</body>
</html>