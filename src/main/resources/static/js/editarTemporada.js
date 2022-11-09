// import moment from moment
window.onload = async () => {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const id = urlParams.get("id");

	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this, id);
	});

	//const situacao = ["AguardandoPagamento", "Confirmado", "ParcialmenteEntregue", "Entregue"];

	const temporada = await fetch(`http://localhost:8080/temporadas/${id}`).then(response => response.json());
	console.log(temporada);
	document.querySelector(".descricao").value = temporada.descricao;
	document.querySelector(".inicio").value = new Date(temporada.dataInicio).toLocaleDateString();
	document.querySelector(".termino").value = temporada.dataFim ? new Date(temporada.dataFim).toLocaleDateString() : "";
	// document.querySelector(".tempo-entrega").value = temporada.tempoEntregaEmDias;

	temporada.catalogo.forEach(produto => {
		if (produto === "Caneca") {
			document.querySelector(".caneca").checked = true;
		} else if (produto === "Tirante") {
			document.querySelector(".tirante").checked = true;
		} else if (produto === "Camisa") {
			document.querySelector(".camisa").checked = true;
		}
	});
};

async function submitForm(e, form, id) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);

	editarTemporada(headers, jsonFormData, id);
}
async function editarTemporada(headers, jsonFormData, id) {
	console.log({
		url: `http://localhost:8080/temporadas/${id}`,
		method: "PUT",
		headers,
		body: JSON.stringify(jsonFormData),
	});
	await fetch(`http://localhost:8080/temporadas/${id}`, {
		method: "PUT",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(() => (location.href = "../temporadas/index.html"));
}

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}
