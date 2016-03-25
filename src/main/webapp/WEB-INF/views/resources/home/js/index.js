var pos=0;
var cnum=0;
var trString="";

String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
	}

function changeSepPos(pos){
	document.getElementById("sepPos").innerHTML=pos.innerHTML;
}

$(function(){
	$("#addConstraint").click(function(){
		if($("#cName").val()==""){
			alert("请输入约束名称");
			return;
		}
		if($("#cLabel").val()==""&&(!$("#maintitle").is(":checked"))){
			alert("请输入约束标记");
			return;
		}
		cnum=cnum+1;
		var label="";
		if($("#maintitle").is(":checked")){
			label="{s}";
			var divStart="<div class='Cline'>";
			var cancelButton="<button type='button' class='clauseButton cancelButton'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></button>";
			var constraint="<p class='clause'>&lt"+$("#cName").val()+"&gt"+"undefined"+label+"</p>";
			$(".constraint").append(divStart+constraint+cancelButton+"</div>");
			$("#maintitle").removeAttr("checked");
		}
		else if($("#onetime").is(":checked")){
			label="{o}";
			var divStart="<div class='Cline'>";
			var cancelButton="<button type='button' class='clauseButton cancelButton'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></button>";
			var constraint="<p class='clause'>&lt"+$("#cName").val()+"&gt"+$("#cLabel").val()+label+"</p>";
			$(".constraint").append(divStart+constraint+cancelButton+"</div>");
			$("#onetime").removeAttr("checked");
		}
		else if($("#repeat").is(":checked")){
			label="{r}";
			var divStart="<div class='Cline'>";
			var upButton="<button type='button' class='clauseButton upButton'><span class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span></button>";
			var downButton="<button type='button' class='clauseButton downButton'><span class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span></button>";
			var cancelButton="<button type='button' class='clauseButton cancelButton'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></button>";
			var constraint="<p class='clause'>&lt"+$("#cName").val()+"&gt"+$("#cLabel").val()+label+"</p>";
			$(".constraint").append(divStart+constraint+cancelButton+downButton+upButton+"</div>");
			$("#repeat").removeAttr("checked");
		}
		else{
			var divStart="<div class='Cline'>";
			var upButton="<button type='button' class='clauseButton upButton'><span class='glyphicon glyphicon-chevron-up' aria-hidden='true'></span></button>";
			var downButton="<button type='button' class='clauseButton downButton'><span class='glyphicon glyphicon-chevron-down' aria-hidden='true'></span></button>";
			var cancelButton="<button type='button' class='clauseButton cancelButton'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></button>";
			var constraint="<p class='clause'>&lt"+$("#cName").val()+"&gt"+$("#cLabel").val()+label+"</p>";
			$(".constraint").append(divStart+constraint+cancelButton+downButton+upButton+"</div>");
			$(".upButton").unbind("click").click(function(){
				if($(this).parent().prev().hasClass("Cline")){
					var temp=$(this).parent().find("p").html();
					var uptemp=$(this).parent().prev().find("p").html();
					if(!uptemp.endWith("{s}")&!uptemp.endWith("{o}")){
						$(this).parent().find("p").html(uptemp);
						$(this).parent().prev().find("p").html(temp);
					}
				}		
			})
			$(".downButton").unbind("click").click(function(){
				if($(this).parent().next().hasClass("Cline")){
					var temp=$(this).parent().find("p").html();
					$(this).parent().find("p").html($(this).parent().next().find("p").html());
					$(this).parent().next().find("p").html(temp);
				}		
			})
		}
		$(".cancelButton").unbind("click").click(function(){
			$(this).parent().remove();
			cnum=cnum-1;
			if(cnum<1)
				$("#maintitle").removeAttr("disabled");
		})
		$("#cName").val("");
		$("#cLabel").val("");
		if(cnum>=1)
			$("#maintitle").attr("disabled","disabled");
	});
})


$(function(){
	$("#deal").click(function(){
		var posSen=document.getElementById("sepPos").innerHTML;
		if(posSen=="分隔符位置：前置")
			pos=1;
		else if(posSen=="分隔符位置：后置")
			pos=2;
		var fullRegex="";
		$(".Cline p").each(function(){
			fullRegex=fullRegex+$(this).text();
		})
		if(fullRegex==""){
			alert("请输入至少一条正则规则");
			return;
		}
		//dealRegex($("#inputText").val(),regex,pos);
		var data={"inputtext":$("#inputText").val(),"regex":fullRegex,"pos":pos};
		$.ajax({
			  type: 'POST',
			  url: "./dealRegex",
			  data: data,
			  dataType: "text",
			  success: function (data) {   
                 var part=data.split("\n");
                 var title=part[0].split(",");
                 var i=0;
                 var thString="<thead><tr>";
                 for(i=0;i<title.length;i++){
                	thString=thString+"<th>"+title[i]+"</th>";
                 }
                 thString=thString+"</tr></thead>";
                 i=1;
                 var j=0;
                 var tdString="<tbody>";
                 for(i=1;i<part.length;i++){
                	 var con=part[i].split(",");
                	 if($.trim(con)!=""){
                		 tdString=tdString+"<tr>";
	                	 for(j=0;j<con.length;j++){
	                		 tdString=tdString+"<td>"+con[j]+"</td>";
	                	 }
	                	 tdString=tdString+"</tr>";
                	 }
                 }
                 tdString=tdString+"</tbody>"
                 var CSVButton="<button type='button' class='btn btn-info' onClick='window.location.href=\"resources/fileDealed/dealedFile.csv\"'>导出为CSV格式</button>"
                 //var CSVButton="<button type='button' class='btn btn-info' onClick='$(\"#outTable\").tableExport({type:\"csv\",escape:\"false\",consoleLog:\"true\"});'>导出为CSV格式（请修改文件名后缀为.csv）</button>"
                 var addString="<table class='table table-hover' id='outTable'>"
                	 +thString
                	 +tdString
                	 +"</table>"
                	 +CSVButton;
                 
                 $(".regexResult").html(addString);
			  },   
			  error: function (XMLHttpRequest, textStatus, errorThrown) {   
				  alert(XMLHttpRequest.status);
				  alert(XMLHttpRequest.readyState);
				  alert(textStatus);
			  } 
		});
	});
})


$(function(){
	$("#introButton").unbind("click").click(function(){
		if($(".introduction").is(":hidden")){
			$(".introduction").slideDown("slow");
		}
		else{
			$(".introduction").hide("slow");
		}
	});

})


$("#upfile").fileinput({
	language: 'zh',
	maxFileCount:4,
	uploadAsync : true,
	allowedFileExtensions: ['txt'],
	browseClass: "btn btn-info",
	uploadLabel: '上传并处理文件',
	uploadUrl : "./uploadFile",
	uploadExtraData: function(){
		var posSen=document.getElementById("sepPos").innerHTML;
		if(posSen=="分隔符位置：前置")
			pos=1;
		else if(posSen=="分隔符位置：后置")
			pos=2;
		var fullRegex="";
		$(".Cline p").each(function(){
			fullRegex=fullRegex+$(this).text();
		});
		if(fullRegex.length==0){
			fullRegex="<主键>undefined{s}";
		}
		return {"regex":fullRegex,"pos":pos};
	},
	error : function(){
		alert("error");
	}
});
$('#upfile').on( "filelock", function(event, filestack,extraData) {
	alert(1);
});
$('#upfile').on( "fileuploaded", function(event, data, previewId, index) {
	var form = data.form, files = data.files, extra = data.extra, response = data.response, reader = data.reader;
	 var part=response.split("\n");
     var title=part[0].split(",");
     var i=0;
     var thString="<thead><tr>";
     alert(2);
     for(i=0;i<title.length;i++){
    	thString=thString+"<th>"+title[i]+"</th>";
     }
     thString=thString+"</tr></thead>";
     i=1;
     var j=0;
     for(i=1;i<part.length;i++){
    	 var con=part[i].split(",");
    	 if($.trim(con)!=""){
    		 trString=trString+"<tr>";
        	 for(j=0;j<con.length;j++){
        		 trString=trString+"<td>"+con[j]+"</td>";
        	 }
        	 trString=trString+"</tr>";
    	 }
     }
    // var CSVButton="<button type='button' class='btn btn-info' onClick='window.location.href=\"resources/fileDealed/dealedFile.csv\"'>导出为CSV格式</button>"
     var CSVButton="<button type='button' class='btn btn-info' onClick='$(\"#outTable\").tableExport({type:\"csv\",escape:\"false\",consoleLog:\"true\"});'>导出为CSV格式（请修改文件名后缀为.csv）</button>"
     var addString="<table class='table table-hover' id='outTable'>"
    	 +thString
    	 +"<tbody>"
    	 +trString
    	 +"</tbody></table>"
    	 +CSVButton;
     
     $(".regexResult").html(addString);
});


/*
$("#uploadForm").submit(function(event) {
	var formData = new FormData($( "#uploadForm" )[0]);  
	event.preventDefault();
	$.ajax({
		url : "./uploadFile",
		type: 'POST',  
        data: formData,  
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false, 
		contentType : false,
        processData : false,
        success :  function(data){
        	alert(1);
        },
        error : function(){
        	alert(0);
        }
	});
});
*/
