window.onload = async () => {
	const temporadaIds = await getTemporadas();
	console.log({
		temporadaIds: temporadaIds,
	});
	let temporadaListElement = document.querySelector(".select-temporada");
	listTemporadas(temporadaIds, temporadaListElement);

	const temporadaAtivaId = await getTemporadaAtiva();
	selectTemporadaAtiva(temporadaAtivaId, temporadaListElement);

	const pedidos = await getPedidos(temporadaAtivaId);
	const pedidosElement = document.getElementsByClassName("content")[0];
	mostrarPedidos(pedidos, pedidosElement);
};

async function getPedidos(temporadaAtivaId) {
	const pedidos = await fetch(`http://localhost:8080/pedidos/admin/${temporadaAtivaId}`).then(response =>
		response.json()
	);
	console.log(pedidos);
	return pedidos;
}

async function getTemporadas() {
	const temporadas = await fetch("http://localhost:8080/temporadas").then(response => response.json());
	console.log({
		temporadas: temporadas,
	});
	return temporadas.map(temporada => temporada.id);
}

async function getTemporadaAtiva() {
	const temporadaAtiva = await fetch("http://localhost:8080/temporadas/ativa").then(response => response.json());
	console.log({
		temporadaAtiva: temporadaAtiva,
	});
	return temporadaAtiva.id;
}

async function removerPedido(id) {
	await fetch(`http://localhost:8080/pedidos/${id}`, {
		method: "DELETE",
	});
	location.reload();
}

function listTemporadas(temporadaIds, temporadaListElement) {
	// 	<div class="pedidos">
	// 	<p class="title-pedidos">
	// 	  Pedidos
	// 	</p>

	// 	<select name="temporadas" class="select-temporada">
	// 	  <option value="Temporada1">Temporada 1</option>
	// 	  <option value="Temporada2">Temporada 2</option>
	// 	  <option value="Temporada3">Temporada 3</option>
	// 	  <option value="Temporada4">Temporada 4</option>
	// 	</select>

	//   </div>

	temporadaIds.forEach(temporadaId => {
		const temporadaElement = document.createElement("option");
		temporadaElement.value = temporadaId;
		temporadaElement.innerHTML = `Temporada ${temporadaId}`;
		temporadaListElement.appendChild(temporadaElement);
	});
}

function selectTemporadaAtiva(temporadaAtivaId, temporadaListElement) {
	temporadaListElement.value = temporadaAtivaId;
}

function mostrarPedidos(pedidos, htmlElement) {
	pedidos.forEach(pedido => {
		const pedidoHTML = montarHTMLPedido(pedido);
		htmlElement.append(pedidoHTML);
		console.log(pedidoHTML);
	}, []);
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

		if (field === "data" || field === "previsaoDeEntrega") {
			text = pedido[field] ? new Date(pedido[field]).toLocaleDateString() : "-";
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

function formatBool(text) {
	const div = document.createElement("div");
	const p = document.createElement("p");
	if (text === "Aguardando pagamento...") {
		div.classList.add("aguardando-pagamento");
		// text = "Aguardando pagamento";
	} else if (text === "Pago!") {
		div.classList.add("pago");
		// text = "Pago";
	} else if (text === "Não pago!") {
		div.classList.add("nao-pago");
		// text = "Não Pago";
	} else if (text === "Confirmado!" || text === "Parcialmente pago!") {
		div.classList.add("confirmado");
		// text = "Confirmado";
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
