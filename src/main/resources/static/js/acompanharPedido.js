window.onload = async () => {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const pedidoId = urlParams.get("id");

	const pedido = await getPedido(pedidoId);

	console.log(pedido);

	document.querySelector(".data p").innerHTML = new Date(pedido.data).toLocaleDateString();

	document.querySelector(".valor p").innerHTML = pedido.valor.toLocaleString("pt-BR", {
		style: "currency",
		currency: "BRL",
	});
	document.querySelector(".situacao p").innerHTML = pedido.situacao;
	document.querySelector(".statusPagamento p").innerHTML = pedido.statusPagamento;
	document.querySelector(".valorPago p").innerHTML = pedido.valorPago.toLocaleString("pt-BR", {
		style: "currency",
		currency: "BRL",
	});
	document.querySelector(".previsaoDeEntrega p").innerHTML = pedido.previsaoDeEntrega ? new Date(pedido.previsaoDeEntrega).toLocaleDateString() : "-";

	const produtos = await getProdutos(pedidoId);

	console.log(produtos);
	const divProdutosPedido = document.querySelector(".produtosPedido");
	mostrarProdutos(produtos, divProdutosPedido);

	// const content = document.getElementsByClassName("content")[0];
	// pedidos.forEach(pedido => {
	// 	const pedidoHTML = montarHTMLPedido(pedido);
	// 	content.append(pedidoHTML);
	// 	console.log(pedidoHTML);
	// }, []);

	const mainTitle = document.querySelector(".main-title");
	mainTitle.innerHTML += `#${pedidoId}`;
};

async function getPedido(pedidoId) {
	const pedidos = await fetch(`http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/pedidos/${pedidoId}`).then(response => response.json());

	return pedidos;
}

async function getProdutos(id) {
	const produto = await fetch(`http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/produtos/filtro/pedido/${id}`).then(response => response.json());

	return produto;
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

		console.log({
			field: field,
		});
		if (field === "valor" || field === "valorPago") {
			text = text.toLocaleString("pt-BR", {
				style: "currency",
				currency: "BRL",
			});
			console.log({
				text: text,
			});
		}

		if (["situacao", "statusPagamento"].indexOf(field) !== -1) {
			item = formatBool(text);
			col.appendChild(item);
		} else {
			p.textContent = text;

			col.appendChild(p);
		}

		tabela.appendChild(col);
	});

	tabela.innerHTML += `<div class="column w6 last-column"> <a href="../editarPedido/index.html?id=${pedido.id}"> <button type="submit" class="edit-button" style="border: 0; background: transparent"> <img src="../../static/img/edit-button.svg" width="20px" alt="submit"/> </div>`;

	//tabela.innerHTML += `<div class="column edit-button w5"> <a href="../editarProduto/index.html?id=${produto.id}"> <button type="submit" style="border: 0; background: transparent"> <p>Editar</p></button> </a></div><div class="column delete-button w5 last-column"> <button onclick="removerProduto(${produto.id})" style="border: 0; background: transparent"> <img src="../../static/img/trash-icon.png" width="20px" alt="submit"/> </button></div>`;

	return tabela;
}

function mostrarProdutos(produtos, htmlElement, isInventory, pedidoId) {
	produtos.forEach(produto => {
		const produtoHTML = montarHTMLProduto(produto);
		// if (isInventory) {
		// 	console.log(produto.id);
		// 	produtoHTML.innerHTML += `<div class="column w15 last-column"><button onclick="adicionarAoPedido(${produto.id}, ${pedidoId})" style="border: 0; background: transparent"><a ><p>Adicionar</p></a></button></div>`;
		// } else {
		// 	produtoHTML.innerHTML += `<div class="column delete-button w15 last-column"><button onclick="removerProduto(${produto.id})" style="border: 0; background: transparent"><img src="../../static/img/trash-icon.png" width="20px" alt="submit"/></button></div>`;
		// }

		htmlElement.append(produtoHTML);
	}, []);
}

function montarHTMLProduto(produto) {
	const fieldsOrder = ["tipo", "valor", "modelo", "curso", "tamanho", "cor", "chegou", "entregue"];

	let tabela = document.createElement("div");
	tabela.className = "tabela";

	fieldsOrder.forEach((field, index) => {
		let text = produto[field];

		const col = document.createElement("div");
		col.classList.add("column");
		if (field === "curso") col.classList.add("w15");
		else col.classList.add("w12");
		if (index == 0) col.classList.add("first-column");

		const p = document.createElement("p");

		text = text != null ? text : "-";
		if (field === "id") text = "#" + text;

		let item;
		if (field === "valor")
			text = text.toLocaleString("pt-BR", {
				style: "currency",
				currency: "BRL",
			});

		if (["entregue", "chegou"].indexOf(field) !== -1) {
			console.log({
				item: item,
				text: text,
			});
			item = formatBool(text);
			col.appendChild(item);
		} else {
			p.textContent = text;

			col.appendChild(p);
		}
		if (index === fieldsOrder.length - 1) col.classList.add("last-column");

		tabela.appendChild(col);
	});

	return tabela;
}

function formatBool(text) {
	const div = document.createElement("div");
	const p = document.createElement("p");
	if (text === true) {
		div.classList.add("pago");
		text = "SIM";
	} else {
		div.classList.add("nao-pago");
		text = "NÃO";
	}

	p.textContent = text;

	div.appendChild(p);

	return div;
}
async function submitForm(e, form) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);

	await fetch(`http://sgp-dacompsi.us-east-1.elasticbeanstalk.com/pedidos/${id}`, {
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
