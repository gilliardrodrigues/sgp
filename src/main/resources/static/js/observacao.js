window.onload = async () => {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const id = urlParams.get("id");

	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this, id);
	});

	const title = document.querySelector(".main-title");
	const fornecedor = await getFornecedor(id);
	title.innerHTML += fornecedor.razaoSocial;

	const observacoes = await getObservacoes(id);
	const content = document.getElementsByClassName("cards")[0];
	observacoes.forEach(observacao => {
		const observacaoHTML = montarHTMLObservacao(observacao);
		content.append(observacaoHTML);
	}, []);
};

async function submitForm(e, form, id) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const comentario = buildJsonFormData(form);

	await criarComentario(headers, id, comentario);

	location.reload();
}

async function criarComentario(headers, fornecedorId, comentario) {
	await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/fornecedores/${fornecedorId}/observacoes", {
		method: "POST",
		headers,
		body: JSON.stringify(comentario),
	});
}

async function getObservacoes(id) {
	return await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/fornecedores/${id}/observacoes").then(response => response.json());
}

async function getFornecedor(id) {
	return await fetch("http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/fornecedores/${id}").then(response => response.json());
}

function montarHTMLObservacao(observacao) {
	let card = document.createElement("div");
	card.className = "card-observacao";

	const p = document.createElement("p");

	p.innerText = observacao.comentario;

	card.appendChild(p);

	return card;
}

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}
