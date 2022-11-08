window.onload = async () => {
	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this);
	});
};

async function submitForm(e, form) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);

	jsonFormData.produtosOferecidos = [];
	if (jsonFormData.caneca) {
		delete jsonFormData.caneca;
		jsonFormData.produtosOferecidos.push("Caneca");
	}
	if (jsonFormData.tirante) {
		delete jsonFormData.tirante;
		jsonFormData.produtosOferecidos.push("Tirante");
	}
	if (jsonFormData.camisa) {
		delete jsonFormData.camisa;
		jsonFormData.produtosOferecidos.push("Camisa");
	}

	let comentario;
	if (jsonFormData.observacao) {
		comentario = jsonFormData.observacao;
		delete jsonFormData.observacao;
	}

	await criarFornecedor(headers, jsonFormData, comentario);
}

async function criarFornecedor(headers, jsonFormData, comentario) {
	console.log(jsonFormData);
	await fetch("http://localhost:8080/fornecedores", {
		method: "POST",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(response => {
		if (!response.ok) {
			response.json().then(body => {
				alert(body.titulo);
			});
		} else {
			response.json().then(body => {
				criarComentario(headers, body.id, comentario);
			});

			location.href = "../fornecedores/index.html";
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

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}
