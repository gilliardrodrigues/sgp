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

	const temporada = await fetch(`http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/temporadas/${id}`).then(response => response.json());
	console.log({ temporada: temporada });
	document.querySelector(".descricao").value = temporada.descricao;
	document.querySelector(".inicio").value = new Date(temporada.dataInicio).toLocaleDateString();
	console.log({
		a: temporada.catalogo.Caneca,
	});
	document.querySelector(".caneca").disabled = temporada.catalogo.Caneca ? "disabled" : "";
	document.querySelector(".tirante").disabled = temporada.catalogo.Tirante ? "disabled" : "";
	document.querySelector(".camisa").disabled = temporada.catalogo.Camisa ? "disabled" : "";
	document.querySelector(".caneca").checked = temporada.catalogo.Caneca ? true : false;
	document.querySelector(".tirante").checked = temporada.catalogo.Tirante ? true : false;
	document.querySelector(".camisa").checked = temporada.catalogo.Camisa ? true : false;
	document.querySelector(".valorCaneca").value = temporada.catalogo.Caneca ? temporada.catalogo.Caneca : "";
	document.querySelector(".valorTirante").value = temporada.catalogo.Tirante ? temporada.catalogo.Tirante : "";
	document.querySelector(".valorCamisa").value = temporada.catalogo.Camisa ? temporada.catalogo.Camisa : "";

	document.querySelector(".valorCaneca").readOnly = temporada.catalogo.Caneca ? temporada.catalogo.Caneca : "";
	document.querySelector(".valorTirante").readOnly = temporada.catalogo.Tirante ? temporada.catalogo.Tirante : "";
	document.querySelector(".valorCamisa").readOnly = temporada.catalogo.Camisa ? temporada.catalogo.Camisa : "";

	atualizarVisibilidade();
};

async function submitForm(e, form, id) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);

	const temporadaJson = {};
	temporadaJson.descricao = jsonFormData.descricao;
	temporadaJson.catalogo = {};
	if (jsonFormData.valorCaneca) {
		temporadaJson.catalogo.Caneca = jsonFormData.valorCaneca;
	}
	if (jsonFormData.valorTirante) {
		temporadaJson.catalogo.Tirante = jsonFormData.valorTirante;
	}
	if (jsonFormData.valorCamisa) {
		temporadaJson.catalogo.Camisa = jsonFormData.valorCamisa;
	}

	editarTemporada(headers, temporadaJson, id);
}
async function editarTemporada(headers, jsonFormData) {
	console.log({
		url: "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/temporadas",
		method: "PUT",
		headers,
		body: JSON.stringify(jsonFormData),
	});
	await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/temporadas", {
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

function atualizarVisibilidade() {
	const caneca = document.querySelector(".caneca");
	const tirante = document.querySelector(".tirante");
	const camisa = document.querySelector(".camisa");

	console.log(caneca);
	console.log(caneca.checked);
	document.querySelector(".valorCaneca").style.display = caneca.checked ? "" : "none";
	document.querySelector(".valorTirante").style.display = tirante.checked ? "" : "none";
	document.querySelector(".valorCamisa").style.display = camisa.checked ? "" : "none";
}
