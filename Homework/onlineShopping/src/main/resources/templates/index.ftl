<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <div class="tab">
            <ul>
                <li <#if listType != 1>class="z-sel"</#if> ><a href="/">所有内容</a></li>
                <#if user?? && user.usertype == 1><li <#if listType == 1>class="z-sel"</#if> ><a href="/?type=1">未购买的内容</a></li></#if>
                <#if user?? && user.usertype == 0><li <#if listType == 1>class="z-sel"</#if> ><a href="/?type=1">您发布的内容</a></li></#if>
            </ul>
        </div>
    </div>
    <#if !productViewList?? || !productViewList?has_content>
    <div class="n-result">
        <h3>暂无内容！</h3>
    </div>
    <#else>
    <div class="n-plist">
        <ul class="f-cb" id="plist">
        <#-- <#if user?? && user.usertype == 1 && listType == 1>
            <#list productViewList as x>
                <#if !x.isBuy>
                <li id="p-${x.product.id}">
                    <a href="/show?id=${x.product.id}" class="link">
                        <div class="img"><img src="${x.product.image}" alt="${x.product.title}"></div>
                        <h3>${x.product.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.product.price}</span></div>
                    </a>
                </li>
                </#if>
            </#list>
        <#else>
            <#list productViewList as x>
                <li id="p-${x.product.id}">
                    <a href="/show?id=${x.product.id}" class="link">
                        <div class="img"><img src="${x.product.image}" alt="${x.product.title}"></div>
                        <h3>${x.product.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.product.price}</span></div>
                        <#if user?? && user.usertype==1 && x.isBuy?? && x.isBuy><span class="had"><b>已购买</b></span></#if>
                        <#if user?? && user.usertype==0 && x.isSell?? && x.isSell><span class="had"><b>已售出</b></span></#if>
                    </a>
                    <#if user?? && user.usertype==0 && x.isSell?? && !x.isSell><span class="u-btn u-btn-normal u-btn-xs del" data-del="${x.id}">删除</span></#if>
                </li>
            </#list>
        </#if> -->
        
            <#list productViewList as x>
	            <#if !(user?? && user.usertype == 1 && listType == 1 && x.isBuy)>
	                <li id="p-${x.product.id}">
	                    <a href="/show?id=${x.product.id}" class="link">
	                        <div class="img"><img src="${x.product.image}" alt="${x.product.title}"></div>
	                        <h3>${x.product.title}</h3>
	                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.product.price}</span></div>
	                        <#if user?? && user.usertype==1 && x.isBuy?? && x.isBuy><span class="had"><b>已购买</b></span></#if>
	                        <#if user?? && user.usertype==0 && x.isSell?? && x.isSell><span class="had"><b>已售出</b></span></#if>
	                    </a>
	                    <#if user?? && user.usertype==0 && x.isSell?? && !x.isSell><span class="u-btn u-btn-normal u-btn-xs del" data-del="${x.product.id}">删除</span></#if>
	                </li>
                </#if>
            </#list>
        </ul>
    </div>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="/js/global.js"></script>
<script type="text/javascript" src="/js/pageIndex.js"></script>
</body>
</html>