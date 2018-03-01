(function(w, d, u) {

	var $ = function(id) {
		return document.getElementById(id);
	}

	var loading = new Loading();
	var layer = new Layer();
	$('Account').onclick = function(e) {
		var itemListTable = document.getElementById("itemListTable");
		var buyData = [];
		for (var i = 1, rows = itemListTable.rows.length; i < rows-1; i++) {
			var id=itemListTable.rows[i].dataset.id;
			var num=itemListTable.rows[i].cells[2].innerText;
			buyData[i-1]={id:id,num:num};
		}
		
		layer.reset({
			content : '确认购买吗？',
			onconfirm : function() {
				layer.hide();
				loading.show();
				
				var data = JSON.stringify(buyData);
				
				var xhr = new XMLHttpRequest();
				xhr.onreadystatechange = function(){
					 if(xhr.readyState == 4){
			                var status = xhr.status;
			                if(status >= 200 && status < 300 || status == 304){
			                	var json = JSON.parse(xhr.responseText);
			                	if(json && json.code == 200){
			                		loading.result('购买成功',function(){location.href = '/account';});
			                	}else{
			                		loading.result(json.message || '购买失败');
			                	}
			                }else{
			                	loading.result('购买失败');
			                }
			            }
				};
				 xhr.open('post','/api/buy');
				 xhr.setRequestHeader('Content-Type','application/json');
				 xhr.send(data);
				
//				ajax({
//					data : data,
//					url : '/api/buy',
//					success : function(result) {
//						loading.result('购买成功');
//						location.href = '/account'
//					},
//					error : function(message) {
//						loading.result(message || '购买失败');
//					}
//				});
			}.bind(this)
		}).show();
		return;
	};
	$('back').onclick = function() {

		location.href = window.history.go(-1);
		// location.href = window.history.back();
	}
})(window, document);