(function(w,d,u){

	var $ = function(id){
		return document.getElementById(id);
	}
	
	var loading = new Loading();
	var layer = new Layer();
	$('Account').onclick = function(e){

			layer.reset({
				content:'确认购买吗？',
				onconfirm:function(){
					layer.hide();
					loading.show();
					ajax({
						url : '/api/buy',
						success : function(result) {
							loading.result('购买成功');
							location.href='/account'
						},
						error : function(message) {
							loading.result(message || '购买失败');
						}
					});
				}.bind(this)
			}).show();
			return;
	};
	$('back').onclick = function(){

		location.href = window.history.go(-1);
//		location.href = window.history.back();
	}
})(window,document);