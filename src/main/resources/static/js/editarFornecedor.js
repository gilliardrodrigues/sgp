window.onload = async () => {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const id = urlParams.get("id");

	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this, id);
	});

	const fornecedor = await fetch(`http://localhost:8080/fornecedores/${id}`).then(response => response.json());
	document.querySelector(".razao-social").value = fornecedor.razaoSocial;
	document.querySelector(".cnpj").value = fornecedor.cnpj;
	document.querySelector(".email").value = fornecedor.email;
	document.querySelector(".tempo-entrega").value = fornecedor.tempoEntregaEmDias;

	fornecedor.produtosOferecidos.forEach(produto => {
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
	jsonFormData.produtosOferecidos = [];
	if (jsonFormData.caneca) {
		delete jsonFormData.caneca;
		jsonFormData.produtosOferecidos.push("CANECA");
	}
	if (jsonFormData.tirante) {
		delete jsonFormData.tirante;
		jsonFormData.produtosOferecidos.push("TIRANTE");
	}
	if (jsonFormData.camisa) {
		delete jsonFormData.camisa;
		jsonFormData.produtosOferecidos.push("CAMISA");
	}

	editarFornecedor(headers, jsonFormData, id);
}
async function editarFornecedor(headers, jsonFormData, id) {
	await fetch(`http://localhost:8080/fornecedores/${id}`, {
		method: "PUT",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(() => (location.href = "../fornecedores/index.html"));
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
