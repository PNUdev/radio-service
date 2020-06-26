<#include "../include/header.ftl">

<div class="container d-flex">
    <form method="get" class="w-100 justify-content-center align-items-center" action="/admin/videos/add">
        <div class="input-group mt-3 mb-1 row justify-content-center">
            <span class="input-group-text ">Посилання на YouTube відео</span>
        </div>
        <div class="row w-100 justify-content-center">
            <input type="url" class="form-control w-50" name="link" <#if link??>value="${link}"</#if> required>
        </div>
        <div class="row m-3 justify-content-center align-items-center">
            <button class="btn btn-primary row">Знайти відео</button>
        </div>
    </form>
</div>
<#if video??>
    <div class="container d-flex bg-light">
        <div class="col justify-content-center text-center">
            <iframe width="420" height="315"
                    src="https://www.youtube.com/embed/${video.id}">
            </iframe>
        </div>
        <div class="col">
            <div class="row">
                <h3>${video.title}</h3>
            </div>
            <div class="row justify-content-center text-center">
                <form method="post" action="/admin/videos/add">
                    <input type="hidden" name="id" value="${video.id}">
                    <input type="hidden" name="title" value="${video.title}">
                    <input type="hidden" name="description" value="${video.description}">
                    <button class="btn btn-primary row m-4">Додати це відео</button>
                </form>
            </div>
        </div>
    </div>
</#if>
<#if exception??>
    <div class="container d-flex bg-light justify-content-center text-center">
        <h2>${exception}</h2>
    </div>
</#if>


<#include "../include/footer.ftl">