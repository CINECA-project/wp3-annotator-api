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

function show_annotation_results(response) {
  const text = response["text"];
  $("#results_text").text(text)
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