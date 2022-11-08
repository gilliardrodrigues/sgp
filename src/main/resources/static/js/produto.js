window.onload = async () => {
	const produtos = await getProdutos();
	const content = document.getElementsByClassName("content")[0];

	produtos.forEach(produto => {
		const produtoHTML = montarHTMLProduto(produto);
		content.append(produtoHTML);
	}, []);

	const deleteForm = document.querySelector(".delete-button");
	deleteForm.addEventListener("submit", function (e) {
		submitForm(e, this);
	});
};

async function getProdutos() {
	const produto = await fetch("http://localhost:8080/produtos").then(response => response.json());

	return produto;
}

function montarHTMLProduto(produto) {
	const fieldsOrder = [
		"tipo",
		"id",
		"valor",
		"prontaEntrega",
		"modelo",
		"curso",
		"tamanho",
		"cor",
		"chegou",
		"entregue",
	];

	let tabela = document.createElement("div");
	tabela.className = "tabela";

	fieldsOrder.forEach((field, index) => {
		let text = produto[field];

		const col = document.createElement("div");
		col.classList.add("column");
		if (field === "curso") col.classList.add("w15");
		else col.classList.add("w8");
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

	tabela.innerHTML += `<div class="column edit-button w5"> <a href="../editarProduto/index.html?id=${produto.id}"> <button type="submit" style="border: 0; background: transparent"> <p>Editar</p></button> </a></div><div class="column delete-button w5 last-column"> <button onclick="removerProduto(${produto.id})" style="border: 0; background: transparent"> <img src="../../static/img/trash-icon.png" width="20px" alt="submit"/> </button></div>`;

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
