window.onload = async () => {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const id = urlParams.get("id");

	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this, id);
	});

	const situacao = ["AguardandoPagamento", "Confirmado", "ParcialmenteEntregue", "Entregue"];

	const pedido = await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/pedidos/${id}").then(response => response.json());
	// document.querySelector(".select-situacao").value = pedido.situacao;
	// document.querySelector(".select-status").value = pedido.statusPagamento;
	// document.querySelector(".previsao-entrega").value = pedido.previsaoEntrega;
	document.querySelector(".valorTotal").value = pedido.valor;
	console.log(pedido.valor)

	// document.querySelector(".tempo-entrega").value = pedido.tempoEntregaEmDias;
	/*
	pedido.produtosOferecidos.forEach(produto => {
		if (produto === "Caneca") {
			document.querySelector(".caneca").checked = true;
		} else if (produto === "Tirante") {
			document.querySelector(".tirante").checked = true;
		} else if (produto === "Camisa") {
			document.querySelector(".camisa").checked = true;
		}
	});*/
};

async function submitForm(e, form, id) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);

	editarPedido(headers, jsonFormData, id);
}
async function editarPedido(headers, jsonFormData, id) {
	console.log({
		url: "http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/pedidos/admin/${id}",
		method: "PUT",
		headers,
		body: JSON.stringify(jsonFormData),
	});
	await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/pedidos/admin/${id}", {
		method: "PUT",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(() => (location.href = "../pedidos/index.html"));
}

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}
