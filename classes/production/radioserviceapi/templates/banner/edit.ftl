<#include "../include/header.ftl">
<div class="mx-auto mt-5 col-lg-8 py-3 px-5" id="editor-container">

    <div class="mb-3 h3 text-center">
        ${banner.bannerType.bannerTypeName}
    </div>

    <form method="POST" action="/admin/banners/update/${banner.bannerType.bannerTypeValue}">
        <textarea id="editor" name="markdown">${banner.markdown}</textarea>

        <div class="row d-flex justify-content-between px-4 pt-4">
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" id="primary-banner-checkbox"
                       name="show" ${banner.show?string('checked','')}>
                <label class="custom-control-label" for="primary-banner-checkbox">Показувати банер</label>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-primary">Зберегти</button>
        </div>
    </form>
</div>

<link href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
<script>
    new SimpleMDE({element: $("#editor")[0], spellChecker: false});
</script>
<style>
    #editor {
        display: none;
    }

    #editor-container {
        height: 25vh;
    }
</style>

<#include "../include/toastr.ftl">
<#include "../include/footer.ftl">