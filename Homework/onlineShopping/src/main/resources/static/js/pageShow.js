var $ = function(id) {
	return document.getElementById(id);
}

$('plusNum').onclick = function(e) {
	e = window.event || e;
	o = e.srcElement || e.target;
	var num = $('allNum').textContent;
	if (num > 0) {
		num--;
		$('allNum').innerHTML = num;
	} else {
		alert("您没有购买任何商品");
	}
};

$('addNum').onclick = function(e) {
	e = window.event || e;
	o = e.srcElement || e.target;
	var num = $('allNum').textContent;
	num++;
	$('allNum').innerHTML = num;
};

var loading = new Loading();
var layer = new Layer();

$('add').onclick = function(e) {

	e == window.event || e;
	var ele = e.target;
	var id = ele && ele.dataset.id;
	var num = $('allNum').innerHTML;

	if (num <= 0) {
		layer.hide();
		loading.show();
		loading.result('购买数须大于0！');
	} else {
		layer.reset({
			content : '确认加入购物车吗？',
			onconfirm : function() {
				layer.hide();
				loading.show();

				ajax({
					data : {
						id : id,
						num : num
					},
					url : '/api/add',
					success : function(result) {
						loading.result('添加购物车成功');
						// loading.hide();

					},
					error : function(message) {
						loading.result(message || '添加购物车失败');
					}
				});
			}.bind(this)
		}).show();
	}

	return;
};
