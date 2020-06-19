<#include "../include/header.ftl">
<div class="container">
    <#if programsPage.number lt 0 || programsPage.number gt programsPage.totalPages - 1>
        <h1 class="text-center">Неіснуючий номер сторінки</h1>
        <a href="/admin/programs"><h2 class="text-center">Список програм</h2></a>
    <#else>
        <div class="py-4">
            <a href="/admin/programs/new">
                <div class="btn btn-primary btn-lg btn-block">Додати нову програму</div>
            </a>
        </div>

        <div>
            <#list programsPage.content as program >
                <div class="row my-3 bg-light rounded p-3">
                    <div class="col-md-4">
                        <img src="${program.imageUrl}" alt="Ілюстрація до програми" class="mw-100 mh-100">
                    </div>
                    <div class="col-md-3 h3">${program.title}</div>
                    <div class="col-md-5 d-flex flex-column col-md-5 justify-content-between">
                        <div>
                            <div class="p">${program.description}</div>
                        </div>
                        <div class="ml-auto pt-3">
                            <a href="/admin/programs/edit/${program.id}">
                                <div class="btn btn-primary">Редагувати</div>
                            </a>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
        <#if programsPage.number == 0 && !programsPage.content?has_content>
            <h1 class="text-center">Список програм пустий</h1>
        <#else>
            <nav>
                <ul class="pagination justify-content-center mt-3">
                    <li class="page-item <#if programsPage.number == 0 >disabled</#if>">
                        <a class="page-link" href="?page=${programsPage.number - 1}">Попередня сторінка</a>
                    </li>
                    <#list 1..programsPage.totalPages as pageNumber>
                        <li class="page-item <#if programsPage.number == pageNumber - 1>active</#if>">
                            <a class="page-link" href="?page=${pageNumber - 1}">
                                ${pageNumber}
                            </a>
                        </li>
                    </#list>
                    <li class="page-item <#if programsPage.number == programsPage.totalPages - 1 >disabled</#if>">
                        <a class="page-link" href="?page=${programsPage.number + 1}">Наступна сторінка</a>
                    </li>
            </nav>
        </#if>
    </#if>

</div>

<#include "../include/footer.ftl">