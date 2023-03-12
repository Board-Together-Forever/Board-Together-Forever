function editField(field) {
    // Hide the label and show the input field
    let label = document.getElementById(field);
    let fieldInput = document.getElementById(field + "Field");
    label.style.display = "none";
    fieldInput.style.display = "inline-block";
    fieldInput.value = label.innerHTML;

    // Hide the Edit button and show the Save Changes button
    let editButton = label.nextElementSibling;
    let submitButton = document.getElementById("submitButton");
    editButton.style.display = "none";
    submitButton.style.display = "inline-block";
}