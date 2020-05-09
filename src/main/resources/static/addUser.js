$(document).ready(function () {
    $('#addUserButton').click(function () {
        ajaxPost();

    });

    function ajaxPost() {
        var roleValue = "";
        var role = document.getElementById("authUserRole");
        if (role[role.selectedIndex].value === 1) {
            roleValue = "ROLE_ADMIN";
        } else {
            roleValue = "ROLE_USER";
        }
        // PREPARE FORM DATA
        var formData = {
            id: $("#userIdToAdd").val(),
            name: $("#userNameToAdd").val(),
            login: $("#userLoginToAdd").val(),
            password: $("#userPswToAdd").val(),
            roles: [{
                id: role[role.selectedIndex].value,
                role: roleValue,
                authority: roleValue
            }
            ]
        };

        // DO POST
        $.ajax({
            type: "POST",
            contentType: "application/json;",
            url: "/api/user",
            data: JSON.stringify(formData),
            dataType: 'json',
            complete: [
                function () {
                    getAllUsers();
                    $(document).ready(function () {
                        $("#usersTableTab").tab('show');
                        reset();
                    });
                    function reset(){
                        $('#userIdToAdd').val('');
                        $('#userNameToAdd').val('');
                        $('#userLoginToAdd').val('');
                        $('#userPswToAdd').val('');
                    }
                }
            ]
        });
    }
});