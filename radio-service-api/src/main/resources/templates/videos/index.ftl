<#include "../include/header.ftl">

<div class="container">
    <div class="py-4">
        <form method="POST" action="/admin/videos/clear-recent-videos-cache">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-primary btn-lg btn-block">Очистити кеш нещодавніх відео</button>
        </form>
    </div>
    <div class="pb-4">
        <a href="/admin/videos/new">
            <div class="btn btn-primary btn-lg btn-block">Додати відео</div>
        </a>
    </div>

    <#if videosPage.content?has_content>
        <form action="/admin/videos">
            <div class="form-row my-3">
                <div class="col-md-4">
                    <input class="form-control py-1 lime-border my-2" name="q" type="text" placeholder="Пошук"
                           aria-label="Пошук">
                </div>
                <div class="col-auto">
                    <button class="btn btn-primary form-control my-2">Шукати</button>
                </div>
            </div>
        </form>
    </#if>

    <div>
        <#if searchKeyword?? >
            <div class="row mt-5 d-flex justify-content-around">
                <div class="h4 text-center">
                    Результати пошуку: <b>${searchKeyword}</b>
                </div>
                <a href="/admin/videos" class="h3">Всі відео</a>
            </div>
        </#if>
        <#list videosPage.content as video>
            <div class="row w-80 my-3 p-4 bg-light">
                <div class="col">
                    <iframe
                            src="https://www.youtube.com/embed/${video.id}">
                    </iframe>
                </div>

                <div class="col-8">
                    <div class="row">
                        <strong>${video.title}</strong>
                    </div>

                    <form action="/admin/videos/updatePriority/${video.id}" method="post">
                        <div class="form-row form-inline">
                            <span class="my-3 ml-3">Приорітет</span>
                            <select class="form-control inline mx-3" name="newPriority" onchange="this.form.submit()"
                                    id="prioritySelect">
                                <#list 1..maxPriority as number>
                                    <option value="${number}" <#if number == (video.priority)>selected</#if>>
                                        ${number}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <div class="row float-right">
                        <a href="/admin/videos/delete/${video.id}">
                            <div class="btn btn-danger row mx-4">Видалити</div>
                        </a>
                    </div>
                </div>
            </div>

        </#list>
    </div>
    <#if videosPage.number == 0 && !videosPage.content?has_content>
        <h1 class="text-center mt-5">
            <#if searchKeyword?? >
                Результатів не знайдено
            <#else >
                Список рекомендованих відео пустий
            </#if>
        </h1>
    <#else>
        <nav>
            <#assign searchQueryParam = searchKeyword???then('&q=${searchKeyword}', '') >
            <ul class="pagination justify-content-center mt-3">
                <li class="page-item <#if videosPage.number == 0 >disabled</#if>">
                    <a class="page-link" href="?page=${videosPage.number - 1}${searchQueryParam}">
                        Попередня сторінка
                    </a>
                </li>
                <#list 1..videosPage.totalPages as pageNumber>
                    <li class="page-item <#if videosPage.number == pageNumber - 1>active</#if>">
                        <a class="page-link" href="?page=${pageNumber - 1}${searchQueryParam}">
                            ${pageNumber}
                        </a>
                    </li>
                </#list>
                <li class="page-item <#if videosPage.number == videosPage.totalPages - 1 >disabled</#if>">
                    <a class="page-link" href="?page=${videosPage.number + 1}${searchQueryParam}">
                        Наступна сторінка
                    </a>
                </li>
        </nav>
    </#if>
</div>

<#include "../include/toastr.ftl">
<#include "../include/footer.ftl">