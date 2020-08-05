<#include "../include/header.ftl">

<table class="table my-5">
    <thead class="thead-dark">
    <tr>
        <th scope="col" colspan="4" class="text-center h3">${dailySchedule.dayOfWeek.nameUkr}</th>
        <th>
            <form action="/admin/schedule/item/new">
                <input type="hidden" name="day" value="${dailySchedule.dayOfWeek.urlValue}">
                <button class="btn btn-lg btn-primary float-right mr-3">Додати</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </th>
    </tr>
    </thead>
    <thead class="thead-light">
    <tr>
        <th scope="col">Назва програми</th>
        <th scope="col">Коментар</th>
        <th scope="col">Початок</th>
        <th scope="col">Кінець</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>

    <#list dailySchedule.scheduleItems as scheduledItem>
        <#assign saveFormId="save-form-for-${scheduledItem.id}">
        <tr id="${scheduledItem.id}">
            <th scope="row">${scheduledItem.programName}</th>
            <td>
                <textarea class="form-control" rows="6" cols="60" name="comment"
                          form="${saveFormId}">${scheduledItem.comment}</textarea>
            </td>
            <td>
                <input class="form-control" type="time" value="${scheduledItem.time.startTime}" name="startTime"
                       form="${saveFormId}">
            </td>
            <td>
                <input class="form-control" type="time" value="${scheduledItem.time.endTime}" name="endTime"
                       form="${saveFormId}">
            </td>
            <td>
                <form action="/admin/schedule/item/update/${scheduledItem.id}" id="${saveFormId}" method="POST">
                    <button class="btn btn-primary">Зберегти</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
                <form action="/admin/schedule/item/delete/${scheduledItem.id}">
                    <button class="btn btn-danger">Видалити</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<#if selectedItemId?? >
    <script>

        $(document).ready(function () {

            const selectedElement = $("#${selectedItemId}");
            const animationDuration = 1000;

            $('html, body').animate({
                scrollTop: selectedElement.offset().top
            }, animationDuration);

            selectedElement.stop().delay(animationDuration + 200).fadeTo(100, 0.3, function () {
                $(this).fadeTo(500, 1.0);
            });

        });

    </script>

</#if>

<#include "../include/toastr.ftl">
<#include "../include/footer.ftl">