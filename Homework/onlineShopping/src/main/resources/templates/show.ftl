<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<div class="g-doc">
    <#if !productView??>
    <div class="n-result">
        <h3>内容不存在！</h3>
    </div>
    <#else>
    <div class="n-show f-cb" id="showContent">
        <div class="img"><img src="${productView.product.image}" alt=""></div>
        <div class="cnt">
            <h2>${productView.product.title}</h2>
            <p class="summary">${productView.product.summary}</p>
            <div class="price">
                <span class="v-unit">¥</span><span class="v-value">${productView.product.price}</span>
            </div>
            <div class="oprt f-cb">
                <#if user?? && user.usertype==1>
                    <#if productView.isBuy?? && productView.isBuy>
                    <span class="u-btn u-btn-primary z-dis">已购买</span>
                    <span class="buyprice">当时购买价格：¥${productView.product.buyPrice}</span>
                    <#else>
                    <button class="u-btn u-btn-primary" data-buy="${productView.product.id}">购 买</button>
                    </#if>
                </#if>
                <#if user?? && user.usertype==0>
                <a href="/edit?id=${productView.product.id}" class="u-btn u-btn-primary">编 辑</a>
                </#if>
            </div>
        </div>
    </div>
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <h2>详细信息</h2>
    </div>
    <div class="n-detail">
        ${productView.product.detail}
    </div>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="/js/global.js"></script>
<script type="text/javascript" src="/js/pageShow.js"></script>
</body>
</html>