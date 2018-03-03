<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<#assign total = 0>
<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <h2>已添加到购物车的内容</h2>
    </div>
    <#if !itemList?? || !itemList?has_content>
    <div class="n-result">
        <h3>暂无内容！</h3>
    </div>
    <#else>
    <table class="m-table m-table-row n-table g-b3" id="itemListTable">
        <colgroup><col class="img"/><col/><col class="time"/><col class="price"/></colgroup>
        <thead>
            <tr><th>内容图片</th><th>内容名称</th><th>数量</th><th>添加时间</th><th>价格</th></tr>
        </thead>
        <tbody>
            <#list itemList as x>
            <#assign total = total + x.product.price * x.number>
            <tr data-id="${x.id}">
                <td><a href="/show?id=${x.product.id}"><img src="${x.product.image}" alt=""></a></td>
                <td><h4><a href="/show?id=${x.product.id}">${x.product.title}</a></h4></td>
                <td><span class="v-num">${x.number}</span></td>
                <td><span class="v-time">${x.time?string("yyyy-MM-dd HH:mm")}</span></td>
                <td><span class="v-unit">¥</span><span class="value">${x.product.price}</span></td>
            </tr>
            </#list>
            
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4"><div class="total">总计：</div></td>
                <td><span class="v-unit">¥</span><span class="value">${total}</span></td>
            </tr>
        </tfoot>
    </table>
    <div id="act-btn"><button class="u-btn u-btn-primary" id="back">退出</button>
 	<button class="u-btn u-btn-primary" id="Account">购买</button></div>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="/js/global.js"></script>
<script type="text/javascript" src="/js/settleAccount.js"></script>
</body>
</html>