<#include "../include/header.ftl">

<div class="mx-auto mt-5 col-lg-4 py-3 px-5 rounded bg-light">

    <#list bannerTypes as bannerType>
        <div class="my-5">
            <a href="/admin/banners/edit/${bannerType.bannerTypeValue}" class="h2">${bannerType.bannerTypeName}</a>
        </div>
    </#list>
</div>

<#include "../include/footer.ftl">