function annotate() {
  if ($("#text").val()) {
    annotate_text();
  } else {
    upload_file_to_annotate();
  }
}

function annotate_text() {
  $.ajax({
    url: "/annotate",
    type: "get",
    data: {
      concept: $("#concept").val(),
      model: $("#model").val(),
      text: $("#text").val()
    },
    success: function (response) {
      show_annotation_results(response);
    },
    error: function (xhr) {
      alert("error")
    }
  });
}

function upload_file_to_annotate() {
  let fd = new FormData();
  let files = $('#file-upload')[0].files[0];
  fd.append('file', files);
  fd.append("model", $("#model").val())
  fd.append("concept", $("#concept").val())

  $.ajax({
    url: "/annotate",
    type: "post",
    data: fd,
    contentType: false,
    processData: false,
    success: function (response) {
      alert('file uploaded: \nMessage: ' + response["message"] + "\nLink: " +  response["link"]);

      $("#resultTable").hide();
      $("#results-text").hide();
      $("#upload-results").show();
      $("#upload-results").html('<h4>File uploaded successfully</h4> <span><b>Message:</b> ' + response["message"] + '</span><br><span><b>Link:</b> ' +  response["link"] + '</span>');
    },
    error: function (xhr) {
      alert("error")
    }
  });
}

function show_annotation_results(response) {
  $("#resultTable").show();
  $("#results-text").show();
  $("#upload-results").hide();
  const text = response["text"];
  $("#results-text").html('<h4>Results for the term <b>' + text + '</b></h4>');
  const annotations = response["annotations"];
  for (let i = 0; i < annotations.length; i++) {
    let annotation = annotations[i];
    $('#resultTable tr:last').after('<tr><td>' + annotation.text + '</td>' +
      '<td>' + annotation.ontology.id + '</td>' +
      '<td>' + annotation.ontology.label + '</td>' +
      '<td>' + annotation.score + '</td>' +
      '<td>' + annotation.source + '</td></tr>');
  }

}