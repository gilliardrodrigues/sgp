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
	let comentarioId;
	await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/fornecedores", {
		method: "POST",
		headers,
		body: JSON.stringify(jsonFormData),
	})
		.then(response => {
			if (!response.ok) {
				return response.json().then(body => {
					alert(body.titulo);
					throw new Error(body.titulo);
				});
			}

			return response.json();
		})
		.then(body => {
			console.log({
				body: body,
			});
			criarComentario(headers, body.id, comentario).then(response => response.json());
		});
}

async function criarComentario(headers, fornecedorId, comentario) {
	console.log({
		url: "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/fornecedores/${fornecedorId}/observacoes",
		method: "POST",
		headers,
		body: JSON.stringify({ comentario: comentario }),
	});
	await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/fornecedores/${fornecedorId}/observacoes", {
		method: "POST",
		headers,
		body: JSON.stringify({ comentario: comentario }),
	}).then(() => (location.href = "../fornecedores/index.html"));
}

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}
