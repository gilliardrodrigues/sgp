window.onload = async () => {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const id = urlParams.get("id");

	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this, id);
	});

	const objeto = await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/produtos/${id}").then(response => response.json());
	console.log(objeto);
	document.querySelector(".tipo").value = objeto.tipo;
	atualizarVisibilidade(document.querySelector(".tipo"));
	document.querySelector(".valor input").value = objeto.valor;

	document.querySelector(".modelo input").value = objeto.modelo ? objeto.modelo : "";
	document.querySelector(".curso select").value = objeto.curso ? objeto.curso : "";
	document.querySelector(".tamanho select").value = objeto.tamanho ? objeto.tamanho : "";
	document.querySelector(".cor select").value = objeto.cor ? objeto.cor : "";
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

	editarProduto(headers, jsonFormData, id);
}
async function editarProduto(headers, jsonFormData, id) {
	let endpoint;
	if (jsonFormData.tipo === "Camisa") {
		endpoint = "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/produtos/admin/camisas";
	} else if (jsonFormData.tipo === "Caneca") {
		endpoint = "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/produtos/admin/canecas";
	} else if (jsonFormData.tipo === "Tirante") {
		endpoint = "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/produtos/admin/tirantes";
	}
	console.log({
		url: `${endpoint}/${id}`,
		method: "PUT",
		headers,
		body: JSON.stringify(jsonFormData),
	});

	await fetch(`${endpoint}/${id}`, {
		method: "PUT",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(() => (location.href = "../produtos/index.html"));
}

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}

function atualizarVisibilidade(input) {
	if (["Caneca", "Tirante"].indexOf(input.value) !== -1) {
		document.querySelector(".tamanho").style.display = "none";
		document.querySelector(".cor").style.display = "none";
		document.querySelector(".curso").style.display = "none";
		document.querySelector(".modelo").style.display = "";
	} else if (input.value === "Camisa") {
		document.querySelector(".modelo").style.display = "none";
		document.querySelector(".tamanho").style.display = "";
		document.querySelector(".cor").style.display = "";
		document.querySelector(".curso").style.display = "";
	} else {
		document.querySelector(".tamanho").style.display = "none";
		document.querySelector(".cor").style.display = "none";
		document.querySelector(".curso").style.display = "none";
		document.querySelector(".modelo").style.display = "none";
	}
}
