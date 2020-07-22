<#include "../include/header.ftl">

<div class="row d-flex justify-content-around">
    <div class="col-md-7 mt-5 px-3 pt-5 rounded bg-light">
        <form method="POST">
            <div class="row d-flex rounded align-items-center image-url-container">
                <div class="input-group my-3 col-lg-10">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Радіо</span>
                    </div>
                    <input type="text" class="form-control image-url" name="radioPage"
                           value="${(imageHrefs.radioPage)!}"
                           required>
                </div>
                <div class="col-md-2">
                    <div class="btn btn-primary show-image-preview">Показати</div>
                </div>
            </div>
            <div class="row d-flex rounded align-items-center image-url-container">
                <div class="input-group my-3 col-lg-10">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Розклад</span>
                    </div>
                    <input type="text" class="form-control image-url" name="schedulePage"
                           value="${(imageHrefs.schedulePage)!}"
                           required>
                </div>
                <div class="col-md-2">
                    <div class="btn btn-primary show-image-preview">Показати</div>
                </div>
            </div>
            <div class="row d-flex rounded align-items-center image-url-container">
                <div class="input-group my-3 col-lg-10">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Програми</span>
                    </div>
                    <input type="text" class="form-control image-url" name="programsPage"
                           value="${(imageHrefs.programsPage)!}"
                           required>
                </div>
                <div class="col-md-2">
                    <div class="btn btn-primary show-image-preview">Показати</div>
                </div>
            </div>
            <div class="row d-flex rounded align-items-center image-url-container">
                <div class="input-group my-3 col-lg-10">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Рекомендовані відео</span>
                    </div>
                    <input type="text" class="form-control image-url" name="recommendedVideosPage"
                           value="${(imageHrefs.recommendedVideosPage)!}" required>
                </div>
                <div class="col-md-2">
                    <div class="btn btn-primary show-image-preview">Показати</div>
                </div>
            </div>
            <div class="row d-flex rounded align-items-center image-url-container">
                <div class="input-group my-3 col-lg-10">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Найновіші відео</span>
                    </div>
                    <input type="text" class="form-control image-url" name="recentVideosPage"
                           value="${(imageHrefs.recentVideosPage)!}"
                           required>
                </div>
                <div class="col-md-2">
                    <div class="btn btn-primary show-image-preview">Показати</div>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-primary mt-3">Зберегти</button>
        </form>
    </div>
    <div class="col-md-4 mt-5 p-5 rounded bg-light">
        <div id="image-preview-block" class="d-none">
            <div class="h4 mb-3">Перегляд зображення</div>
            <img src="" class="mw-100 mh-100" id="image-display" alt="Попередній перегляд зображення">
        </div>
        <div id="image-loading-error" class="d-none">
            <div class="h2">Зображення не знайдено</div>
        </div>
    </div>
</div>

<script>

    let imageContainers = $('.image-url-container');
    let imageDisplay = $('#image-display');
    let imagePreviewBlock = $('#image-preview-block');
    let imageLoadingErrorBlock = $('#image-loading-error');

    $('.show-image-preview').click(function () {
        imageContainers.css("background-color", "white");

        let imageContainer = $(this).parents('.image-url-container');
        imageContainer.css("background-color", "yellow");

        let imageUrl = imageContainer.find('.image-url').val();
        imageDisplay.attr('src', imageUrl);

        imageDisplay
            .on('load', function () {
                imagePreviewBlock.removeClass('d-none');
                imageLoadingErrorBlock.addClass('d-none');
            })
            .on('error', function () {
                imagePreviewBlock.addClass('d-none');
                imageLoadingErrorBlock.removeClass('d-none');
            });
    });

</script>

<#include "../include/toastr.ftl">
<#include "../include/footer.ftl">