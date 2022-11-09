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

	const jsonFormData = {
		aluno: buildJsonFormData(form),
	};

	await criarObjeto(headers, jsonFormData);
}

async function criarObjeto(headers, jsonFormData) {
	console.log({
		url: "localhost:8080/pedidos",
		method: "POST",
		headers,
		body: JSON.stringify(jsonFormData),
	});

	await fetch("http://localhost:8080/pedidos", {
		method: "POST",
		headers,
		body: JSON.stringify(jsonFormData),
	})
		.then(response => response.json())
		.then(objeto => {
			console.log(objeto);
			location.href = `../pedidoCliente/index.html?id=${objeto.id}`;
		});
}

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}
