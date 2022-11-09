window.onload = async () => {
	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this);
	});

	atualizarVisibilidade();
};

async function submitForm(e, form) {
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

	console.log({
		afeter: temporadaJson,
	});
	await criarTemporada(headers, temporadaJson);
}

async function criarTemporada(headers, jsonFormData, comentario) {
	console.log(jsonFormData);
	await fetch("http://localhost:8080/temporadas", {
		method: "POST",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(response => {
		if (!response.ok) {
			response.json().then(body => alert(body.titulo));
		} else {
			location.href = "../temporadas/index.html";
		}
	});
}

async function criarComentario(headers, fornecedorId, comentario) {
	await fetch(`http://localhost:8080/fornecedores/${fornecedorId}/observacoes`, {
		method: "POST",
		headers,
		body: JSON.stringify({ comentario: comentario }),
	});
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

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}
