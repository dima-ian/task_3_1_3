function deleteUser(id) {
    var res = confirm('Are you sure ??');
    if (res) {
        $.ajax({
            url: '/api/users/' + id,
            method: 'DELETE',
            success: function () {
                getAllUsers();
            },
            error: function (error) {
                alert(error);
            }
        })
    }
}