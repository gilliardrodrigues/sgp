window.onload = async () => {
	document.querySelector(".tamanho").style.display = "none";
	document.querySelector(".cor").style.display = "none";
	document.querySelector(".curso").style.display = "none";
	document.querySelector(".modelo").style.display = "none";

	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this);
	});

	const tipoInput = document.querySelector(".tipo");
	tipoInput.addEventListener("change", function (e) {
		atualizarVisibilidade(e, this);
	});
};

async function submitForm(e, form) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);
	jsonFormData.entregue = false;
	jsonFormData.chegou = false;

	await criarProduto(headers, jsonFormData);
}

async function criarProduto(headers, jsonFormData, comentario) {
	let endpoint;

	if (jsonFormData.tipo === "Camisa") {
		endpoint = "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/produtos/admin/camisas";
	} else if (jsonFormData.tipo === "Caneca") {
		endpoint = "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/produtos/admin/canecas";
	} else if (jsonFormData.tipo === "Tirante") {
		endpoint = "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/produtos/admin/tirantes";
	}

	console.log({ endpoint: endpoint, method: "POST", headers, body: JSON.stringify(jsonFormData) });

	await fetch(endpoint, {
		method: "POST",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(response => {
		if (!response.ok) {
			response.json().then(body => {
				alert(body.titulo);
			});
		} else {
			location.href = "../produtos/index.html";
		}
	});
}

async function criarComentario(headers, fornecedorId, comentario) {
	await fetch(`http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/fornecedores/${fornecedorId}/observacoes`, {
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

function atualizarVisibilidade(e, input) {
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
