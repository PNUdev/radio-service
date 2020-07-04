<style>
    #toast-container > div {
        opacity: 1;
    }
</style>
<script>
    <#if message??>
    $(document).ready(function () {
        toastr.success("${message}");
    });
    </#if>
    <#if error??>
    $(document).ready(function () {
        toastr.error("${error}");
    });
    </#if>
</script>