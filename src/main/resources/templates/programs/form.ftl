<#include "../include/header.ftl">

<div class="row d-flex justify-content-around">
    <div class="col-md-6 mt-5 p-5 rounded bg-light">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Назва</span>
            </div>
            <input type="text" class="form-control" value="${(program.title)!}">
        </div>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Посилання на зображення</span>
            </div>
            <input type="text" class="form-control" id="image-url" value="${(program.imageUrl)!}">
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Опис</span>
            </div>
            <textarea class="form-control" rows="10">${(program.description)!}</textarea>
        </div>

        <div class="p-3">
            <#if program??>
                <div class="row">
                    <div class="pt-3">
                        <div class="btn btn-primary">Оновити</div>
                        <div class="btn btn-danger">Видалити</div>
                    </div>
                </div>
            <#else >
                <div class="btn btn-primary">Додати програму</div>
            </#if>
        </div>

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