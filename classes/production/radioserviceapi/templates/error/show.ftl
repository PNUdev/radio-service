<#if noHeader?? && noHeader>
    <#include "../include/coreDependencies.ftl">
<#else>
    <#include "../include/header.ftl">
</#if>

<div class="w-100 mt-5 d-flex justify-content-center">
    <div class="jumbotron my-auto">
        <div class="m-a text-center h5">${message}</div>
    </div>
</div>

<#include "../include/footer.ftl">