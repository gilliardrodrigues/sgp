window.onload = async () => {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const pedidoId = urlParams.get("id");

	const mainTitle = document.querySelector(".main-title");
	mainTitle.innerHTML += `#${pedidoId}`;

	const produtos = await getProdutos(pedidoId);
	console.log({
		produtos: produtos,
	});
	const content = document.querySelector(".produtosDoPedido");
	mostrarProdutos(produtos, content);

	const produtosProntaEntrega = await getProdutosProntaEntrega();
	console.log({
		produtosProntaEntrega: produtosProntaEntrega,
	});

	const divProntaEntrega = document.querySelector(".produtosProntaEntrega");
	mostrarProdutos(produtosProntaEntrega, divProntaEntrega, true, pedidoId);

	const addNovoProduto = document.querySelector(".button-add-novo-produto");
	addNovoProduto.addEventListener("click", () => {
		location.href = `../addProdutoPedido/index.html?id=${pedidoId}`;
	});

	const finalizarPedidoBtn = document.querySelector(".button-finalizar-pedido");
	finalizarPedidoBtn.addEventListener("click", () => {
		location.href = `../homeCliente/index.html`;
	});
};

function mostrarProdutos(produtos, htmlElement, isInventory, pedidoId) {
	produtos.forEach(produto => {
		const produtoHTML = montarHTMLProduto(produto);
		if (isInventory) {
			console.log(produto.id);
			produtoHTML.innerHTML += `<div class="column w15 last-column"><button onclick="adicionarAoPedido(${produto.id}, ${pedidoId})" style="border: 0; background: transparent"><a ><p>Adicionar</p></a></button></div>`;
		} else {
			produtoHTML.innerHTML += `<div class="column delete-button w15 last-column"><button onclick="removerProduto(${produto.id})" style="border: 0; background: transparent"><img src="../../static/img/trash-icon.png" width="20px" alt="submit"/></button></div>`;
		}

		htmlElement.append(produtoHTML);
	}, []);
}

async function getProdutos(id) {
	const produto = await fetch(`http://localhost:8080/produtos/filtro/pedido/${id}`).then(response => response.json());

	return produto;
}

async function getProdutosProntaEntrega() {
	return await fetch(`http://localhost:8080/produtos/inventario`).then(response => response.json());
}

function montarHTMLProduto(produto) {
	const fieldsOrder = ["tipo", "id", "valor", "prontaEntrega", "modelo", "curso", "tamanho", "cor"];

	let tabela = document.createElement("div");
	tabela.className = "tabela";

	fieldsOrder.forEach((field, index) => {
		let text = produto[field];

		const col = document.createElement("div");
		col.classList.add("column");
		if (field === "curso") col.classList.add("w15");
		else col.classList.add("w10");
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

		if (["entregue", "chegou", "prontaEntrega"].indexOf(field) !== -1) {
			item = formatBool(text);
			col.appendChild(item);
		} else {
			p.textContent = text;

			col.appendChild(p);
		}

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

async function adicionarAoPedido(id, pedidoId) {
	const headers = {
		"Content-Type": "application/json",
	};
	console.log({
		url: `http://localhost:8080/produtos/inventario/${id}`,
		method: "PUT",
		headers,
		body: { id: pedidoId },
	});
	await fetch(`http://localhost:8080/produtos/inventario/${id}`, {
		method: "PUT",
		headers,
		body: JSON.stringify({ id: pedidoId }),
	});
}

async function removerProduto(id) {
	await fetch(`http://localhost:8080/produtos/admin/${id}`, {
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

	await fetch(`http://localhost:8080/produto/${id}`, {
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
