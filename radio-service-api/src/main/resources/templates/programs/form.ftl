<#include "../include/header.ftl">

<#assign formSubmissionUrl = program???then('/admin/programs/update/${program.id}', '/admin/programs/new') >

<div class="row d-flex justify-content-around">
    <div class="col-md-7 mt-5 p-5 rounded bg-light">
        <form method="POST" action="${formSubmissionUrl}">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Назва</span>
                </div>
                <input type="text" class="form-control" name="title" value="${(program.title)!}" required>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Посилання на зображення</span>
                </div>
                <input type="text" class="form-control" name="imageUrl" id="image-url" value="${(program.imageUrl)!}" required>
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">Опис</span>
                </div>
                <textarea class="form-control" name="description" rows="10" required>${(program.description)!}</textarea>
            </div>

            <div class="p-3">
                <#if program??>
                    <div class="row">
                        <div class="pt-3">
                            <button class="btn btn-primary">Оновити</button>
                            <a href="/admin/programs/delete/${program.id}">
                                <div class="btn btn-danger">Видалити</div>
                            </a>
                        </div>
                    </div>
                <#else >
                    <button class="btn btn-primary">Додати програму</button>
                </#if>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

    </div>
    <div class="col-md-4 mt-5 p-5 rounded bg-light">
        <div id="image-preview-block">
            <div class="h4 mb-3">Попередній перегляд зображення</div>
            <img src="" class="mw-100 mh-100" id="image-display" alt="Попередній перегляд зображення">
        </div>
        <div id="image-loading-error">
            <div class="h2">Зображення не знайдено</div>
        </div>
    </div>
</div>

<script>
    let imageUrl = $('#image-url');
    let imageDisplay = $('#image-display');
    let imagePreviewBlock = $('#image-preview-block');
    let imageLoadingErrorBlock = $('#image-loading-error');

    let setImageSrcUrl = function () {
        imageDisplay.attr('src', imageUrl.val())
    };

    $(document).ready(setImageSrcUrl);
    imageUrl.change(setImageSrcUrl);

    imageDisplay
        .on('load', function () {
            imagePreviewBlock.show();
            imageLoadingErrorBlock.hide();
        })
        .on('error', function () {
            imagePreviewBlock.hide();
            imageLoadingErrorBlock.show();
        });
</script>
<#include "../include/footer.ftl">