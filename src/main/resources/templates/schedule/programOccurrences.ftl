<#include "../include/header.ftl">

<table class="table my-5">
    <thead class="thead-dark">
    <tr>
        <th scope="col" colspan="4" class="text-center h3">${programName}</th>
    </tr>
    </thead>
    <thead class="thead-light">
    <tr>
        <th scope="col">Коментар</th>
        <th scope="col">Початок</th>
        <th scope="col">Кінець</th>
        <th></th>
    </tr>
    </thead>
    <tbody>

    <#if !scheduleItems?has_content>
        <tr>
            <td colspan="4" class="text-center h4">Записів з даною програмою немає у розкладі</td>
        </tr>
    </#if>

    <#list scheduleItems as scheduleItem >
        <tr>
            <td>${scheduleItem.comment}</td>
            <td>${scheduleItem.time.startTime}</td>
            <td>${scheduleItem.time.endTime}</td>
            <td>
                <a href="/admin/schedule/day/${scheduleItem.dayOfWeek.urlValue}#${scheduleItem.id}">
                    <div class="btn btn-primary">Перейти</div>
                </a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<#include "../include/footer.ftl">