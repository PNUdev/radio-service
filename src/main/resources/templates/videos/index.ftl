<#include "../include/header.ftl">

<div class="container">
    <div class="py-3">
        <a href="/admin/videos/add">
            <div class="btn btn-primary btn-lg btn-block">Додати відео</div>
        </a>
    </div>
    <#list videos as video>
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

                <form action="/admin/videos/changePriority" method="post">

                    <div class="form-row form-inline">
                        <input type="hidden" name="id" value="${video.id}">
                        <input type="hidden" name="title" value="${video.title}">
                        <input type="hidden" name="description" value="${video.description}">
                        <input type="hidden" name="imageUrl" value="${video.imageUrl}">
                        <span class="my-3 ml-3">Приорітет</span>
                        <select class="form-control inline mx-3" name="priority" onchange="this.form.submit()"
                                id="prioritySelect">
                            <#list 1..videos?size as number>
                                <option value="${number}" <#if number == (video?index + 1)>selected</#if>>
                                    ${number}
                                </option>
                            </#list>
                        </select>
                    </div>
                </form>
                <div class="row float-right">
                    <form action="/admin/videos/delete/${video.id}" method="post">
                        <button class="btn btn-danger row mx-4">Видалити</button>
                    </form>
                </div>
            </div>
        </div>
    </#list>
</div>
<#include "../include/footer.ftl">