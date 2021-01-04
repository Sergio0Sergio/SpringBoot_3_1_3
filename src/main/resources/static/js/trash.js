user_data = '';
user_data += "<button id=\"buttonUserEdit" + value.id + "\" type=\"button\" class=\"btn btn-primary btn-sm\" data-bs-toggle=\"modal\"\n" +
    "        data-bs-target=\"#userEditModal\" value=\"Edit user\">\n" +
    "    Edit\n" +
    "</button>"

$("#buttonUserEdit").click(function () {
    e.preventDefault();
    alert("button");
});

// result.forEach(writingRows())
// $( "#userTableBody" ).append( "<tr><td>djsafladjf</td><td>asdjlfasdlkjfajf</td></tr>" );
// $("#u")
// function writingRows(){
//
// }