<#include "../include/header.ftl">

<table class="table my-5">
    <thead class="thead-dark">
    <tr>
        <th scope="col" colspan="4" class="text-center h3">${dailySchedule.dayOfWeekName}</th>
        <th>
            <a href="/admin/schedule/item/new">
                <div class="btn btn-lg btn-primary float-right mr-3">Додати</div>
            </a>
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
        <tr>
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
                <form action="/update" id="${saveFormId}">
                    <input type="hidden" name="itemId" value="${scheduledItem.id}">
                    <button class="btn btn-primary">Зберегти</button>
                </form>
                <form action="/delete">
                    <input type="hidden" name="itemId" value="${scheduledItem.id}">
                    <button class="btn btn-danger">Видалити</button>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>


<#include "../include/footer.ftl">