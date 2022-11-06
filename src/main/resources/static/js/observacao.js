window.onload = async () => {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id');
    console.log('aa')
	const observacoes = await getObservacoes(id);
    console.log(observacoes);
	const content = document.getElementsByClassName("cards")[0];
	observacoes.forEach(observacao => {
		const observacaoHTML = montarHTMLObservacao(observacao);
		content.append(observacaoHTML);
		console.log(observacaoHTML);
	}, []);

};

async function getObservacoes(id) {
	return await fetch(`http://localhost:8080/fornecedores/${id}/observacoes`).then(response => response.json());;
}

function montarHTMLObservacao(observacao) {
	let card = document.createElement("div");
	card.className = "card-observacao";

		const p = document.createElement("p");

		p.innerText = observacao.comentario;

		card.appendChild(p);

	return card;
}
