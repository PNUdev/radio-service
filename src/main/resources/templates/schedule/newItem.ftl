<#include "../include/header.ftl">

<div class="col-md-7 mt-5 px-5 pb-2 pt-4 rounded bg-light mx-auto">
    <form method="POST" action="/admin/schedule/item/new">
        <div class="input-group mb-3">
            <select class="select-program col-md-12" name="programId" required>
                <option value="" disabled selected>Виберіть програму</option>
                <#list programs as program>
                <option value="${program.id}">${program.title}</option>
                </#list>
            </select>
        </div>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Коментар</span>
            </div>
            <textarea class="form-control" name="comment" rows="10"></textarea>
        </div>

        <div class="row">
            <div class="input-group col-md-5 my-2">
                <div class="input-group-prepend">
                    <span class="input-group-text">Початок</span>
                </div>
                <input type="time" class="form-control" name="startTime" required>
            </div>
            <div class="input-group col-md-5 my-2">
                <div class="input-group-prepend">
                    <span class="input-group-text">Кінець</span>
                </div>
                <input type="time" class="form-control" name="endTime" required>
            </div>
        </div>

        <div class="my-4">
            <button class="btn btn-primary">Додати</button>
        </div>
    </form>

</div>

<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/css/select2.min.css" rel="stylesheet"/>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/js/select2.min.js"></script>
<script>
    $(document).ready(function () {
        $('.select-program').select2();
    });
</script>

<#include "../include/footer.ftl">