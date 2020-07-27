<script>
    $('button').click(function () {
        const btn = $(this);
        setTimeout(function () {
            btn.prop('disabled', true);
        }, 0);
    });
</script>

</body>
</html>