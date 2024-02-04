function edit_task(task_id) {
    let identifier_delete = "#delete_" + task_id;
    $(identifier_delete).remove();


    let identifier_edit = "#edit_" + task_id;
    let save_tag = "<button id='save_" + task_id + "'>Save</button>";
    $(identifier_edit).html(save_tag);
    let property_save_tag = "update_task(" + task_id + ")";
    $(identifier_edit).attr("onclick", property_save_tag);

    let current_tr_element = $(identifier_edit).parent().parent();
    let children = current_tr_element.children();
    let td_description = children[1];
    td_description.innerHTML = "<input id='input_description_" + task_id + "' type='text' value='" + td_description.innerHTML + "'>"

    let td_status = children[2];
    let status_id = "#select_status_" + task_id
    let status_current_value = td_status.innerHTML;
    td_status.innerHTML = getDropdownstatusHtml(task_id);
    $(status_id).val(status_current_value).change();
}

function getDropdownstatusHtml(task_id) {
    let status_id = "select_status_" + task_id;
    return "<label for='status'></label>"
        + "<select id=" + status_id + " name='status'>"
        + "<option value='IN_PROGRESS'>IN PROGRESS</option>"
        + "<option value='DONE'>DONE</option>"
        + "<option value='PAUSED'>PAUSED</option>"
        + "</select>";
}

function update_task(task_id) {
    let url = getBaseURL() + task_id;

    let val_desc = $("#input_description_" + task_id).val();
    let val_stat = $("#select_status_" + task_id).val();

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": val_desc, "status": val_stat})
    })

    setTimeout( () => {document.location.reload();}, 300)
}

function add_task() {
    let url = getBaseURL();
    let val_desc = $("#description_new").val();
    let val_stat = $("#status_new").val();

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": val_desc, "status": val_stat})
    })

    setTimeout( () => {document.location.reload();}, 300);

}

function delete_func(task_id) {
    let url = getBaseURL()+ "delete?id=" + task_id;

    $.ajax({
        url: url,
        type: 'GET'
    })

    setTimeout( () => {document.location.reload();}, 300)
}

function getBaseURL() {
    let current_path = window.location.href;
    let end_position = current_path.indexOf('?');
    if (end_position < 0) return current_path + "/";
    return current_path.substring(0, end_position).concat('/');
}