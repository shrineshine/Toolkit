<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>  
    <head>  
        <meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="${initParam.resources}/resources/exlib/jquery/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="${initParam.resources}/resources/exlib/tableExport/jquery.base64.js"></script>
        <script type="text/javascript" src="${initParam.resources}/resources/exlib/tableExport/tableExport.js"></script>
        <script type="text/javascript" src="${initParam.resources}/resources/exlib/common/ajax.js"></script>
        <script type="text/javascript" src="${initParam.resources}/resources/exlib/fileinput/js/fileinput.js" ></script>
        <script type="text/javascript" src="${initParam.resources}/resources/exlib/bootstrap/js/bootstrap.min.js"></script>
        <script src="${initParam.resources}/resources/exlib/fileinput/js/fileinput_locale_zh.js" ></script>
        <link rel="stylesheet" type=text/css href="${initParam.resources}/resources/exlib/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type=text/css href="${initParam.resources}/resources/home/css/offcanvas.css">
		<link rel="stylesheet" type=text/css href="${initParam.resources}/resources/home/css/index.css">
		<link rel="stylesheet" type=text/css href="${initParam.resources}/resources/exlib/fileinput/css/fileinput.min.css" media="all"/>
        <title>TCM工具</title>  
    </head>  
       
    <body>  
    	<nav class="navbar navbar-static-top navbar-inverse">
			<div class="container">
        		<div class="navbar-header">
          			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            			<span class="sr-only">Toggle navigation</span>
            			<span class="icon-bar"></span>
           				<span class="icon-bar"></span>
          			</button>
          			<a class="navbar-brand" href="#">TCM Calculation Tools</a>
        		</div>
        		<div id="navbar" class="collapse navbar-collapse">
          			<ul class="nav navbar-nav">
            			<li class="active"><a href="#">Home</a></li>
            			<li><a href="http://zcy.ckcest.cn/tcm/">TCM</a></li>
          			</ul>
        		</div><!-- /.nav-collapse -->
      		</div><!-- /.container -->
    	</nav><!-- /.navbar -->
    	<div class="container">
      		<div class="row row-offcanvas row-offcanvas-right">
       			<div class="col-xs-12 col-sm-12">
       				<p class="pull-right visible-xs">
            			<button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          			</p><!-- 超小屏幕下右栏的按钮 -->
          			
                    <div class="toolTitle">
	                    <a id="toolTitle"></a>
	                    <span class="glyphicon glyphicon-triangle-right" style="font-size:30px">
	                    	<span class="titleLogo">基于正则表达式的文档处理工具</span>
	                    </span>
                    </div>
                    
                    <div class="jumbotron">
            			<p>本工具用于处理带标记的文档，根据用户定义的正则约束把文档按表格形式输出</p>
            			<div class="introduction">
            				<p>对于结构整齐的语料：</p>
            				<img src="${initParam.resources}/resources/pic/regex-sample.jpg" style="max-width:100%"></img>
            				</br>
            				<p>对于特殊类型的语料：</p>
            				<p>1. 主标题（无标记）表示允许文档中存在无标记的主标题。默认允许下文存在重复。为了区分主标题，除第一个主标题外，后续主标题前必须存在空行。</p>
            				<img src="${initParam.resources}/resources/pic/regex-sample-2.jpg" style="max-width:100%"></img>
            				<p>2. 允许下文存在重复表示若存在AA*BB#CC#DD*EE#FF#GG#类型的格式，即某一部分段落中会重复出现某标记（例如BB#CC#和EE#FF#GG#），则AA和DD需要勾选该类型指示符。</p>
            				<p>3. 存在重复表示2中的BB#CC#和EE#FF#GG#,只需要输入一次约束，但是要勾选该类型指示符。</p>
            				<img src="${initParam.resources}/resources/pic/regex-sample-3.jpg" style="max-width:100%"></img>
            				</br>
            			</div>
            			<button type="button" class="btn btn-info" id="introButton" >使用说明</button>
                    </div>
                    
                    <div class="toolContent">
                    	<div class="row">
                    		<div class="col-sm-8">
                    			<h2>输入栏:</h2></br> 
			                    <div class="input-group">
			  						<textarea class="form-control"  cols=175 rows=15 placeholder="input text" id="inputText"></textarea>
								</div>
								</br>
								<div class="row">
									<div class="col-sm-12">	
										<form enctype="multipart/form-data" id="uploadForm">
											<input id="upfile" type="file" multiple="multiple" class="file" data-show-preview="false">
										</form>
									</div>
								</div>
							</div>
							<div class="col-sm-4">
								<h2>工具栏:</h2></br>
								<div class="btn-group">
  									<button type="button" class="btn btn-lg btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    									<span id="sepPos">分隔符位置：后置 </span>
    									<span class="caret"></span>
  									</button>
 									<ul class="dropdown-menu">
								    	<li><a href="javascript:void(0)" onclick="changeSepPos(this)">分隔符位置：前置</a></li>
								    	<li><a href="javascript:void(0)" onclick="changeSepPos(this)" >分隔符位置：后置</a></li>
  									</ul>
								</div>
								<div class="constraint"></div>
								<div class="newConstraint">
									<p >创建新的正则约束:</p>
									<div class="input-group">
  										<span class="input-group-addon" >名称</span>
  										<input type="text" class="form-control" id="cName" placeholder="名称" >
  									</div>
  									<div class="input-group">
  										<span class="input-group-addon" >标记</span>
  										<input type="text" class="form-control" id="cLabel" placeholder="标记" >
									</div>
									<div>
										<label class="checkbox-inline">
	 										<input type="checkbox" id="maintitle" value="{s}"> 主标题（无标记）{s}
	 									</label>
 									</div>
 									<div>
	 									<label class="checkbox-inline">
	 										<input type="checkbox" id="onetime" value="{o}"> 允许下文存在重复 {o}
										</label>
									</div>
									<div>
	 									<label class="checkbox-inline">
	 										<input type="checkbox" id="repeat" value="{o}"> 存在重复 {r}
										</label>
									</div>
									<div>
										<button type="button" class="btn btn-info" id="addConstraint">增加</button>
										<button type="button" class="btn btn-info" id="deal">处理</button>						
									</div>
								</div>
							</div>
               			</div>
               			</br><h2>输出栏:</h2></br>
               			<div class="regexResult">
               			</div>
               		</div>
       			</div>
       		</div>
       	</div>
       	 <script type="text/javascript" src="${initParam.resources}/resources/home/js/index.js"></script>
    </body>  

</html>  