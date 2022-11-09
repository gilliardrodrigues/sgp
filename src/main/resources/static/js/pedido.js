window.onload = async () => {
	const pedidos = await getPedidos();
	const content = document.getElementsByClassName("content")[0];
	pedidos.forEach(pedido => {
		const pedidoHTML = montarHTMLPedido(pedido);
		content.append(pedidoHTML);
		console.log(pedidoHTML);
	}, []);

};

async function getPedidos() {
	const pedidos = await fetch("http://localhost:8080/pedidos/admin").then(response => response.json());

	return pedidos;
}

function montarHTMLPedido(pedido) {
	const fieldsOrder = [
		"id",
		"data",
		"valor",
		"situacao",
		"statusPagamento",
		"valorPago",
		"temporadaId",
		"aluno",
    "previsaoDeEntrega",
	];

	let tabela = document.createElement("div");
	tabela.className = "tabela";

	fieldsOrder.forEach((field, index) => {
		let text = pedido[field];

		const col = document.createElement("div");
		col.classList.add("column");
		if (field === "temporadaId") col.classList.add("w6");
    else if (field === "id" || field === "valor" || field === "valorPago") col.classList.add("w8");
    else if (field === "previsaoDeEntrega") col.classList.add("w10");
		else if (field === "situacao" || field === "statusPagamento") col.classList.add("w12");
    else if (field === "data" || field === "aluno") col.classList.add("w15");
		if (index == 0) col.classList.add("first-column");

		const p = document.createElement("p");

		text = text != null ? text : "-";
		if (field === "id") text = "#" + text;
    else if (field === "aluno") text = text["nome"];

		let item;
		if (field === "valor" || field === "valorPago")
			text = text.toLocaleString("pt-BR", {
				style: "currency",
				currency: "BRL",
			});

		if (["situacao", "statusPagamento"].indexOf(field) !== -1) {
			item = formatBool(text);
			col.appendChild(item);
		} else {
			p.textContent = text;

			col.appendChild(p);
		}

		tabela.appendChild(col);
	});

	tabela.innerHTML += `<div class="column w6 last-column"> <a href="../editarPedido/index.html?id=${pedido.id}"> <button type="submit" class="edit-button" style="border: 0; background: transparent"> <img src="../../static/img/edit-button.svg" width="20px" alt="submit"/> </div>`

	//tabela.innerHTML += `<div class="column edit-button w5"> <a href="../editarProduto/index.html?id=${produto.id}"> <button type="submit" style="border: 0; background: transparent"> <p>Editar</p></button> </a></div><div class="column delete-button w5 last-column"> <button onclick="removerProduto(${produto.id})" style="border: 0; background: transparent"> <img src="../../static/img/trash-icon.png" width="20px" alt="submit"/> </button></div>`;

	return tabela;
}

function formatBool(text) {
	const div = document.createElement("div");
	const p = document.createElement("p");
	if (text === "Aguardando pagamento...") {
		div.classList.add("aguardando-pagamento");
		text = "Aguardando pagamento";
	} else if (text === "Pago!") {
		div.classList.add("pago");
		text = "Pago";
	} else if (text === "Não pago") {
		div.classList.add("nao-pago");
		text = "Não Pago";
	}  else if (text === "Confirmado") {
		div.classList.add("confirmado");
		text = "Confirmado";
	}

	p.textContent = text;

	div.appendChild(p);

	return div;
}

async function removerPedido(id) {
	await fetch(`http://localhost:8080/pedidos/${id}`, {
		method: "DELETE",
	});
	location.reload();
}

async function submitForm(e, form) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);

	await fetch(`http://localhost:8080/pedidos/${id}`, {
		method: "DELETE",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(response => {
		if (!response.ok) {
			response.json().then(body => alert(body.titulo));
		} else {
			location.href = "homeAdmin/index.html";
		}
	});
}