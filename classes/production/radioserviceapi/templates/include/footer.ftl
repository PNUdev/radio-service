<script>
    $('button').click(function () {
        const btn = $(this);
        const form = btn.closest('form');

        if (form[0].checkValidity()) {
            setTimeout(function () {
                btn.prop('disabled', true);
            }, 0);
        }
    });
</script>

</body>
</html>