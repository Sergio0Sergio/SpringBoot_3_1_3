function showUserTable(key, value) {
    var user_data = '';
    user_data += '<tr>'
    user_data += '<td>' + value.id + '</td>'
    user_data += '<td>' + value.username + '</td>'
    user_data += '<td>' + value.password + '</td>'
    user_data += '<td>' + '<table>'
    $(value.roles).each(function (rKey, rValue) {
        user_data += '<tr>'
        user_data += '<td>' + rValue.name + '</td>'
        user_data += '</tr>'
    })
    user_data += '</table>' + '</td>'

    user_data += "<td><button id=\"buttonUserEdit" + value.id + "\" type=\"button\" class=\"btn btn-primary btn-sm\" data-bs-toggle=\"modal\"\n" +
        "        data-bs-target=\"#userEditModal\" value=\"Edit user\">\n" +
        "    Edit\n" +
        "</button></td>"
    user_data += "<td><button id=\"buttonUserDelete" + value.id + "\" type=\"button\" class=\"btn btn-warning btn-sm\" \n" +
        "        value=\""+value.id+"\">\n" +
        "    Delete\n" +
        "</button></td>"


    user_data += '</tr>'

    $('#userTableBody').append(user_data);
}

function showroles(fieldid) {
    $.ajax({
        url: '/api/roles/',
        method: 'get',
        dataType: 'json',
        contentType: "application/json",
        success: function (result) {
            $(fieldid).empty();
            $.each(result, function (roleKey, roleValue) {
                console.log(roleValue.name + ' ' + roleValue.id);
                var option = new Option(roleValue.name, roleValue.name);
                $(fieldid).append(option);
            });

        }
    })
}

$(document).ready(function () {


    let endpoint = '/api/users'
    $.ajax({
        url: endpoint,
        method: 'get',
        dataType: 'json',
        contentType: "application/json",

        success: function (result) {


            $(result).each(function (key, value) {
                showUserTable(key, value);
                $("#buttonUserEdit" + value.id).click(function () {

                    $.ajax({
                        url: '/api/users/' + value.id,
                        method: 'get',
                        dataType: 'json',
                        contentType: "application/json",
                        success: function (result) {
                            $.each(result, function (k, v) {
                                $("input[name='" + k + "']", '#userEditForm').val(v);
                                console.log(k + " " + v);
                            });

                        }
                    })
                    showroles($("#roles"));
                })

                $("#buttonUserDelete"+value.id).click(function () {

                    $.ajax({
                        url: "/api/users/"+value.id,
                        async: true,
                        // dataType: 'json',
                        //contentType: "application/json",
                        type: "DELETE",

                        success: function (result) {
                            $('#userTableBody').empty();
                            $(result).each(function (key, value) {

                                showUserTable(key, value);
                            })

                        }
                    })

                });
            })


        }
    })

    $("#ed_submit").on('click', function () {

        $.ajax({
            url: '/api/users',
            async: true,
            // dataType: 'json',
            contentType: "application/json",
            type: "PUT",
            data:
                JSON.stringify({
                    id: jQuery('#id').val(),
                    username: jQuery('#username').val(),
                    password: jQuery('#password').val(),
                    roles: jQuery('#roles').val()

                }),
            success: function (result) {
                $('#userTableBody').empty();
                $(result).each(function (key, value) {

                    showUserTable(key, value);
                })

            }
        })

        $('#userEditModal').modal('hide');
    });

    $("#create-submit").on('click', function () {

        $.ajax({
            url: '/api/users',
            async: true,
            // dataType: 'json',
            contentType: "application/json",
            type: "POST",
            data:
                JSON.stringify({
                    id: jQuery('#id').val(),
                    username: jQuery('#c_username').val(),
                    password: jQuery('#c_password').val(),
                    roles: jQuery('#c_roles').val()

                }),
            success: function (result) {
                $('#userTableBody').empty();
                $(result).each(function (key, value) {

                    showUserTable(key, value);
                })
            }
        })
    });

    let navList = $("#nav-list");
    let navCreate = $("#nav-create");
    let pageList = $("#page-list");
    let pageCreate = $("#page-create");

    pageList.show();
    $("#caption-admin").show();
    navList.click(() => {
        navCreate.removeClass('active');
        navList.addClass('active');
        pageCreate.hide();
        pageList.show();
        $("#caption-admin").show();
        $("#caption-create").hide();
    });
    navCreate.click(() => {
        navList.removeClass('active');
        navCreate.addClass('active');
        pageCreate.show();
        pageList.hide();
        $("#caption-admin").hide();
        $("#caption-create").show();
        showroles($("#c_roles"));
    });


});



