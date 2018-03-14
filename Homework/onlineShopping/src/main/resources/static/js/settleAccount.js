(function(w, d, u) {

	var loading = new Loading();
	var layer = new Layer();
	
	var $ = function(id) {
		return document.getElementById(id);
	}

	$('itemListTable').onclick = function(e){
		var e = arguments[0] || window.event;
		target = e.srcElement ? e.srcElement : e.target;
		target=target.parentElement;
		if(target.nodeName == "SPAN" && target.className == "moreNum"){
			var num = target.parentElement.children[1].textContent;
			var id = target.parentElement.parentElement.parentElement.dataset.id;
			num ++;
//			target.parentElement.children[1].textContent = num;
			updateCartItem(id,num);
		}else if(target.nodeName == "SPAN" && target.className == "lessNum"){
			var num = target.parentElement.children[1].textContent;
			var id = target.parentElement.parentElement.parentElement.dataset.id;
			num --;
			if(num <= 0){
				layer.reset({
					content : '是否删除？',
					onconfirm : function() {
						layer.hide();
						loading.show();
						
						deleteCartItem(id);
					}.bind(this)
				}).show();
			}else{
				updateCartItem(id,num);
			}
		}
		return false;
	};
	

	$('Account').onclick = function(e) {
		var itemListTable = document.getElementById("itemListTable");
		var buyData = [];
		for (var i = 1, rows = itemListTable.rows.length; i < rows-1; i++) {
			var id=itemListTable.rows[i].dataset.id;
			var num=itemListTable.rows[i].cells[2].innerText;


            var length=num.length;
            num=num.substr(1,length-2);

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
		window.history.go(-1); //刷新上一页
//		location.href = window.history.go(-1);
		// location.href = window.history.back();
	}
	
	var updateCartItem=function(id,num)
	{
		var data={id:id,num:num};
//		console.log({id:id,num:num});
		ajax({
		data : data,
		url : '/api/updateItem',
		success : function(result) {
			location.reload();
		},
		error : function(message) {
			loading.result(message || '购物车更新失败');
		}
	});
	}
	
	var deleteCartItem=function(id)
	{
		var data={id:id};
//		console.log({id:id,num:num});
		ajax({
		data : data,
		url : '/api/deleteItem',
		success : function(result) {
			location.reload();
		},
		error : function(message) {
			loading.result(message || '购物车更新失败');
		}});
	}
	
	$('itemListTable').addEventListener('click',function(e){
		var ele = e.target;
		var delId = ele.dataset && ele.dataset.del;
		if(delId){
			layer.reset({
				content : '是否删除？',
				onconfirm : function() {
					layer.hide();
					loading.show();
					
					deleteCartItem(delId);
				}.bind(this)
			}).show();
			return;
		}
	}.bind(this),false);
})(window, document);